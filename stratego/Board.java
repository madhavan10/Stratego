package stratego;

public class Board {
	Square[][] board = new Square[10][10];
	
	public Board() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if((i == 4 || i == 5) && (j == 2 || j == 3 || j == 6 || j == 7))
					board[i][j] = new Square(i, j, true);
				else 
					board[i][j] = new Square(i, j, false);
			}
		}
	}
	
	public String toString() {
		String result = "";
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				result += board[i][j];
				result += "\t";
			}
			result += "\n";
		}
		return result;
	}
}
