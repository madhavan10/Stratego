package stratego;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
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
	public static void main(String[] args) throws IOException {
		final String serverIP;
		boolean handicap = false;
		if(args.length >= 1) {
			serverIP = args[0];
			if(args.length >= 2 && args[1].equals("1")) {
				handicap = true;
			}
		}
		else serverIP = JOptionPane.showInputDialog("Enter server address");
		final Stratego frame = new Stratego(serverIP, handicap);
		frame.setVisible(true);
		frame.play();
	}
	private final boolean handicap;

	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	
	private JPanel auxPanel;
	private Board board;
	
	private JPanel spPanel;
	private JPanel messagePanel;
	private JLabel messageLabel;
	private JLabel eventLabel;
	private JFileChooser setupChooserSaver;
	private JButton loadSetupButton, saveSetupButton;
	private JButton readyButton;
	
	/**
	 * Create the frame.
	 * @param serverIP 
	 */
	public Stratego(String serverIP, boolean handicap) throws IOException {
		this.handicap = handicap;
		socket = new Socket(serverIP, 58901);
		in = new Scanner(socket.getInputStream());
		out = new PrintWriter(socket.getOutputStream(), true);
		initUI();
	}
	
	private void initUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Stratego");
		setSize(768, 768);
		
		messageLabel = new JLabel("...");
		messageLabel.setHorizontalAlignment(SwingConstants.LEFT);
		messageLabel.setFont(messageLabel.getFont().deriveFont(Font.PLAIN, 16));
		
		eventLabel = new JLabel("...");
		eventLabel.setFont(eventLabel.getFont().deriveFont(Font.PLAIN, 16));
		eventLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		setupChooserSaver = new JFileChooser();
		
		loadSetupButton = new JButton("Load Setup");
		loadSetupButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {	
				if(setupChooserSaver.showOpenDialog(Stratego.this) == JFileChooser.APPROVE_OPTION) {
					File setupFile = setupChooserSaver.getSelectedFile();
					try(Scanner setupFileReader = new Scanner(setupFile)) {
						while(setupFileReader.hasNextLine()) {
							String fullCommand = setupFileReader.nextLine();
							if(!fullCommand.startsWith("" + board.getPlayerTeam())) {
								throw new InvalidSetupFileException("Team mismatch");
							} else {
								int tmpIndex = fullCommand.indexOf(":MOVE ");
								String coordinates = fullCommand.substring(tmpIndex + 6, tmpIndex + 10);
								int x1 = Integer.parseInt(coordinates.substring(0, 1));
								int y1 = Integer.parseInt(coordinates.substring(1, 2));
								int x2 = Integer.parseInt(coordinates.substring(2, 3));
								int y2 = Integer.parseInt(coordinates.substring(3, 4));
								if(board.isSetupTime())
									board.swapPieces(board.getBoard()[x1][y1], board.getBoard()[x2][y2]);
							}
						}
					} catch (FileNotFoundException | InvalidSetupFileException ex) {
						ex.printStackTrace();
					}
					
				}
			}
		});
		saveSetupButton = new JButton("Save Setup");
		saveSetupButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if(setupChooserSaver.showSaveDialog(Stratego.this) == JFileChooser.APPROVE_OPTION) {
					File saveFile = setupChooserSaver.getSelectedFile();
					try(PrintWriter setupWriter = new PrintWriter(saveFile)){
						setupWriter.print(board.getCurrentSetupString());
						setupWriter.flush();
						setupWriter.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		readyButton = new JButton("Ready");
		readyButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				out.println("READY");
				messagePanel.remove(readyButton);
				messagePanel.repaint();
				messagePanel.revalidate();
			}
		});
		
		messagePanel = new JPanel();
		messagePanel.add(messageLabel);
		messagePanel.add(eventLabel);
		
		spPanel = new JPanel();
		
		auxPanel = new JPanel();
		auxPanel.setLayout(new BorderLayout());
		auxPanel.add(spPanel, BorderLayout.NORTH);
		auxPanel.add(messagePanel, BorderLayout.SOUTH);
		
		board = new Board(out, spPanel, eventLabel, handicap);
		
		Container contentPane = getContentPane();
		contentPane.add(board, BorderLayout.CENTER);
		contentPane.add(auxPanel, BorderLayout.SOUTH);
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
				updateMessageLabel(response);
			} else if(response.startsWith("YOUR_TEAM")) {
				boolean team = Boolean.parseBoolean(response.substring(10));
				board.setTeam(team);
				String teamAsStr = team ? "HUMAN" : "ORC";
				setTitle("Stratego " + teamAsStr);
			} else if(response.startsWith("SETUP") && !response.equals("SETUP_TIME_OVER")) {
				int setupTime = Integer.parseInt(response.substring(6));
				board.setGameStarted(true);
				updateMessageLabel(setupTime + " minutes to setup");
				messagePanel.add(loadSetupButton);
				messagePanel.add(saveSetupButton);
				messagePanel.add(readyButton);
				messagePanel.repaint();
				messagePanel.revalidate();
			} else if(response.equals("SETUP_TIME_OVER")) {
				board.setupTimeOver();
				if(board.getPlayerTeam() == Board.ORC) {
					updateMessageLabel("You start");
					board.setIsMyTurn(true);
				} else {
					updateMessageLabel("Opponent to play");
					board.setIsMyTurn(false);
				}
				messagePanel.remove(loadSetupButton);
				messagePanel.remove(saveSetupButton); messagePanel.remove(readyButton);
				messagePanel.repaint();
				messagePanel.revalidate();
			} else if(response.equals("OK")) {
				updateMessageLabel("...");
			} else if(response.startsWith("OPPONENT_REPEAT_ATTACK")) {
				String moveStr = response.substring(23, 27);
				int x1 = Integer.parseInt(moveStr.substring(0, 1));
				int y1 = Integer.parseInt(moveStr.substring(1, 2));
				int x2 = Integer.parseInt(moveStr.substring(2, 3));
				int y2 = Integer.parseInt(moveStr.substring(3, 4));
				board.moveOpponentPiece(x1, y1, x2, y2);
			} else if(response.equals("OPPONENT_STOP_REPEAT_ATTACK")) {
				board.setIsMyTurn(true);
				updateMessageLabel("Your turn | ");
			} else if(response.equals("MOVE_OK")) {
				if(!board.isSetupTime()) {
					board.setIsMyTurn(false);
					updateMessageLabel("Opponent's turn | ");
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
					updateMessageLabel("Your turn | ");
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
				scan.close();
				board.opponentDwarvenAxe(x1, y1, x2, y2, targets);
				board.setIsMyTurn(true);
				updateMessageLabel("Your turn | ");;		
			} else if(response.startsWith("OPPONENT_RAMPAGE")) {
				String moveStr = response.substring(17, 21);
				int x1 = Integer.parseInt(moveStr.substring(0, 1));
				int y1 = Integer.parseInt(moveStr.substring(1, 2));
				int x2 = Integer.parseInt(moveStr.substring(2, 3));
				int y2 = Integer.parseInt(moveStr.substring(3, 4));
				board.opponentRampage(x1, y1, x2, y2);
				board.setIsMyTurn(true);
				updateMessageLabel("Your turn | ");
			} else if(response.startsWith("OPPONENT_FLIGHT")) {
				String moveStr = response.substring(16, 20);
				int x1 = Integer.parseInt(moveStr.substring(0, 1));
				int y1 = Integer.parseInt(moveStr.substring(1, 2));
				int x2 = Integer.parseInt(moveStr.substring(2, 3));
				int y2 = Integer.parseInt(moveStr.substring(3, 4));
				board.opponentFlight(x1, y1, x2, y2);
				board.setIsMyTurn(true);
				updateMessageLabel("Your turn | ");
			} else if(response.startsWith("OPPONENT_SWIFT_STEED")) {
				String moveStr = response.substring(21, 25);
				int x1 = Integer.parseInt(moveStr.substring(0, 1));
				int y1 = Integer.parseInt(moveStr.substring(1, 2));
				int x2 = Integer.parseInt(moveStr.substring(2, 3));
				int y2 = Integer.parseInt(moveStr.substring(3, 4));
				board.opponentSwiftSteed(x1, y1, x2, y2);
				board.setIsMyTurn(true);
				updateMessageLabel("Your turn | ");
			} else if(response.startsWith("OPPONENT_DETECT")) {
				String moveStr = response.substring(16, 20);
				int x1 = Integer.parseInt(moveStr.substring(0, 1));
				int y1 = Integer.parseInt(moveStr.substring(1, 2));
				int x2 = Integer.parseInt(moveStr.substring(2, 3));
				int y2 = Integer.parseInt(moveStr.substring(3, 4));
				board.opponentDetect(x1, y1, x2, y2);
				board.setIsMyTurn(true);
				updateMessageLabel("Your turn | ");
			} else if(response.startsWith("OPPONENT_LONGBOW")) {
				String moveStr = response.substring(17, 21);
				int x1 = Integer.parseInt(moveStr.substring(0, 1));
				int y1 = Integer.parseInt(moveStr.substring(1, 2));
				int x2 = Integer.parseInt(moveStr.substring(2, 3));
				int y2 = Integer.parseInt(moveStr.substring(3, 4));
				board.opponentLongbow(x1, y1, x2, y2);
				board.setIsMyTurn(true);
				updateMessageLabel("Your turn | ");
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
	
	private void updateMessageLabel(String message) {
		messageLabel.setText(message);
		messagePanel.repaint();
		messagePanel.revalidate();
	}
	
}
