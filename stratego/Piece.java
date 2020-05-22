package stratego;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Piece extends JLabel {
	
	final int level;
	final String name;
	final boolean team;
	final boolean special;
	final ArrayList<String> specialPowerNames;
	private boolean isDead;
	
	private void setFont() {
		if(special || level == STRONGHOLD || level == FLAG || level == SPY)
			setFont(getFont().deriveFont(Font.BOLD, 12));
		else
			setFont(getFont().deriveFont(Font.PLAIN, 24));
	}

	public Piece(int level, String name, boolean team) {
		
		this.level = level;
		this.name = name;
		this.team = team;
		specialPowerNames = null;
		special = false;
		isDead = false;
		
		setText(this.toString());
		setForeground(team ? Color.BLACK : Color.WHITE);
		setFont();
	}
	
	public Piece(int level, String name, boolean team, ArrayList<String> specialPowerNames) {
		this.level = level;
		this.name = name;
		this.team = team;
		this.specialPowerNames = specialPowerNames;
		special = specialPowerNames != null && specialPowerNames.size() > 0;
		isDead = false;
		
		setText(this.toString());
		setForeground(team ? Color.BLACK : Color.WHITE);
		setFont();
	}

	public Piece(int level, String name, boolean team, String spName) {
		this.level = level;
		this.name = name;
		this.team = team;
		specialPowerNames = new ArrayList<String>();
		specialPowerNames.add(spName);
		special = true;
		isDead = false;
		
		setText(this.toString());
		setForeground(team ? Color.BLACK : Color.WHITE);
		setFont();
	}
	
	public boolean isDead() {
		return isDead;
	}

	public boolean isSpecial() {
		return special;
	}

	public ArrayList<String> getSpecialPowerNames() {
		return specialPowerNames;
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
		if(level == STRONGHOLD)
			return "Stronghold";
		if(level == FLAG)
			return "Flag";
		if(level == SPY)
			return "Spy";
		if(special)
			return name + "(" + level + ")";
		return "" + level;
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
