package com.othellog4.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.othellog4.game.board.GameBoard;
import com.othellog4.game.board.InvalidMoveException;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;

/**
 * The {@code GameTest} class is a Junit test suit for the {@link Game} class.
 * 
 * @author 	159014260 John Berg
 * @since 	25/01/2018
 * @version 18/03/2018
 */
public class GameTest
{
	//=========================================================================
	//Static fields.
	/**
	 * The {@link Position} object which represents a position which is
	 * outside the board.
	 * 
	 * @see Position
	 */
	private static final Position OUTSIDE_BOARD = Position.at(-1, -1);
	/**
	 * The {@link Position} object which represents a position which already is
	 * occupied and a {@link Piece} can be placed on during the first turn.
	 * 
	 * @see Position
	 */
	private static final Position INVALID_MOVE = Position.at(1, 1);
	/**
	 * The {@link Position} object which represents a position which not
	 * occupied.
	 * 
	 * @see Position
	 */
	private static final Position LEGAL_MOVE = Position.at(5, 3);
	//=========================================================================
	//Fields.
	/**
	 * The {@link Game} object which will be used during the tests.
	 */
	private Game game;
	//=========================================================================
	//Before.
	/**
	 * The setup method which is run before each test case.
	 */
	@Before
	public final void setup()
	{
		game = new Game(new GameBoard(8, 8));
	}
	//=========================================================================
	//Test.
	/**
	 * Test the {@link Game#isStarted()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#isStarted()} method
	 * returns <code>false</code> before the game has started.
	 * </p>
	 */
	@Test
	public final void testIsStarted_NotStarted()
	{
		assertFalse(game.isStarted());
	}
	/**
	 * Test the {@link Game#isStarted()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#isStarted()} method
	 * returns <code>true</code> after the game has started.
	 * </p>
	 */
	@Test
	public final void testIsStarted_Started()
	{
		game.start();
		assertTrue(game.isStarted());
	}
	/**
	 * Test the {@link Game#isPlaying()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#isPlaying()} method
	 * returns <code>false</code> if the game is paused.
	 * </p>
	 */
	@Test
	public final void testIsPlaying_NotPlaying()
	{
		game.start();
		game.pause();
		assertFalse(game.isPlaying());
	}
	/**
	 * Test the {@link Game#isPlaying()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#isPlaying()} method
	 * returns <code>true</code> if the game is playing.
	 * </p>
	 */
	@Test
	public final void testIsPlaying_Playing()
	{
		game.start();
		assertTrue(game.isPlaying());
	}
	/**
	 * Test the {@link Game#isPaused()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#isPaused()} method
	 * returns <code>false</code> when the game is not paused.
	 * </p>
	 */
	@Test
	public final void testIsPaused_NotPaused()
	{
		game.start();
		assertFalse(game.isPaused());
	}
	/**
	 * Test the {@link Game#isPaused()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#isPaused()} method
	 * returns <code>true</code> when the game is paused.
	 * </p>
	 */
	@Test
	public final void testIsPaused_Paused()
	{
		game.start();
		game.pause();
		assertTrue(game.isPaused());
	}
	/**
	 * Test the {@link Game#isGameOver()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#isGameOver()} method
	 * returns <code>false</code> if the game has not ended.
	 * </p>
	 */
	@Test
	public final void testIsGameOver_NotGameOver()
	{
		game.start();
		assertFalse(game.isGameOver());
	}
	/**
	 * Test the {@link Game#isGameOver()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#isGameOver()} method
	 * returns <code>true</code> after the game has been ended.
	 * </p>
	 */
	@Test
	public final void testIsGameOver_GameOver()
	{
		game.start();
		game.end();
		assertTrue(game.isGameOver());
	}
	/**
	 * Test the {@link Game#isGameOver()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#isGameOver()} method
	 * returns <code>true</code> after the game has been ended by a surrender.
	 * </p>
	 */
	@Test
	public final void testIsGameOver_Surrendered()
	{
		game.start();
		game.surrender(game.getPlayer1());
		assertTrue(game.isGameOver());
	}
	/**
	 * Test the {@link Game#isEventEnabled()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#isEventEnabled()}
	 * returns <code>false</code> when {@link GameEvents} are disabled.
	 * </p>
	 */
	@Test
	public final void testIsEventEnabled_NotEnabled()
	{
		game.enableEvent(false);
		assertFalse(game.isEventEnabled());
	}
	/**
	 * Test the {@link Game#isEventEnabled()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#isEventEnabled()}
	 * returns <code>true</code> when {@link GameEvents} are enabled.
	 * </p>
	 */
	@Test
	public final void testIsEventEnabled_Enabled()
	{
		assertTrue(game.isEventEnabled());
	}
	/**
	 * Test the {@link Game#turn()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#isGameOver()} method
	 * returns <code>1</code> at the beginning of the game.
	 * </p>
	 */
	@Test
	public final void testTurn_Beginning()
	{
		assertEquals(1, game.turn());
	}
	/**
	 * Test the {@link Game#turn()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#isGameOver()} method
	 * returns an <code>int</code> which is <code>1</code> larger than the
	 * previous turn.
	 * </p>
	 */
	@Test
	public final void testTurn_NextTurn()
			throws
			InvalidMoveException
	{
		final int previousTurn = game.turn();
		game.start();
		game.put(LEGAL_MOVE);
		assertEquals(previousTurn + 1, game.turn());
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
	/**
	 * Test the {@link Game#pause()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#pause()} method throws an
	 * {@link IllegalStateException}, when invoked before the game has been
	 * started.
	 * </p>
	 */
	@Test(expected = IllegalStateException.class)
	public final void testPause_Beginning()
	{
		game.pause();
	}
	/**
	 * Test the {@link Game#pause()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#pause()} method makes the
	 * {@link Game} object be in the {@link GameState#PAUSED} when invoked
	 * after starting the game.
	 * </p>
	 */
	@Test
	public final void testPause_Playing()
	{
		game.start();
		game.pause();
		assertEquals(GameState.PAUSED, game.getCurrentState());
	}
	/**
	 * Test the {@link Game#pause()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#pause()} method throws an
	 * {@link IllegalStateException}, when invoked when the game is already
	 * paused.
	 * </p>
	 */
	@Test(expected = IllegalStateException.class)
	public final void testPause_Paused()
	{
		game.start();
		game.pause();
		game.pause();
	}
	/**
	 * Test the {@link Game#pause()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#pause()} method throws an
	 * {@link IllegalStateException}, when invoked when the game has already
	 * ended.
	 * </p>
	 */
	@Test(expected = IllegalStateException.class)
	public final void testPause_GameOver()
	{
		game.start();
		game.end();
		game.pause();
	}
	/**
	 * Test the {@link Game#end()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#end()} method makes the
	 * {@link Game} object be in the {@link GameState#GAME_OVER} state.
	 * </p>
	 */
	@Test
	public final void testEnd_Beginning()
	{
		game.end();
		assertEquals(GameState.GAME_OVER, game.getCurrentState());
	}
	/**
	 * Test the {@link Game#end()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#end()} method makes the
	 * {@link Game} object be in the {@link GameState#GAME_OVER} state.
	 * </p>
	 */
	@Test
	public final void testEnd_Playing()
	{
		game.start();
		game.end();
		assertEquals(GameState.GAME_OVER, game.getCurrentState());
	}
	/**
	 * Test the {@link Game#end()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#end()} method makes the
	 * {@link Game} object be in the {@link GameState#GAME_OVER} state.
	 * </p>
	 */
	@Test
	public final void testEnd_Paused()
	{
		game.start();
		game.pause();
		game.end();
		assertEquals(GameState.GAME_OVER, game.getCurrentState());
	}
	/**
	 * Test the {@link Game#end()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#end()} method throws an
	 * {@link IllegalStateException} when the game has already ended.
	 * </p>
	 */
	@Test(expected = IllegalStateException.class)
	public final void testEnd_GameOver()
	{
		game.end();
		game.end();
	}
	/**
	 * Test the {@link Game#enableEvent(boolean)} method of the {@link Game}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#enableEvent(boolean)}
	 * method does not send a {@link GameEvent} to the {@link GameListener}
	 * objects, when {@link GameEvents} are set to <code>false</code>.
	 * </p>
	 */
	public final void testEnableEvent_NoEvent()
			throws
			InvalidMoveException
	{
		game.enableEvent(false);
		game.addListener(e -> fail());
		game.put(game.getBoard().legalMoves(game.getCurrent())
				.iterator()
				.next());
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
	 * Test the {@link Game#put(Position)} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#put(Position)} method
	 * throws an {@link InvalidMoveException} when provided a {@link Position}
	 * argument which represents a position on the board which already is
	 * occupied by a {@link Piece} object.
	 * </p>
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
	 * Test the {@link Game#put(Position)} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#put(Position)} method
	 * modifies the board such that the {@link Position} where a {@link Piece}
	 * is placed contains a {@link Piece} after modification.
	 * </p>
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
	/**
	 * Test the {@link Game#surrender(Piece)} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#surrender(Piece)} method
	 * throws a {@link NullPointerException} when provided a <code>null</code>
	 * argument.
	 * </p>
	 */
	@Test(expected = NullPointerException.class)
	public final void testSurrender_NullArg()
	{
		game.start();
		game.surrender(null);
	}
	/**
	 * Test the {@link Game#surrender(Piece)} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#surrender(Piece)} method
	 * makes the first player of the {@link Game} object to be the losing
	 * player.
	 * </p>
	 */
	@Test
	public final void testSurrender_Player1()
	{
		game.start();
		game.surrender(game.getPlayer1());
		assertEquals(game.getPlayer1(), game.getConclusion().getLoser());
	}
	/**
	 * Test the {@link Game#surrender(Piece)} method of the {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#surrender(Piece)} method
	 * makes the first player of the {@link Game} object to be the losing
	 * player.
	 * </p>
	 */
	@Test
	public final void testSurrender_Player2()
	{
		game.start();
		game.surrender(game.getPlayer2());
		assertEquals(game.getPlayer2(), game.getConclusion().getLoser());
	}
	/**
	 * Test the {@link Game#addListener(GameListener)} method of the
	 * {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Game#addListener(GameListener)}
	 * method, if no abnormalities such as exceptions being thrown when
	 * provided a <code>null</code> argument.
	 * </p>
	 */
	@Test
	public final void testAddListener_NullArg()
	{
		game.addListener(null);
	}
	/**
	 * Test the {@link Game#removeListener(GameListener)} method of the
	 * {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link Game#removeListener(GameListener)} method, if no abnormalities
	 * such as exceptions being thrown when provided a <code>null</code?
	 * argument.
	 * </p>
	 */
	@Test
	public final void testRemoveListener_NullArg()
	{
		game.removeListener(null);
	}
	/**
	 * Test the {@link Game#removeListener(GameListener)} method of the
	 * {@link Game} class.
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link Game#removeListener(GameListener)} method, if no abnormalities
	 * such as exceptions being thrown when provided a {@link GameListener}
	 * object which does not exist.
	 * </p>
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
	/**
	 * Test the {@link Game#getConclusion()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This method should only pass if the {@link Game#getConclusion()} method
	 * throws an {@link IllegalStateException} when attempting to get the
	 * {@link GameConclusion} from a {@link Game} which has not ended.
	 * </p>
	 */
	@Test(expected = IllegalStateException.class)
	public final void testGetConclusion_NotGameOver()
	{
		game.start();
		game.getConclusion();
	}
	/**
	 * Test the {@link Game#getConclusion()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This method should only pass if the {@link Game#getConclusion()} method
	 * returns a {@link GameConclusion} from a {@link Game} which is a draw,
	 * and the {@link GameConclusion} object reflects the draw state.
	 * </p>
	 */
	@Test
	public final void testGetConclusion_Draw()
	{
		game.start();
		game.end();
		assertTrue(game.getConclusion().isDraw());
	}
	/**
	 * Test the {@link Game#getConclusion()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This method should only pass if the {@link Game#getConclusion()} method
	 * returns a {@link GameConclusion} from a {@link Game} which the first
	 * player is victorious, and the {@link GameConclusion} reflects that
	 * state.
	 * </p>
	 */
	@Test
	public final void testGetConclusion_Player1Winner()
			throws
			InvalidMoveException
	{
		game.start();
		game.put(LEGAL_MOVE);
		game.end();
		assertEquals(game.getPlayer1(), game.getConclusion().getWinner());
	}
	/**
	 * Test the {@link Game#getConclusion()} method of the {@link Game} class.
	 * 
	 * <p>
	 * This method should only pass if the {@link Game#getConclusion()} method
	 * returns a {@link GameConclusion} from a {@link Game} which the second
	 * player is victorious, and the {@link GameConclusion} reflects that
	 * state.
	 * </p>
	 */
	@Test
	public final void testGetConclusion_Player2Winner()
			throws
			InvalidMoveException
	{
		game.start();
		game.surrender(game.getPlayer1());
		assertEquals(game.getPlayer2(), game.getConclusion().getWinner());
	}
}
