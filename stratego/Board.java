package stratego;

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


@SuppressWarnings("serial")
public class Board extends JPanel {
		
		private final Square[][] board = new Square[BOARD_DIM][BOARD_DIM];
		private final Piece[] whitePieces = new Piece[NO_OF_PIECES];
		private final Piece[] blackPieces = new Piece[NO_OF_PIECES];
		private final Queue lastTwoMoves = new Queue(2);
		
		private boolean gameStarted;
		private boolean isSetupTime;
		private boolean playerTeam;
		
		private Square selected;
		private Square target;
		private boolean isMyTurn;
		
		private final PrintWriter out;
		private final JLabel eventLabel;
		private final ScorePanel scorePanel;
		
		public Board(PrintWriter out, JLabel eventLabel, ScorePanel scorePanel) {
			this.out = out;
			this.eventLabel = eventLabel;
			this.scorePanel = scorePanel;
			setLayout(new GridLayout(BOARD_DIM, BOARD_DIM));
			createSquares();
			createPieces();
			setPieces();
			gameStarted = false;
			isSetupTime = true;
			selected = null;
		}

		public void setIsMyTurn(boolean b) {
			isMyTurn = b;
		}
		
		public boolean isSetupTime() {
			return isSetupTime;
		}

		public void setGameStarted(boolean b) {
			gameStarted = b;
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
		
		public void updateEventLabel(String s) {
			eventLabel.setText(s);
			eventLabel.repaint();
			eventLabel.revalidate();
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
		
		private void hideEnemyPieces() {
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
					board[i][j].setOccupant(p);
					board[i][j].add(p, BorderLayout.CENTER);
					count++;
				}
			}
			
			//black pieces
			count = 0;
			for(int j = 9; j >= 6; j--) {
				for(int i = 0; i < 10; i++) {
					Piece p = blackPieces[count];
					board[i][j].setOccupant(p);
					board[i][j].add(p, BorderLayout.CENTER);
					count++;
				}
			}
			shadeOccupiedSquares();
		}

