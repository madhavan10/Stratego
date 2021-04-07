package stratego;

import java.awt.BorderLayout;
import java.awt.Container;
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
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		final String serverIP = JOptionPane.showInputDialog("Enter Server address");
		final Stratego frame = new Stratego(serverIP);
		frame.setVisible(true);
		frame.play();
	}
	
	protected void play() {
		
		while(in.hasNextLine()) {
			String response = in.nextLine();
			System.out.println(response);
			if(response.equals("CHOOSE_TEAM")) {
				
				messageLabel.setText("Choose Team:");
				final JButton orc = new JButton("ORC");
				final JButton human = new JButton("HUMAN");
				orc.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						out.println("PICK_TEAM orc");
						messagePanel.remove(orc);
						messagePanel.remove(human);
						messagePanel.repaint();
						messagePanel.revalidate();
					}
				});
				human.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						out.println("PICK_TEAM human");
						messagePanel.remove(human);
						messagePanel.remove(orc);
						messagePanel.repaint();
						messagePanel.revalidate();
					}
				});
				messagePanel.add(orc);
				messagePanel.add(human);
				messagePanel.repaint();
				messagePanel.revalidate();
				
				//System.out.println("end of choose team");
			} else if(response.startsWith("MESSAGE")) {
				
				
				messageLabel.setText(response);
				messagePanel.repaint();
				messagePanel.revalidate();
			} else if(response.startsWith("YOUR_TEAM")) {
				boolean team = Boolean.parseBoolean(response.substring(10));
				board.setTeam(team);
				String teamAsStr = team ? "HUMAN" : "ORC";
				setTitle("Stratego " + teamAsStr);
			} else if(response.startsWith("SETUP") && !response.equals("SETUP_TIME_OVER")) {
				int setupTime = Integer.parseInt(response.substring(6));
				board.setGameStarted(true);
				messageLabel.setText(setupTime + " minutes to setup");
				messagePanel.repaint();
				messagePanel.revalidate();
			} else if(response.equals("SETUP_TIME_OVER")) {
				board.setupTimeOver();
				if(board.getPlayerTeam() == Board.ORC) {
					messageLabel.setText("You start");
					board.setIsMyTurn(true);
				} else {
					messageLabel.setText("Opponent to play");
					board.setIsMyTurn(false);
				}
				
				messagePanel.repaint();
				messagePanel.revalidate();
			} else if(response.equals("MOVE_OK")) {
				if(!board.isSetupTime()) {
					board.setIsMyTurn(false);
					messageLabel.setText("Opponent's turn");
					messagePanel.repaint();
					messagePanel.revalidate();
				}
			} else if(response.startsWith("OPPONENT_MOVED")) {
				String moveStr = response.substring(15, 19);
				int x1 = Integer.parseInt(moveStr.substring(0, 1));
				int y1 = Integer.parseInt(moveStr.substring(1, 2));
				int x2 = Integer.parseInt(moveStr.substring(2, 3));
				int y2 = Integer.parseInt(moveStr.substring(3, 4));
				board.moveOpponentPiece(x1, y1, x2, y2);
				if(!board.isSetupTime()) {
					board.setIsMyTurn(true);
					messageLabel.setText("Your turn");
					messagePanel.repaint();
					messagePanel.revalidate();
				}
			} else if(response.equals("OTHER_PLAYER_LEFT")) {
				JOptionPane.showMessageDialog(this, "Other player left");
				break;
			} else if(response.equals("VICTORY")) {
				JOptionPane.showMessageDialog(this, "End");
				break;
			} else if(response.equals("DEFEAT")) {
				JOptionPane.showMessageDialog(this, "End");
				break;
			}
			//System.out.println("end of loop body");
		}
		//System.out.println("end of method body");
		
	}

	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	
	private JPanel messagePanel;
	private JLabel messageLabel;
	private JLabel eventLabel;
	private Board board;
	
	private ScorePanel scorePanel;
	
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
		setBounds(100, 100, 840, 768);
		
		scorePanel = new ScorePanel();
		
		messagePanel = new JPanel();
		messageLabel = new JLabel("...");
		messageLabel.setHorizontalAlignment(SwingConstants.LEFT);
		messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		
		eventLabel = new JLabel("...");
		eventLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		eventLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		board = new Board(out, eventLabel, scorePanel);
		
		Container contentPane = getContentPane();
		contentPane.add(messagePanel, BorderLayout.SOUTH);
		contentPane.add(board, BorderLayout.CENTER);
		contentPane.add(scorePanel, BorderLayout.WEST);
		messagePanel.add(messageLabel, BorderLayout.NORTH);
		messagePanel.add(eventLabel, BorderLayout.CENTER);
	}
	
}
