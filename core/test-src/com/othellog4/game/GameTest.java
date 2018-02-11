package com.othellog4.game;

import org.junit.Before;
import org.junit.Test;

import com.othellog4.game.board.GameBoard;
import com.othellog4.game.board.InvalidMoveException;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;
import static org.junit.Assert.*;

/**
 * 
 * 
 * 
 * 
 * @author 	159014260 John Berg
 * @since 	25/01/2018
 * @version 25/01/2018
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
	public final void testIsGameOver()
	{
		//Must ...
		fail();
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
	@Test
	public final void testStart_Beginning()
	{
		
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
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public final void testPut_NullArg()
			throws
			InvalidMoveException
	{
		game.put(null);
	}
	/**
	 * 
	 */
	@Test(expected = InvalidMoveException.class)
	public final void testPut_OutsideBoard()
			throws
			InvalidMoveException
	{
		game.put(OUTSIDE_BOARD);
	}
	/**
	 * 
	 */
	@Test(expected = InvalidMoveException.class)
	public final void testPut_InvalidPosition()
			throws
			InvalidMoveException
	{
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
		game.put(LEGAL_MOVE);
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
	public final void testAddListener_Success()
	{
		fail();
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
	 * 
	 */
	@Test
	public final void testRemoveListener_Success()
			throws
			InvalidMoveException
	{
		final GameListener listener = e -> fail();
		game.addListener(listener);
		game.removeListener(listener);
		game.put(LEGAL_MOVE);
	}
	/**
	 * 
	 */
	@Test
	public final void testRemoveAllListeners()
			throws
			InvalidMoveException
	{
		final GameListener listener = e -> fail();
		game.addListener(listener);
		game.removeAllListeners();
		game.put(LEGAL_MOVE);
	}
	/**
	 * 
	 */
	@Test
	public final void testGetPlayer1()
	{
		assertEquals(Piece.PIECE_A, game.getPlayer1());
	}
	/**
	 * 
	 */
	@Test
	public final void testGetPlayer2()
	{
		assertEquals(Piece.PIECE_B, game.getPlayer2());
	}
	/**
	 * 
	 */
	@Test
	public final void testGetCurrent_Player1()
	{
		assertEquals(Piece.PIECE_A, game.getCurrent());
	}
	/**
	 * 
	 */
	@Test
	public final void testGetCurrent_Player2()
			throws
			InvalidMoveException
	{
		game.put(LEGAL_MOVE);
		assertEquals(Piece.PIECE_B, game.getCurrent());
	}
	/**
	 * 
	 */
	@Test
	public final void testGetCurrentGameState_Beginning()
	{
		assertEquals(GameState.READY, game.getCurrentState());
	}
	/**
	 * 
	 */
	@Test
	public final void testGetCurrentGameState_Started()
	{
		game.start();
		assertEquals(GameState.PLAYING, game.getCurrentState());
	}
	/**
	 * 
	 */
	@Test
	public final void testGetCurrentGameState_Paused()
	{
		game.start();
		game.pause();
		assertEquals(GameState.PAUSED, game.getCurrentState());
	}
	/**
	 * 
	 */
	@Test
	public final void testGetCurrentGameState_Ended()
	{
		game.start();
		game.end();
		assertEquals(GameState.GAME_OVER, game.getCurrentState());
	}
	/**
	 * 
	 */
	@Test
	public final void testGetBoardView()
	{
		fail();
	}
}
