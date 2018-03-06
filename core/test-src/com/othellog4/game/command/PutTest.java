package com.othellog4.game.command;

import static org.junit.Assert.*;

import org.junit.Test;

import com.othellog4.game.Game;
import com.othellog4.game.GameException;
import com.othellog4.game.board.GameBoard;
import com.othellog4.game.board.Position;
import com.othellog4.game.player.Player;

/**
 * The JUnit test suit for the {@link Put} class.
 * 
 * <p>
 * Redefines the tests from the {@link GameCommandTest} class.
 * </p>
 * 
 * @author 	159015260 John Berg
 * @since	16/02/2018
 * @version	05/03/2018
 */
public class PutTest extends GameCommandTest
{
	//=========================================================================
	//Tests.
	/**
	 * Test the {@link Put#position()} method of the {@link Put} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Put#position()} method
	 * returns a {@link Position} object which is equal to the {@link Position}
	 * object used for the construction of the {@link Put} object.
	 * </p>
	 */
	@Test
	public final void testPosition()
	{
		assertEquals(
				Position.at(4, 7),
				new Put(new Player(), Position.at(4, 7)).position());
	}
	//=========================================================================
	//Overriden tests.
	/**
	 * Test the {@link Put#getSource()} method of the {@link Put} class.
	 * 
	 * <p>
	 * Redefined from the {@link GameCommandTest} class.
	 * </p>
	 * 
	 * <p>
	 * This test should only pass if the {@link Put#getSource()} method
	 * returns the {@link com.othellog4.game.player.Participant} object
	 * which was used to create the {@link Put} object.
	 * </p>
	 */
	@Override
	@Test
	public final void testGetSource()
	{
		final Player source = new Player();
		assertSame(source, new Put(source, Position.at(3, 3)).getSource());
	}
	/**
	 * Test the {@link Put#canExecute(com.othellog4.game.player.Participant)}
	 * method of the {@link Put} class.
	 * 
	 * <p>
	 * Redefined from the {@link GameCommandTest} class.
	 * </p>
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link Put#canExecute(com.othellog4.game.player.Participant)} returns
	 * <code>true</code> when passed a
	 * {@link com.othellog4.game.player.Participant} which is equal to the
	 * {@link com.othellog4.game.player.Participant} object which was used
	 * to initialise the {@link Put} object.
	 * </p>
	 */
	@Override
	@Test
	public final void testCanExecute_IssuersTurn()
	{
		final Player player = new Player();
		assertTrue(new Put(player, Position.at(0, 0))
				.canExecute(player));
	}
	/**
	 * Test the {@link Put#canExecute(com.othellog4.game.player.Participant)}
	 * method of the {@link Put} class.
	 * 
	 * <p>
	 * Redefined from the {@link GameCommandTest} class.
	 * </p>
	 * 
	 * <p>
	 * This test should only pass if the
	 * {@link Put#canExecute(com.othellog4.game.player.Participant)} returns
	 * <code>false</code> when passed a
	 * {@link com.othellog4.game.player.Participant} which is not equal to the
	 * {@link com.othellog4.game.player.Participant} object which was used
	 * to initialise the {@link Put} object.
	 * </p>
	 */
	@Override
	@Test
	public final void testCanExecute_NotIssuersTurn()
	{
		assertFalse(new Put(new Player(), Position.at(0, 0))
				.canExecute(new Player()));
	}
	/**
	 * Test the {@link Put#execute(Game)} method of the {@link Put} class.
	 * 
	 * <p>
	 * Redefined from the {@link GameCommandTest} class.
	 * </p>
	 * 
	 * <p>
	 * This test should only pass if the {@link Put#execute(Game)} method
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
		new Put(new Player(), Position.at(0, 0)).execute(null);
	}
	/**
	 * Test the {@link Put#execute(Game)} method of the {@link Put} class.
	 * 
	 * <p>
	 * Redefined from the {@link GameCommandTest} class.
	 * </p>
	 * 
	 * <p>
	 * This test should only pass if the {@link Put#execute(Game)} method
	 * throws a {@link GameException} when attempting to execute a {@link Put}
	 * command on an illegal position on a {@link Game} object.
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
		new Put(new Player(), Position.at(0, 0))
				.execute(game);
	}
	/**
	 * Test the {@link Put#execute(Game)} method of the {@link Put} class.
	 * 
	 * <p>
	 * Redefined from the {@link GameCommandTest} class.
	 * </p>
	 * 
	 * <p>
	 * This test should only pass if the {@link Put#execute(Game)} method
	 * successfully modifies the state of a {@link Game} object by executing
	 * and on a legal target location.
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
		final Position pos = game.getBoard().legalMoves(game.getCurrent())
				.iterator().next();
		new Put(new Player(), pos)
						.execute(game);
		assertTrue(game.getBoard().view(pos).isPresent());
	}
	/**
	 * Test the {@link Put#toString()} method of the {@link Put} class.
	 * 
	 * <p>
	 * Redefined from the {@link GameCommandTest} class.
	 * </p>
	 * 
	 * <p>
	 * This test should only pass if the {@link Put#toString()} method returns
	 * a {@link String} which contains the {@link String} from the
	 * {@link Position#toString()} for the {@link Position} which was used
	 * to initialise the {@link Put} object.
	 * </p>
	 */
	@Override
	@Test
	public final void testToString()
	{
		assertTrue(new Put(new Player(), Position.at(3,  2))
				.toString()
				.contains(Position.at(3, 2).toString()));
	}
}
