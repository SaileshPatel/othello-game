package com.othellog4.game.player.ai;

import com.othellog4.game.board.BoardView;
import com.othellog4.game.board.Piece;

/**
 * The {@code EvaluationStrategies} enumeration is a class containing concrete
 * implementations of the {@link EvaluationStrategy} interface.
 *
 * @author 	159014260 John Berg
 * @since 	25/01/2018
 * @version 15/02/2018
 */
public enum EvaluationStrategies implements EvaluationStrategy
{
	/**
	 * Evaluation based on the winner of a game.
	 */
	WINNER
	{
		/**
		 * Rank the {@link BoardView} based on the winner.
		 *
		 * <p>
		 * If the {@link BoardView} is:
		 * </p>
		 *
		 * <ul>
		 * 		<li>
		 * 			Draw: <code>0.0</code>.
		 * 		</li>
		 * 		<li>
		 * 			Favourable: <code>1.0</code>.
		 * 		</li>
		 * 		<li>
		 * 			Unfavourable: <code>-1.0</code>.
		 * 		</li>
		 * </ul>
		 *
		 * @param board The {@link BoardView} object to evaluate.
		 * @param The {@link Piece} object which is the basis of the
		 * 			evaluation.
		 * @return The <code>double</code> ranking of the <code>board</code>
		 * 			based on the <code>piece</code>.
		 */
		@Override
		public double evaluate(
				final BoardView board,
				final Piece piece)
		{
			if(board.isDraw())
				return 0.0;
			return board.winning() == piece? 1.0: -1.0;
		}
	},
	/**
	 * Evaluation based on the number of {@link Piece} objects.
	 */
	COUNT
	{
		/**
		 * Evaluate the {@link BoardView} based on the rate of {@link Piece}
		 * objects of a specific type.
		 *
		 * <p>
		 * The evaluation process:
		 * </p>
		 *
		 * <ol>
		 * 		<li>
		 * 			Get the total number of placed {@link Piece} objects for a
		 * 			specified {@link Piece} and the opposing {@link Piece}
		 * 			object.
		 * 		</li>
		 * 		<li>
		 * 			If the sum of the total {@link Piece} objects is
		 * 			<code>0.0</code>, then the rating is <code>0.0</code>.
		 * 		</li>
		 * 		<li>
		 * 			Compute the ranking as:
		 * 			<code>(piece - otherPiece) / (piece + otherPiece)</code>.
		 * 		</li>
		 * </ol>
		 *
		 * @param board The {@link BoardView} object to evaluate.
		 * @param The {@link Piece} object which is the basis of the
		 * 			evaluation.
		 * @return The <code>double</code> ranking of the <code>board</code>
		 * 			based on the <code>piece</code>.
		 */
		@Override
		public double evaluate(
				final BoardView board,
				final Piece piece)
		{
			final double a = board.count(piece);
			final double b = board.count(piece.flip());
			if(a + b == 0)
				return 0.0;
			return (a - b) / (a + b);
		}
	};
}
