package othello.game.board;

/**
 * This class contains enums which represents Pieces on the board. 
 * @author John Berg
 * @version 26/10/2017
 *
 */
public enum Piece {

	PIECE_A{
		@Override
		public Piece flip(){
			return PIECE_B;
		}
	},
	PIECE_B{
		@Override
		public Piece flip(){
			return PIECE_A;
		}
	};
	
	/**
	 * A private constructor which merely constructs the enums
	 */
	private Piece(){}
	
	/**
	 * An abstract method which returns another piece
	 * @return returns a piece (usually an opposing piece)
	 */
	public abstract Piece flip();
}
