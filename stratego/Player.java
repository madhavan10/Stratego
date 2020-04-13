package stratego;

public class Player {
	public final static boolean ORC = false;
	public final static boolean HUMAN = true;
	
	final boolean team;
	
	public Player(boolean team) {
		this.team = team;
	}
}
