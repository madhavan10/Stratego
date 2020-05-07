package stratego;

import java.awt.Color;

import javax.swing.BorderFactory;
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
		if(this.occupant == null)
			isOccupied = false;
		else
			isOccupied = true;
	}

	public boolean isWithinAxeRange(Square other) {
		if(this.isAdjacent(other))
			return true;
		if(Math.abs(this.x - other.x) == 1 && Math.abs(this.y - other.y) == 1)
			return true;
		return false;

	}

	public boolean isAdjacent(Square other) {
		if(this.x == other.x)
			if(Math.abs(this.y - other.y) == 1)
				return true;
		if(this.y == other.y)
			if(Math.abs(this.x - other.x) == 1)
				return true;
		return false;
	}
	
	public void setLastMoveBorder() {
		setBorder(BorderFactory.createLineBorder(Color.green, 2));
		repaint();
	}
	
	public void setSelectedBorder() {
		setBorder(BorderFactory.createLineBorder(Color.red));
		repaint();
	}

	public void setTargetBorder() {
		setBorder(BorderFactory.createLineBorder(Color.blue));
		repaint();
	}
	
	public void removeSelectedBorder() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		repaint();
	}
	
	public String toString() {
		if(isForbidden)
			return "X";
		else if(!isOccupied)
			return "O";
		else return occupant.toString();
	}
}