package com.othellog4.game;

import org.junit.Before;
import org.junit.Test;

import com.othellog4.game.board.GameBoard;
import com.othellog4.game.board.InvalidMoveException;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;

import static org.junit.Assert.*;

/**
 * 
 * 
 * 
 * 
 * @author 	159014260 John Berg
 * @since 	25/01/2018
 * @version 25/01/2018
 */
public class GameTest
{
	//=========================================================================
	//Fields.
	/**
	 */
	private Game game;
	//=========================================================================
	//Before.
	/**
	 * 
	 */
	@Before
	public final void setup()
	{
		game = new Game(new GameBoard(8, 8));
	}
	//=========================================================================
	//Test.
	/**
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public final void testPut_NullArg()
			throws
			InvalidMoveException
	{
		game.put(null);
	}
	/**
	 * 
	 */
	@Test(expected = InvalidMoveException.class)
	public final void testPut_OutsideBoard()
			throws
			InvalidMoveException
	{
		game.put(Position.at(-1, -1));
	}
	/**
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public final void testPut_OnExistingPiece()
	{
		
		fail();
	}
	/**
	 * 
	 */
	@Test
	public final void testPut_ValidMove()
	{
		fail();
	}
	/**
	 * 
	 */
	@Test
	public final void testAddListener_NullArg()
	{
		fail();
	}
	/**
	 * 
	 */
	@Test
	public final void testAddListener_Success()
	{
		fail();
	}
	/**
	 * 
	 */
	@Test
	public final void testRemoveListener_NullArg()
	{
		fail();
	}
	/**
	 * 
	 */
	@Test
	public final void testRemoveListener_NotExisitng()
	{
		fail();
	}
	/**
	 * 
	 */
	@Test
	public final void testRemoveListener_Success()
	{
		fail();
	}
	/**
	 * 
	 */
	@Test
	public final void testGetPlayer1()
	{
		assertEquals(Piece.PIECE_A, game.getPlayer2());
	}
	/**
	 * 
	 */
	@Test
	public final void testGetPlayer2()
	{
		//Not complete yet.
		assertEquals(Piece.PIECE_B, game.getPlayer2());
	}
	/**
	 * 
	 */
	@Test
	public final void testGetCurrent_Player1()
	{
		fail();
	}
	/**
	 * 
	 */
	@Test
	public final void testGetCurrent_Player2()
	{
		fail();
	}
	/**
	 * 
	 */
	@Test
	public final void testGetBoardView()
	{
		fail();
	}
}
