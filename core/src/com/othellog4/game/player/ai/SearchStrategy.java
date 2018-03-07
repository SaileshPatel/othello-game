package com.othellog4.game.player.ai;

import com.othellog4.game.board.BoardView;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;

/**
 * The {@code SearchStrategy} interface is a functional interface which defines
 * the signature for searching for possible moves.
 *
 * @author 	159014260 John Berg
 * @since 	08/12/2017
 * @version 25/01/2018
 */
@FunctionalInterface
public interface SearchStrategy
{
	/**
	 * Search a {@link BoardView} object for a {@link Position} object where a
	 * specified {@link Piece} object should be placed.
	 *
	 * @param board The {@link BoardView} object to search.
	 * @param piece The {@link Piece} object used to find a {@link Position}
	 * 			object to place the {@link Piece} object.
	 * @param eval The {@link EvaluationStrategy} which defines how the state
	 * 			of the <code>board</code> is evaluated.
	 * @return The {@link Position} object which represents the place to put a
	 * 			{@link Piece} object, which is determined as a result of this
	 * 			method.
	 */
	public Position search(
			final BoardView board,
			final Piece piece,
			final EvaluationStrategy eval);
}
