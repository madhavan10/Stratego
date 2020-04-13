package stratego;

public class Square {
	final int x, y;
	final boolean isForbidden;
	boolean isOccupied;
	Piece occupant;

	public Square(int x, int y, boolean isForbidden) {
		this.x = x;
		this.y = y;
		this.isForbidden = isForbidden;
		isOccupied = false;
	}
	
	public String toString() {
		if(isForbidden)
			return "X";
		else if(!isOccupied)
			return "O";
		else return occupant.toString();
	}
}