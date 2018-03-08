package com.othellog4.game;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.othellog4.game.board.GameBoard;
import com.othellog4.game.player.Player;

/**
 * 
 * @author John
 *
 */
public class GameSessionTest
{
	//=========================================================================
	//Before and after.
	private GameManager manager;
	private GameSession session;
	@Before
	public final void setup()
	{
		manager = new GameManager(
				new Game(new GameBoard(8)),
				new Player(),
				new Player());
		session = new GameSession(manager);
		manager.game().start();
	}
	//=========================================================================
	//Tests.
	@Test
	public final void testAccept_NullArg()
	{
		fail();
	}
	@Test
	public final void testAccept_WrongIssuer()
	{
		fail();
	}
	@Test
	public final void testAccept_Success()
	{
		fail();
	}
	@Test
	public final void testGetBoard_SameAsMamager()
	{
		fail();
	}
	@Test
	public final void testCurrent_Player1()
	{
		fail();
	}
	@Test
	public final void testCurrent_Player2()
	{
		fail();
	}
	
}
