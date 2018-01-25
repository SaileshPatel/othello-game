package com.othellog4.game.player.ai;

import com.othellog4.game.board.BoardView;
import com.othellog4.game.board.Piece;

/**
 * 
 * @author 	159014260 John Berg
 * @since 	25/01/2018
 * @version 25/01/2018
 */
public enum EvaluationStrategies implements EvaluationStrategy
{
	COUNT
	{
		@Override
		public double evaluate(
				final BoardView board,
				final Piece piece)
		{
			// TODO Auto-generated method stub
			//num of piece - num of other piece div total num of pieces.
			return 0;
		}
	};
	@Override
	public abstract double evaluate(
			final BoardView board,
			final Piece piece);
}
