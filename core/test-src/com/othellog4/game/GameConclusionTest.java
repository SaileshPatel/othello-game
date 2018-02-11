package com.othellog4.game;

import org.junit.Test;

import com.othellog4.game.board.Piece;

import static org.junit.Assert.*;

/**
 * The JUnit test suite for the {@link GameConclusion} class.
 * 
 * @author 	159014260 John Berg
 * @since	10/02/2018
 * @version 11/02/2018
 */
public class GameConclusionTest
{
	/**
	 * Test the {@link GameConclusion#isDraw()} method of the
	 * {@link GameConclusion} class.
	 * 
	 * <p>
	 * This test should only pass if {@link GameConclusion#isDraw()} returns
	 * <code>true</code>.
	 * </p>
	 */
	@Test
	public final void testIsDraw_Draw()
	{
		assertTrue(GameConclusion.draw().isDraw());
	}
	/**
	 * Test the {@link GameConclusion#isDraw()} method of the
	 * {@link GameConclusion} class.
	 * 
	 * <p>
	 * This test should only pass if {@link GameConclusion#isDraw()} returns
	 * <code>false</code>.
	 * </p>
	 */
	@Test
	public final void testIsDraw_NotDraw()
	{
		assertFalse(GameConclusion.winner(Piece.PIECE_A).isDraw());
	}
	/**
	 * Test the {@link GameConclusion#getWinner()} method of the
	 * {@link GameConclusion} class.
	 * 
	 * <p>
	 * This test should only pass if {@link GameConclusion#getWinner()} throws
	 * an {@link IllegalStateException}.
	 * </p>
	 */
	@Test(expected = IllegalStateException.class)
	public final void testGetWinner_Draw()
	{
		GameConclusion.draw().getWinner();
	}
	/**
	 * Test the {@link GameConclusion#getWinner()} method of the
	 * {@link GameConclusion} class.
	 * 
	 * <p>
	 * This test should only pass if {@link GameConclusion#getWinner()} returns
	 * the {@link Piece} object which was set as the winner.
	 * </p>
	 */
	@Test
	public final void testGetWinner_NotDraw()
	{
		assertEquals(
				Piece.PIECE_A,
				GameConclusion.winner(Piece.PIECE_A).getWinner());
	}
	/**
	 * Test the {@link GameConclusion#getLoser()} method of the
	 * {@link GameConclusion} class.
	 * 
	 * <p>
	 * This test should only pass if {@link GameConclusion#getLoser()} throws
	 * an {@link IllegalStateException}.
	 * </p>
	 */
	@Test
	public final void testGetLoser_Draw()
	{
		GameConclusion.winner(Piece.PIECE_A).getLoser();
	}
	/**
	 * Test the {@link GameConclusion#getLoser()} method of the
	 * {@link GameConclusion} class.
	 * 
	 * <p>
	 * This test should only pass if {@link GameConclusion#getLoser()} returns
	 * the {@link Piece} object which was set as the loser.
	 * </p>
	 */
	@Test
	public final void testGetLoser_NotDraw()
	{
		assertEquals(
				Piece.PIECE_A,
				GameConclusion.loser(Piece.PIECE_A).getLoser());
	}
	/**
	 * Test the {@link GameConclusion#equals(Object)} method of the
	 * {@link GameConclusion} class.
	 * 
	 * <p>
	 * This test should only pass if {@link GameConclusion#equals(Object)}
	 * returns <code>false</code> when compared with another
	 * {@link GameConclusion} object which is not in the same state as the
	 * other {@link GameConclusion} object.
	 * </p>
	 */
	@Test
	public final void testEquals_NotEqual()
	{
		assertNotEquals(
				GameConclusion.draw(),
				GameConclusion.winner(Piece.PIECE_A));
	}
	/**
	 * Test the {@link GameConclusion#equals(Object)} method of the
	 * {@link GameConclusion} class.
	 * 
	 * <p>
	 * This test should only pass if {@link GameConclusion#equals(Object)}
	 * returns <code>true</code> when compared with another
	 * {@link GameConclusion} object which is in the same state as the other
	 * {@link GameConclusion} object.
	 * </p>
	 */
	@Test
	public final void testEquals_Equal()
	{
		assertEquals(
				GameConclusion.winner(Piece.PIECE_A),
				GameConclusion.winner(Piece.PIECE_A));
	}
	/**
	 * Test the {@link GameConclusion#hashCode()} method of the
	 * {@link GameConclusion} class.
	 * 
	 * <p>
	 * This test should only pass if {@link GameConclusion#hashCode()} returns
	 * <code>0</code> when the {@link GameConclusion} object is in the draw
	 * state.
	 * </p>
	 */
	@Test
	public final void testHashCode_Draw()
	{
		assertEquals(0, GameConclusion.draw().hashCode());
	}
	/**
	 * Test the {@link GameConclusion#hashCode()} method of the
	 * {@link GameConclusion} class.
	 * 
	 * <p>
	 * This test should only pass if {@link GameConclusion#hashCode()} returns
	 * different hash values for {@link GameConclusion} objects with different
	 * winners.
	 * </p>
	 * 
	 * <p>
	 * May fail if the different winners return the same hash value, however,
	 * this is unlikely to happen.
	 * </p>
	 */
	@Test
	public final void testHashCode_NotEqual()
	{
		assertNotEquals(
				GameConclusion.loser(Piece.PIECE_A).hashCode(),
				GameConclusion.winner(Piece.PIECE_A).hashCode());
	}
	/**
	 * Test the {@link GameConclusion#hashCode()} method of the
	 * {@link GameConclusion} class.
	 * 
	 * <p>
	 * This test should only pass if {@link GameConclusion#hashCode()} returns
	 * the same hash value for {@link GameConclusion} objects with the same
	 * winner.
	 * </p>
	 */
	@Test
	public final void testHashCode_Equal()
	{
		assertEquals(
				GameConclusion.winner(Piece.PIECE_A).hashCode(),
				GameConclusion.winner(Piece.PIECE_A).hashCode());
	}
	/**
	 * Test the {@link GameConclusion#toString()} method of the
	 * {@link GameConclusion} class.
	 * 
	 * <p>
	 * The test should only pass if {@link GameConclusion#toString()} returns
	 * a {@link String} object which contains the name of the {@link Piece}
	 * which is the winner.
	 * </p>
	 */
	@Test
	public final void testToString_NotDraw()
	{
		assertTrue(GameConclusion.winner(Piece.PIECE_A)
				.toString()
				.contains(Piece.PIECE_A.name()));
	}
	/**
	 * Test the {@link GameConclusion#toString()} method of the
	 * {@link GameConclusion} class.
	 * 
	 * <p>
	 * The test should only pass if {@link GameConclusion#toString()} returns
	 * a {@link String} object which contains "Draw".
	 * </p>
	 */
	@Test
	public final void testToString_Draw()
	{
		assertTrue(GameConclusion.draw()
				.toString()
				.contains("Draw"));
	}
}
