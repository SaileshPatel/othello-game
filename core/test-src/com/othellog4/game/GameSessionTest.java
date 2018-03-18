package com.othellog4.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.othellog4.game.board.GameBoard;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;
import com.othellog4.game.command.Pause;
import com.othellog4.game.command.Put;
import com.othellog4.game.command.Resume;
import com.othellog4.game.player.Player;

/**
 * 
 * 
 * @author 	159014260 John Berg
 * @since 	
 * @version 18/03/2018
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
	@Test(expected = NullPointerException.class)
	public final void testAccept_NullArg()
			throws
			GameException
	{
		session.accept(null);
	}
	@Test
	public final void testAccept_WrongIssuer()
			throws
			GameException
	{
		manager.game().addListener(e -> fail());
		session.accept(new Put(new Player(), 0, 0));
	}
	@Test
	public final void testAccept_Success()
			throws
			GameException
	{
		session.accept(new Pause(manager.current()));
		assertTrue(manager.game().isPaused());
	}
	@Test
	public final void testAccept_AnyTurn()
			throws
			GameException
	{
		manager.game().pause();
		session.accept(new Resume(manager.player2()));
		assertFalse(manager.game().isPaused());
	}
	@Test
	public final void testAccept_WrongTurn()
			throws
			GameException
	{
		session.accept(new Pause(manager.player2()));
		assertFalse(manager.game().isPaused());
	}
	@Test
	public final void testGetBoard()
			throws
			GameException
	{
		final Position pos = session.getBoard()
				.legalMoves(session.current())
				.iterator()
				.next();
		session.accept(new Put(manager.current(), pos));
		assertEquals(Piece.PIECE_A, session.getBoard().view(pos).get());
	}
	@Test
	public final void testCurrent_Player1()
	{
		assertSame(manager.player1(), manager.playerOf(session.current()));
	}
	@Test
	public final void testCurrent_Player2()
			throws
			GameException
	{
		session.accept(
				new Put(manager.current(),
				session.getBoard()
					.legalMoves(session.current())
					.iterator()
					.next()));
		assertSame(manager.player2(), manager.playerOf(session.current()));
	}
}
