package stratego;

public class SPSM {
	boolean usingSpecialPower = false;
	boolean flight = false;
	boolean detectEnemy = false;
	boolean longbow = false;
	boolean swiftSteed = false;
	Square dwarvenAxeInitialSquare = null;
	Square dwarvenAxeMoveSquare = null;
	Square[] dwarvenAxeTargets = new Square[3];
	int dwarvenAxeTargetNumber = 0;
	boolean rampage = false;

	public void setAllFalse() {
		usingSpecialPower = false;
		flight = detectEnemy = longbow = swiftSteed = rampage = false;
		dwarvenAxeInitialSquare = dwarvenAxeMoveSquare = null;
		for(int i = 0; i < dwarvenAxeTargets.length; i++)
			dwarvenAxeTargets[i] = null;
		dwarvenAxeTargetNumber = 0;
	}

}