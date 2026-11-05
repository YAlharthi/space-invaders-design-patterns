import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * A Board that never starts its own game loop, so a test can step it one
 * frame at a time. Anything it would draw on screen lands in a buffer instead.
 */
class HeadlessBoard extends Board {

	private static final long serialVersionUID = 1L;

	final BufferedImage buffer = new BufferedImage(BOARD_WIDTH, BOARD_HEIGTH, BufferedImage.TYPE_INT_RGB);

	@Override
	public void run() {
		// The test drives animationCycle() itself.
	}

	@Override
	public Graphics getGraphics() {
		return buffer.getGraphics();
	}
}
