package stratego;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {
	
	public static final int NO_OF_PIECES = 40;
	public static final int BOARD_DIM = 10;
	
	private final Square[][] board = new Square[BOARD_DIM][BOARD_DIM];
	private final Piece[] whitePieces = new Piece[NO_OF_PIECES];
	private final Piece[] blackPieces = new Piece[NO_OF_PIECES];
	
	private boolean isSetupTime;
	
	private Square selected;
	private Square target;
	
	public Board() {
		setLayout(new GridLayout(BOARD_DIM, BOARD_DIM));
		createSquares();
		createPieces();
		setPieces();
		isSetupTime = true;
		selected = null;
		
		Timer timer = new Timer(1000 * 60 * 10, this);
		timer.setRepeats(false);
		timer.start();
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
		whitePieces[i] = new Piece(10, "Gandalf", Player.HUMAN); i++;
		whitePieces[i] = new Piece(9, "Aragorn", Player.HUMAN); i++;
		whitePieces[i] = new Piece(8, "Legolas", Player.HUMAN); i++;
		whitePieces[i] = new Piece(8, "Gimli", Player.HUMAN); i++;
		whitePieces[i] = new Piece(7, "Faramir", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(7, "Theoden", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(7, "Eomer", Player.HUMAN); i++;
		whitePieces[i] = new Piece(6, "Haldir", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(6, "Arwen", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(6, "Treebeard", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(6, "Boromir", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(5, "Elf-1", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(5, "Elf-2", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(5, "Elf-3", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(5, "Elf-4", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(4, "Merry", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(4, "Pippin", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(4, "Frodo", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(4, "Sam", Player.HUMAN); i++;
		whitePieces[i] = new Piece(3, "Soldier-1", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(3, "Soldier-2", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(3, "Soldier-3", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(3, "Soldier-4", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(3, "Soldier-5", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(2, "Rider-1", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(2, "Rider-2", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(2, "Rider-3", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(2, "Rider-4", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(2, "Rider-5", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(2, "Rider-6", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(2, "Rider-7", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(2, "Rider-8", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(Piece.SPY, "Eowyn", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-1", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-2", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-3", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-4", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-5", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-6", Player.HUMAN); i++;
		whitePieces[i] = new Piece(Piece.FLAG, "Flag", Player.HUMAN); i++;
		
		int j = 0;
		blackPieces[j] = new Piece(10, "Witch King", Player.ORC); j++;
		blackPieces[j] = new Piece(9, "Saruman", Player.ORC); j++;
		blackPieces[j] = new Piece(8, "Ringwraith-1", Player.ORC); j++;
		blackPieces[j] = new Piece(8, "Ringwraith-2", Player.ORC); j++;
		blackPieces[j] = new Piece(7, "Gothmog", Player.ORC); j++;		
		blackPieces[j] = new Piece(7, "Sharku", Player.ORC); j++;
		blackPieces[j] = new Piece(7, "Lurtz", Player.ORC); j++;
		blackPieces[j] = new Piece(6, "Uruk-1", Player.ORC); j++;
		blackPieces[j] = new Piece(6, "Uruk-2", Player.ORC); j++;
		blackPieces[j] = new Piece(6, "Uruk-3", Player.ORC); j++;
		blackPieces[j] = new Piece(6, "Uruk-4", Player.ORC); j++;
		blackPieces[j] = new Piece(5, "Beserker-1", Player.ORC); j++;
		blackPieces[j] = new Piece(5, "Beserker-2", Player.ORC); j++;
		blackPieces[j] = new Piece(5, "Beserker-3", Player.ORC); j++;
		blackPieces[j] = new Piece(5, "Beserker-4", Player.ORC); j++;
		blackPieces[j] = new Piece(4, "Haradrim-1", Player.ORC); j++;
		blackPieces[j] = new Piece(4, "Haradrim-2", Player.ORC); j++;
		blackPieces[j] = new Piece(4, "Haradrim-3", Player.ORC); j++;
		blackPieces[j] = new Piece(4, "Haradrim-4", Player.ORC); j++;
		blackPieces[j] = new Piece(3, "Orc-1", Player.ORC); j++;
		blackPieces[j] = new Piece(3, "Orc-2", Player.ORC); j++;
		blackPieces[j] = new Piece(3, "Orc-3", Player.ORC); j++;
		blackPieces[j] = new Piece(3, "Orc-4", Player.ORC); j++;
		blackPieces[j] = new Piece(3, "Orc-5", Player.ORC); j++;
		blackPieces[j] = new Piece(2, "Warg-1", Player.ORC); j++;
		blackPieces[j] = new Piece(2, "Warg-2", Player.ORC); j++;
		blackPieces[j] = new Piece(2, "Warg-3", Player.ORC); j++;
		blackPieces[j] = new Piece(2, "Warg-4", Player.ORC); j++;
		blackPieces[j] = new Piece(2, "Warg-5", Player.ORC); j++;
		blackPieces[j] = new Piece(2, "Warg-6", Player.ORC); j++;
		blackPieces[j] = new Piece(2, "Warg-7", Player.ORC); j++;
		blackPieces[j] = new Piece(2, "Warg-8", Player.ORC); j++;
		blackPieces[j] = new Piece(Piece.SPY, "Grima", Player.ORC); j++;
		blackPieces[j] = new Piece(Piece.STRONGHOLD, "Stronghold-1", Player.ORC); j++;
		blackPieces[j] = new Piece(Piece.STRONGHOLD, "Stronghold-2", Player.ORC); j++;
		blackPieces[j] = new Piece(Piece.STRONGHOLD, "Stronghold-3", Player.ORC); j++;
		blackPieces[j] = new Piece(Piece.STRONGHOLD, "Stronghold-4", Player.ORC); j++;
		blackPieces[j] = new Piece(Piece.STRONGHOLD, "Stronghold-5", Player.ORC); j++;
		blackPieces[j] = new Piece(Piece.STRONGHOLD, "Stronghold-6", Player.ORC); j++;
		blackPieces[j] = new Piece(Piece.FLAG, "Flag", Player.ORC); j++;
	}
	
	private void setPieces() {
		
		//white pieces
		int count = 0;
		for(int j = 0; j < 4; j++) {
			for(int i = 0; i < 10; i++) {
				Piece p = whitePieces[count];
				//p.setPos(i, j);
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
				//blackPieces[count].setPos(i, j);
				board[i][j].setOccupant(p);
				board[i][j].add(p, BorderLayout.CENTER);
				count++;
			}
		}
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
		selected = null;
		target = null;
		repaint();
		revalidate();
	}
	
	private boolean isValidMove(Square square1, Square square2) {
		//TODO
		return false;
	}
	
	private void movePiece(Square square1, Square square2) {
		//TODO
	}
	
	private class SquareMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			//System.out.println("Mouse Dragged!");
			Square square = (Square) e.getSource();
			if(square.isOccupied())
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
			if(!square.isForbidden()) {
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
			else
				//forbidden square
				target = null;
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			//System.out.println("Mouse Released!");
			if(target != null)
				if(isSetupTime)
					swapPieces(selected, target);
				else {
					if(isValidMove(selected, target))
						movePiece(selected, target);
				}
			else 
				System.out.println("no target");
		}
		
	}

	public void actionPerformed(ActionEvent arg0) {
		isSetupTime = false;
		System.out.println("Setup time over.");
	}
	
} //end class
