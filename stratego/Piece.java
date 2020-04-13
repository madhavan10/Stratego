package stratego;

public class Piece {
	
	final static int SPY = 1;
	final static int STRONGHOLD = 11;
	final static int FLAG = 0;
	
	final int level;
	final String name;
	final boolean team;
	
	boolean isDead;
	int xPos, yPos;
	
	public Piece(int level, String name, boolean team) {
		
		this.level = level;
		this.name = name;
		this.team = team;
		
		isDead = false;
	}
	
	public String toString() {
		String result = name;
		if(team == Player.HUMAN)
			result += "(W)";
		else
			result += "(B)";
		return result;
	}
}
