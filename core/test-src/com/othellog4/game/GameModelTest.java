package com.othellog4.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.othellog4.game.board.GameBoard;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;
import com.othellog4.game.player.AutomaticPlayer;
import com.othellog4.game.player.Player;
import com.othellog4.game.player.ai.DelayStrategies;
import com.othellog4.game.player.ai.EvaluationStrategies;
import com.othellog4.game.player.ai.SearchStrategies;


public class GameModelTest
{
	
	//=========================================================================
	//Fields.
	private GameManager manager;
	private GameModel model;
	//=========================================================================
	//Before and after.
	@Before
	public final void setup()
	{
		model = new GameModel(new Game(
				new GameBoard(8)),
				new Player(),
				new Player());
		model.start();
	}
	//=========================================================================
	//Test.
	@Test
	public final void testIsStarted_NotStarted()
	{
		model = new GameModel(new Game(
				new GameBoard(8)),
				new Player(),
				new Player());
		assertFalse(model.isStarted());
	}
	@Test
	public final void testIsStarted_Started()
	{
		assertTrue(model.isStarted());
	}
	@Test
	public final void testIsPlaying_NotPlaying()
			throws
			GameException
	{
		model.pause();
		assertFalse(model.isPlaying());
	}
	@Test
	public final void testIsPlaying_Playing()
			throws
			GameException
	{
		assertTrue(model.isPlaying());
	}
	@Test
	public final void testIsPaused_NotPaused()
			throws
			GameException
	{
		assertFalse(model.isPaused());
	}
	@Test
	public final void testIsPaused_Paused()
			throws
			GameException
	{
		model.pause();
		assertTrue(model.isPaused());
	}
	@Test
	public final void testIsGameOver_NotGameOver()
	{
		assertFalse(model.isGameOver());
	}
	@Test
	public final void testIsGameOver_GameOver()
	{
		model.surrender();
		assertTrue(model.isGameOver());
	}
	@Test
	public final void testIsWaiting_Player()
	{
		assertTrue(model.isWaiting());
	}
	@Test
	public final void testIsWaiting_AIPlayer()
	{
		final GameModel model = new GameModel(new Game(
				new GameBoard(8)),
				new AutomaticPlayer(
						EvaluationStrategies.WINNER,
						SearchStrategies.BEST_IMMEDIATE,
						DelayStrategies.WAIT_ONE_SEC),
				new Player());
		assertFalse(model.isWaiting());
	}
	@Test
	public final void testIsInputEnabled_Enabled()
	{
		model.enableInput(true);
		assertTrue(model.isInputEnabled());
	}
	@Test
	public final void testIsInputEnabled_NotEnabled()
	{
		model.enableInput(false);
		assertFalse(model.isInputEnabled());
	}
	@Test
	public final void testIsEventEnabled_Enabled()
	{
		model.enableEvent(true);
		assertTrue(model.isEventEnabled());
	}
	@Test
	public final void testIsEventEnabled_NotEnabled()
	{
		model.enableEvent(false);
		assertFalse(model.isEventEnabled());
	}
	@Test
	public final void testTurn_Beginning()
	{
		assertEquals(1, model.turn());
	}
	@Test
	public final void testTurn_NextTurn()
			throws
			GameException
	{
		final int previousTurn = 1;
		final Position pos = model.getBoard()
				.legalMoves(model.getCurrentPiece())
				.iterator()
				.next();
		model.put(pos.col, pos.row);
		assertNotEquals(previousTurn, model.turn());
	}
	@Test
	public final void testGetPlayer1Piece()
	{
		assertEquals(Piece.PIECE_A, model.getPlayer1Piece());
	}
	@Test
	public final void testGetPlayer2Piece()
	{
		assertEquals(Piece.PIECE_B, model.getPlayer2Piece());
	}
}
