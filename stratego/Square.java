package stratego;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.BorderFactory;

@SuppressWarnings("serial")
public class Square extends JPanel {
	
	final int x, y;
	final boolean isForbidden;
	
	
	boolean isOccupied;
	
	Piece occupant;
	
	public boolean isForbidden() {
		return isForbidden;
	}

	public void setRedBorder() {
		setBorder(BorderFactory.createLineBorder(Color.RED));
	}

	private void setLastMoveBorder(Square s) {
		setBorder(BorderFactory.createLineBorder(Color.green, 2));
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
	
	public String toString() {
		if(isForbidden)
			return "X";
		else if(!isOccupied)
			return "O";
		else return occupant.toString();
	}
}