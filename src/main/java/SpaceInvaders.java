import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author 
*/
public class SpaceInvaders {

	private final GameWindows windows = new GameWindows();

	/*
	 * Constructor
	 */
	public SpaceInvaders() {
		windows.showIntro(new HelpButton(), new ButtonListener());
	}

	/*
	 * Main
	 */
	public static void main(String[] args) {
		new SpaceInvaders();
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			windows.showGame();
		}
	}

	private class CloseHelp implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			windows.closeHelp();
		}
	}

	private class HelpButton implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			windows.showHelp(new CloseHelp());
		}
	}
}
