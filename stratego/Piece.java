package stratego;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Piece extends JLabel {
	
	final int level;
	final String name;
	final boolean team;
	
	private boolean isDead;
	//private int xPos, yPos;
	
	public Piece(int level, String name, boolean team) {
		
		this.level = level;
		this.name = name;
		this.team = team;
		
		isDead = false;
		
		setText(this.toString());
		setForeground(team ? Color.WHITE : Color.BLACK);
		setFont(new Font("Arial", Font.BOLD, 12));
	}
	
	public boolean isDead() {
		return isDead;
	}

	public void die() {
		isDead = true;
	}

	public int getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

	public boolean getTeam() {
		return team;
	}
	
	public String toString() {
		return name;
	}

	/**
	 * @return Positive int if this wins, negative int if other wins, 0 if tie
	 */
	public int clash(Piece other) {
		if(other.level == STRONGHOLD)
			if(this.level == 3)
				return 1;
			else
				return -1;
		if(other.level == 10 && this.level == SPY)
			return 1;
		return this.level - other.level;
	}
	
	public final static int SPY = 1;
	public final static int STRONGHOLD = 11;
	public final static int FLAG = 0;
}
