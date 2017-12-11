package com.othellog4.game.player;

import com.othellog4.game.board.BoardView;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;

/**
 * 
 * @author 	159014260 John Berg
 * @since 	08/12/2017
 * @version 08/12/2017
 */
@FunctionalInterface
public interface GameStrategy
{
	//=========================================================================
	//Abstract methods.
	/**
	 * 
	 * 
	 * @param piece The {@link Piece} object to find a move for.
	 * @param board The {@link Board} object to find a move on.
	 * @return A {@link Position} to place a {@link Piece} on.
	 */
	public Position evaluate(Piece piece, BoardView board);
}
