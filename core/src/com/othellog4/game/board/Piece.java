package com.othellog4.game.board;

/**
 * This class contains enums which represents Pieces on the board.
 * 
 * @author 	159014260 John Berg
 * @since 	26/10/2017
 * @version 26/10/2017
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
	}/*,
	PIECE_NULL{
		@Override
		public Piece flip(){
			return PIECE_NULL;
		}
	}*/;
	
	/**
	 * A private constructor which merely constructs the enums.
	 */
	private Piece(){}
	
	/**
	 * An abstract method which returns another piece.
	 * 
	 * @return returns a piece (usually an opposing piece)
	 */
	public abstract Piece flip();
	//=========================================================================
	//Static methods.
	/**
	 * Get the {@link Piece} object which represents the first player.
	 * 
	 * @return {@link Piece#PIECE_A}.
	 */
	public static final Piece player1()
	{
		return Piece.PIECE_A;
	}
	/**
	 * Get the {@link Piece} object which represents the second player.
	 * 
	 * @return {@link Piece#PIECE_B}.
	 */
	public static final Piece player2()
	{
		return Piece.PIECE_B;
	}
}
