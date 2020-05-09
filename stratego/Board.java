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
			gameStarted = true;
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
			whitePieces[i] = new Piece(5, "Elf-1", HUMAN, "LONGBOW"); i++;		
			whitePieces[i] = new Piece(5, "Elf-2", HUMAN, "LONGBOW"); i++;		
			whitePieces[i] = new Piece(5, "Elf-3", HUMAN, "LONGBOW"); i++;		
			whitePieces[i] = new Piece(5, "Elf-4", HUMAN, "LONGBOW"); i++;		
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
			blackPieces[j] = new Piece(5, "Beserker-1", ORC, "RAMPAGE"); j++;
			blackPieces[j] = new Piece(5, "Beserker-2", ORC, "RAMPAGE"); j++;
			blackPieces[j] = new Piece(5, "Beserker-3", ORC, "RAMPAGE"); j++;
			blackPieces[j] = new Piece(5, "Beserker-4", ORC, "RAMPAGE"); j++;
			blackPieces[j] = new Piece(4, "Haradrim-1", ORC, "LONGBOW"); j++;
			blackPieces[j] = new Piece(4, "Haradrim-2", ORC, "LONGBOW"); j++;
			blackPieces[j] = new Piece(4, "Haradrim-3", ORC, "LONGBOW"); j++;
			blackPieces[j] = new Piece(4, "Haradrim-4", ORC, "LONGBOW"); j++;
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

		private void addDoneButton() {
			final JButton done = new JButton("done");
			done.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					dwarvenAxe(sm.dwarvenAxeInitialSquare, sm.dwarvenAxeMoveSquare, sm.dwarvenAxeTargets);
					sm.setAllFalse();
					updateSpMessage("...");
					clearButtonsOnSpPanel();
					selected = target = null;
				}
			});
			spPanel.add(done);
			spPanel.repaint();
			spPanel.revalidate();
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
			for(Square target : targets) {
				if(target != null) {
					if(gimli.clash(target.occupant) > 0 && target.occupant.level != Piece.FLAG) {
						result += " " + target.occupant;
						target.occupant.die();
					}
					else if(gimli.clash(target.occupant) < 0) {
						if(!gimli.isDead()) {
							gimli.die();
							move.remove(gimli);				
							move.setOccupant(null);
							result = "You lost Gimli - " + result;
						}
					}
					else if(gimli.clash(target.occupant) == 0) {
						if(!gimli.isDead()) {
							gimli.die();
							move.remove(gimli);				
							move.setOccupant(null);
							result = "You lost Gimli - " + result;
						}
						result += " " + target.occupant;
						target.occupant.die();
					}
					target.occupant.setVisible(true);
				}
			}

			updateEventLabel(result);
			repaint();
			revalidate();
			move.removeSelectedBorder();
			String moveStr = "DWARVEN_AXE " + initial.x + "" + initial.y + "" + move.x + "" + move.y;
			for(Square target : targets) {
				if(target != null) {
					moveStr += " " + target.x + "" + target.y;
					if(target.occupant.isDead()) {
						target.remove(target.occupant);
						target.setOccupant(null);
					}
					target.removeSelectedBorder();
					
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
			if(move != initial) {
				move.setOccupant(initial.occupant);
				move.add(initial.occupant);
				initial.remove(initial.occupant);
				initial.setOccupant(null);
			}
			Piece gimli = move.occupant;
			String result = "Gimli used Dwarven axe - You lost ";
			for(Square target : targets) {
				if(target != null) {
					target.setLastMoveBorder();
					if(gimli.clash(target.occupant) > 0 && target.occupant.level != Piece.FLAG) {
						result += " " + target.occupant;
						target.remove(target.occupant);
						target.setOccupant(null);
					}
					else if(gimli.clash(target.occupant) < 0) {
						if(!gimli.isDead()) {
							gimli.die();
							move.remove(gimli);				
							move.setOccupant(null);
							result = "Opponent lost Gimli - " + result;
						}
					}
					else if(gimli.clash(target.occupant) == 0) {
						if(!gimli.isDead()) {
							gimli.die();
							move.remove(gimli);				
							move.setOccupant(null);
							result = "Opponent lost Gimli - " + result;
							
						}
						result += " " + target.occupant;
						target.remove(target.occupant);
						target.setOccupant(null);
					}
				}
			}
			gimli.setVisible(true);
			updateEventLabel(result);
			shadeOccupiedSquares();			
		}
		
		private boolean cancelAlreadyExists() {
			for(Component c : spPanel.getComponents()) {
				if(c instanceof JButton) {
					JButton button = (JButton) c;
					if(button.getText().equalsIgnoreCase("cancel"))
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
					System.out.println("Using " + specialPower);
					sm.usingSpecialPower = true;
					if(specialPower.equals("DWARVEN_AXE")) {
						sm.dwarvenAxeInitialSquare = selected; 
						updateSpMessage("Choose square from which to dwarven axe");
					}
					clearButtonsOnSpPanel();
					if(!cancelAlreadyExists()) {
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
					if(sm.dwarvenAxeMoveSquare != null)
						sm.dwarvenAxeMoveSquare.removeSelectedBorder();
					for(Square daTarget : sm.dwarvenAxeTargets) {
						if(daTarget != null)
							daTarget.removeSelectedBorder();
					}
					//debug
					//System.out.println("selected " + selected);
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
				Square square = (Square) e.getSource();
				if(square.isOccupied() && square.getOccupant().getTeam() == playerTeam) {
					if(selected != null) 
						selected.removeSelectedBorder();
					selected = square;
				}
				else {
					if(selected != null) 
						selected.removeSelectedBorder();
					selected = null;
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
						//debug
						//System.out.println("square occupant: "  + square.getOccupant());
						//System.out.println("Selected occupant " + selected.getOccupant());
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
					if(!sm.usingSpecialPower) {
						if(isValidMove(selected, target)) {
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
						if(sm.dwarvenAxeMoveSquare != null && sm.dwarvenAxeTargetNumber <= 3) {
							System.out.println("Choosing a target");
							if(square.isOccupied() && square.isWithinAxeRange(sm.dwarvenAxeMoveSquare)) {
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
						else if(sm.dwarvenAxeInitialSquare != null &&
							(square == sm.dwarvenAxeInitialSquare ||
								(!(square.isOccupied() || square.isForbidden()) && 
									square.isAdjacent(sm.dwarvenAxeInitialSquare)))) {
							System.out.println("Choose targets");
							sm.dwarvenAxeMoveSquare = square;	
							addDoneButton();
							square.setTargetBorder();
							updateSpMessage("Choose upto 3 targets");
							
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