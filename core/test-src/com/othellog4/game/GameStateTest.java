package com.othellog4.game;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * The {@code GameStateTest} class is a JUnit test suit for the
 * {@link GameState} class.
 * 
 * @author 	159014260 John Berg
 * @since	05/02/2018
 * @version 06/02/2018
 */
public class GameStateTest
{
	/**
	 * Test the {@link GameState#start()} method of the {@link GameState#READY}
	 * object.
	 * 
	 * <p>
	 * This test will pass if the {@link GameState#start()} method returns the
	 * {@link GameState#PLAYING} object.
	 * </p>
	 */
	@Test
	public final void testStart_READY()
	{
		assertEquals(GameState.PLAYING, GameState.READY.start());
	}
	/**
	 * Test the {@link GameState#pause()} method of the {@link GameState#READY}
	 * object.
	 * 
	 * <p>
	 * This test will pass if the {@link GameState#pause()} method throws an
	 * {@link IllegalStateException}.
	 * </p>
	 */
	@Test(expected = IllegalStateException.class)
	public final void testPause_READY()
	{
		GameState.READY.pause();
	}
	/**
	 * Test the {@link GameState#end()} method of the {@link GameState#READY}
	 * object.
	 * 
	 * <p>
	 * This test will pass if the {@link GameState#end()} method returns the
	 * {@link GameState#GAME_OVER} object.
	 * </p>
	 */
	@Test
	public final void testEnd_READY()
	{
		assertEquals(GameState.GAME_OVER, GameState.READY.end());
	}
	/**
	 * Test the {@link GameState#start()} method of the
	 * {@link GameState#PLAYING} object.
	 * 
	 * <p>
	 * This test will pass if the {@link GameState#start()} method throws an
	 * {@link IllegalStateException}.
	 * </p>
	 */
	@Test(expected = IllegalStateException.class)
	public final void testStart_PLAYING()
	{
		GameState.PLAYING.start();
	}
	/**
	 * Test the {@link GameState#pause()} method of the
	 * {@link GameState#PLAYING} object.
	 * 
	 * <p>
	 * This test will pass if the {@link GameState#pause()} method returns the
	 * {@link GameState#PAUSED} object.
	 * </p>
	 */
	@Test
	public final void testPause_PLAYING()
	{
		assertEquals(GameState.PAUSED, GameState.PLAYING.pause());
	}
	/**
	 * Test the {@link GameState#end()} method of the
	 * {@link GameState#PLAYING} object.
	 * 
	 * <p>
	 * This test will pass if the {@link GameState#end()} method returns the
	 * {@link GameState#GAME_OVER} object.
	 * </p>
	 */
	@Test
	public final void testEnd_PLAYING()
	{
		assertEquals(GameState.GAME_OVER, GameState.PLAYING.end());
	}
	/**
	 * Test the {@link GameState#start()} method of the
	 * {@link GameState#PAUSED} object.
	 * 
	 * <p>
	 * This test will pass if the {@link GameState#start()} method returns the
	 * {@link GameState#PLAYING} object.
	 * </p>
	 */
	@Test
	public final void testStart_PAUSED()
	{
		assertEquals(GameState.PLAYING, GameState.PAUSED.start());
	}
	/**
	 * Test the {@link GameState#pause()} method of the
	 * {@link GameState#PAUSED} object.
	 * 
	 * <p>
	 * This test will pass if the {@link GameState#pause()} method throws
	 * an {@link IllegalStateException}.
	 * </p>
	 */
	@Test(expected = IllegalStateException.class)
	public final void testPause_PAUSED()
	{
		GameState.PAUSED.pause();
	}
	/**
	 * Test the {@link GameState#end()} method of the
	 * {@link GameState#PAUSED} object.
	 * 
	 * <p>
	 * This test will pass if the {@link GameState#end()} method returns the
	 * {@link GameState#GAME_OVER} object.
	 * </p>
	 */
	@Test
	public final void testEnd_PAUSED()
	{
		assertEquals(GameState.GAME_OVER, GameState.PAUSED.end());
	}
	/**
	 * Test the {@link GameState#start()} method of the
	 * {@link GameState#GAME_OVER} object.
	 * 
	 * <p>
	 * This test will pass if the {@link GameState#start()} method throws
	 * an {@link IllegalStateException}.
	 * </p>
	 */
	@Test(expected = IllegalStateException.class)
	public final void testStart_GAME_OVER()
	{
		GameState.GAME_OVER.start();
	}
	/**
	 * Test the {@link GameState#pause()} method of the
	 * {@link GameState#GAME_OVER} object.
	 * 
	 * <p>
	 * This test will pass if the {@link GameState#pause()} method throws
	 * an {@link IllegalStateException}.
	 * </p>
	 */
	@Test(expected = IllegalStateException.class)
	public final void testPause_GAME_OVER()
	{
		GameState.GAME_OVER.pause();
	}
	/**
	 * Test the {@link GameState#end()} method of the
	 * {@link GameState#GAME_OVER} object.
	 * 
	 * <p>
	 * This test will pass if the {@link GameState#end()} method throws
	 * an {@link IllegalStateException}.
	 * </p>
	 */
	@Test(expected = IllegalStateException.class)
	public final void testEnd_GAME_OVER()
	{
		assertEquals(GameState.PLAYING, GameState.GAME_OVER.end());
	}
}
