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
	public static void main(String[] args) throws IOException, InterruptedException {
		if(args.length != 1) {
			System.err.println("Pass the IP of the Server as the sole command-line argument");
			return;
		}
		final String serverIP = args[0];
		final Stratego frame = new Stratego(serverIP);
		frame.initUI();
		frame.setVisible(true);
		frame.play();
		/*
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.initUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
	}
	
	protected void play() {
		
		while(in.hasNextLine()) {
			//System.out.println("head of loop");
			String response = in.nextLine();
			if(response.equals("CHOOSE_TEAM")) {
				
				//System.out.println("Choose team");
				
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
				
				//System.out.println("end of choose team");
			} else if(response.startsWith("MESSAGE")) {
				
				System.out.println(response);
				
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
	
	/**
	 * Create the frame.
	 * @param serverIP 
	 */
	public Stratego(String serverIP) throws IOException {
		socket = new Socket(serverIP, 58901);
		in = new Scanner(socket.getInputStream());
		out = new PrintWriter(socket.getOutputStream(), true);
	}
	
	public void initUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Stratego");
		setBounds(100, 100, 768, 768);
		
		messagePanel = new JPanel();
		messageLabel = new JLabel("...");
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		
		eventLabel = new JLabel("...");
		eventLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		eventLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		board = new Board();
		
		Container contentPane = getContentPane();
		contentPane.add(messagePanel, BorderLayout.SOUTH);
		contentPane.add(board, BorderLayout.CENTER);
		messagePanel.add(messageLabel, BorderLayout.NORTH);
		messagePanel.add(eventLabel, BorderLayout.SOUTH);
	}
	
	


	@SuppressWarnings("serial")
	class Board extends JPanel {
		
		private final Square[][] board = new Square[BOARD_DIM][BOARD_DIM];
		private final Piece[] whitePieces = new Piece[NO_OF_PIECES];
		private final Piece[] blackPieces = new Piece[NO_OF_PIECES];
		
		private boolean isSetupTime;
		private boolean playerTeam;
		
		private Square selected;
		private Square target;
		private boolean isMyTurn;
		
		public Board() {
			setLayout(new GridLayout(BOARD_DIM, BOARD_DIM));
			createSquares();
			createPieces();
			setPieces();
			isSetupTime = true;
			selected = null;
		}
		
		public void hideEnemyPieces() {
			for(Piece piece: whitePieces) {
				if(piece.getTeam() != playerTeam) {
					piece.setVisible(false);
				}
			}
			for(Piece piece: blackPieces) {
				if(piece.getTeam() != playerTeam) {
					piece.setVisible(false);
				}
			}
			repaint();
			revalidate();
		}

		public void setIsMyTurn(boolean b) {
			isMyTurn = b;
		}
		
		public boolean isSetupTime() {
			return isSetupTime;
		}
		
		public boolean getPlayerTeam() {
			return playerTeam;
		}
		
		public Square[][] getBoard() {
			return board;
		}
		
		public void setTeam(boolean team) {
			playerTeam = team;
			
			for (Piece piece : whitePieces)
				if(piece.getTeam() != playerTeam) 
					piece.setVisible(false);
			
			for (Piece piece : blackPieces) 
				if(piece.getTeam() != playerTeam)
					piece.setVisible(false);
			
			repaint();
			revalidate();
		}
		
		public void setupTimeOver() {
			isSetupTime = false;
		}
		
		public String toString() {
			String result = "";
			for(int j = 0; j < BOARD_DIM; j++) {
				for(int i = 0; i < BOARD_DIM; i++) {
					result += board[i][j];
					result += "\t";
				}
				result += "\n";
			}
			return result;
		}
		
		private void createSquares() {
			
			boolean forbidden = true;
			
			for(int j = 0; j < BOARD_DIM; j++) {
				for(int i = 0; i < BOARD_DIM; i++) {
					Square square;
					if((j == 4 || j == 5) && (i == 2 || i == 3 || i == 6 || i == 7)) {
						board[i][j] = new Square(i, j, forbidden);
						square = board[i][j];
						square.setBackground(Color.blue);
						add(square);
					}			
					else {
						board[i][j] = new Square(i, j, !forbidden);
						square = board[i][j];
						square.setBorder(BorderFactory.createLineBorder(Color.black));
					}
					square.addMouseMotionListener(new SquareMotionListener());
					square.addMouseListener(new SquareMouseListener());
					add(square);
				}
			}
		} //end method
		
		private void createPieces() {
			int i = 0;
			whitePieces[i] = new Piece(10, "Gandalf", HUMAN); i++;
			whitePieces[i] = new Piece(9, "Aragorn", HUMAN); i++;
			whitePieces[i] = new Piece(8, "Legolas", HUMAN); i++;
			whitePieces[i] = new Piece(8, "Gimli", HUMAN); i++;
			whitePieces[i] = new Piece(7, "Faramir", HUMAN); i++;		
			whitePieces[i] = new Piece(7, "Theoden", HUMAN); i++;		
			whitePieces[i] = new Piece(7, "Eomer", HUMAN); i++;
			whitePieces[i] = new Piece(6, "Haldir", HUMAN); i++;		
			whitePieces[i] = new Piece(6, "Arwen", HUMAN); i++;		
			whitePieces[i] = new Piece(6, "Treebeard", HUMAN); i++;		
			whitePieces[i] = new Piece(6, "Boromir", HUMAN); i++;		
			whitePieces[i] = new Piece(5, "Elf-1", HUMAN); i++;		
			whitePieces[i] = new Piece(5, "Elf-2", HUMAN); i++;		
			whitePieces[i] = new Piece(5, "Elf-3", HUMAN); i++;		
			whitePieces[i] = new Piece(5, "Elf-4", HUMAN); i++;		
			whitePieces[i] = new Piece(4, "Merry", HUMAN); i++;		
			whitePieces[i] = new Piece(4, "Pippin", HUMAN); i++;		
			whitePieces[i] = new Piece(4, "Frodo", HUMAN); i++;		
			whitePieces[i] = new Piece(4, "Sam", HUMAN); i++;
			whitePieces[i] = new Piece(3, "Soldier-1", HUMAN); i++;		
			whitePieces[i] = new Piece(3, "Soldier-2", HUMAN); i++;		
			whitePieces[i] = new Piece(3, "Soldier-3", HUMAN); i++;		
			whitePieces[i] = new Piece(3, "Soldier-4", HUMAN); i++;		
			whitePieces[i] = new Piece(3, "Soldier-5", HUMAN); i++;		
			whitePieces[i] = new Piece(2, "Rider-1", HUMAN); i++;		
			whitePieces[i] = new Piece(2, "Rider-2", HUMAN); i++;		
			whitePieces[i] = new Piece(2, "Rider-3", HUMAN); i++;		
			whitePieces[i] = new Piece(2, "Rider-4", HUMAN); i++;		
			whitePieces[i] = new Piece(2, "Rider-5", HUMAN); i++;		
			whitePieces[i] = new Piece(2, "Rider-6", HUMAN); i++;		
			whitePieces[i] = new Piece(2, "Rider-7", HUMAN); i++;		
			whitePieces[i] = new Piece(2, "Rider-8", HUMAN); i++;		
			whitePieces[i] = new Piece(Piece.SPY, "Eowyn", HUMAN); i++;		
			whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-1", HUMAN); i++;		
			whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-2", HUMAN); i++;		
			whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-3", HUMAN); i++;		
			whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-4", HUMAN); i++;		
			whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-5", HUMAN); i++;		
			whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-6", HUMAN); i++;
			whitePieces[i] = new Piece(Piece.FLAG, "Flag", HUMAN); i++;
			
			int j = 0;
			blackPieces[j] = new Piece(10, "Witch King", ORC); j++;
			blackPieces[j] = new Piece(9, "Saruman", ORC); j++;
			blackPieces[j] = new Piece(8, "Ringwraith-1", ORC); j++;
			blackPieces[j] = new Piece(8, "Ringwraith-2", ORC); j++;
			blackPieces[j] = new Piece(7, "Gothmog", ORC); j++;		
			blackPieces[j] = new Piece(7, "Sharku", ORC); j++;
			blackPieces[j] = new Piece(7, "Lurtz", ORC); j++;
			blackPieces[j] = new Piece(6, "Uruk-1", ORC); j++;
			blackPieces[j] = new Piece(6, "Uruk-2", ORC); j++;
			blackPieces[j] = new Piece(6, "Uruk-3", ORC); j++;
			blackPieces[j] = new Piece(6, "Uruk-4", ORC); j++;
			blackPieces[j] = new Piece(5, "Beserker-1", ORC); j++;
			blackPieces[j] = new Piece(5, "Beserker-2", ORC); j++;
			blackPieces[j] = new Piece(5, "Beserker-3", ORC); j++;
			blackPieces[j] = new Piece(5, "Beserker-4", ORC); j++;
			blackPieces[j] = new Piece(4, "Haradrim-1", ORC); j++;
			blackPieces[j] = new Piece(4, "Haradrim-2", ORC); j++;
			blackPieces[j] = new Piece(4, "Haradrim-3", ORC); j++;
			blackPieces[j] = new Piece(4, "Haradrim-4", ORC); j++;
			blackPieces[j] = new Piece(3, "Orc-1", ORC); j++;
			blackPieces[j] = new Piece(3, "Orc-2", ORC); j++;
			blackPieces[j] = new Piece(3, "Orc-3", ORC); j++;
			blackPieces[j] = new Piece(3, "Orc-4", ORC); j++;
			blackPieces[j] = new Piece(3, "Orc-5", ORC); j++;
			blackPieces[j] = new Piece(2, "Warg-1", ORC); j++;
			blackPieces[j] = new Piece(2, "Warg-2", ORC); j++;
			blackPieces[j] = new Piece(2, "Warg-3", ORC); j++;
			blackPieces[j] = new Piece(2, "Warg-4", ORC); j++;
			blackPieces[j] = new Piece(2, "Warg-5", ORC); j++;
			blackPieces[j] = new Piece(2, "Warg-6", ORC); j++;
			blackPieces[j] = new Piece(2, "Warg-7", ORC); j++;
			blackPieces[j] = new Piece(2, "Warg-8", ORC); j++;
			blackPieces[j] = new Piece(Piece.SPY, "Grima", ORC); j++;
			blackPieces[j] = new Piece(Piece.STRONGHOLD, "Stronghold-1", ORC); j++;
			blackPieces[j] = new Piece(Piece.STRONGHOLD, "Stronghold-2", ORC); j++;
			blackPieces[j] = new Piece(Piece.STRONGHOLD, "Stronghold-3", ORC); j++;
			blackPieces[j] = new Piece(Piece.STRONGHOLD, "Stronghold-4", ORC); j++;
			blackPieces[j] = new Piece(Piece.STRONGHOLD, "Stronghold-5", ORC); j++;
			blackPieces[j] = new Piece(Piece.STRONGHOLD, "Stronghold-6", ORC); j++;
			blackPieces[j] = new Piece(Piece.FLAG, "Flag", ORC); j++;
		}
		
		private void setPieces() {
			
			//white pieces
			int count = 0;
			for(int j = 0; j < 4; j++) {
				for(int i = 0; i < 10; i++) {
					Piece p = whitePieces[count];
					//p.setPos(i, j);
					board[i][j].setOccupant(p);
					board[i][j].setOccupied(true);
					board[i][j].add(p, BorderLayout.CENTER);
					count++;
				}
			}
			
			//black pieces
			count = 0;
			for(int j = 9; j >= 6; j--) {
				for(int i = 0; i < 10; i++) {
					Piece p = blackPieces[count];
					//blackPieces[count].setPos(i, j);
					board[i][j].setOccupant(p);
					board[i][j].setOccupied(true);
					board[i][j].add(p, BorderLayout.CENTER);
					count++;
				}
			}
			shadeOccupiedSquares();
		}
		
		private void shadeOccupiedSquares() {
			for(int j = 0; j <= 9; j++) {
				for(int i = 0; i <= 9; i++) {
					if(board[i][j].isOccupied())
						board[i][j].setBackground(Color.darkGray);
					else if(!board[i][j].isForbidden())
						board[i][j].setBackground(Color.gray);
					else
						board[i][j].setBackground(Color.blue);
				}
			}
			repaint();
			revalidate();
		}

		private void swapPieces(Square square1, Square square2) {
			//System.out.println("Swapping pieces!");
			Piece tmp = square1.getOccupant();
			square1.remove(tmp);
			square1.setOccupant(square2.getOccupant());
			square1.add(square2.getOccupant());
			square2.remove(square2.getOccupant());
			square2.setOccupant(tmp);
			square2.add(tmp);
			selected = target = null;
			repaint();
			revalidate();
			out.println("MOVE " + square1.x + "" + square1.y + "" + square2.x + "" + square2.y);
		}
		
		private boolean isValidMove(Square square1, Square square2) {
			int level1 = square1.getOccupant().getLevel();
			if(level1 == Piece.FLAG || level1 == Piece.STRONGHOLD)
				return false;
			if(square2.y == square1.y)
				if(Math.abs(square2.x - square1.x) == 1)
					return true;
			if(square2.x == square1.x)
				if(Math.abs(square2.y - square1.y) == 1)
					return true;
			//scout
			if(level1 == 2 && (square2.x == square1.x || square2.y == square1.y))
				return true;
			return false;
		}
		
		private void movePiece(Square square1, Square square2) {
			hideEnemyPieces();
			boolean flag = false;
			if(!square2.isOccupied()) {	
				square2.setOccupant(square1.getOccupant());
				square2.setOccupied(true);
				square2.add(square1.getOccupant());
				
				square1.remove(square1.getOccupant());
				square1.setOccupant(null);
				square1.setOccupied(false);
			}
			else {
				//clash
				if(square2.getOccupant().getLevel() == Piece.FLAG)
					flag = true;
				if(square1.getOccupant().clash(square2.getOccupant()) > 0) {
					eventLabel.setText("You captured " + square2.getOccupant());
					eventLabel.repaint();
					eventLabel.revalidate();
					square2.remove(square2.getOccupant());
					square2.setOccupant(square1.getOccupant());
					square2.add(square1.getOccupant());
					
					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
					square1.setOccupied(false);
					
				}
				else if(square1.getOccupant().clash(square2.getOccupant()) < 0) {
					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
					square1.setOccupied(false);
					square2.getOccupant().setVisible(true);
				}
				else {
					eventLabel.setText("Tie with " + square2.getOccupant());
					eventLabel.repaint();
					eventLabel.revalidate();
					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
					square1.setOccupied(false);
					square2.remove(square2.getOccupant());
					square2.setOccupant(null);
					square2.setOccupied(false);
				}
			}
			shadeOccupiedSquares();
			selected = target = null;
			out.println("MOVE " + square1.x + "" + square1.y + "" + square2.x + "" + square2.y);
			if(flag)
				out.println("FLAG");
		}
		
		public void moveOpponentPiece(int x1, int y1, int x2, int y2) {
			Square square1 = board[x1][y1];
			Square square2 = board[x2][y2];
			if(isSetupTime) {
				//swap
				Piece tmp = square1.getOccupant();
				square1.remove(tmp);
				square1.setOccupant(square2.getOccupant());
				square1.add(square2.getOccupant());
				square2.remove(square2.getOccupant());
				square2.setOccupant(tmp);
				square2.add(tmp);
				revalidate();
				return;
			}
			if(!square2.isOccupied()) {
				square2.setOccupant(square1.getOccupant());
				square2.setOccupied(true);
				square2.add(square1.getOccupant());
				
				square1.remove(square1.getOccupant());
				square1.setOccupant(null);
				square1.setOccupied(false);
			}
			else {
				//clash
				if(square1.getOccupant().clash(square2.getOccupant()) > 0) {
					eventLabel.setText("You lost " + square2.getOccupant());
					eventLabel.repaint();
					eventLabel.revalidate();
					square2.remove(square2.getOccupant());
					square2.setOccupant(square1.getOccupant());
					square2.add(square1.getOccupant());
					square2.getOccupant().setVisible(true);
					
					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
					square1.setOccupied(false);
					
				}
				else if(square1.getOccupant().clash(square2.getOccupant()) < 0) {
					eventLabel.setText("Opponent lost " + square1.getOccupant());
					eventLabel.repaint();
					eventLabel.revalidate();
					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
					square1.setOccupied(false);
				}
				else {
					eventLabel.setText("Tie with " + square1.getOccupant());
					eventLabel.repaint();
					eventLabel.revalidate();
					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
					square1.setOccupied(false);
					square2.remove(square2.getOccupant());
					square2.setOccupant(null);
					square2.setOccupied(false);
				}
			}
			shadeOccupiedSquares();
		}
		
		private class SquareMotionListener extends MouseMotionAdapter {
			@Override
			public void mouseDragged(MouseEvent e) {
				//System.out.println("Mouse Dragged!");
				Square square = (Square) e.getSource();
				if(square.isOccupied() && square.getOccupant().getTeam() == playerTeam)
					selected = square;
				else
					selected = null;
			}
		}
		
		private class SquareMouseListener extends MouseAdapter {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(selected == null)
					return;
				//System.out.println("Mouse Entered!");
				Square square = (Square) e.getSource();
				if(square.isForbidden()) {
					target = null;
					return;
				}
				if(isSetupTime)
					if(square.isOccupied())
						if(square.getOccupant().getTeam() == selected.getOccupant().getTeam())
							target = square;
						else
							target = null;
					else
						target = null;
				else
					//not setup time
					if(square.isOccupied())
						if(square.getOccupant().getTeam() != selected.getOccupant().getTeam())
							target = square;
						else
							//same team
							target = null;
					else
						//target is empty square
						target = square;
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				//System.out.println("Mouse Released!");
				if(target == null) {
					selected = null;
					return;
				}
				if(isSetupTime) {
					swapPieces(selected, target);
					selected = target = null;
				}
				else {
					if(!isMyTurn) {
						selected = target = null;
						return;
					}
					if(isValidMove(selected, target)) {
						movePiece(selected, target);
						selected = target = null;
						isMyTurn = false;
					}
				}
			}
			
		}
		
		public static final int NO_OF_PIECES = 40;
		public static final int BOARD_DIM = 10;
		public static final boolean ORC = false;
		public static final boolean HUMAN = true;


		
	} //end class
}
