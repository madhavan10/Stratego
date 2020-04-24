package stratego;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class Stratego extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("Pass the IP of the Server as the sole command-line argument");
			return;
		}
		final String serverIP = args[0];
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stratego frame = new Stratego(serverIP);
					frame.setVisible(true);
					frame.play();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	protected void play() {
		
		while(in.hasNextLine() ) {
			String response = in.nextLine();
			if(response.equals("CHOOSE_TEAM")) {
				messageLabel.setText("Choose Team:");
				final JButton orc = new JButton("ORC");
				final JButton human = new JButton("HUMAN");
				orc.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						out.println("PICK_TEAM orc");
						messagePanel.remove(orc);
						messagePanel.remove(human);
						messageLabel.setText("...");
						messagePanel.repaint();
						messagePanel.revalidate();
					}
				});
				human.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						out.println("PICK_TEAM human");
						messagePanel.remove(human);
						messagePanel.remove(orc);
						messageLabel.setText("...");
						messagePanel.repaint();
						messagePanel.revalidate();
					}
				});
				messagePanel.add(orc);
				messagePanel.add(human);
				messagePanel.repaint();
				messagePanel.revalidate();
			} else if(response.startsWith("YOUR_TEAM")) {
				boolean team = Boolean.parseBoolean(response.substring(10));
				board.setTeam(team);
				String teamAsStr = team ? "HUMAN" : "ORC";
				setTitle("Stratego " + teamAsStr);
			} else if(response.startsWith("SETUP")) {
				int setupTime = Integer.parseInt(response.substring(6));
				messageLabel.setText(setupTime + " minutes to setup");
				messagePanel.repaint();
				messagePanel.revalidate();
			} else if(response.equals("SETUP_TIME_OVER")) {
				board.setupTimeOver();
				if(board.getPlayerTeam() == Board.ORC)
					messageLabel.setText("You start");
				else
					messageLabel.setText("Opponent to play");
				messagePanel.repaint();
				messagePanel.revalidate();
			} else if(response.equals("MOVE_OK")) {
				messageLabel.setText("Opponent's turn");
				messagePanel.repaint();
				messagePanel.revalidate();
			} else if(response.startsWith("OTHER_PLAYER_MOVED")) {
				String moveStr = response.substring(18, 22);
				int x1 = Integer.parseInt(moveStr.substring(18, 19));
				int y1 = Integer.parseInt(moveStr.substring(19, 20));
				int x2 = Integer.parseInt(moveStr.substring(20, 21));
				int y2 = Integer.parseInt(moveStr.substring(21, 22));
				board.moveOpponentPiece(x1, y1, x2, y2);
				messageLabel.setText("Your turn");
				messagePanel.repaint();
				messagePanel.revalidate();
			} else if(response.equals("OTHER_PLAYER_LEFT")) {
				JOptionPane.showMessageDialog(this, "Other player left");
				break;
			}
		}
		
		
	}

	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	
	private JPanel messagePanel;
	private JLabel messageLabel;
	private Board board;
	
	/**
	 * Create the frame.
	 * @param serverIP 
	 */
	public Stratego(String serverIP) throws IOException {
		socket = new Socket(serverIP, 58901);
		in = new Scanner(socket.getInputStream());
		out = new PrintWriter(socket.getOutputStream(), true);
		initUI();
	}
	
	private void initUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Stratego");
		setBounds(100, 100, 768, 768);
		
		messagePanel = new JPanel();
		messageLabel = new JLabel("...");
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		board = new Board(out, messageLabel);
		
		Container contentPane = getContentPane();
		contentPane.add(messagePanel, BorderLayout.SOUTH);
		contentPane.add(board, BorderLayout.CENTER);
		messagePanel.add(messageLabel);
	}

}
