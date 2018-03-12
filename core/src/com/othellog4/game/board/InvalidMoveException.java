package com.othellog4.game.board;

/**
 * A new exception which builds upon {@link java.lang.Exception Exception} for invalid moves
 * @author James Shorthouse
 * @since 29/11/2017
 *
 */
public class InvalidMoveException extends Exception {

	//private Piece type;
	private Position cord;

	/**
	 * The constructor for this exeception. It calls {@link java.lang.Exception#Exception parent constructor}
	 * @param cord the {@link com.othellog4.game.board.Position Position} of the move
	 * @param type the type of {@link com.othellog4.game.board.Piece Piece} being moved to the 
	 * {@link com.othellog4.game.board.Position Position}
	 */
	public InvalidMoveException(Position cord ,Piece type){
		super();
		//this.type = type;
		this.cord = cord;
	}

	@Override
	public String toString(){
		String temp;
		temp = "The move " + cord.toString() + " is not a valid move, please try again";
		return temp;
	}
}
