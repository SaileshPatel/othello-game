package com.othellog4.game.player.ai;

import java.util.Set;

import com.othellog4.game.board.BoardView;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;

/**
 * 
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
	 * 
	 */
	RANDOM_SELECTION
	{
		/**
		 * The seed for the random number generator.
		 */
		private static final int RNG_SEED = 5;
		/**
		 * The {@link java.util.Random} object to generate random numbers
		 * when selecting a {@link Position}.
		 */
		private final java.util.Random rng = new java.util.Random(RNG_SEED);
		/**
		 * 
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
	}
}