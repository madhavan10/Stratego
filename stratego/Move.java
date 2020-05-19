package stratego;

public class Move {
	final Square src, dest;
	final Piece piece;
	public Move(Square src, Square dest, Piece piece) {
		this.src = src;
		this.dest = dest;
		this.piece = piece;
	}
}
