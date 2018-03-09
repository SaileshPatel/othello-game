package com.othellog4.game.player.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
	},
	MINIMAX
	{
		@Override
		public final Position search(
				final BoardView board,
				final Piece piece,
				final EvaluationStrategy eval)
		{
			//MUST BE EVEN NUMBER OTHERWISE IT FAVOURS OPPONENT
			//TOTAL TURNS IT PLAYS AHEAD IS 4 BUT DIVIDE BY 2 FOR EACH COLOUR'S TURN
			final int DEPTH_LIMIT = 4;
			
			Iterator<Position> iter = minimax(board, piece, eval, DEPTH_LIMIT, true).values().iterator();
			
			Position finalPosition = (Position) iter.next();
			return finalPosition;
		}
		
		private HashMap<Double, Position> minimax(BoardView board, Piece piece, EvaluationStrategy eval, int depth, boolean isMax) {
			
			int currentDepth = depth; //might not be needed
			HashMap<Double, Position> scorePositionMap = new HashMap<Double, Position>();
			Position bestMove = null; 
			
			//Leaf node
			if(currentDepth == 0) {
				Double score = eval.evaluate(board, piece);
				scorePositionMap.put(score, null);
				return scorePositionMap;
			}
			
			if(isMax) {
				double bestScore = -1000; 
				
				List<Position> currentPlayerMoveList = new ArrayList<Position>();
				currentPlayerMoveList.addAll(board.legalMoves(piece));
				
				for (Position move : currentPlayerMoveList) {
					BoardView newBoard = null;
					try {
						newBoard = board.tryPut(move, piece);
					} catch (com.othellog4.game.board.InvalidMoveException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Double currentScore = -1000.0;
					//If it is a leaf node just evaluate the move otherwise, continue recursion
					try {
					currentScore = (Double) minimax(newBoard, piece.flip(), eval, currentDepth-1, !isMax).keySet().toArray()[0];
					}
					catch(ArrayIndexOutOfBoundsException e) {
						currentScore = eval.evaluate(newBoard, piece);
					}
					
					
					if(currentScore > bestScore) {
						bestScore = currentScore;
						bestMove = move;
						scorePositionMap.clear();
						scorePositionMap.put(bestScore, bestMove);
					}
				}
				return scorePositionMap;
			}
			//!isMax
			else {
				double bestScore = 1000;
				
				
				List<Position> currentPlayerMoveList = new ArrayList<Position>();
				currentPlayerMoveList.addAll(board.legalMoves(piece));
				
				for(Position move : currentPlayerMoveList) {
					BoardView newBoard = null;
					try {
						newBoard = board.tryPut(move, piece);
					} catch (com.othellog4.game.board.InvalidMoveException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Double currentScore = -1000.0;
					//If it is a leaf node just evaluate the move otherwise, continue recursion
					try {
					currentScore = (Double) minimax(newBoard, piece.flip(), eval, currentDepth-1, !isMax).keySet().toArray()[0];
					}
					catch(ArrayIndexOutOfBoundsException e) {
						currentScore = eval.evaluate(newBoard, piece);
					}
					
					if(currentScore < bestScore) {
						bestScore = currentScore;
						bestMove = move;
						scorePositionMap.clear();
						scorePositionMap.put(bestScore, bestMove);
					}
				}
				return scorePositionMap;
			}
		}
	};
}