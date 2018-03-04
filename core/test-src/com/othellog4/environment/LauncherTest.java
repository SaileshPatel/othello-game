package com.othellog4.environment;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.othellog4.game.GameModel;

/**
 * The JUnit test suit for the {@link Launcher} class.
 * 
 * @author 	159015260 John Berg
 * @since	03/03/2018
 * @version 04/03/2018
 */
public class LauncherTest
{
	//=========================================================================
	//Before and after.
	/**
	 * After each test, clear the {@link Launcher} so that the cache is empty.
	 */
	@After
	public final void tearDown()
	{
		Launcher.get().clear();
	}
	//=========================================================================
	//Tests.
	/**
	 * Test the {@link Launcher#hasCache()} method of the {@link Launcher}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Launcher#hasCache()} returns
	 * <code>false</code> when no {@link GameModel} object has been cached.
	 * </p>
	 */
	@Test
	public final void testHasCache_None()
	{
		assertFalse(Launcher.get().hasCache());
	}
	/**
	 * Test the {@link Launcher#hasCache()} method of the {@link Launcher}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Launcher#hasCache()} returns
	 * <code>true</code> when a {@link GameModel} object has been cached.
	 * </p>
	 */
	@Test
	public final void testHasCache_Cached()
	{
		Launcher.get().cache(Launcher.get().newGame(
				PlayerType.USER,
				PlayerType.USER,
				Mode.BASIC));
		assertTrue(Launcher.get().hasCache());
	}
	/**
	 * Test the {@link Launcher#clear()} method of the {@link Launcher} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Launcher#clear()} method
	 * removes the {@link GameModel} which was previously cached.
	 * </p>
	 * 
	 * <p>
	 * The {@link Launcher#hasCache()} method will be used to check if the
	 * cache has been cleared.
	 * </p>
	 */
	@Test
	public final void testClear()
	{
		Launcher.get().cache(Launcher.get().newGame(
				PlayerType.USER,
				PlayerType.USER,
				Mode.BASIC));
		Launcher.get().clear();
		assertFalse(Launcher.get().hasCache());
	}
	/**
	 * Test the {@link Launcher#cache(GameModel)} method of the {@link Launcher}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Launcher#cache(GameModel)}
	 * method throws a {@link NullPointerException} when provided a
	 * {@link GameModel} which is <code>null</code>.
	 * </p>
	 */
	@Test(expected = NullPointerException.class)
	public final void testCache_NullArg()
	{
		Launcher.get().cache(null);
	}
	/**
	 * Test the {@link Launcher#cache(GameModel)} method of the {@link Launcher}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Launcher#cache(GameModel)}
	 * method caches the {@link GameModel} which was provided.
	 * </p>
	 * 
	 * <p>
	 * The {@link Launcher#release()} will be used to check if the
	 * {@link GameModel} is the same one which was cached.
	 * </p>
	 */
	@Test
	public final void testCache_Model()
	{
		final GameModel model = Launcher.get().newGame(
				PlayerType.USER,
				PlayerType.USER,
				Mode.BASIC);
		Launcher.get().cache(model);
		assertEquals(model, Launcher.get().release());
	}
	/**
	 * Test the {@link Launcher#release()} method of the {@link Launcher}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Launcher#release()} method
	 * throws an {@link IllegalStateException} when attempting to release
	 * a {@link GameModel} when the cache is empty.
	 * </p>
	 */
	@Test(expected = IllegalStateException.class)
	public final void testRelease_None()
	{
		Launcher.get().release();
	}
	/**
	 * Test the {@link Launcher#release()} method of the {@link Launcher}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Launcher#release()} method
	 * makes the {@link Launcher} object have an empty cache after invocation.
	 * </p>
	 * 
	 * <p>
	 * The {@link Launcher#hasCache()} is used to check if the cache has been
	 * cleared.
	 * </p>
	 */
	@Test
	public final void testRelease_After()
	{
		Launcher.get().cache(Launcher.get().newGame(
				PlayerType.USER,
				PlayerType.USER,
				Mode.BASIC));
		Launcher.get().release();
		assertFalse(Launcher.get().hasCache());
	}
	/**
	 * Test the {@link Launcher#newGame(PlayerType, PlayerType, Mode)} method
	 * of the {@link Launcher} class.
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link Launcher#newGame(PlayerType, PlayerType, Mode)} method returns
	 * a {@link GameModel} which is not equal to any other {@link GameModel}
	 * objects subsequently created by the
	 * {@link Launcher#newGame(PlayerType, PlayerType, Mode)} method.
	 * </p>
	 */
	@Test
	public final void testNewGame()
	{
		final GameModel model = Launcher.get().newGame(
				PlayerType.USER,
				PlayerType.USER,
				Mode.BASIC);
		assertNotEquals(model, Launcher.get().newGame(
				PlayerType.USER,
				PlayerType.USER,
				Mode.BASIC));
	}
	/**
	 * Test the {@link Launcher#get()} method of the {@link Launcher} class.
	 * 
	 * <p>
	 * This test should only if the {@link Launcher#get()} method returns
	 * the same {@link Launcher} object every time.
	 * </p>
	 */
	@Test
	public final void testGet()
	{
		assertSame(Launcher.get(), Launcher.get());
	}
}
