package stratego;



public class Main {
	
	static Piece[] whitePieces = new Piece[40];
	static Piece[] blackPieces = new Piece[40];
	static Board board = new Board();
	
	public static void main(String[] args) {
		
		
		createPieces();
		System.out.print(board);
		
	}
	
	public static void createPieces() {
		int i = 0, j = 0;
		
		whitePieces[i] = new Piece(10, "10", Player.HUMAN); i++;
		whitePieces[i] = new Piece(9, "9", Player.HUMAN); i++;
		whitePieces[i] = new Piece(8, "Legolas", Player.HUMAN); i++;
		whitePieces[i] = new Piece(8, "Gimli", Player.HUMAN); i++;
		whitePieces[i] = new Piece(7, "Faramir", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(7, "Theoden", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(7, "Eomer", Player.HUMAN); i++;
		whitePieces[i] = new Piece(6, "Haldir", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(6, "Arwen", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(6, "Treebeard", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(6, "Boromir", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(5, "Elf-1", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(5, "Elf-2", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(5, "Elf-3", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(5, "Elf-4", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(4, "Merry", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(4, "Pippin", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(4, "Frodo", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(4, "Sam", Player.HUMAN); i++;
		whitePieces[i] = new Piece(3, "Soldier-1", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(3, "Soldier-2", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(3, "Soldier-3", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(3, "Soldier-4", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(3, "Soldier-5", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(2, "Rider-1", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(2, "Rider-2", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(2, "Rider-3", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(2, "Rider-4", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(2, "Rider-5", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(2, "Rider-6", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(2, "Rider-7", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(2, "Rider-8", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(Piece.SPY, "Eowyn", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-1", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-2", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-3", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-4", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-5", Player.HUMAN); i++;		
		whitePieces[i] = new Piece(Piece.STRONGHOLD, "Stronghold-6", Player.HUMAN); i++;
		whitePieces[i] = new Piece(Piece.FLAG, "Flag", Player.HUMAN); i++;		


		
		blackPieces[j] = new Piece(10, "10", Player.ORC); j++;
		blackPieces[j] = new Piece(9, "9", Player.ORC); j++;
		blackPieces[j] = new Piece(8, "Ringwraith-1", Player.ORC); j++;
		blackPieces[j] = new Piece(8, "Ringwraith-2", Player.ORC); j++;
		blackPieces[i] = new Piece(7, "Gothmog", Player.ORC); j++;		
		blackPieces[i] = new Piece(7, "Sharku", Player.ORC); j++;
		blackPieces[i] = new Piece(7, "Lurtz", Player.ORC); j++;
		blackPieces[i] = new Piece(6, "Uruk-1", Player.ORC); j++;
		blackPieces[i] = new Piece(6, "Uruk-2", Player.ORC); j++;
		blackPieces[i] = new Piece(6, "Uruk-3", Player.ORC); j++;
		blackPieces[i] = new Piece(6, "Uruk-4", Player.ORC); j++;
		blackPieces[i] = new Piece(5, "Beserker-1", Player.ORC); j++;
		blackPieces[i] = new Piece(5, "Beserker-2", Player.ORC); j++;
		blackPieces[i] = new Piece(5, "Beserker-3", Player.ORC); j++;
		blackPieces[i] = new Piece(5, "Beserker-4", Player.ORC); j++;
		blackPieces[i] = new Piece(4, "Haradrim-1", Player.ORC); j++;
		blackPieces[i] = new Piece(4, "Haradrim-2", Player.ORC); j++;
		blackPieces[i] = new Piece(4, "Haradrim-3", Player.ORC); j++;
		blackPieces[i] = new Piece(4, "Haradrim-4", Player.ORC); j++;
		blackPieces[i] = new Piece(3, "Orc-1", Player.ORC); j++;
		blackPieces[i] = new Piece(3, "Orc-2", Player.ORC); j++;
		blackPieces[i] = new Piece(3, "Orc-3", Player.ORC); j++;
		blackPieces[i] = new Piece(3, "Orc-4", Player.ORC); j++;
		blackPieces[i] = new Piece(3, "Orc-5", Player.ORC); j++;
		blackPieces[i] = new Piece(2, "Warg-1", Player.ORC); j++;
		blackPieces[i] = new Piece(2, "Warg-2", Player.ORC); j++;
		blackPieces[i] = new Piece(2, "Warg-3", Player.ORC); j++;
		blackPieces[i] = new Piece(2, "Warg-4", Player.ORC); j++;
		blackPieces[i] = new Piece(2, "Warg-5", Player.ORC); j++;
		blackPieces[i] = new Piece(2, "Warg-6", Player.ORC); j++;
		blackPieces[i] = new Piece(2, "Warg-7", Player.ORC); j++;
		blackPieces[i] = new Piece(2, "Warg-8", Player.ORC); j++;
		blackPieces[i] = new Piece(Piece.SPY, "Grima", Player.ORC); j++;
	}
}
