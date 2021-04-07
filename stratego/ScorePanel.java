package stratego;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ScorePanel extends JPanel {
	private JLabel capturedHeader, lostHeader;
	private JLabel[] capturedTallies, lostTallies;
	
	public final static int[] PIECE_LABEL_INDICES = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 10}; 
	public final static boolean LOST = false, CAPTURED = true;
		
	public ScorePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		capturedTallies = new JLabel[11];
		lostTallies = new JLabel[11];
		
		capturedHeader = new JLabel("Pieces Captured:");
		lostHeader = new JLabel("Pieces Lost:");
		capturedHeader.setForeground(Color.BLUE);
		lostHeader.setForeground(Color.BLUE);
		
		capturedTallies[0] = new JLabel("  10: 0/1");
		capturedTallies[1] = new JLabel("   9: 0/1");
		capturedTallies[2] = new JLabel("   8: 0/2");
		capturedTallies[3] = new JLabel("   7: 0/3");
		capturedTallies[4] = new JLabel("   6: 0/4");
		capturedTallies[5] = new JLabel("   5: 0/4");
		capturedTallies[6] = new JLabel("   4: 0/4");
		capturedTallies[7] = new JLabel("   3: 0/5");
		capturedTallies[8] = new JLabel("   2: 0/8");
		capturedTallies[9] = new JLabel("   S: 0/1");
		capturedTallies[10] = new JLabel(" STHL: 0/6");
		
	
		lostTallies[0] = new JLabel("  10: 0/1");
		lostTallies[1] = new JLabel("   9: 0/1");
		lostTallies[2] = new JLabel("   8: 0/2");
		lostTallies[3] = new JLabel("   7: 0/3");
		lostTallies[4] = new JLabel("   6: 0/4");
		lostTallies[5] = new JLabel("   5: 0/4");
		lostTallies[6] = new JLabel("   4: 0/4");
		lostTallies[7] = new JLabel("   3: 0/5");
		lostTallies[8] = new JLabel("   2: 0/8");
		lostTallies[9] = new JLabel("   S: 0/1");
		lostTallies[10] = new JLabel(" STHL: 0/6");
		
		this.add(capturedHeader);
		for (JLabel e : capturedTallies) {
			this.add(e);
		}
		
		this.add(new JLabel("----------------"));
		
		this.add(lostHeader);
		for (JLabel e : lostTallies) {
			this.add(e);
		}
	}
	
	public JLabel[] getCapturedTallies() {
		return capturedTallies;
	}
	
	public JLabel[] getLostTallies() {
		return lostTallies;
	}
	
	public void incrementTally(boolean lostOrCaptured, int index) {
		
		for (JLabel e : lostTallies) {
			e.setForeground(Color.BLACK);
		}
		for (JLabel e : capturedTallies) {
			e.setForeground(Color.BLACK);
		}
		if(lostOrCaptured == LOST) {
			String currentText = lostTallies[index].getText();
			int currentTally = Integer.parseInt(currentText.substring(currentText.length() - 3, currentText.length() - 2));
			currentTally++;
			String newText = currentText.substring(0, currentText.length() - 3) + "" + currentTally + "" + currentText.substring(currentText.length() - 2);
			lostTallies[index].setText(newText);
			lostTallies[index].setForeground(Color.RED);
		} else {
			String currentText = capturedTallies[index].getText();
			int currentTally = Integer.parseInt(currentText.substring(currentText.length() - 3, currentText.length() - 2));
			currentTally++;
			String newText = currentText.substring(0, currentText.length() - 3) + "" + currentTally + "" + currentText.substring(currentText.length() - 2);
			capturedTallies[index].setText(newText);
			capturedTallies[index].setForeground(Color.RED);
		}
		
	}
}
