/**
 * 
 */
package com.othellog4.game.board;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.othellog4.game.Game;
import com.othellog4.game.board.GameBoard;
import com.othellog4.game.board.Piece;

/**
 * The {@code GameBoardTest} class is a JUnit test suit for the {@link GameBoard} class.
 * Tests are applied to the standard 8x8 board.
 * 
 * @author 159148026 Arvinder Chatha
 *
 */
public class GameBoardTest {

	private static final Piece player1 = Piece.PIECE_A;
	private static final Piece player2 = Piece.PIECE_B;
	private static final int boardSize = 8;

	private GameBoard board;
	private GameBoard player1WinningBoard;
	private GameBoard player2WinningBoard;

	/**
	 * 
	 */
	@Before
	public void setUp(){
		board = new GameBoard(boardSize);
		player1WinningBoard = new GameBoard(new Piece[][]{
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, player1, player2, null, null, null },
				{ null, null, null, player1, player1, null, null, null },
				{ null, null, null, player1, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }
		});
		player2WinningBoard = new GameBoard(new Piece[][]{
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, player1, player2, null, null, null },
				{ null, null, null, player1, player2, player2, null, null },
				{ null, null, null, player2, null, null, null, null },
				{ null, null, player2, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }
		});
	}

	
	//=========================================================================
	//METHODS
	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#isDraw()}.
	 */
	@Test
	public void testIsDraw() {
		assertTrue(board.isDraw());

		assertFalse(player1WinningBoard.isDraw());
		assertFalse(player2WinningBoard.isDraw());
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#put(com.othellog4.game.board.Position, com.othellog4.game.board.Piece)}.
	 */
	@Test
	public void testPut() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#isEnd()}.
	 */
	@Test
	public void testIsEnd() {

		assertFalse(board.isEnd());
		assertFalse(player1WinningBoard.isEnd());
		assertFalse(player2WinningBoard.isEnd());

		//test for full board
		GameBoard fullBoard = new GameBoard(new Piece[][]{
				{ player1, player2, player1, player2, player2, player2, player1, player1 },
				{ player2, player1, player1, player1, player2, player1, player1, player1 },
				{ player2, player2, player1, player2, player2, player2, player2, player2 },
				{ player1, player2, player2, player1, player2, player2, player2, player2 },
				{ player2, player1, player2, player1, player2, player2, player2, player2 },
				{ player2, player1, player2, player2, player2, player1, player2, player1 },
				{ player1, player2, player2, player2, player1, player1, player1, player2 },
				{ player2, player1, player2, player1, player2, player1, player2, player2 }
		});
		assertTrue(fullBoard.isEnd());

		//test for no moves left on non-full board
		GameBoard nonFullBoard = new GameBoard(new Piece[][]{
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, player2, player2, null, null, null },
				{ null, null, null, player2, player2, player2, null, null },
				{ null, null, null, player2, null, null, null, null },
				{ null, null, player2, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }
		});
		assertTrue(nonFullBoard.isEnd());
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#size()}.
	 */
	@Test
	public void testSize() {
		assertEquals(8, board.size());
		assertEquals(8, player1WinningBoard.size());
		assertEquals(8, player2WinningBoard.size());
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#count(com.othellog4.game.board.Piece)}.
	 */
	@Test
	public void testCount() {
		//standard board
		assertEquals(2, board.count(player1));
		assertEquals(2, board.count(player2));
		
		//player1WinningBoard
		assertEquals(4, player1WinningBoard.count(player1));
		assertEquals(1, player1WinningBoard.count(player2));
		
		//player2WinningBoard
		assertEquals(2, player2WinningBoard.count(player1));
		assertEquals(5, player2WinningBoard.count(player2));
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#countFlips(int, int, com.othellog4.game.board.Piece)}.
	 */
	@Test
	public void testCountFlips() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#flips()}.
	 */
	@Test
	public void testFlips() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#legalMoves(com.othellog4.game.board.Piece)}.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testLegalMoves() {
		//standard board
		assertTrue(board.legalMoves(player1).contains(new Position(4,2)));
		assertTrue(board.legalMoves(player1).contains(new Position(5,3)));
		assertTrue(board.legalMoves(player1).contains(new Position(3,5)));
		assertTrue(board.legalMoves(player1).contains(new Position(2,4)));
		assertEquals(4, board.legalMoves(player1).size());

		assertTrue(board.legalMoves(player2).contains(new Position(3,2)));
		assertTrue(board.legalMoves(player2).contains(new Position(2,3)));
		assertTrue(board.legalMoves(player2).contains(new Position(5,4)));
		assertTrue(board.legalMoves(player2).contains(new Position(4,5)));
		assertEquals(4, board.legalMoves(player2).size());


		//player1WinningBoard
		assertTrue(player1WinningBoard.legalMoves(player1).contains(new Position(4,2)));
		assertTrue(player1WinningBoard.legalMoves(player1).contains(new Position(5,3)));
		assertTrue(player1WinningBoard.legalMoves(player1).contains(new Position(5,2)));
		assertEquals(3, player1WinningBoard.legalMoves(player1).size());

		assertTrue(player1WinningBoard.legalMoves(player2).contains(new Position(2,3)));
		assertTrue(player1WinningBoard.legalMoves(player2).contains(new Position(2,5)));
		assertTrue(player1WinningBoard.legalMoves(player2).contains(new Position(4,5)));
		assertEquals(3, player1WinningBoard.legalMoves(player2).size());


		//player2WinningBoard
		assertTrue(player2WinningBoard.legalMoves(player1).contains(new Position(5,3)));
		assertTrue(player2WinningBoard.legalMoves(player1).contains(new Position(3,6)));
		assertTrue(player2WinningBoard.legalMoves(player1).contains(new Position(6,4)));
		assertTrue(player2WinningBoard.legalMoves(player1).contains(new Position(5,2)));
		assertTrue(player2WinningBoard.legalMoves(player1).contains(new Position(5,5)));
		assertEquals(5, player2WinningBoard.legalMoves(player1).size());

		assertTrue(player2WinningBoard.legalMoves(player2).contains(new Position(2,4)));
		assertTrue(player2WinningBoard.legalMoves(player2).contains(new Position(2,5)));
		assertTrue(player2WinningBoard.legalMoves(player2).contains(new Position(3,2)));
		assertTrue(player2WinningBoard.legalMoves(player2).contains(new Position(2,3)));
		assertTrue(player2WinningBoard.legalMoves(player2).contains(new Position(2,2)));
		assertEquals(5, player2WinningBoard.legalMoves(player2).size());
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#view(com.othellog4.game.board.Position)}.
	 */
	@Test
	public void testView() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#flip(int, int, com.othellog4.game.board.Piece)}.
	 */
	@Test
	public void testFlip() {
		//standard board
		board.flip(2, 4, player1); 
		//TODO: works not as expected, flips pieces in between but doesnt place piece?
		
		GameBoard newBoard = new GameBoard(new Piece[][]{
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, player1, player2, null, null, null },
				{ null, null, player1, player1, player1, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }
		});
		
		assertTrue(board.equals(newBoard));
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#winning()}.
	 */
	@Test
	public void testWinning() {
		assertEquals(null, board.winning());
		assertEquals(player1, player1WinningBoard.winning());
		assertEquals(player2, player2WinningBoard.winning());
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#losing()}.
	 */
	@Test
	public void testLosing() {
		assertEquals(null, board.losing());
		assertEquals(player2, player1WinningBoard.losing());
		assertEquals(player1, player2WinningBoard.losing());
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#tryPut(com.othellog4.game.board.Position, com.othellog4.game.board.Piece)}.
	 */
	@Test
	public void testTryPut() {
		fail("Not yet implemented"); // TODO
	}

}
