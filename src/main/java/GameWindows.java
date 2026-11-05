import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Facade: one small set of calls for everything the menus need. Callers ask for
 * a screen and never touch frames, labels, fonts or layouts.
 */
public class GameWindows implements Commons {

	private static final String TOP_MESSAGE = "Space Invaders <br> Java Version";
	private static final String INITIAL_MESSAGE = "Ajude-nos, capitão impressionante!!"
			+ "<br>Os alienígenas estão tentando invadir nosso planeta."
			+ "<br><br><br>Sua missão:"
			+ "<br><br>Matar todos os alienígenas invasores antes que eles consigam invadir o planeta Terra."
			+ "<br>E, de preferência, não morra durante a batalha!"
			+ "<br><br><br>BOA SORTE!!!";
	private static final String HELP_TOP_MESSAGE = "Ajuda";
	private static final String HELP_MESSAGE = "Controles: "
			+ "<br><br>Movimento à Esquerda: <br>Seta Esquerda do teclado"
			+ "<br><br>Movimento à Direita: <br>Seta Direita do teclado"
			+ "<br><br>Atirar: <br>Barra de espaço";

	private final JFrame gameFrame = new JFrame("Space Invaders");
	private final JFrame introFrame = new JFrame("Space Invaders");
	private final JFrame helpFrame = new JFrame("Ajuda");

	public void showIntro(ActionListener onHelp, ActionListener onStart) {
		JButton start = new JButton("Iniciar Missão");
		start.addActionListener(onStart);
		start.setBounds(800, 800, 200, 100);

		JButton help = new JButton("Ajuda");
		help.addActionListener(onHelp);

		addTexts(introFrame, "<html><br><br>" + TOP_MESSAGE + "</html>", "<html>" + INITIAL_MESSAGE + "</html>");

		JPanel buttons = new JPanel();
		buttons.add(help);
		buttons.add(start);

		introFrame.add(buttons, BorderLayout.PAGE_END);
		introFrame.setSize(500, 500);
		introFrame.setLocationRelativeTo(null);
		introFrame.setVisible(true);
		introFrame.setResizable(false);
	}

	public void showHelp(ActionListener onClose) {
		JButton close = new JButton("Fechar");
		close.addActionListener(onClose);

		addTexts(helpFrame, "<html><br>" + HELP_TOP_MESSAGE + "</html>", "<html>" + HELP_MESSAGE + "</html> ");

		helpFrame.add(close, BorderLayout.PAGE_END);
		helpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		helpFrame.setSize(250, 290);
		helpFrame.setResizable(false);
		helpFrame.setLocationRelativeTo(null);
		helpFrame.setVisible(true);
	}

	public void closeHelp() {
		helpFrame.dispose();
	}

	/** Swaps the menus for the game board. */
	public void showGame() {
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setSize(BOARD_WIDTH, BOARD_HEIGTH);
		gameFrame.getContentPane().add(new Board());
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true);
		introFrame.dispose();
		helpFrame.dispose();
	}

	private void addTexts(JFrame frame, String title, String body) {
		frame.add(label(body, 12));
		frame.add(label(title, 20), BorderLayout.PAGE_START);
	}

	private JLabel label(String text, int size) {
		JLabel label = new JLabel(text, SwingConstants.CENTER);
		label.setFont(new Font("Helvetica", Font.BOLD, size));
		return label;
	}
}
