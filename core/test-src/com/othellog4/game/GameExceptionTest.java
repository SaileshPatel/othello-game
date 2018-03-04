package com.othellog4.game;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * The JUnit test suit for the {@link GameException} class.
 * 
 * @author 	159014260 John Berg
 * @since	03/02/2018
 * @version 02/03/2018
 */
public class GameExceptionTest
{
	/**
	 * Test the {@link GameException#getCause()} of the {@link GameException}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link GameException#getCause()}
	 * returns <code>null</code> when a {@link GameException} object was
	 * initialised with a <code>null</code> argument.
	 * </p>
	 */
	@Test
	public final void testGetCause_Null()
	{
		final GameException ge = new GameException(null);
		assertEquals(null, ge.getCause());
	}
	/**
	 * Test the {@link GameException#getCause()} of the {@link GameException}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link GameException#getCause()}
	 * returns the {@link Exception} which was used to initialise the
	 * {@link GameException} object.
	 * </p>
	 */
	@Test
	public final void testGetCause_SameException()
	{
		final Exception e = new NullPointerException();
		final GameException ge = new GameException(e);
		assertEquals(e, ge.getCause());
	}
	/**
	 * Test the {@link GameException#toString()} method of the
	 * {@link GameException} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link GameException#toString()}
	 * returns a {@link String} object which is equal to the {@link Exception}
	 * contained in the {@link GameException} object.
	 * </p>
	 */
	@Test
	public final void testToString_SameString()
	{
		final Exception e = new NullPointerException();
		final GameException ge = new GameException(e);
		assertEquals(e.toString(), ge.toString());
	}
}
