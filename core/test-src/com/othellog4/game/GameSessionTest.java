package com.othellog4.game;

import static org.junit.Assert.*;

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
 * The JUnit test suit for the {@link GameSession} class.
 * 
 * @author 	159014260 John Berg
 * @since 	16/02/2018
 * @version 18/03/2018
 */
public class GameSessionTest
{
	//=========================================================================
	//Fields.
	/**
	 * The {@link GameManager} object which manages the details of the game for
	 * the {@link GameSession} object.
	 * 
	 * @see GameManager
	 */
	private GameManager manager;
	/**
	 * The {@link GameSession} object which will be used during the test.
	 * 
	 * @see GameSession
	 */
	private GameSession session;
	//=========================================================================
	//Before and after.
	/**
	 * Setup the {@link #session} and {@link #manager} before running each
	 * test.
	 */
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
	/**
	 * Test the
	 * {@link GameSession#accept(com.othellog4.game.command.GameCommand)} of
	 * the {@link GameSession} class.
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link GameSession#accept(com.othellog4.game.command.GameCommand)}
	 * method throws a {@link NullPointerException} when <code>null</code> is
	 * provided as an argument.
	 * </p>
	 */
	@Test(expected = NullPointerException.class)
	public final void testAccept_NullArg()
			throws
			GameException
	{
		session.accept(null);
	}
	/**
	 * Test the
	 * {@link GameSession#accept(com.othellog4.game.command.GameCommand)} of
	 * the {@link GameSession} class.
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link GameSession#accept(com.othellog4.game.command.GameCommand)}
	 * method does not cause a {@link GameEvent} to be issued when a
	 * {@link com.othellog4.game.command.GameCommand} which is not a
	 * {@link com.othellog4.game.player.Participant} in the {@link #session}
	 * object.
	 * </p>
	 */
	@Test
	public final void testAccept_WrongIssuer()
			throws
			GameException
	{
		manager.game().addListener(e -> fail());
		session.accept(new Put(new Player(), 0, 0));
	}
	/**
	 * Test the
	 * {@link GameSession#accept(com.othellog4.game.command.GameCommand)} of
	 * the {@link GameSession} class.
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link GameSession#accept(com.othellog4.game.command.GameCommand)}
	 * method causes the game to change state when a 
	 * {@link com.othellog4.game.command.GameCommand} is issued by a
	 * {@link com.othellog4.game.player.Participant} from the {@link #session}
	 * object.
	 * </p>
	 * 
	 * <p>
	 * The command used for the test is the {@link Pause} command which is
	 * only usable during the same turn of the issuer.
	 * </p>
	 */
	@Test
	public final void testAccept_Success()
			throws
			GameException
	{
		session.accept(new Pause(manager.current()));
		assertTrue(manager.game().isPaused());
	}
	/**
	 * Test the
	 * {@link GameSession#accept(com.othellog4.game.command.GameCommand)} of
	 * the {@link GameSession} class.
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link GameSession#accept(com.othellog4.game.command.GameCommand)}
	 * method modifies the state of the game when executing a
	 * {@link com.othellog4.game.command.GameCommand} which can be executed
	 * during any turn.
	 * </p>
	 * 
	 * <p>
	 * The command used in the test is the {@link Resume} command.
	 * </p>
	 */
	@Test
	public final void testAccept_AnyTurn()
			throws
			GameException
	{
		manager.game().pause();
		session.accept(new Resume(manager.player2()));
		assertFalse(manager.game().isPaused());
	}
	/**
	 * Test the
	 * {@link GameSession#accept(com.othellog4.game.command.GameCommand)} of
	 * the {@link GameSession} class.
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link GameSession#accept(com.othellog4.game.command.GameCommand)}
	 * method does not modify the state of the game when a
	 * {@link com.othellog4.game.command.GameCommand} is issued by a
	 * {@link com.othellog4.game.player.Participant} which is in the
	 * {@link #session} but is not the player with the current turn.
	 * </p>
	 *  
	 * <p>
	 * The command used for the test is the {@link Pause} command which is
	 * only usable during the same turn of the issuer.
	 * </p>
	 */
	@Test
	public final void testAccept_WrongTurn()
			throws
			GameException
	{
		session.accept(new Pause(manager.player2()));
		assertFalse(manager.game().isPaused());
	}
	/**
	 * Test the {@link GameSession#getBoard()} method of the
	 * {@link GameSession} class.
	 * 
	 * <p>
	 * This test should only pass if a command which places a piece on the
	 * board results in the {@link GameSession#getBoard()} method returns an
	 * object which reflecting the placment.
	 * </p>
	 */
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
	/**
	 * Test the {@link GameSession#current()} method of the {@link GameSession}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link GameSession#current()} method
	 * returns the {@link com.othellog4.game.board.Piece} which is returned
	 * by the {@link GameManager#player1()} method, when it is the first
	 * player's turn.
	 * </p>
	 */
	@Test
	public final void testCurrent_Player1()
	{
		assertSame(manager.player1(), manager.playerOf(session.current()));
	}
	/**
	 * Test the {@link GameSession#current()} method of the {@link GameSession}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link GameSession#current()} method
	 * returns the {@link com.othellog4.game.board.Piece} which is returned
	 * by the {@link GameManager#player1()} method, when it is the second
	 * player's turn.
	 * </p>
	 */
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
