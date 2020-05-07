package stratego;

public class SPSM {
	boolean usingSpecialPower = false;
	boolean flight = false;
	boolean detectEnemy = false;
	boolean longbow = false;
	boolean swiftSteed = false;
	boolean dwarvenAxe = false;
	Square dwarvenAxeInitialSquare = null;
	Square dwarvenAxeMoveSquare = null;
	Square[] dwarvenAxeTargets = new Square[3];
	boolean rampage = false;

	public void setAllFalse() {
		usingSpecialPower = false;
		flight = detectEnemy = longbow = swiftSteed = dwarvenAxe = rampage = false;
		dwarvenAxeInitialSquare = dwarvenAxeMoveSquare = null;
		for(Square s : dwarvenAxeTargets) {
			s = null;
		}
	}

}