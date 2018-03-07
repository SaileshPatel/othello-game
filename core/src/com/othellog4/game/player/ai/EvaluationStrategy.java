package com.othellog4.game.player.ai;

import com.othellog4.game.board.BoardView;
import com.othellog4.game.board.Piece;

/**
 * The {@code EvaluationStrategy} is a functional interface which defines the
 * signature used for evaluating the state of a {@link BoardView} object.
 *
 * @author 	159014260 John Berg
 * @since	25/01/2018
 * @version 25/01/2018
 */
@FunctionalInterface
public interface EvaluationStrategy
{
	/**
	 * Evaluate the state of a {@link BoardView} object for a given
	 * {@link Piece} object.
	 *
	 * <p>
	 * Implementation of this method should follow these cases:
	 * </p>
	 *
	 * <p>
	 * <ul>
	 * 		<li>
	 * 			When the returned value is a positive number, then the state of
	 * 			the {@link BoardView} object is in a favourable state towards
	 * 			the given {@link Piece} object.
	 * 		</li>
	 * 		<li>
	 * 			When the returned value is <code>0.0</code> the state of the
	 * 			{@link BoardView} object, the board is in a state which is
	 * 			neutral to the {@link Piece} object.
	 * 		</li>
	 * 		<li>
	 * 			When the returned value is negative, the {@link BoardView}
	 * 			object is in an unfavourable state towards the {@link Piece}
	 * 			object.
	 * 		</li>
	 * </ul>
	 * </p>
	 *
	 * @param board The {@link BoardView} object to be evaluated.
	 * @param piece The {@link Piece} object which the is used during the
	 * 			evaluation of the board.
	 * @return The evaluation ranking of the current <code>board</code> for a
	 * 			given <code>piece</code>. When 0 < the evaluation is a
	 * 			favourable state of the <code>board</code> for the specified
	 * 			<code>piece</code>.
	 */
	public double evaluate(final BoardView board, final Piece piece);
}
