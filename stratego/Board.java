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
import javax.swing.JButton;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Board extends JPanel {
		
		private final Square[][] board = new Square[BOARD_DIM][BOARD_DIM];
		private final Piece[] whitePieces = new Piece[NO_OF_PIECES];
		private final Piece[] blackPieces = new Piece[NO_OF_PIECES];
		
		private boolean isSetupTime;
		private boolean playerTeam;
		private boolean gameStarted;
		
		private Square selected;
		private Square target;
		private boolean isMyTurn;
		private String specialPower;
		private JLabel spMessage;
		
		private final SPSM sm = new SPSM();
		private final PrintWriter out;
		private final JPanel spPanel;
		private final JLabel eventLabel;
		
		public Board(PrintWriter out, JPanel spPanel, JLabel eventLabel) {
			this.out = out;
			this.spPanel = spPanel;
			spMessage = new JLabel("...");
			spPanel.add(spMessage);
			this.eventLabel = eventLabel;
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
		
		public void setGameStarted(boolean b) {
			gameStarted = true;	
		}
		
		public void setupTimeOver() {
			isSetupTime = false;
		}

		public void updateEventLabel(String s) {
			eventLabel.setText(s);
			eventLabel.repaint();
			eventLabel.revalidate();
		}

		public void updateSpMessage(String s) {
			spMessage.setText(s);
			spPanel.repaint();
			spPanel.revalidate();
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

			whitePieces[i] = new Piece(10, "Gandalf", HUMAN, "FLIGHT"); i++;
			whitePieces[i] = new Piece(9, "Aragorn", HUMAN, "DETECT_ENEMY"); i++;
			ArrayList<String> tmp = new ArrayList<String>();
			tmp.add("DETECT_ENEMY");
			tmp.add("LONGBOW");
			whitePieces[i] = new Piece(8, "Legolas", HUMAN, tmp); i++;
			whitePieces[i] = new Piece(8, "Gimli", HUMAN, "DWARVEN_AXE"); i++;
			whitePieces[i] = new Piece(7, "Faramir", HUMAN, "LONGBOW"); i++;		
			whitePieces[i] = new Piece(7, "Theoden", HUMAN, "SWIFT_STEED"); i++;		
			whitePieces[i] = new Piece(7, "Eomer", HUMAN); i++;
			whitePieces[i] = new Piece(6, "Haldir", HUMAN); i++;		
			whitePieces[i] = new Piece(6, "Arwen", HUMAN); i++;		
			whitePieces[i] = new Piece(6, "Treebeard", HUMAN); i++;		
			whitePieces[i] = new Piece(6, "Boromir", HUMAN); i++;		
			whitePieces[i] = new Piece(5, "Elf", HUMAN, "LONGBOW"); i++;		
			whitePieces[i] = new Piece(5, "Elf", HUMAN, "LONGBOW"); i++;		
			whitePieces[i] = new Piece(5, "Elf", HUMAN, "LONGBOW"); i++;		
			whitePieces[i] = new Piece(5, "Elf", HUMAN, "LONGBOW"); i++;		
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
			blackPieces[j] = new Piece(10, "Witch King", ORC, "FLIGHT"); j++;
			blackPieces[j] = new Piece(9, "Saruman", ORC, "DETECT_ENEMY"); j++;
			blackPieces[j] = new Piece(8, "Ringwraith-1", ORC); j++;
			blackPieces[j] = new Piece(8, "Ringwraith-2", ORC); j++;
			blackPieces[j] = new Piece(7, "Gothmog", ORC); j++;		
			blackPieces[j] = new Piece(7, "Sharku", ORC); j++;
			blackPieces[j] = new Piece(7, "Lurtz", ORC); j++;
			blackPieces[j] = new Piece(6, "Uruk-1", ORC); j++;
			blackPieces[j] = new Piece(6, "Uruk-2", ORC); j++;
			blackPieces[j] = new Piece(6, "Uruk-3", ORC); j++;
			blackPieces[j] = new Piece(6, "Uruk-4", ORC); j++;
			blackPieces[j] = new Piece(5, "Beserker", ORC, "RAMPAGE"); j++;
			blackPieces[j] = new Piece(5, "Beserker", ORC, "RAMPAGE"); j++;
			blackPieces[j] = new Piece(5, "Beserker", ORC, "RAMPAGE"); j++;
			blackPieces[j] = new Piece(5, "Beserker", ORC, "RAMPAGE"); j++;
			blackPieces[j] = new Piece(4, "Haradrim", ORC, "LONGBOW"); j++;
			blackPieces[j] = new Piece(4, "Haradrim", ORC, "LONGBOW"); j++;
			blackPieces[j] = new Piece(4, "Haradrim", ORC, "LONGBOW"); j++;
			blackPieces[j] = new Piece(4, "Haradrim", ORC, "LONGBOW"); j++;
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
			repaint();
			revalidate();
			out.println("MOVE " + square1.x + "" + square1.y + "" + square2.x + "" + square2.y);
		}
		
		private boolean isValidMove(Square square1, Square square2) {
			//debug
			//System.out.println("square1 occupant: " + square1.getOccupant());
			int level1 = square1.getOccupant().getLevel();
			if(buttonAlreadyExists("end turn") && square1.occupant != sm.repeatAttacker)
				return false;
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
					updateEventLabel("You captured " + square2.getOccupant());
					square2.remove(square2.getOccupant());
					square2.setOccupant(square1.getOccupant());
					square2.add(square1.getOccupant());

					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
					
					//repeat attack
					if(!flag && square2.occupant.level == 6 && square2.occupant.team == ORC) {
						shadeOccupiedSquares();
						if(adjacentTarget(square2)) {
							out.println("REPEAT_ATTACK " + square1.x + "" + square1.y + "" + square2.x + "" + square2.y);
							updateSpMessage("You may use repeat attack or end turn");
							if(!buttonAlreadyExists("end turn"))
								addEndTurnButton();
							sm.repeatAttacker = square2.occupant;
							return;
						}
					}
					
				}
				else if(square1.getOccupant().clash(square2.getOccupant()) < 0) {
					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
					square2.getOccupant().setVisible(true);
				}
				else {
					updateEventLabel("Tie with " + square2.getOccupant());
					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
					square2.remove(square2.getOccupant());
					square2.setOccupant(null);
				}
			}
			shadeOccupiedSquares();
			sm.setAllFalse();
			clearButtonsOnSpPanel();
			updateSpMessage("...");
			out.println("MOVE " + square1.x + "" + square1.y + "" + square2.x + "" + square2.y);
			if(flag)
				out.println("FLAG");
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
				
				square1.remove(square1.getOccupant());
				square1.setOccupant(null);
			}
			else {
				//clash
				if(square1.getOccupant().clash(square2.getOccupant()) > 0) {
					updateEventLabel("You lost " + square2.getOccupant());
					square2.remove(square2.getOccupant());
					square2.setOccupant(square1.getOccupant());
					square2.add(square1.getOccupant());
					square2.getOccupant().setVisible(true);
					
					square2.setLastMoveBorder();
					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
					
				}
				else if(square1.getOccupant().clash(square2.getOccupant()) < 0) {
					updateEventLabel("Opponent lost " + square1.getOccupant());
					square2.setLastMoveBorder();
					
					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
				}
				else {
					updateEventLabel("Tie with " + square1.getOccupant());
					square1.remove(square1.getOccupant());
					square1.setOccupant(null);
					square2.remove(square2.getOccupant());
					square2.setOccupant(null);
					square2.setLastMoveBorder();
				}
			}
			shadeOccupiedSquares();
		}
		
		private void clearButtonsOnSpPanel() {
			for(Component c : spPanel.getComponents())
				if(c instanceof JButton)
					spPanel.remove(c);
			spPanel.repaint();
			spPanel.revalidate();
		}
		
		private void addEndTurnButton() {
			final JButton endTurn = new JButton("end turn");
			endTurn.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					out.println("STOP_REPEAT_ATTACK");
					updateSpMessage("...");
					clearButtonsOnSpPanel();
					sm.setAllFalse();
					selected = target = null;
				}
			});
			spPanel.add(endTurn);
			spPanel.repaint();
			spPanel.revalidate();
		}
		
		private void addDoneButton() {
			final JButton done = new JButton("done");
			done.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) { 
					dwarvenAxe(sm.dwarvenAxeInitial, sm.dwarvenAxeMove, sm.dwarvenAxeTargets);
					updateSpMessage("...");
					clearButtonsOnSpPanel();
					selected = target = null;
				}
			});
			spPanel.add(done);
			spPanel.repaint();
			spPanel.revalidate();
		}
		
		private ArrayList<Square> getAdjacentSquares(Square square) {
			ArrayList<Square> result = new ArrayList<Square>();
			for(int i = 0; i < BOARD_DIM; i++) {
				for(Square s : board[i])
					if(s.isAdjacent(square))
						result.add(s);
			}
			return result;
		}
		
		private boolean adjacentTarget(Square s) {
			for(Square adj : getAdjacentSquares(s)) {
				if(adj.isOccupied && adj.occupant.team != s.occupant.team) {
					 return true;
				}
			}
			return false;
		}
		
		private ArrayList<Square> getRampageTargets(Square square) {
			ArrayList<Square> targets = new ArrayList<Square>();
			for(int j = 0; j < BOARD_DIM; j++) {
				for(int i = 0; i < BOARD_DIM; i++) {
					if(board[i][j].isWithinAxeRange(square)	&& board[i][j].isOccupied 
							&& board[i][j].occupant.team != square.occupant.team) {
						targets.add(board[i][j]);
					}
				}
			}
			return targets;
		}
		
		private void rampage(Square initial, Square move) {
			updateEventLabel("...");
			hideEnemyPieces();
			if(move != initial) {
				move.setOccupant(initial.occupant);
				move.add(initial.occupant);
				initial.remove(initial.occupant);
				initial.setOccupant(null);
			}
			Piece berserker = move.occupant;
			String result = "You lost " + berserker + " - You captured";
			ArrayList<Square> targets = getRampageTargets(move);
				
			for(Square targeted: targets) {
				move.remove(berserker);
				move.setOccupant(null);
				targeted.occupant.setVisible(true);				
				if((targeted.occupant.level == Piece.STRONGHOLD || berserker.clash(targeted.occupant) >= 0) 
						&& targeted.occupant.level != Piece.FLAG) {
					result += ", " + targeted.occupant;
					targeted.remove(targeted.occupant);
					targeted.setOccupant(null);
				}

			}
			updateEventLabel(result);
			out.println("RAMPAGE " + initial.x + "" + initial.y + "" + move.x + "" + move.y);
			shadeOccupiedSquares();
			selected.removeSelectedBorder();
			sm.setAllFalse();			
		}
		
		public void opponentRampage(int x1, int y1, int x2, int y2) {
			refreshBorders();
			Square initial = board[x1][y1];
			Square move = board[x2][y2];
			if(move != initial) {
				move.setOccupant(initial.occupant);
				move.add(initial.occupant);
				initial.remove(initial.occupant);
				initial.setOccupant(null);
			}
			move.setLastMoveBorder();
			Piece berserker = move.occupant;
			
			String result = berserker + " used rampage: You lost";
			ArrayList<Square> targets = getRampageTargets(move);
			move.remove(berserker);
			move.setOccupant(null);
			for(Square targeted: targets) {
				targeted.setLastMoveBorder();
				if((targeted.occupant.level == Piece.STRONGHOLD || berserker.clash(targeted.occupant) >= 0) 
						&& targeted.occupant.level != Piece.FLAG) {
					result += ", " + targeted.occupant;
					targeted.remove(targeted.occupant);
					targeted.setOccupant(null);
				}				
			}
			updateEventLabel(result);
			shadeOccupiedSquares();
		}
		
		private boolean isValidSwiftSteed(Square src, Square dest) {
			if(!dest.isForbidden && (!dest.isOccupied || dest.occupant.team != playerTeam)) {
				if(src.x == dest.x) {
					if(Math.abs(src.y - dest.y) == 2) {
						if(src.y < dest.y) {
							if(board[src.x][src.y + 1].isOccupied)
								return false;
							return true;
						}
						else if(src.y > dest.y) {
							if(board[src.x][src.y - 1].isOccupied)
								return false;
							return true;
						}
					}
					return false;
				}
				else if(src.y == dest.y) {
					if(Math.abs(src.x - dest.x) == 2) {
						if(src.x < dest.x) {
							if(board[src.x + 1][src.y].isOccupied)
								return false;
							return true;
						}
						else if(src.x > dest.x) {
							if(board[src.x - 1][src.y].isOccupied)
								return false;
							return true;
						}
					}
					return false;
				}
				return false;
			}
			return false;
		}
		
		private void swiftSteed(Square src, Square dest) {
			hideEnemyPieces();
			Piece theoden = src.occupant;
			if(!dest.isOccupied) {
				dest.add(theoden);
				dest.setOccupant(theoden);
				src.remove(theoden);
				src.setOccupant(null);
			}
			else if(theoden.clash(dest.occupant) > 0) {
				updateEventLabel("You captured " + dest.occupant);
				dest.remove(dest.occupant);
				dest.add(theoden);
				dest.setOccupant(theoden);
				src.remove(theoden);
				src.setOccupant(null);	
			}
			else if(theoden.clash(dest.occupant) == 0) {
				updateEventLabel("Tie with " + dest.occupant);
				dest.remove(dest.occupant);
				dest.setOccupant(null);
				src.remove(theoden);
				src.setOccupant(null);
			}
			else {
				//theoden dies
				updateEventLabel("You lost " + theoden);
				src.remove(theoden);
				src.setOccupant(null);
			}
			shadeOccupiedSquares();
			sm.setAllFalse();
			out.println("SWIFT_STEED " + src.x + "" + src.y + "" + dest.x + "" + dest.y);
			selected.removeSelectedBorder();
		}
		
		public void opponentSwiftSteed(int x1, int y1, int x2, int y2) {
			refreshBorders();
			Square src = board[x1][y1];
			Square dest = board[x2][y2];
			Piece theoden = src.occupant;
			theoden.setVisible(true);
			dest.setLastMoveBorder();
			String result = theoden + " used Swift Steed:";
			if(!dest.isOccupied) {
				dest.add(theoden);
				dest.setOccupant(theoden);
				src.remove(theoden);
				src.setOccupant(null);
			}
			else if(theoden.clash(dest.occupant) > 0) {
				result += " You lost " + dest.occupant;
				dest.remove(dest.occupant);
				dest.add(theoden);
				dest.setOccupant(theoden);
				src.remove(theoden);
				src.setOccupant(null);	
			}
			else if(theoden.clash(dest.occupant) == 0) {
				result += " Tie with " + dest.occupant;
				dest.remove(dest.occupant);
				dest.setOccupant(null);
				src.remove(theoden);
				src.setOccupant(null);
			}
			else {
				//theoden dies
				result += " You captured " + theoden;
				src.remove(theoden);
				src.setOccupant(null);
			}
			updateEventLabel(result);
			shadeOccupiedSquares();
		}
		
		private boolean isValidDetect(Square src, Square dest) {
			if(dest.isOccupied && dest.occupant.team != playerTeam) {
				if(src.isWithinAxeRange(dest))
					return true;
				else if(src.x == dest.x) {
					if(Math.abs(src.y - dest.y) == 2)
						return true;
				}
				else if(src.y == dest.y) {
					if(Math.abs(src.x - dest.x) == 2)
						return true;
				}
			}
			return false;
		}
		
		private void longbow(Square src, Square dest) {
			hideEnemyPieces();
			Piece archer = src.occupant;
			Piece other = dest.occupant;
			if(archer.clash(other) > 0 && !(other.level == Piece.FLAG || (other.level == 5 && other.team == ORC))) {
				updateEventLabel("You captured " + other);
				dest.remove(other);
				dest.setOccupant(null);
			}
			else {
				updateEventLabel("Longbow rendered ineffective");
			}
			shadeOccupiedSquares();
			sm.setAllFalse();
			selected.removeSelectedBorder();
			out.println("LONGBOW " + src.x + "" + src.y + "" + dest.x + "" + dest.y);
		}
		
		public void opponentLongbow(int x1, int y1, int x2, int y2) {
			Square src = board[x1][y1];
			Square dest = board[x2][y2];
			refreshBorders();
			Piece archer = src.occupant;
			Piece other = dest.occupant;
			src.setLastMoveBorder();
			dest.setLastMoveBorder();
			archer.setVisible(true);
			if(archer.clash(other) > 0 && !(other.level == Piece.FLAG || (other.level == 5 && other.team == ORC))) {
				updateEventLabel(archer + " used longbow: You lost " + other);
				dest.remove(other);
				dest.setOccupant(null);
			}
			else {
				updateEventLabel(archer + " used longbow on " + other + " ineffectively");	
			}
			shadeOccupiedSquares();
		}
		
		private void detect(Square src, Square dest) {
			hideEnemyPieces();
			dest.occupant.setVisible(true);
			sm.setAllFalse();
			selected.removeSelectedBorder();
			repaint();
			revalidate();
			out.println("DETECT " + src.x + "" + src.y + "" + dest.x + "" + dest.y);
		}
		
		public void opponentDetect(int x1, int y1, int x2, int y2) {
			Square src = board[x1][y1];
			Square dest = board[x2][y2];
			refreshBorders();
			Piece nine = src.occupant;
			nine.setVisible(true);
			src.setLastMoveBorder();
			dest.setLastMoveBorder();
			updateEventLabel(nine + " used detect enemy on " + dest.occupant);
		}
		
		private void flight(Square initial, Square dest) {
			hideEnemyPieces();
			Piece ten = initial.occupant;
			dest.add(ten);
			dest.setOccupant(ten);
			initial.remove(ten);
			initial.setOccupant(null);
			shadeOccupiedSquares();
			out.println("FLIGHT " + initial.x + "" + initial.y + "" + dest.x + "" + dest.y);
			sm.setAllFalse();
			selected.removeSelectedBorder();		
		}
		
		public void opponentFlight(int x1, int y1, int x2, int y2) {
			refreshBorders();
			Square initial = board[x1][y1];
			Square dest = board[x2][y2];
			Piece ten = initial.occupant;
			dest.add(ten);
			dest.setLastMoveBorder();
			dest.setOccupant(ten);
			initial.remove(ten);
			initial.setOccupant(null);
			ten.setVisible(true);
			shadeOccupiedSquares();
			updateEventLabel(ten + " used Flight");
		}
		
		private boolean isValidFlight(Square initial, Square dest) {
			if(!dest.isOccupied) {
				if(dest.x == initial.x) {
					if(dest.y > initial.y) {
						for(int i = initial.y + 1; i < dest.y; i++) {
							if(!(board[dest.x][i].isForbidden || board[dest.x][i].isOccupied))
								return false;
						}
						return true;
					}
					else if(dest.y < initial.y) {
						for(int i = initial.y; i > dest.y; i--) {
							if(!(board[dest.x][i].isForbidden || board[dest.x][i].isOccupied))
								return false;
						}
						return true;
					}
				}
				else if(dest.y == initial.y) {
					if(dest.x > initial.x) {
						for(int i = initial.x; i < dest.x; i++) {
							if(!(board[i][dest.y].isForbidden || board[i][dest.y].isOccupied))
								return false;
						}
						return true;
					}
					else if(dest.x < initial.x) {
						for(int i = initial.x; i > dest.x; i--) {
							if(!(board[i][dest.y].isForbidden || board[i][dest.y].isOccupied))
								return false;
						}
						return true;
					}
				}
				return false;
			}
			return false;
		}
		
		private void dwarvenAxe(Square initial, Square move, Square[] targets) {
			updateEventLabel("...");
			hideEnemyPieces();
			if(move != initial) {
				move.setOccupant(initial.occupant);
				move.add(initial.occupant);
				initial.remove(initial.occupant);
				initial.setOccupant(null);
			}

			Piece gimli = move.occupant;
			String result = "You captured";
			for(Square targeted : targets) {
				if(targeted != null) {
					if(gimli.clash(targeted.occupant) > 0 && targeted.occupant.level != Piece.FLAG) {
						result += ", " + targeted.occupant;
						targeted.occupant.die();
					}
					else if(gimli.clash(targeted.occupant) < 0) {
						if(!gimli.isDead()) {
							gimli.die();
							move.remove(gimli);				
							move.setOccupant(null);
							result = "You lost Gimli - " + result;
						}
					}
					else if(gimli.clash(targeted.occupant) == 0) {
						if(!gimli.isDead()) {
							gimli.die();
							move.remove(gimli);				
							move.setOccupant(null);
							result = "You lost Gimli - " + result;
						}
						result += ", " + targeted.occupant;
						targeted.occupant.die();
					}
					targeted.occupant.setVisible(true);
				}
			}

			updateEventLabel(result);
			repaint();
			revalidate();
			move.removeSelectedBorder();
			String moveStr = "DWARVEN_AXE " + initial.x + "" + initial.y + "" + move.x + "" + move.y;
			for(Square targeted : targets) {
				if(targeted != null) {
					moveStr += " " + targeted.x + "" + targeted.y;
					if(targeted.occupant.isDead()) {
						targeted.remove(targeted.occupant);
						targeted.setOccupant(null);
					}
					targeted.removeSelectedBorder();
					
				}
			}
			out.println(moveStr);
			shadeOccupiedSquares();
			sm.setAllFalse();
			selected.removeSelectedBorder();
			
		}
		
		public void opponentDwarvenAxe(int x1, int y1, int x2, int y2, Square[] targets) {
			refreshBorders();
			Square initial = board[x1][y1];
			Square move = board[x2][y2];
			move.setLastMoveBorder();
			if(move != initial) {
				move.setOccupant(initial.occupant);
				move.add(initial.occupant);
				initial.remove(initial.occupant);
				initial.setOccupant(null);
			}
			Piece gimli = move.occupant;
			String result = "Gimli used Dwarven axe: You lost ";
			for(Square targeted : targets) {
				if(targeted != null) {
					targeted.setLastMoveBorder();
					if(gimli.clash(targeted.occupant) > 0 && targeted.occupant.level != Piece.FLAG) {
						result += ", " + targeted.occupant;
						targeted.remove(targeted.occupant);
						targeted.setOccupant(null);
					}
					else if(gimli.clash(targeted.occupant) < 0) {
						if(!gimli.isDead()) {
							gimli.die();
							move.remove(gimli);				
							move.setOccupant(null);
							result = "Opponent lost Gimli - " + result;
						}
					}
					else if(gimli.clash(targeted.occupant) == 0) {
						if(!gimli.isDead()) {
							gimli.die();
							move.remove(gimli);				
							move.setOccupant(null);
							result = "Opponent lost Gimli - " + result;
							
						}
						result += ", " + targeted.occupant;
						targeted.remove(targeted.occupant);
						targeted.setOccupant(null);
					}
				}
			}
			gimli.setVisible(true);
			updateEventLabel(result);
			shadeOccupiedSquares();			
		}
		
		private boolean buttonAlreadyExists(String text) {
			for(Component c : spPanel.getComponents()) {
				if(c instanceof JButton) {
					JButton button = (JButton) c;
					if(button.getText().equalsIgnoreCase(text))
						return true;
				}
			}
			return false;
		}

		private void addSpButton(String text) {
			JButton spButton = new JButton(text);
			spButton.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					specialPower = ((JButton) e.getSource()).getText();
					//debug
					System.out.println("Using " + specialPower);
					sm.usingSpecialPower = true;
					if(specialPower.equals("DWARVEN_AXE")) {
						sm.dwarvenAxeInitial = selected; 
						updateSpMessage("Choose square from which to dwarven axe");
					}
					else if(specialPower.equals("RAMPAGE")) {
						sm.rampageInitial = selected;
						updateSpMessage("Choose square from which to use rampage");
					}
					else if(specialPower.equals("FLIGHT")) {
						sm.flight = true;
						updateSpMessage("Choose destination");
					}
					else if(specialPower.equals("SWIFT_STEED")) {
						sm.swiftSteed = true;
						updateSpMessage("Choose destination");
					}
					else if(specialPower.equals("DETECT_ENEMY")) {
						sm.detectEnemy = true;
						updateSpMessage("Choose target");
					}
					else if(specialPower.equals("LONGBOW")) {
						sm.longbow = true;
						updateSpMessage("Choose longbow target");
					}
					clearButtonsOnSpPanel();
					if(!buttonAlreadyExists("cancel")) {
						addCancelButton();
					}
				}
			});
			spPanel.add(spButton);
			spPanel.repaint();
			spPanel.revalidate();			
		}
		
		private void addCancelButton() {
			JButton cancel = new JButton("cancel");
			cancel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					//remove cancel
					updateSpMessage("...");
					clearButtonsOnSpPanel();
					if(sm.dwarvenAxeMove != null)
						sm.dwarvenAxeMove.removeSelectedBorder();
					for(Square daTarget : sm.dwarvenAxeTargets) {
						if(daTarget != null)
							daTarget.removeSelectedBorder();
					}
					//debug
					System.out.println("selected " + selected);
					selected.removeSelectedBorder();
					sm.setAllFalse();
					selected = target = null;
				}
			});
			spPanel.add(cancel);
			spPanel.repaint();
			spPanel.revalidate();
		}
		
		private class SquareMotionListener extends MouseMotionAdapter {
			@Override
			public void mouseDragged(MouseEvent e) {
				//System.out.println("Mouse Dragged!");
				if(!gameStarted)
					return;
				if(sm.usingSpecialPower) {
					return;
				}
				
				Square square = (Square) e.getSource();
				if(square.isOccupied() && square.getOccupant().getTeam() == playerTeam) {
					if(selected != null) 
						selected.removeSelectedBorder();
					selected = square;
				}
				else {
					if(selected != null) {
						selected.removeSelectedBorder();
						selected = null;
					}
				}
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
					if(square.isOccupied()) {
						if(square.getOccupant().getTeam() != selected.getOccupant().getTeam())
							target = square;
						else
							//same team
							target = null;
					}
					else
						//target is empty square
						target = square;
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(isSetupTime)
					return;
				if(!sm.usingSpecialPower) {
					if(selected != null) {
						clearButtonsOnSpPanel();
						spPanel.repaint();
						spPanel.revalidate();
						selected.removeSelectedBorder();
					}
					selected = target = null;
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(isSetupTime) {
					if(target == null) {
						selected = null;
					}
					else {
						swapPieces(selected, target);
						selected = target = null;
					}
				}
				else {
					if(!isMyTurn) {
						selected = target = null;
						return;
					}
					if(!sm.usingSpecialPower) {
						if(target == null) {
							selected = null;
						}
						else if(isValidMove(selected, target)) {
							movePiece(selected, target);
							selected = target = null;
						}
						else
							selected = target = null;
					}
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(isSetupTime)
					return;
				if(!isMyTurn)
					return;
				Square square = (Square) e.getSource();
				if(!sm.usingSpecialPower && square.isOccupied() && square.getOccupant().getTeam() == playerTeam) {
					//new selection
					if(selected != null) {
						//remove all buttons and old border
						clearButtonsOnSpPanel();
						selected.removeSelectedBorder();
					}
					selected = square;
					selected.setSelectedBorder();
					if(square.getOccupant().isSpecial()) {
						//add buttons for special power(s)
						for(String name : square.getOccupant().getSpecialPowerNames()) {
							addSpButton(name);
						}
					}
				} 
				else if(selected != null && sm.usingSpecialPower) {
					
					if(specialPower.equals("DWARVEN_AXE")) {
						if(sm.dwarvenAxeMove != null && sm.dwarvenAxeTargetNumber <= 2) {
							//debug
							System.out.println("Choosing a target");
							if(square.isOccupied() && square.occupant.team != playerTeam && square.isWithinAxeRange(sm.dwarvenAxeMove)) {
								boolean alreadyInTargets = false;
								for(Square s : sm.dwarvenAxeTargets) {
									if(square == s) {
										alreadyInTargets = true;
										break;
									}
								} 
								if(!alreadyInTargets) {
									square.setTargetBorder();
									sm.dwarvenAxeTargets[sm.dwarvenAxeTargetNumber] = square;
									sm.dwarvenAxeTargetNumber++;
								}
							}
						}
						else if(sm.dwarvenAxeInitial != null &&
								(square == sm.dwarvenAxeInitial ||
								(!(square.isOccupied() || square.isForbidden()) && 
										square.isAdjacent(sm.dwarvenAxeInitial)))) {
							//debug
							System.out.println("Choose targets");
							sm.dwarvenAxeMove = square;	
							addDoneButton();
							square.setTargetBorder();
							updateSpMessage("Choose upto 3 targets");
							
						}
					}
					else if(specialPower.equals("RAMPAGE")) {
						if(sm.rampageInitial != null &&
								(square == sm.rampageInitial ||
								(!(square.isOccupied || square.isForbidden) &&
										square.isAdjacent(sm.rampageInitial)))) {
							sm.rampageMove = square;
							rampage(sm.rampageInitial, sm.rampageMove);
							updateSpMessage("...");
							clearButtonsOnSpPanel();
							selected = target = null;
						}
								
					}
					else if(specialPower.equals("FLIGHT")) {
						if(sm.flight && isValidFlight(selected, square)) {
							flight(selected, square);
							updateSpMessage("...");
							clearButtonsOnSpPanel();
							selected = target = null;
						}
					}
					else if(specialPower.equals("SWIFT_STEED")) {
						if(sm.swiftSteed && isValidSwiftSteed(selected, square)) {
							swiftSteed(selected, square);
							updateSpMessage("...");
							clearButtonsOnSpPanel();
							selected = target = null;
						}
					}
					else if(specialPower.equals("DETECT_ENEMY")) {
						if(sm.detectEnemy && isValidDetect(selected, square)) {
							detect(selected, square);
							updateSpMessage("...");
							clearButtonsOnSpPanel();
							selected = target = null;
						}
					}
					else if(specialPower.equals("LONGBOW")) {
						if(sm.longbow && isValidDetect(selected, square)) {
							longbow(selected, square);
							updateSpMessage("...");
							clearButtonsOnSpPanel();
							selected = target = null;
						}
					}
				}
			}
			
		}
		
		public static final int NO_OF_PIECES = 40;
		public static final int BOARD_DIM = 10;
		public static final boolean ORC = false;
		public static final boolean HUMAN = true;
		
	} //end class