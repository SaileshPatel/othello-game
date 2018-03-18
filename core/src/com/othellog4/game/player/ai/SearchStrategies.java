package com.othellog4.game.player.ai;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.othellog4.game.board.BoardView;
import com.othellog4.game.board.InvalidMoveException;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;

/**
 * The {@code SearchStrategies} enumeration is a class containing concrete
 * implementations of the {@link SearchStrategy} interface.
 * 
 * @author 	159014260 John Berg
 * @author  159148026 Arvinder Chatha
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
		//Fields.
		/**
		 * The {@link java.util.Random} object to generate random numbers
		 * when selecting a {@link Position}.
		 */
		private final java.util.Random rng = new java.util.Random(
				new Date().getTime());
		//=====================================================================
		//Overridden methods.
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
	BEST_IMMEDIATE
	{
		/**
		 * Search for the {@link Position} object where s {@link Piece} object
		 * can be placed on a board for the best immediate outcome.
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
	},	
	/**
	 * The implementation of the {@link SearchStrategy} interface which selects
	 * the {@link Position} object which would result in the best score by
	 * the supplied {@link EvaluationStrategy}.
	 */
	MINIMAX
	{
		//=====================================================================
		//Fields.
		/**
		 * The DEPTH_LIMIT determines how deep the minimax algorithm will 
		 * search.
		 */
		final int DEPTH_LIMIT = 4;
		/**
		 * The {@link java.util.Random} object to generate random numbers
		 * when selecting a {@link Position}.
		 */
		private final java.util.Random rng = new java.util.Random(
				new Date().getTime());

		//=====================================================================
		//Overridden methods.
		/**
		 * Search for the {@link Position} object where s {@link Piece} object
		 * can be placed on a board using the MINIMAX algorithm.
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
			Position finalPosition = null;
			try {
				ArrayList<ScorePosition> bestPossiblePositions = minimax(
						board,
						piece,
						eval,
						DEPTH_LIMIT,
						DEPTH_LIMIT % 2 == 0);
				finalPosition = bestPossiblePositions.get(
						rng.nextInt(bestPossiblePositions.size())).getPos(); 
			}
			catch (com.othellog4.game.board.InvalidMoveException e) {
				e.printStackTrace();
			}
			return finalPosition;
		}

		/**
		 * The recursive function used to determine the best possible 
		 * {@link Position} for the current game {@link BoardView}
		 * 
		 * @param board The {@link BoardView} which to search for moves.
		 * @param piece The {@link Piece} object to search for moves for.
		 * @param eval The {@link EvaluationStrategy} used to rank the
		 * 			<code>board</code>.
		 * @param depth The current depth of the recursive function. 
		 * 		  (Inverted, 0 is bottom of tree)
		 * @param isMax True if maximising player, false if minimising player
		 * @return An ArrayList<ScorePosition> storing the best 
		 * 			{@link ScorePosition}s
		 * @throws InvalidMoveException 
		 */
		private ArrayList<ScorePosition> minimax(
				BoardView board,
				Piece piece,
				EvaluationStrategy eval,
				int depth, boolean isMax)
						throws InvalidMoveException {
			ArrayList<ScorePosition> scorePositionList =
					new ArrayList<ScorePosition>();
			List<Position> currentPlayerMoveList = new ArrayList<Position>();
			currentPlayerMoveList.addAll(board.legalMoves(piece));
			Position bestMove = null; 
			double bestScore;
			if(isMax) {
				bestScore = -1000.0; 
			} else {
				bestScore = 1000.0;
			}

			//Leaf node or game ended
			if(board.isEnd() || depth == 0) {
				scorePositionList.add(
						new ScorePosition(eval.evaluate(board, piece), null));
				return scorePositionList;
			}
			//Current player can't take a move due to being no available moves
			else if(board.legalMoves(piece).isEmpty()){
				return minimax(board, piece.flip(), eval, depth-1, !isMax);
			}

			for (Position move : currentPlayerMoveList) {
				BoardView newBoard = board.tryPut(move, piece);;
				ArrayList<ScorePosition> ranks = 
						minimax(newBoard, piece.flip(), eval, depth-1, !isMax);

				//Update scorePositionList
				double currentScore = ranks.get(0).getScore();
				if(currentScore==bestScore) {
					scorePositionList.add(
							new ScorePosition(currentScore, move));
				}					
				else if(isMax && (currentScore > bestScore)) {
					bestScore = currentScore;
					bestMove = move;
					scorePositionList.clear();
					scorePositionList.add(
							new ScorePosition(bestScore, bestMove));
				}
				else if(!isMax && (currentScore < bestScore)){
					bestScore = currentScore;
					bestMove = move;
					scorePositionList.clear();
					scorePositionList.add(
							new ScorePosition(bestScore, bestMove));
				}
			}
			return scorePositionList;
		}

		/**
		 * Class that exists to store the score of a given {@link Position}.
		 * 
		 * @author 	159148026 Arvinder Chatha
		 * @since 	14/03/2017
		 * @version 13/03/2018
		 */
		class ScorePosition {

			private double score;
			private Position pos;

			/**
			 * The constructor initialises both the score and {@link Position}
			 * @param score The score associated to the {@link Position}
			 * @param pos The {@link Position} associated to the score
			 */
			private ScorePosition(Double score, Position pos) {
				this.score = score;
				this.pos = pos;
			}
			/**
			 * @return the score
			 */
			public double getScore() {
				return score;
			}
			/**
			 * @return the pos
			 */
			public Position getPos() {
				return pos;
			}
		}
	}
};
