package stratego;

public class SPSM {
	boolean usingSpecialPower = false;
	boolean flight = false;
	boolean detectEnemy = false;
	boolean longbow = false;
	Piece repeatAttacker = null;
	boolean swiftSteed = false;
	Square dwarvenAxeInitial = null;
	Square dwarvenAxeMove = null;
	Square[] dwarvenAxeTargets = new Square[3];
	int dwarvenAxeTargetNumber = 0;
	Square rampageInitial = null;
	Square rampageMove = null;

	public void setAllFalse() {
		usingSpecialPower = false;
		flight = detectEnemy = longbow = swiftSteed = false;
		dwarvenAxeInitial = dwarvenAxeMove = null;
		for(int i = 0; i < dwarvenAxeTargets.length; i++)
			dwarvenAxeTargets[i] = null;
		dwarvenAxeTargetNumber = 0;
		rampageInitial = rampageMove = null;
		repeatAttacker = null;
	}

}