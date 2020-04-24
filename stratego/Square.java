package stratego;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Square extends JPanel {
	
	final int x, y;
	final boolean isForbidden;
	
	
	boolean isOccupied;
	
	Piece occupant;
	
	public boolean isForbidden() {
		return isForbidden;
	}

	public boolean isOccupied() {
		return isOccupied;
	}
	
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}
	
	public Piece getOccupant() {
		return occupant;
	}

	public Square(int x, int y, boolean forbidden) {
		this.x = x;
		this.y = y;
		isForbidden = forbidden;
		isOccupied = false;
	}
	
	public void setOccupant(Piece occupant) {
		this.occupant = occupant;
		isOccupied = true;
	}
	
	public String toString() {
		if(isForbidden)
			return "X";
		else if(!isOccupied)
			return "O";
		else return occupant.toString();
	}
}