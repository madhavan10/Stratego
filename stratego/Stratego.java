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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.PrintWriter;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class Stratego extends JFrame {

	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			System.err.println("Pass the IP of the Server as the sole command-line argument");
			return;
		}
		final String serverIP = args[0];
		final Stratego frame = new Stratego(serverIP);
		frame.setVisible(true);
		frame.play();
	}
	
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	
	private JPanel auxPanel;
	private Board board;
	
	private JPanel spPanel;
	private JPanel messagePanel;
	private JLabel messageLabel;
	private JLabel eventLabel;
	
	
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
		setSize(768, 768);
		
		messagePanel = new JPanel();
		messageLabel = new JLabel("...");
		messageLabel.setHorizontalAlignment(SwingConstants.LEFT);
		messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		
		eventLabel = new JLabel("...");
		eventLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		eventLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		spPanel = new JPanel();
		
		auxPanel = new JPanel();
		board = new Board(out, spPanel, eventLabel);
		
		Container contentPane = getContentPane();
		contentPane.add(board, BorderLayout.CENTER);
		contentPane.add(auxPanel, BorderLayout.SOUTH);
		
		auxPanel.setLayout(new BorderLayout());
		auxPanel.add(spPanel, BorderLayout.NORTH);
		auxPanel.add(messagePanel, BorderLayout.SOUTH);
		messagePanel.add(messageLabel);
		messagePanel.add(eventLabel);
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
			} else if(response.startsWith("OPPONENT_DWARVEN_AXE")) {
				Scanner scan = new Scanner(response.substring(21));
				String daMove = scan.next();
				int x1 = Integer.parseInt(daMove.substring(0, 1));
				int y1 = Integer.parseInt(daMove.substring(1, 2));
				int x2 = Integer.parseInt(daMove.substring(2, 3));
				int y2 = Integer.parseInt(daMove.substring(3, 4));
				int i = 0;
				Square[] targets = new Square[3];
				while(scan.hasNext()) {
					String targetStr = scan.next();
					int x = Integer.parseInt(targetStr.substring(0, 1));
					int y = Integer.parseInt(targetStr.substring(1, 2));
					targets[i] = board.getBoard()[x][y];
					i++;
				}
				board.opponentDwarvenAxe(x1, y1, x2, y2, targets);
				board.setIsMyTurn(true);
				messageLabel.setText("Your turn");
				messagePanel.repaint();
				messagePanel.revalidate();
				
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
		}	
	}
	
}