		private void refreshBorders() {
			for(int j = 0; j < BOARD_DIM; j++)
				for(int i = 0; i < BOARD_DIM; i++)
					if(!board[i][j].isForbidden())
						board[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
			repaint();
			revalidate();
		}
		
		private void shadeOccupiedSquares() {
			for(int j = 0; j <= 9; j++) {
				for(int i = 0; i <= 9; i++) {
					if(board[i][j].isOccupied()) {
						Color color = board[i][j].getOccupant().getTeam() ? Color.yellow : Color.darkGray;
						board[i][j].setBackground(color);
                                        }
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
			
			//2-square rule
			if(lastTwoMoves.isFull()) {
				Move move1 = lastTwoMoves.get(0);
				Move move2 = lastTwoMoves.get(1);
				if(move2.src == square2 && move2.dest == square1 && move2.piece == square1.occupant &&
						move1.src == square1 && move1.dest == square2 && move1.piece == square1.occupant)
					return false;
			}
					
			
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
			if(level1 == 2) {
				if(square2.x == square1.x) {
					if(square2.y > square1.y)
						for(int i = square1.y + 1; i < square2.y; i++) {
							if(board[square2.x][i].isOccupied() || board[square2.x][i].isForbidden())
								return false;
						}
					else if(square1.y > square2.y)
						for(int i = square1.y - 1; i > square2.y; i--) {
							if(board[square2.x][i].isOccupied() || board[square2.x][i].isForbidden())
								return false;
						}
					else
						return false;
					return true;
				}
				if(square2.y == square1.y) {
					if(square2.x > square1.x)
						for(int i = square1.x + 1; i < square2.x; i++) {
							if(board[i][square2.y].isOccupied() || board[i][square2.y].isForbidden())
								return false;
						}
					else if(square1.x > square2.x)
						for(int i = square1.x - 1; i > square2.x; i--) {
							if(board[i][square2.y].isOccupied() || board[i][square2.y].isForbidden())
								return false;
						}
					else
						return false;
					return true;
				}
			}
			return false;
		}
		
		private void movePiece(Square square1, Square square2) {
			updateEventLabel("...");
			hideEnemyPieces();
			lastTwoMoves.insert(new Move(square1, square2, square1.occupant));
			boolean flag = false;
			if(!square2.isOccupied()) {	
				square2.setOccupant(square1.getOccupant());
				square2.add(square1.getOccupant());
			        	
				square1.remove(square1.getOccupant());
				square1.setOccupant(null);
			}
			else {
				//clash
				if(square2.getOccupant().getLevel() == Piece.FLAG)
					flag = true;
				if(square1.getOccupant().clash(square2.getOccupant()) > 0) {
					if(!flag)
						updateScorePanel(ScorePanel.CAPTURED, ScorePanel.PIECE_LABEL_INDICES[square2.occupant.level - 1]);
					updateEventLabel("You captured " + square2.getOccupant());
					square2.remove(square2.getOccupant());
					square2.setOccupant(square1.getOccupant());
					square2.add(square1.getOccupant());

					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
					
				}
				else if(square1.getOccupant().clash(square2.getOccupant()) < 0) {
					updateScorePanel(ScorePanel.LOST, ScorePanel.PIECE_LABEL_INDICES[square1.occupant.level - 1]);
					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
					square2.getOccupant().setVisible(true);
				}
				else {
					updateScorePanel(ScorePanel.LOST, ScorePanel.PIECE_LABEL_INDICES[square1.occupant.level - 1]);
					updateScorePanel(ScorePanel.CAPTURED, ScorePanel.PIECE_LABEL_INDICES[square2.occupant.level - 1]);
					updateEventLabel("Tie with " + square2.getOccupant());
					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
					square2.remove(square2.getOccupant());
					square2.setOccupant(null);
				}
			}
			shadeOccupiedSquares();
			selected = target = null;
			out.println("MOVE " + square1.x + "" + square1.y + "" + square2.x + "" + square2.y);
			if(flag)
				out.println("FLAG");
		}
		
		private void updateScorePanel(boolean lostOrCaptured, int i) {
			scorePanel.incrementTally(lostOrCaptured, i);
			scorePanel.repaint();
			scorePanel.revalidate();
		}

		public void moveOpponentPiece(int x1, int y1, int x2, int y2) {
			Square square1 = board[x1][y1];
			Square square2 = board[x2][y2];
			refreshBorders();
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
				square2.add(square1.getOccupant());
				square2.setLastMoveBorder();
				square1.setRedBorder();
				square1.remove(square1.getOccupant());
				square1.setOccupant(null);
			}
			else {
				//clash
				if(square1.getOccupant().clash(square2.getOccupant()) > 0) {
					if(square2.occupant.level != Piece.FLAG)
						updateScorePanel(ScorePanel.LOST, ScorePanel.PIECE_LABEL_INDICES[square2.occupant.level - 1]);
					updateEventLabel("You lost " + square2.getOccupant());
					square2.remove(square2.getOccupant());
					square2.setOccupant(square1.getOccupant());
					square2.add(square1.getOccupant());
					square2.getOccupant().setVisible(true);
					square2.setLastMoveBorder();
					square1.setRedBorder();
					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
					
				}
				else if(square1.getOccupant().clash(square2.getOccupant()) < 0) {
					updateScorePanel(ScorePanel.CAPTURED, ScorePanel.PIECE_LABEL_INDICES[square1.occupant.level - 1]);
					updateEventLabel("Opponent lost " + square1.getOccupant());
					square2.setLastMoveBorder();
					square1.setRedBorder();
					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
				}
				else {
					updateScorePanel(ScorePanel.CAPTURED, ScorePanel.PIECE_LABEL_INDICES[square1.occupant.level - 1]);
					updateScorePanel(ScorePanel.LOST, ScorePanel.PIECE_LABEL_INDICES[square2.occupant.level - 1]);
					updateEventLabel("Tie with " + square1.getOccupant());
					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
					square2.remove(square2.getOccupant());
					square2.setOccupant(null);
					square2.setLastMoveBorder();
					square1.setRedBorder();
				}
			}
			shadeOccupiedSquares();
		}
		
		private class SquareMotionListener extends MouseMotionAdapter {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(!gameStarted)
					return;
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
					else
						selected = target = null;
				}
			}
			
		}
		
		public static final int NO_OF_PIECES = 40;
		public static final int BOARD_DIM = 10;
		public static final boolean ORC = false;
		public static final boolean HUMAN = true;	
	} //end class