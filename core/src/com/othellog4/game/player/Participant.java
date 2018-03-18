package com.othellog4.game.player;

import java.util.Optional;

import com.othellog4.game.GameException;
import com.othellog4.game.GameSession;

/**
 * The {@code Participant} interface represents participants which can partake
 * in a {@link GameSession}.
 *
 * <p>
 * The {@code Participant} interface should be implemented by classes which
 * can behave like players.
 * </p>
 *
 * @author 159014260 John Berg
 * @author Zak Hirsi
 * @since 	01/10/2017
 * @version 04/03/2018
 * @see Control
 */
public interface Participant
{
	//=========================================================================
	//Abstract methods.
	/**
	 * Notify <code>this</code> {@link Participant} that it is currently
	 * its turn.
	 *
	 * @param session The {@link GameSession} which it is the turn of
	 * 			<code>this</code> {@link Participant} to make a move.
	 */
	public void notifyTurn(GameSession session);
	/**
	 * Get the {@link Control} of <code>this</code>.
	 *
	 * <p>
	 * The {@link Control} is {@link Optional} which means that a
	 * {@code Participant} is not guaranteed be returned, and the return
	 * value may be {@link Optional#empty()}.
	 * </p>
	 *
	 * <p>
	 * This method should be redefined in subclasses of {@code Participant}.
	 * </p>
	 *
	 * @return The {@link Optional} of {@link Control} to <code>this</code>
	 * 			instance of {@code Participant}. {@link Optional#empty()} is
	 * 			returned if <code>this</code> {@code Participant} is not
	 * 			controllable.
	 */
	public Optional<Control> getControl();
	//=========================================================================
	//Inner classes.
	/**
	 * The {@code Control} interface provides the methods for controlling a
	 * {@link Participant}.
	 *
	 * <p>
	 * The methods in the {@code Control} interface correspond to actions which
	 * can be taken by a {@link Participant}.
	 * </p>
	 *
	 * @author 	159014260 John Berg
	 * @since	01/12/2017
	 * @version 01/12/2017
	 */
	public interface Control
	{
		//=====================================================================
		//Abstract methods.
		/**
		 * Attempt to place a piece on the behalf of a {@link Participant}.
		 *
		 * @param x The column index.
		 * @param y The row index.
		 * @throws GameException If a {@link GameException} occurs.
		 */
		public void put(int x, int y) throws GameException;
		/**
		 * Make the {@link Participant} associated with <code>this</code>
		 * {@code Control} surrender.
		 *
		 * @throws GameException If a {@link GameException} occurs.
		 */
		public void surrender() throws GameException;
		/**
		 * Make the {@link Participant} associated with <code>this</code>
		 * {@code Control} pause the game.
		 *
		 * @throws GameException If a {@link GameException} occurs.
		 */
		public void pause() throws GameException;
		/**
		 * Make the {@link Participant} associated with <code>this</code>
		 * {@code Control} resume a paused game.
		 *
		 * @throws GameException If a {@link GameException} occurs.
		 */
		public void resume() throws GameException;
	}
}
