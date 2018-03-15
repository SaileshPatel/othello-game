package com.othellog4.game.extension;

import org.junit.Before;
import org.junit.Test;

import com.othellog4.game.board.GameBoard;
import com.othellog4.game.Game;
import com.othellog4.game.GameManager;
import com.othellog4.game.board.Piece;
import com.othellog4.game.command.Put;
import com.othellog4.game.player.AutomaticPlayer;
import com.othellog4.game.player.ai.DelayStrategies;
import com.othellog4.game.player.ai.SearchStrategies;

import static org.junit.Assert.*;

/**
 * 
 * @author John
 *
 */
public class FlipCounterTest
{
	private static final AutomaticPlayer PLAYER = new AutomaticPlayer(
			null,
			SearchStrategies.RANDOM_SELECTION,
			DelayStrategies.IMMEDIATE);
	private GameManager manager;
	private FlipCounter counter;
	@Before
	public final void setup()
	{
		manager = new GameManager(
				new Game(new GameBoard(8, 0)),
				PLAYER,
				PLAYER);
		counter = new FlipCounter();
	}
	@Test
	public final void testGetScore_Beginning()
	{
		assertEquals(0, counter.getScore(Piece.PIECE_A));
	}
	@Test
	public final void testGetScore_AfterFlip()
	{
		
		fail();
	}
	@Test
	public final void testOnCommand()
	{
		counter.onCommand(
				new Put(PLAYER, manager.game()
						.getBoard()
						.legalMoves(Piece.PIECE_A)
						.iterator()
						.next()),
				manager);
		assertEquals(0, 1);
	}
	@Test
	public final void testGetResult_Beginning()
	{
		assertTrue(counter.getResult(Piece.PIECE_B).contains(" 0"));
	}
	@Test
	public final void testToString()
	{
		fail();
	}
}
