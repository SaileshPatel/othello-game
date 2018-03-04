package com.othellog4.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.othellog4.game.board.GameBoard;
import com.othellog4.game.board.Piece;
import com.othellog4.game.player.Player;

/**
 * 
 * @author 	159014260 John Berg
 * @since 	20/02/2018
 * @version	20/02/2018
 */
public class GameManagerTest
{
	private static final Player PLAYER1 = new Player();
	private static final Player PLAYER2 = new Player();
	private GameManager manager;
	
	
	@Before
	public final void setup()
	{
		manager = new GameManager(
				new Game(new GameBoard(8)),
				PLAYER1,
				PLAYER2);
	}
	/**
	 * 
	 */
	@Test
	public final void testCalculateScore_NoExtensions()
	{
		assertEquals(0, manager.calculateScore(Piece.player1()));
	}
	@Test
	public final void testExecute()
	{
		fail();
	}
	/**
	 * 
	 */
	@Test
	public final void testPlayer1()
	{
		assertEquals(PLAYER1, manager.player1());
	}
	/**
	 * 
	 */
	@Test
	public final void testPlayer2()
	{
		assertEquals(PLAYER2, manager.player2());
	}
	/**
	 * 
	 */
	@Test
	public final void testCurrent_Beginning()
	{
		assertEquals(PLAYER1, manager.current());
	}
	/**
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public final void testPlayerOf_NullArg()
	{
		manager.playerOf(null);
	}
	@Test
	public final void testScore()
	{
		fail();
	}
	@Test
	public final void testGetResults()
	{
		fail();
	}
}
