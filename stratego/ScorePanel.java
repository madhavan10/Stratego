package stratego;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ScorePanel extends JPanel {
	private JLabel header;
	private JLabel[] tallies;
	
	public final static int[] PIECE_LABEL_INDICES = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 10}; 
		
	public ScorePanel(String headerString) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		tallies = new JLabel[11];
		
		header = new JLabel(headerString);
		header.setForeground(Color.BLUE);
		header.setFont(new Font(header.getFont().getName(), Font.PLAIN, 16));
		
		tallies[0] = new JLabel("  10: 0/1");
		tallies[1] = new JLabel("   9: 0/1");
		tallies[2] = new JLabel("   8: 0/2");
		tallies[3] = new JLabel("   7: 0/3");
		tallies[4] = new JLabel("   6: 0/4");
		tallies[5] = new JLabel("   5: 0/4");
		tallies[6] = new JLabel("   4: 0/4");
		tallies[7] = new JLabel("   3: 0/5");
		tallies[8] = new JLabel("   2: 0/8");
		tallies[9] = new JLabel("   S: 0/1");
		tallies[10] = new JLabel(" STRHD: 0/6");
		
		for (JLabel e: tallies) {
			e.setFont(new Font(e.getFont().getName(), Font.PLAIN, 14));
		}
		
		this.add(header);
		for (JLabel e : tallies) {
			this.add(e);
		}
	}
	
	public JLabel[] getCapturedTallies() {
		return tallies;
	}
	
	public void incrementTally (int index) {

		for (JLabel e : tallies) {
			e.setForeground(Color.BLACK);
		}
		
		String currentText = tallies[index].getText();
		int currentTally = Integer.parseInt(currentText.substring(currentText.length() - 3, currentText.length() - 2));
		currentTally++;
		String newText = currentText.substring(0, currentText.length() - 3) + "" + currentTally + "" + currentText.substring(currentText.length() - 2);
		tallies[index].setText(newText);
		tallies[index].setForeground(Color.RED);
		
	}
}
