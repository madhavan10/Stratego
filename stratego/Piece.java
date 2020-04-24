package stratego;

import java.awt.event.MouseAdapter;

import javax.swing.JTextField;
import javax.swing.TransferHandler;

@SuppressWarnings("serial")
public class Piece extends JTextField {
	
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
		setEditable(false);
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

	/*
	public void setPos(int x, int y) {
		xPos = x;
		yPos = y;
	}
	*/
	
	public final static int SPY = 1;
	public final static int STRONGHOLD = 11;
	public final static int FLAG = 0;
	
}
