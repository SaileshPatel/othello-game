package com.othellog4.game.command;

import static org.junit.Assert.*;

import org.junit.Test;

import com.othellog4.game.Game;
import com.othellog4.game.GameException;
import com.othellog4.game.board.GameBoard;
import com.othellog4.game.board.Piece;
import com.othellog4.game.player.Player;

/**
 * The JUnit test suit for the {@link Surrender} class.
 * 
 * <p>
 * Redefines the tests from the {@link GameCommandTest} class.
 * </p>
 * 
 * @author 	159014260 John Berg
 * @since	05/03/2018
 * @version 05/03/2018
 */
public final class SurrenderTest extends GameCommandTest
{
	//=========================================================================
	//Overriden tests.
	/**
	 * Test the {@link Surrender#getSource()} method of the {@link Surrender}
	 * class.
	 * 
	 * <p>
	 * Redefined from the {@link GameCommandTest} class.
	 * </p>
	 * 
	 * <p>
	 * This test should only pass if the {@link Surrender#getSource()} method
	 * returns the {@link com.othellog4.game.player.Participant} object
	 * which was used to create the {@link Put} object.
	 * </p>
	 */
	@Override
	@Test
	public final void testGetSource()
	{
		final Player source = new Player();
		assertSame(
				source,
				new Surrender(source, Piece.player1()).getSource());
	}
	/**
	 * Test the
	 * {@link Surrender#canExecute(com.othellog4.game.player.Participant)}
	 * method of the {@link Surrender} class.
	 * 
	 * <p>
	 * Redefined from the {@link GameCommandTest} class.
	 * </p>
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link Surrender#canExecute(com.othellog4.game.player.Participant)}
	 * returns <code>true</code> when passed a
	 * {@link com.othellog4.game.player.Participant} which is equal to the
	 * {@link com.othellog4.game.player.Participant} object which was used
	 * to initialise the {@link Surrender} object.
	 * </p>
	 */
	@Override
	@Test
	public final void testCanExecute_IssuersTurn()
	{
		final Player player = new Player();
		assertTrue(new Surrender(player, Piece.player1()).canExecute(player));
	}
	/**
	 * Test the
	 * {@link Surrender#canExecute(com.othellog4.game.player.Participant)}
	 * method of the {@link Surrender} class.
	 * 
	 * <p>
	 * Redefined from the {@link GameCommandTest} class.
	 * </p>
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link Surrender#canExecute(com.othellog4.game.player.Participant)}
	 * returns <code>true</code> when passed a
	 * {@link com.othellog4.game.player.Participant} which is not equal to the
	 * {@link com.othellog4.game.player.Participant} object which was used
	 * to initialise the {@link Surrender} object.
	 * </p>
	 */
	@Override
	@Test
	public final void testCanExecute_NotIssuersTurn()
	{
		assertTrue(new Surrender(new Player(), Piece.player1())
				.canExecute(new Player()));
	}
	/**
	 * Test the {@link Surrender#execute(Game)} method of the {@link Surrender}
	 * class.
	 * 
	 * <p>
	 * Redefined from the {@link GameCommandTest} class.
	 * </p>
	 * 
	 * <p>
	 * This test should only pass if the {@link Surrender#execute(Game)} method
	 * throws a {@link NullPointerException} when provided a <code>null</code>
	 * argument.
	 * </p>
	 */
	@Override
	@Test(expected = NullPointerException.class)
	public final void testExecute_NullArg()
			throws
			GameException
	{
		new Surrender(new Player(), Piece.player1()).execute(null);
	}
	/**
	 * Test the {@link Surrender#execute(Game)} method of the {@link Surrender}
	 * class.
	 * 
	 * <p>
	 * Redefined from the {@link GameCommandTest} class.
	 * </p>
	 * 
	 * <p>
	 * This test should only pass if the {@link Surrender#execute(Game)} method
	 * throws a {@link GameException} if the {@link Game} object the
	 * {@link Surrender} object is executed onto is already over.
	 * </p>
	 */
	@Override
	@Test(expected = GameException.class)
	public final void testExecute_Error()
			throws
			GameException
	{
		final Game game = new Game(new GameBoard(8));
		game.start();
		game.end();
		new Surrender(new Player(), Piece.player1()).execute(game);
	}
	/**
	 * Test the {@link Surrender#execute(Game)} method of the {@link Surrender}
	 * class.
	 * 
	 * <p>
	 * Redefined from the {@link GameCommandTest} class.
	 * </p>
	 * 
	 * <p>
	 * This test should only pass if the {@link Surrender#execute(Game)} method
	 * makes a {@link Game} in an end state where the {@link Piece} object
	 * which was provided to the {@link Surrender} object is the losing
	 * {@link Piece} object.
	 * </p>
	 */
	@Override
	@Test
	public final void testExecute_Success()
			throws
			GameException
	{
		final Game game = new Game(new GameBoard(8));
		game.start();
		new Surrender(new Player(), Piece.player1()).execute(game);
		assertSame(Piece.player1(), game.getConclusion().getLoser());
	}
	/**
	 * Test the {@link Surrender#toString()} method of the {@link Surrender}
	 * class.
	 * 
	 * <p>
	 * Redefined from the {@link GameCommandTest} class.
	 * </p>
	 * 
	 * <p>
	 * This test should only pass if the {@link Surrender#toString()} method
	 * returns a {@link String} which contains the {@link String} from the
	 * {@link Piece#toString()} for the {@link Piece} which was used to
	 * initialise the {@link Surrender} object.
	 * </p>
	 */
	@Override
	@Test
	public final void testToString()
	{
		assertTrue(new Surrender(new Player(), Piece.player1())
				.toString()
				.contains(Piece.player1().toString()));
	}
}
