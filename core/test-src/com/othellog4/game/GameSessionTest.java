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
	public final void test()
	{
		fail();
	}
}
