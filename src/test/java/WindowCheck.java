import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Walks through the menu windows the way a player does (open the game, open
 * help, close help, start the mission) and describes every visible window at
 * each step. The description is compared with the one recorded from the code
 * before any refactoring. This check needs a real display.
 */
public class WindowCheck {

	private static final Path BASELINE = Paths.get("src/test/resources/baseline-windows.txt");

	public static void main(String[] args) throws Exception {
		if (GraphicsEnvironment.isHeadless()) {
			System.out.println("SKIPPED: WindowCheck needs a display");
			return;
		}
		StringBuilder out = new StringBuilder();
		SwingUtilities.invokeAndWait(() -> SpaceInvaders.main(new String[0]));
		describe(out, "intro");
		press("Ajuda");
		describe(out, "help-open");
		press("Fechar");
		describe(out, "help-closed");
		press("Iniciar Missão");
		describe(out, "game-started");

		String text = out.toString();
		if (args.length > 0 && args[0].equals("record")) {
			Files.write(BASELINE, text.getBytes(StandardCharsets.UTF_8));
			System.out.println("Baseline written");
		} else {
			String expected = new String(Files.readAllBytes(BASELINE), StandardCharsets.UTF_8);
			if (!expected.equals(text)) {
				System.out.println("FAIL: windows differ from the baseline\n" + text);
				System.exit(1);
			}
			System.out.println("PASS: menu windows match the baseline");
		}
		System.exit(0);
	}

	private static void press(String label) throws Exception {
		SwingUtilities.invokeAndWait(() -> {
			for (Frame f : Frame.getFrames()) {
				AbstractButton b = f.isVisible() ? find(f, label) : null;
				if (b != null) {
					b.doClick();
					return;
				}
			}
			throw new IllegalStateException("No visible button: " + label);
		});
	}

	private static AbstractButton find(Container c, String label) {
		for (Component child : c.getComponents()) {
			if (child instanceof AbstractButton && label.equals(((AbstractButton) child).getText())) {
				return (AbstractButton) child;
			}
			if (child instanceof Container) {
				AbstractButton inner = find((Container) child, label);
				if (inner != null) {
					return inner;
				}
			}
		}
		return null;
	}

	private static void describe(StringBuilder out, String step) throws Exception {
		SwingUtilities.invokeAndWait(() -> {
			out.append("# ").append(step).append('\n');
			List<String> windows = new ArrayList<>();
			for (Frame f : Frame.getFrames()) {
				if (f.isVisible()) {
					StringBuilder w = new StringBuilder();
					w.append("window title=").append(f.getTitle()).append(" size=").append(f.getWidth()).append('x')
							.append(f.getHeight()).append(" resizable=").append(f.isResizable());
					if (f instanceof JFrame) {
						w.append(" close=").append(((JFrame) f).getDefaultCloseOperation());
					}
					w.append('\n');
					tree(w, f, 1);
					windows.add(w.toString());
				}
			}
			Collections.sort(windows);
			windows.forEach(out::append);
		});
	}

	private static void tree(StringBuilder out, Container c, int depth) {
		for (Component child : c.getComponents()) {
			String type = child.getClass().getSimpleName();
			if (child instanceof JLabel || child instanceof AbstractButton || child instanceof Board) {
				String text = child instanceof JLabel ? ((JLabel) child).getText()
						: child instanceof AbstractButton ? ((AbstractButton) child).getText() : "";
				out.append("  ".repeat(depth)).append(type).append(" text=").append(text);
				if (child instanceof JLabel) {
					// Button fonts come from the platform; the game sets label fonts itself.
					Font font = child.getFont();
					out.append(" font=").append(font.getName()).append('/').append(font.getStyle()).append('/')
							.append(font.getSize());
				}
				out.append('\n');
			}
			if (child instanceof Container && !(child instanceof Board)) {
				tree(out, (Container) child, depth + 1);
			}
		}
	}
}
