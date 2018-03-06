package com.othellog4.game.player.ai;

import java.util.function.Consumer;

import com.othellog4.game.board.BoardView;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;

/**
 * The {@code Tactic} class is a record which combines both a
 * {@link SearchStrategy} and a {@link EvaluationStrategy} to formulate
 * a strategy of how to play.
 *
 * @author 	159014260 John Berg
 * @since	14/02/2018
 * @version 15/02/2018
 */
public final class Tactic
{
	//=========================================================================
	//Fields.
	/**
	 * The {@link EvaluationStrategy} which is used to evaluate a
	 * {@link BoardView} object.
	 */
	private final EvaluationStrategy eval;
	/**
	 * The {@link SearchStrategy} which is used to control how to search a
	 * {@link BoardView} object.
	 */
	private final SearchStrategy search;
	//=========================================================================
	//Constructors.
	/**
	 * Create a {@code Tactic} by defining how to search and evaluate a board.
	 *
	 * @param eval The {@link EvaluationStrategy} object used to evaluate a
	 * 			{@link BoardView} object.
	 * @param search The {@link SearchStrategy} object used to search a
	 * 			{@link BoardView} object.
	 */
	public Tactic(
			final EvaluationStrategy eval,
			final SearchStrategy search)
	{
		this.eval = eval;
		this.search = search;
	}
	//=========================================================================
	//Methods.
	/**
	 * Prepare the application of <code>this</code> {@code Tactic} object.
	 *
	 * <p>
	 * Create a {@link Runnable} object which will execute the {@code Tactic}.
	 * This allows for suspended evaluation.
	 * </p>
	 *
	 * @param board The {@link BoardView} object to apply the strategies to.
	 * @param piece The {@link Piece} object to use during the search.
	 * @param callback The function to return the {@link Position} object
	 * 			result from evaluation.
	 * @return The {@link Runnable} object which when run will execute the
	 * 			strategy.
	 */
	public final Runnable plan(
			final BoardView board,
			final Piece piece,
			final Consumer<Position> callback)
	{
		return () -> callback.accept(search.search(board, piece, eval));
	}
}
