package com.othellog4.game.board;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The {@code PieceTest} class is the JUnit test suit for the {@link Piece}
 * class.
 * 
 * @author 	159015260 John Berg
 * @since 	17/02/2018
 * @version 17/02/2018
 */
public class PieceTest
{
	//=========================================================================
	//Tests.
	/**
	 * Test the {@link Piece#flip()} method of the {@link Piece} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Piece#flip()} returns
	 * {@link Piece#PIECE_B} when invoked on {@link Piece#PIECE_A}.
	 * </p>
	 */
	@Test
	public final void testFlip_PIECE_A()
	{
		assertEquals(Piece.PIECE_B, Piece.PIECE_A.flip());
	}
	/**
	 * Test the {@link Piece#flip()} method of the {@link Piece} class.
	 * 
	 * <p>
	 * This test should only pass if the {@link Piece#flip()} returns
	 * {@link Piece#PIECE_A} when invoked on {@link Piece#PIECE_B}.
	 * </p>
	 */
	@Test
	public final void testFlip_PIECE_B()
	{
		assertEquals(Piece.PIECE_A, Piece.PIECE_B.flip());
	}
}
