import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;

/**
 * Records what the game does in fixed scenarios. Every frame feeds a running
 * checksum, and every tenth frame is written out as a line. It reads
 * Board only through its public methods and private field names, none of which
 * change during the refactoring, so the same recorder runs on old and new code.
 *
 * Alien bombs are random, so they are switched off around each frame. The
 * separate bomb scenario places one bomb by hand.
 */
final class GameTrace {

	private final boolean withPixels;
	private final StringBuilder out = new StringBuilder();
	private final CRC32 running = new CRC32();

	GameTrace(boolean withPixels) {
		this.withPixels = withPixels;
	}

	String record() throws Exception {
		noInputInvasion();
		sweepAndShoot();
		aimedWin();
		bombHitsPlayer();
		return out.toString();
	}

	/** Nobody touches the keyboard, so the aliens reach the ground. */
	private void noInputInvasion() throws Exception {
		HeadlessBoard b = new HeadlessBoard();
		section("no-input-invasion");
		for (int f = 0; f < 15000 && inGame(b); f++) {
			step(b, f);
		}
		finish(b);
	}

	/** The player sweeps left and right and fires as often as possible. */
	private void sweepAndShoot() throws Exception {
		HeadlessBoard b = new HeadlessBoard();
		section("sweep-and-shoot");
		for (int f = 0; f < 6000 && inGame(b); f++) {
			if (f % 240 == 0) {
				key(b, KeyEvent.KEY_PRESSED, f % 480 == 0 ? KeyEvent.VK_LEFT : KeyEvent.VK_RIGHT);
			}
			key(b, KeyEvent.KEY_PRESSED, KeyEvent.VK_SPACE);
			step(b, f);
		}
		key(b, KeyEvent.KEY_RELEASED, KeyEvent.VK_LEFT);
		finish(b);
	}

	/** Every shot is placed on the next alien until the wave is gone. */
	private void aimedWin() throws Exception {
		HeadlessBoard b = new HeadlessBoard();
		section("aimed-win");
		for (int f = 0; f < 6000 && inGame(b); f++) {
			Sprite shot = (Sprite) field(b, "shot");
			if (!shot.isVisible()) {
				key(b, KeyEvent.KEY_PRESSED, KeyEvent.VK_SPACE);
				shot = (Sprite) field(b, "shot");
			}
			for (Alien a : aliens(b)) {
				if (a.isVisible() && !a.isDying()) {
					shot.setX(a.getX() + 2);
					shot.setY(a.getY() + 2);
					break;
				}
			}
			step(b, f);
		}
		finish(b);
	}

	/** One bomb is dropped directly on the player. */
	private void bombHitsPlayer() throws Exception {
		HeadlessBoard b = new HeadlessBoard();
		section("bomb-hits-player");
		Player player = (Player) field(b, "player");
		Bomb bomb = aliens(b).get(0).getBomb();
		bomb.setDestroyed(false);
		bomb.setX(player.getX() + 5);
		bomb.setY(player.getY() + 5);
		b.animationCycle();
		clearBombs(b);
		line("dying=" + player.isDying() + " bombDestroyed=" + bomb.isDestroyed() + pixels(b));
		b.paint(b.buffer.getGraphics());
		line("visible=" + player.isVisible() + " ingame=" + inGame(b) + " won=" + field(b, "havewon"));
		finish(b);
	}

	private void step(HeadlessBoard b, int frame) throws Exception {
		b.paint(b.buffer.getGraphics());
		String pixels = frame % 25 == 0 ? pixels(b) : "";
		disarmBombs(b);
		b.animationCycle();
		clearBombs(b);
		String state = state(b);
		running.update(state.getBytes());
		if (frame % 10 == 0) {
			line(frame + " " + state + " all=" + Long.toHexString(running.getValue()) + pixels);
		}
	}

	private void finish(HeadlessBoard b) throws Exception {
		line("end ingame=" + inGame(b) + " won=" + field(b, "havewon") + " deaths=" + field(b, "deaths")
				+ " message=" + field(b, "message"));
		if (!inGame(b)) {
			b.gameOver();
			line("end-screen" + pixels(b));
		}
	}

	private String state(HeadlessBoard b) throws Exception {
		Sprite player = (Sprite) field(b, "player");
		Sprite shot = (Sprite) field(b, "shot");
		int visible = 0;
		CRC32 crc = new CRC32();
		for (Alien a : aliens(b)) {
			crc.update((a.getX() + "," + a.getY() + "," + a.isVisible() + "," + a.isDying() + ";").getBytes());
			if (a.isVisible()) {
				visible++;
			}
		}
		return "p=" + player.getX() + "," + player.getY() + " s=" + shot.getX() + "," + shot.getY() + ","
				+ shot.isVisible() + " a=" + visible + "/" + Long.toHexString(crc.getValue()) + " d=" + field(b, "deaths");
	}

	private String pixels(HeadlessBoard b) {
		if (!withPixels) {
			return "";
		}
		BufferedImage img = b.buffer;
		CRC32 crc = new CRC32();
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				crc.update(img.getRGB(x, y));
			}
		}
		return " px=" + Long.toHexString(crc.getValue());
	}

	/**
	 * Board only drops a bomb that is currently destroyed, so marking them all
	 * live and parked off-screen for one cycle stops the random drop.
	 */
	private void disarmBombs(HeadlessBoard b) throws Exception {
		for (Alien a : aliens(b)) {
			Bomb bomb = a.getBomb();
			bomb.setDestroyed(false);
			bomb.setX(-1000);
			bomb.setY(0);
		}
	}

	private void clearBombs(HeadlessBoard b) throws Exception {
		for (Alien a : aliens(b)) {
			a.getBomb().setDestroyed(true);
		}
	}

	private void key(HeadlessBoard b, int id, int code) {
		KeyEvent e = new KeyEvent(b, id, 0L, 0, code, KeyEvent.CHAR_UNDEFINED);
		if (id == KeyEvent.KEY_PRESSED) {
			b.getKeyListeners()[0].keyPressed(e);
		} else {
			b.getKeyListeners()[0].keyReleased(e);
		}
	}

	@SuppressWarnings("unchecked")
	private List<Alien> aliens(Board b) throws Exception {
		List<Alien> all = new ArrayList<>();
		for (Object a : (Iterable<Object>) field(b, "aliens")) {
			all.add((Alien) a);
		}
		return all;
	}

	private boolean inGame(Board b) throws Exception {
		return (Boolean) field(b, "ingame");
	}

	private Object field(Board b, String name) throws Exception {
		Field f = Board.class.getDeclaredField(name);
		f.setAccessible(true);
		return f.get(b);
	}

	private void section(String name) {
		out.append("# ").append(name).append('\n');
	}

	private void line(String text) {
		out.append(text).append('\n');
	}
}
