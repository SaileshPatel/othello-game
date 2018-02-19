package com.othellog4.game;

import org.junit.Before;
import org.junit.Test;

import com.othellog4.game.board.GameBoard;
import com.othellog4.game.board.InvalidMoveException;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;
import static org.junit.Assert.*;

/**
 * The {@code GameTest} class is a Junit test suit for the {@link Game} class.
 * 
 * @author 	159014260 John Berg
 * @since 	25/01/2018
 * @version 19/02/2018
 */
public class GameTest
{
	//=========================================================================
	//Static fields.
	/**
	 * 
	 */
	private static final Position OUTSIDE_BOARD = Position.at(-1, -1);
	/**
	 * 
	 */
	private static final Position INVALID_MOVE = Position.at(1, 1);
	/**
	 * 
	 */
	private static final Position LEGAL_MOVE = Position.at(5, 3);
	//=========================================================================
	//Fields.
	/**
	 * 
	 */
	private Game game;
	//=========================================================================
	//Before.
	/**
	 * 
	 */
	@Before
	public final void setup()
	{
		game = new Game(new GameBoard(8, 8));
	}
	//=========================================================================
	//Test.
	/**
	 * 
	 */
	@Test
	public final void testIsGameOver_NotGameOver()
	{
		assertFalse(game.isGameOver());
	}
	@Test
	public final void testIsGameOver()
	{
		game.start();
		game.end();
		assertTrue(game.isGameOver());
	}
	@Test
	public final void testIsDraw()
	{
		fail();
	}
	/**
	 * 
	 */
	@Test
	public final void testTurn_Beginning()
	{
		fail();
	}
	/**
	 * 
	 */
	@Test
	public final void testTurn_NextTurn()
	{
		fail();
	}
	/**
	 * Test the {@link Game#start()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#start()} method makes the
	 * {@link Game} object be in the  {@link GameState#PLAYING} state.
	 * </p>
	 */
	@Test
	public final void testStart_Beginning()
	{
		game.start();
		assertEquals(GameState.PLAYING, game.getCurrentState());
	}
	/**
	 * Test the {@link Game#start()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#start()} method throws
	 * an {@link IllegalStateException} when the {@link Game} object is in
	 * the {@link GameState#PLAYING} state.
	 * </p>
	 */
	@Test(expected = IllegalStateException.class)
	public final void testStart_Playing()
	{
		game.start();
		game.start();
	}
	/**
	 * Test the {@link Game#start()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#start()} method makes the
	 * {@link Game} object be in the  {@link GameState#PLAYING} state, when the
	 * {@link Game} object was in the {@link GameState#PLAYING} state.
	 * </p>
	 */
	@Test
	public final void testStart_Paused()
	{
		game.start();
		game.pause();
		game.start();
		assertEquals(GameState.PLAYING, game.getCurrentState());
	}
	/**
	 * Test the {@link Game#start()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#start()} method throws
	 * an {@link IllegalStateException} when the {@link Game} object is in
	 * the {@link GameState#GAME_OVER} state.
	 * </p>
	 */
	@Test(expected = IllegalStateException.class)
	public final void testStart_GameOver()
	{
		game.start();
		game.end();
		game.start();
	}
	@Test
	public final void testPause()
	{
		fail();
	}
	@Test
	public final void testEnd()
	{
		fail();
	}
	/**
	 * Test the {@link Game#put(Position)} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#put(Position)} method
	 * causes no changes to the board when the {@link Game} is not in the
	 * {@link GameState#PLAYING} state.
	 * </p>
	 */
	@Test
	public final void testPut_NotStarted()
			throws
			InvalidMoveException
	{
		game.put(LEGAL_MOVE);
		assertFalse(game.getBoard().view(LEGAL_MOVE).isPresent());
	}
	/**
	 * Test the {@link Game#put(Position)} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#put(Position)} method
	 * throws a {@link NullPointerException} when provided a <code>null</code>
	 * argument.
	 * </p>
	 */
	@Test(expected = NullPointerException.class)
	public final void testPut_NullArg()
			throws
			InvalidMoveException
	{
		game.start();
		game.put(null);
	}
	/**
	 * Test the {@link Game#put(Position)} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#put(Position)} method
	 * throws an {@link InvalidMoveException} when provided a {@link Position}
	 * argument which is outside to board.
	 * </p>
	 */
	@Test(expected = InvalidMoveException.class)
	public final void testPut_OutsideBoard()
			throws
			InvalidMoveException
	{
		game.start();
		game.put(OUTSIDE_BOARD);
	}
	/**
	 * Test the {@link Game#put(Position)} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#put(Position)} method
	 * throws an {@link InvalidMoveException} when provided a {@link Position}
	 * argument which is an invalid position.
	 * </p>
	 */
	@Test(expected = InvalidMoveException.class)
	public final void testPut_InvalidPosition()
			throws
			InvalidMoveException
	{
		game.start();
		game.put(INVALID_MOVE);
	}
	/**
	 * 
	 */
	@Test(expected = InvalidMoveException.class)
	public final void testPut_OnExistingPiece()
			throws
			InvalidMoveException
	{
		game.start();
		game.put(LEGAL_MOVE);
		game.put(LEGAL_MOVE);
	}
	/**
	 * 
	 */
	@Test
	public final void testPut_ValidMove()
			throws
			InvalidMoveException
	{
		game.start();
		game.put(LEGAL_MOVE);
		assertTrue(game.getBoard().view(LEGAL_MOVE).isPresent());
	}
	@Test
	public final void testSurrender()
	{
		fail();
	}
	/**
	 * 
	 */
	@Test
	public final void testAddListener_NullArg()
	{
		game.addListener(null);
	}
	/**
	 * 
	 */
	@Test
	public final void testRemoveListener_NullArg()
	{
		game.removeListener(null);
	}
	/**
	 * 
	 */
	@Test
	public final void testRemoveListener_NotExisitng()
	{
		game.removeListener(e -> fail());
	}
	/**
	 * Test the {@link Game#removeAllListeners()} method of the {@link Game}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#removeAllListeners()}
	 * method removes the specified {@link GameListener} object from the
	 * {@link Game} object.
	 * </p>
	 */
	@Test
	public final void testRemoveListener_Success()
			throws
			InvalidMoveException
	{
		game.start();
		final GameListener listener = e -> fail();
		game.addListener(listener);
		game.removeListener(listener);
		game.put(LEGAL_MOVE);
	}
	/**
	 * Test the {@link Game#removeAllListeners()} method of the {@link Game}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#removeAllListeners()}
	 * method removes all {@link GameListener} objects from the {@link Game}
	 * object.
	 * </p>
	 */
	@Test
	public final void testRemoveAllListeners()
			throws
			InvalidMoveException
	{
		game.start();
		final GameListener listener = e -> fail();
		game.addListener(listener);
		game.removeAllListeners();
		game.put(LEGAL_MOVE);
	}
	/**
	 * Test the {@link Game#getPlayer1()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This method should only pass if the {@link Game#getPlayer1()} method
	 * returns {@link Piece#PIECE_A}.
	 * </p>
	 */
	@Test
	public final void testGetPlayer1()
	{
		assertEquals(Piece.PIECE_A, game.getPlayer1());
	}
	/**
	 * Test the {@link Game#getPlayer1()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This method should only pass if the {@link Game#getPlayer2()} method
	 * returns {@link Piece#PIECE_B}.
	 * </p>
	 */
	@Test
	public final void testGetPlayer2()
	{
		assertEquals(Piece.PIECE_B, game.getPlayer2());
	}
	/**
	 * Test the {@link Game#getCurrent()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#getCurrent()} method
	 * return the {@link Piece} object which is {@link Game#getPlayer1()} when
	 * the current turn is the first players turn.
	 * </p>
	 */
	@Test
	public final void testGetCurrent_Player1()
	{
		assertEquals(game.getPlayer1(), game.getCurrent());
	}
	/**
	 * Test the {@link Game#getCurrent()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#getCurrent()} method
	 * return the {@link Piece} object which is {@link Game#getPlayer2()} when
	 * the current turn is the second players turn.
	 * </p>
	 */
	@Test
	public final void testGetCurrent_Player2()
			throws
			InvalidMoveException
	{
		game.start();
		game.put(LEGAL_MOVE);
		assertEquals(game.getPlayer2(), game.getCurrent());
	}
	/**
	 * Test the {@link Game#getCurrentState()} method of the {@link Game}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#getCurrentState()}
	 * returns {@link GameState#READY}, before the game has been started.
	 * </p>
	 */
	@Test
	public final void testGetCurrentGameState_Beginning()
	{
		assertEquals(GameState.READY, game.getCurrentState());
	}
	/**
	 * Test the {@link Game#getCurrentState()} method of the {@link Game}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#getCurrentState()}
	 * returns {@link GameState#PLAYING}, once the game has been started.
	 * </p>
	 */
	@Test
	public final void testGetCurrentGameState_Started()
	{
		game.start();
		assertEquals(GameState.PLAYING, game.getCurrentState());
	}
	/**
	 * Test the {@link Game#getCurrentState()} method of the {@link Game}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#getCurrentState()}
	 * returns {@link GameState#PAUSED}, when the game has been paused.
	 * </p>
	 */
	@Test
	public final void testGetCurrentGameState_Paused()
	{
		game.start();
		game.pause();
		assertEquals(GameState.PAUSED, game.getCurrentState());
	}
	/**
	 * Test the {@link Game#getCurrentState()} method of the {@link Game}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#getCurrentState()}
	 * returns {@link GameState#GAME_OVER}, once the game has been ended.
	 * </p>
	 */
	@Test
	public final void testGetCurrentGameState_Ended()
	{
		game.start();
		game.end();
		assertEquals(GameState.GAME_OVER, game.getCurrentState());
	}
	@Test
	public final void testGetConclusion()
	{
		fail();
	}
}
