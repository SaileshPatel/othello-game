package com.othellog4.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.othellog4.game.board.GameBoard;
import com.othellog4.game.board.InvalidMoveException;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;
import com.othellog4.game.command.Put;
import com.othellog4.game.command.Surrender;
import com.othellog4.game.extension.FlipCounter;
import com.othellog4.game.player.Player;

/**
 * The JUnit test suit for the {@link GameManager} class.
 * 
 * @author 	159014260 John Berg
 * @since 	20/02/2018
 * @version	19/03/2018
 */
public class GameManagerTest
{
	//=========================================================================
	//Fields.
	/**
	 * The {@link com.othellog4.game.player.Participant} which is used as the
	 * first player within the {@link GameManager}.
	 * 
	 * @see com.othellog4.game.player.Participant
	 */
	private static final Player PLAYER1 = new Player();
	/**
	 * The {@link com.othellog4.game.player.Participant} which is used as the
	 * second player within the {@link GameManager}.
	 * 
	 * @see com.othellog4.game.player.Participant
	 */
	private static final Player PLAYER2 = new Player();
	/**
	 * The {@link GameManager} object which is used to test the
	 * {@link GameManager} class.
	 * 
	 * @see GameManager
	 */
	private GameManager manager;
	//=========================================================================
	//Before and after.
	/**
	 * Setup the {@link #manager} object before each test and start the game.
	 */
	@Before
	public final void setup()
	{
		manager = new GameManager(
				new Game(new GameBoard(8)),
				PLAYER1,
				PLAYER2);
		manager.game().start();
	}
	//=========================================================================
	//Tests.
	/**
	 * Tes the {@link GameManager#isInputEnabled()} method of the
	 * {@link GameManager} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link GamaManager#isInputEnabled()}
	 * returns <code>true</code> when the {@link GameManager} is newly created.
	 * </p>
	 */
	@Test
	public final void testIsInputEnabled_Beginning()
	{
		assertTrue(manager.isInputEnabled());
	}
	/**
	 * Tes the {@link GameManager#isInputEnabled()} method of the
	 * {@link GameManager} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link GamaManager#isInputEnabled()}
	 * returns <code>false</code> when the {@link GameManager} is set to
	 * disabled.
	 * </p>
	 */
	@Test
	public final void testIsInputEnabled_NotEnabled()
	{
		manager.enableInput(false);
		assertFalse(manager.isInputEnabled());
	}
	/**
	 * Test the {@link GameManager#isInputEnabled()} method of the
	 * {@link GameManager} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link GamaManager#isInputEnabled()}
	 * returns <code>true</code> when the {@link GameManager} input is
	 * enabled again.
	 * </p>
	 */
	@Test
	public final void testInputEnabled_Renabled()
	{
		manager.enableInput(false);
		manager.enableInput(true);
		assertTrue(manager.isInputEnabled());
	}
	/**
	 * Test the {@link GameManager#isInputEnabled()} method of the
	 * {@link GameModel} class.
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link GameManager#enableInput(boolean)} method makes the
	 * {@link GameManager} unable to accept commands, and returns
	 * <code>false</code> when attempting to execute a command.
	 * </p>
	 */
	@Test
	public final void testEnableInput_Disable()
			throws
			GameException
	{
		manager.enableInput(false);
		final Position pos = manager.game().getBoard()
				.legalMoves(manager.game().getCurrent())
				.iterator()
				.next();
		assertFalse(manager.execute(new Put(manager.player1(), pos)));
	}
	/**
	 * Test the {@link GameManager#isInputEnabled()} method of the
	 * {@link GameModel} class.
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link GameManager#enableInput(boolean)} method makes the
	 * {@link GameManager} accept commands, and returns <code>true</code> when
	 * attempting to execute a command.
	 * </p>
	 */
	@Test
	public final void testEnableInput_Enable()
			throws
			GameException
	{
		final Position pos = manager.game().getBoard()
				.legalMoves(manager.game().getCurrent())
				.iterator()
				.next();
		assertTrue(manager.execute(new Put(manager.player1(), pos)));
	}
	/**
	 * Test the
	 * {@link GameManager#execute(com.othellog4.game.command.GameCommand)}
	 * method of the {@link GameManager} class.
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link GameManager#execute(com.othellog4.game.command.GameCommand)}
	 * modifies the board such that the location is occupied, when provided a
	 * valid {@link Put} command which is issued by the current player.
	 * </p>
	 */
	@Test
	public final void testExecute_Success()
			throws
			GameException
	{
		final Position pos = manager.game().getBoard()
				.legalMoves(manager.game().getCurrent())
				.iterator()
				.next();
		assertTrue(manager.execute(new Put(manager.current(), pos)));
		assertEquals(Piece.PIECE_A, manager.game().getBoard().view(pos).get());
	
	}
	/**
	 * Test the
	 * {@link GameManager#execute(com.othellog4.game.command.GameCommand)}
	 * method of the {@link GameManager} class.
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link GameManager#execute(com.othellog4.game.command.GameCommand)}
	 * does not modifies the board such that the location is occupied, when
	 * provided a valid {@link Put} command which is not issued by the current
	 * player.
	 * </p>
	 */
	@Test
	public final void testExecute_WrongTurn()
			throws
			GameException
	{
		final Position pos = manager.game().getBoard()
				.legalMoves(manager.game().getCurrent())
				.iterator()
				.next();
		manager.execute(new Put(manager.player2(), pos));
		assertFalse(manager.game().getBoard().view(pos).isPresent());
	}
	/**
	 * Test the
	 * {@link GameManager#execute(com.othellog4.game.command.GameCommand)}
	 * method of the {@link GameManager} class.
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link GameManager#execute(com.othellog4.game.command.GameCommand)}
	 * modifies the game such that the game has ended when a {@link Surrender}
	 * command is issued by any player.
	 * </p>
	 */
	@Test
	public final void testExecute_AnyTurn()
			throws
			GameException
	{
		manager.execute(new Surrender(
				manager.player2(),
				manager.game().getPlayer2()));
		assertEquals(
				manager.game().getPlayer1(),
				manager.game().getConclusion().getWinner());
	}
	/**
	 * Test the {@link GameManager#player1()} method of the {@link GameManager}
	 * class.
	 * 
	 * <p>
	 * This method should only pass if the {@link GameManager#player1()} method
	 * returns the player which was used to initialise the {@link GameManager}
	 * as the first player.
	 * </p>
	 */
	@Test
	public final void testPlayer1()
	{
		assertEquals(PLAYER1, manager.player1());
	}
	/**
	 * Test the {@link GameManager#player1()} method of the {@link GameManager}
	 * class.
	 * 
	 * <p>
	 * This method should only pass if the {@link GameManager#player1()} method
	 * returns the player which was used to initialise the {@link GameManager}
	 * as the second player.
	 * </p>
	 */
	@Test
	public final void testPlayer2()
	{
		assertEquals(PLAYER2, manager.player2());
	}
	/**
	 * Test the {@link GameManager#current()} method of the {@link GameManager}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link GameManager#current()}
	 * returns the player is the first player.
	 * </p>
	 */
	@Test
	public final void testCurrent_Player1()
	{
		assertEquals(PLAYER1, manager.current());
	}
	/**
	 * Test the {@link GameManager#current()} method of the {@link GameManager}
	 * class.
	 * 
	 * <p>
	 * This test should only pass if the {@link GameManager#current()}
	 * returns the player is the second player.
	 * </p>
	 */
	@Test
	public final void testCurrent_Player2()
			throws
			InvalidMoveException
	{
		final Position pos = manager.game().getBoard()
				.legalMoves(manager.game().getCurrent())
				.iterator()
				.next();
		manager.game().put(pos);;
		assertEquals(PLAYER2, manager.current());
	}
	/**
	 * Test the {@link GameManager#playerOf(Piece) method of the
	 * {@link GameManager} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link GameManager#playerOf(Piece)}
	 * throws a {@link NullPointerException} when provided a <code>null</code>
	 * argument.
	 * </p>
	 */
	@Test(expected = NullPointerException.class)
	public final void testPlayerOf_NullArg()
	{
		manager.playerOf(null);
	}
	/**
	 * Test the {@link GameManager#playerOf(Piece) method of the
	 * {@link GameManager} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link GameManager#playerOf(Piece)}
	 * method returns {@link #PLAYER1} when provided the piece which
	 * corresponds to player 1.
	 * </p>
	 */
	@Test
	public final void testPlayerOf_Player1()
	{
		assertEquals(
				manager.player1(),
				manager.playerOf(manager.game().getPlayer1()));
	}
	/**
	 * Test the {@link GameManager#playerOf(Piece) method of the
	 * {@link GameManager} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link GameManager#playerOf(Piece)}
	 * method returns {@link #PLAYER2} when provided the piece which
	 * corresponds to player 2.
	 * </p>
	 */
	@Test
	public final void testPlayerOf_Player2()
	{
		assertEquals(
				manager.player2(),
				manager.playerOf(manager.game().getPlayer2()));
	}
	/**
	 * Test the
	 * {@link GameManager#pieceOf(com.othellog4.game.player.Participant)}
	 * method of the {@link GameManager} class.
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link GameManager#pieceOf(com.othellog4.game.player.Participant)}
	 * method returns an empty optional piece.
	 * </p>
	 */
	@Test
	public final void testPieceOf_NullArg()
	{
		assertFalse(manager.pieceOf(null).isPresent());
	}
	/**
	 * Test the
	 * {@link GameManager#pieceOf(com.othellog4.game.player.Participant)}
	 * method of the {@link GameManager} class.
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link GameManager#pieceOf(com.othellog4.game.player.Participant)}
	 * method returns the piece of the first player.
	 * </p>
	 */
	@Test
	public final void testPieceOf_Player1()
	{
		assertEquals(
				manager.game().getPlayer1(),
				manager.pieceOf(manager.player1()).get());
	}
	/**
	 * Test the
	 * {@link GameManager#pieceOf(com.othellog4.game.player.Participant)}
	 * method of the {@link GameManager} class.
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link GameManager#pieceOf(com.othellog4.game.player.Participant)}
	 * method returns the piece of the second player.
	 * </p>
	 */
	@Test
	public final void testPieceOf_Player2()
	{
		assertEquals(
				manager.game().getPlayer2(),
				manager.pieceOf(manager.player2()).get());
	}
	/**
	 * Test the {@link GameManager#score()} method of the {@link GameManager}
	 * class.
	 * 
	 * <p>
	 * This test will only pass if the {@link GameManager#score()} method
	 * throws an {@link IllegalArgumentException} when the game has not ended.
	 * </p>
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testScore_NotGameOver()
	{
		manager.score();
	}
	/**
	 * Test the {@link GameManager#score()} method of the {@link GameManager}
	 * class.
	 * 
	 * <p>
	 * This test will only pass if the {@link GameManager#score()} method
	 * returns a {@link GameScore} object which represent the state of the
	 * game after the game has ended.
	 * </p>
	 */
	@Test
	public final void testScore_GameOver()
	{
		manager.game().end();
		assertTrue(manager.score().conclusion().isDraw());
	}
	/**
	 * Test the {@link GameManager#getResults()} method of the
	 * {@link GameManager} class.
	 * 
	 * <p>
	 * This method should only pass if the {@link GameManager#getResults()}
	 * method returns a map containing the extensions and {@link GameResult}
	 * generated by the extension.
	 * </p>
	 */
	@Test
	public final void testGetResults()
	{
		final GameManager managerWithExtension = new GameManager(
				new Game(new GameBoard(8)),
				PLAYER1,
				PLAYER2,
				new FlipCounter());
		assertNotNull(managerWithExtension
				.getResults()
				.get(FlipCounter.class));
	}
}
