package com.othellog4.game.command;

import org.junit.Test;

import com.othellog4.game.GameException;

/**
 * The JUnit test suit for the {@link GameCommand} class.
 * 
 * <p>
 * The {@code GameCommandTest} JUnit test suit is abstract and does not contain
 * no concrete tests.
 * </p>
 * 
 * <p>
 * Subclasses of the {@code GameCommandTest} which test specific implementation
 * must provide implementation to the tests defined in the
 * {@code GameCommandTest} suit.
 * </p>
 * 
 * @author 	159014260 John Berg
 * @since	15/02/2018
 * @version 05/03/2018
 */
public abstract class GameCommandTest
{
	//=========================================================================
	//Abstract tests.
	/**
	 * Test the {@link GameCommand#getSource()} method.
	 */
	@Test
	public abstract void testGetSource();
	/**
	 * Test the
	 * {@link GameCommand#canExecute(com.othellog4.game.player.Participant)}
	 * method.
	 */
	@Test
	public abstract void testCanExecute_IssuersTurn();
	/**
	 * Test the
	 * {@link GameCommand#canExecute(com.othellog4.game.player.Participant)}
	 * method.
	 */
	@Test
	public abstract void testCanExecute_NotIssuersTurn();
	/**
	 * Test the {@link GameCommand#execute(com.othellog4.game.Game)} method.
	 * 
	 * @throws GameException If a {@link GameException} occurs.
	 */
	@Test
	public abstract void testExecute_NullArg() throws GameException;
	/**
	 * Test the {@link GameCommand#execute(com.othellog4.game.Game)} method.
	 * 
	 * @throws GameException If a {@link GameException} occurs.
	 */
	@Test
	public abstract void testExecute_Error() throws GameException;
	/**
	 * Test the {@link GameCommand#execute(com.othellog4.game.Game)} method.
	 * 
	 * @throws GameException If a {@link GameException} occurs.
	 */
	@Test
	public abstract void testExecute_Success() throws GameException;
	/**
	 * Test the {@link GameCommand#toString()} method.
	 */
	@Test
	public abstract void testToString();
}
