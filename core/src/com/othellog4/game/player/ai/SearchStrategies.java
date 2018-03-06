package com.othellog4.game.player.ai;

import java.util.Set;

import com.othellog4.game.board.BoardView;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;

/**
 * The {@code SearchStrategies} enumeration is a class containing concrete
 * implementations of the {@link SearchStrategy} interface.
 * 
 * @author 	159014260 John Berg
 * @since 	08/12/2017
 * @version 08/12/2017
 */
public enum SearchStrategies implements SearchStrategy
{
	//=========================================================================
	//Enum constants.
	/**
	 * The implementation of the {@link SearchStrategy} which randomly selects
	 * a position where a {@link Piece} object should be placed.
	 */
	RANDOM_SELECTION
	{
		//=====================================================================
		//Static fields.
		/**
		 * The seed for the random number generator.
		 */
		private static final int RNG_SEED = 5;
		//=====================================================================
		//Fields.
		/**
		 * The {@link java.util.Random} object to generate random numbers
		 * when selecting a {@link Position}.
		 */
		private final java.util.Random rng = new java.util.Random(RNG_SEED);
		//=====================================================================
		//Overriden methods.
		/**
		 * Search for a random {@link Position} where a {@link Piece} object
		 * can be placed on a {@link BoardView} object.
		 * 
		 * @param board The {@link BoardView} which to search for moves.
		 * @param piece The {@link Piece} object to search for moves for.
		 * @param eval Unused.
		 * @return The selected {@link Position} object.
		 */
		@Override
		public final Position search(
				final BoardView board,
				final Piece piece,
				final EvaluationStrategy eval)
		{
			final Set<Position> legalMoves = board.legalMoves(piece);
			//Temporary array for storing the positions.
			final Position[] temp = new Position[legalMoves.size()];
			return legalMoves.toArray(temp)[rng.nextInt(legalMoves.size())];
		}
	},
	/**
	 * The implementation of the {@link SearchStrategy} interface which selects
	 * the {@link Position} object which would result in the best score by
	 * the supplied {@link EvaluationStrategy}.
	 */
	BEST_IMMIDIATE
	{
		/**
		 * Search for the {@link Position} object where s {@link Piece} object
		 * can be placed on a board for the best immidiate outcome.
		 * 
		 * @param board The {@link BoardView} which to search for moves.
		 * @param piece The {@link Piece} object to search for moves for.
		 * @param eval The {@link EvaluationStrategy} used to rank the
		 * 			<code>board</code>.
		 * @return The selected {@link Position} object.
		 */
		@Override
		public final Position search(
				final BoardView board,
				final Piece piece,
				final EvaluationStrategy eval)
		{
			return board.legalMoves(piece).stream().reduce((acc, pos) ->
			{
				try
				{
					return eval.evaluate(board.tryPut(acc, piece), piece) >
							eval.evaluate(board.tryPut(pos, piece), piece)
							?acc
							:pos;
				}
				catch(final com.othellog4.game.board.InvalidMoveException e)
				{
					e.printStackTrace();
					return pos;
				}
			}).get();
		}
	};
}