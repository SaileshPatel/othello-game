package com.othellog4.game;

import com.othellog4.game.board.Piece;
import com.othellog4.game.player.Participant;

/**
 * 
 * 
 * 
 * @author 	1159014260 John Berg
 * @since	17/02/2018
 * @version	20/02/2018
 */
public final class GameScore
{
	//=========================================================================
	//Fields.
	/**
	 * The {@link GameManager} object which created the <code>this</code>
	 * {@code GameScore} object.
	 * 
	 * <p>
	 * This {@link GameManager} will used by <code>this</code>
	 * {@code GameScore} object, to generate statistics.
	 * </p>
	 * 
	 * @see GameManager
	 */
	private final GameManager manager;
	/**
	 * 
	 * 
	 * @param manager The {@link GameManager} which is creating the
	 * 			{@code GameScore} object.
	 * @throws IllegalArgumentException If the {@link Game} of the
	 * 			<code>manager</code> is not over.
	 */
	GameScore(final GameManager manager)
			throws IllegalArgumentException
	{
		if(!manager.game().isGameOver())
			throw new IllegalArgumentException();
		this.manager = manager;
	}
	//=========================================================================
	//Methods.
	/**
	 * Check if the outcome of a {@link Game} was a draw.
	 * 
	 * @return <code>true</code> if the outcome was a draw, otherwise, returns
	 * 			<code>false</code>.
	 */
	public final boolean isDraw()
	{
		return conclusion().isDraw();
	}
	/**
	 * Get the final turn of a {@link Game} object.
	 * 
	 * @return The final turn of a {@link Game} object.
	 */
	public final int turn()
	{
		return manager.game().turn();
	}
	/**
	 * Get the <code>int</code> which represents the final score of a
	 * {@link Piece} object.
	 * 
	 * @param piece The {@link Piece} object to get the score for.
	 * @return The score for the <code>piece</code> object.
	 */
	public final int score(final Piece piece)
	{
		return manager.calculateScore(piece);
	}
	/**
	 * 
	 * 
	 * @param player The {@link Participant} object to get the score for.
	 * @return The score for the <code>player</code> object.
	 */
	public final int score(final Participant player)
	{
		return 0;
	}
	/**
	 * Get the {@link Participant} object which was the winner of a
	 * {@link Game} object.
	 * 
	 * @return The winning {@link Participant} object.
	 * @throws IllegalStateException If {@link GameScore#isDraw()} returns
	 * 			<code>true</code>.
	 */
	public final Participant winner()
			throws
			IllegalStateException
	{
		//May throw an IllegalStateException.
		return manager.playerOf(conclusion().getWinner());
	}
	/**
	 * Get the {@link Participant} object which was the loser of a
	 * {@link Game} object.
	 * 
	 * @return The losing {@link Participant} object.
	 * @throws IllegalStateException If {@link GameScore#isDraw()} returns
	 * 			<code>true</code>.
	 */
	public final Participant loser()
			throws
			IllegalStateException
	{
		//May throw an IllegalStateException.
		return manager.playerOf(conclusion().getLoser());
	}
	/**
	 * Get the {@link GameConclusion} object which represent the conclusion of
	 * a {@link Game}.
	 * 
	 * @return The {@link GameConclusion} from a {@link Game}.
	 */
	public final GameConclusion conclusion()
	{
		return manager.game().getConclusion();
	}
	/**
	 * Get the {@link String} array of results from a {@link Game} for a
	 * {@link Piece} object.
	 * 
	 * @param piece The {@link Piece} object to get the results for.
	 * @return The {@link String} array which represent results for the
	 * 			<code>piece</code>.
	 */
	public final String[] results(final Piece piece)
	{
		return manager.getResults(piece);
	}
	//=========================================================================
	//Overriden methods.
	/**
	 * 
	 * @return
	 */
	@Override
	public final String toString()
	{
		return "";
	}
}
