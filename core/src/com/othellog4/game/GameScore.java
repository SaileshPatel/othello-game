package com.othellog4.game;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.othellog4.game.board.BoardView;
import com.othellog4.game.extension.GameExtension;
import com.othellog4.game.player.Participant;
import com.othellog4.game.player.Player;

/**
 * The {@code GameScore} class is an object which provides the ability
 * to obtain and calculate results and scores from a game.
 *
 * @author 	1159014260 John Berg
 * @since	17/02/2018
 * @version	06/03/2018
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
	 * Create a {@code GameScore} object with a {@link GameManager} which
	 * manages a {@link Game} which is over.
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
	 * Get the {@link Participant} object which acted as the first player.
	 * 
	 * @return The {@link Participant} object which is the first player.
	 */
	public final Participant player1()
	{
		return manager.player1();
	}
	/**
	 * Get the {@link Participant} object which acted as the second player.
	 * 
	 * @return The {@link Participant} object which is the second player.
	 */
	public final Participant player2()
	{
		return manager.player2();
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
	 * Get the {@link GameResult} objects from <code>this</code>
	 * {@code GameScore} object.
	 *
	 * @return The Mpa of {@link GameExtension} {@link Class} objects and the
	 * 			corresponding {@link GameResult} object.
	 */
	public final Map<Class<? extends GameExtension>, GameResult> results()
	{
		return manager.getResults();
	}
	/**
	 * Get the {@link Player} objects which can be controlled.
	 * 
	 * @return The {@link Set} of {@link Participant} objects which can be
	 * 			controlled.
	 */
	public final Set<Participant> getControlable()
	{
		return new HashSet<Participant>(3)
		{{
			if(manager.player1().getControl().isPresent())
				add(manager.player1());
			if(manager.player2().getControl().isPresent())
				add(manager.player2());
		}};
	}
	/**
	 * Get the {@link BoardView} of the completed {@link Game}.
	 * 
	 * @return The {@link BoardView} of the completed {@link Game}.
	 */
	public final BoardView getBoard()
	{
		return manager.game().getBoard();
	}
}
