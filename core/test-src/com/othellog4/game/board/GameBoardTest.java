/**
 * 
 */
package com.othellog4.game.board;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.othellog4.game.board.GameBoard;
import com.othellog4.game.board.Piece;

/**
 * The {@code GameBoardTest} class is a JUnit test suit for the 
 * {@link GameBoard} class.
 * Tests are applied to the standard 8x8 board.
 * 
 * @author 159148026 Arvinder Chatha
 *
 */
public class GameBoardTest {

	private static final Piece p1 = Piece.PIECE_A;
	private static final Piece p2 = Piece.PIECE_B;
	private static final int boardSize = 8;

	private GameBoard board;
	private GameBoard p1WinningBoard;
	private GameBoard p2WinningBoard;

	/**
	 * 
	 */
	@Before
	public void setUp(){
		board = new GameBoard(boardSize);
		p1WinningBoard = new GameBoard(new Piece[][]{
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, p1, p2, null, null, null },
				{ null, null, null, p1, p1, null, null, null },
				{ null, null, null, p1, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }
		});
		p2WinningBoard = new GameBoard(new Piece[][]{
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, p1, p2, null, null, null },
				{ null, null, null, p1, p2, p2, null, null },
				{ null, null, null, p2, null, null, null, null },
				{ null, null, p2, null, null, null, null, null },
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

		assertFalse(p1WinningBoard.isDraw());
		assertFalse(p2WinningBoard.isDraw());
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#put(
	 * com.othellog4.game.board.Position, com.othellog4.game.board.Piece)}.
	 */
	@Test
	public void testPut() {
		try {
			board.put(Position.at(2, 4), p1);
		}
		catch(InvalidMoveException e) {
			e.printStackTrace();
		}
		GameBoard newBoard = new GameBoard(new Piece[][]{
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, p1, null, null, null },
				{ null, null, null, p1, p1, null, null, null },
				{ null, null, null, p2, p1, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }
		});

		assertEquals(newBoard.count(p1), board.count(p1));
		assertEquals(newBoard.count(p2), board.count(p2));

		assertEquals(
				newBoard.view(Position.at(2, 4)),
				board.view(Position.at(2, 4))
				);
		assertEquals(
				newBoard.view(Position.at(3, 3)),
				board.view(Position.at(3, 3))
				);
		assertEquals(
				newBoard.view(Position.at(3, 4)),
				board.view(Position.at(3, 4))
				);
		assertEquals(
				newBoard.view(Position.at(4, 4)),
				board.view(Position.at(4, 4))
				);

		assertEquals(
				newBoard.view(Position.at(4, 3)),
				board.view(Position.at(4, 3))
				);
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#isEnd()}.
	 */
	@Test
	public void testIsEnd() {

		assertFalse(board.isEnd());
		assertFalse(p1WinningBoard.isEnd());
		assertFalse(p2WinningBoard.isEnd());

		//test for full board
		GameBoard fullBoard = new GameBoard(new Piece[][]{
				{ p1, p2, p1, p2, p2, p2, p1, p1 },
				{ p2, p1, p1, p1, p2, p1, p1, p1 },
				{ p2, p2, p1, p2, p2, p2, p2, p2 },
				{ p1, p2, p2, p1, p2, p2, p2, p2 },
				{ p2, p1, p2, p1, p2, p2, p2, p2 },
				{ p2, p1, p2, p2, p2, p1, p2, p1 },
				{ p1, p2, p2, p2, p1, p1, p1, p2 },
				{ p2, p1, p2, p1, p2, p1, p2, p2 }
		});
		assertTrue(fullBoard.isEnd());

		//test for no moves left on non-full board
		GameBoard nonFullBoard = new GameBoard(new Piece[][]{
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, p2, p2, null, null, null },
				{ null, null, null, p2, p2, p2, null, null },
				{ null, null, null, p2, null, null, null, null },
				{ null, null, p2, null, null, null, null, null },
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
		assertEquals(8, p1WinningBoard.size());
		assertEquals(8, p2WinningBoard.size());
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#count(
	 * com.othellog4.game.board.Piece)}.
	 */
	@Test
	public void testCount() {
		//standard board
		assertEquals(2, board.count(p1));
		assertEquals(2, board.count(p2));

		//player1WinningBoard
		assertEquals(4, p1WinningBoard.count(p1));
		assertEquals(1, p1WinningBoard.count(p2));

		//player2WinningBoard
		assertEquals(2, p2WinningBoard.count(p1));
		assertEquals(5, p2WinningBoard.count(p2));
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#countFlips(
	 * int, int, com.othellog4.game.board.Piece)}.
	 */
	@Test
	public void testCountFlips() {
		//Position that flips one piece
		assertEquals(1, board.countFlips(3, 5, p1));

		//Position that flips nothing
		assertEquals(0, board.countFlips(0, 0, p1));

		//Position that flips more than one
		assertEquals(2, p2WinningBoard.countFlips(2, 3, p2));
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#legalMoves(
	 * com.othellog4.game.board.Piece)}.
	 */
	@Test
	public void testLegalMoves() {
		//standard board
		assertTrue(board.legalMoves(p1).contains(Position.at(2,4)));
		assertTrue(board.legalMoves(p1).contains(Position.at(3,5)));
		assertTrue(board.legalMoves(p1).contains(Position.at(5,3)));
		assertTrue(board.legalMoves(p1).contains(Position.at(4,2)));
		assertEquals(4, board.legalMoves(p1).size());

		assertTrue(board.legalMoves(p2).contains(Position.at(3,2)));
		assertTrue(board.legalMoves(p2).contains(Position.at(2,3)));
		assertTrue(board.legalMoves(p2).contains(Position.at(5,4)));
		assertTrue(board.legalMoves(p2).contains(Position.at(4,5)));
		assertEquals(4, board.legalMoves(p2).size());


		//player1WinningBoard
		assertTrue(p1WinningBoard.legalMoves(p1).contains(Position.at(2,4)));
		assertTrue(p1WinningBoard.legalMoves(p1).contains(Position.at(3,5)));
		assertTrue(p1WinningBoard.legalMoves(p1).contains(Position.at(2,5)));
		assertEquals(3, p1WinningBoard.legalMoves(p1).size());

		assertTrue(p1WinningBoard.legalMoves(p2).contains(Position.at(3,2)));
		assertTrue(p1WinningBoard.legalMoves(p2).contains(Position.at(5,2)));
		assertTrue(p1WinningBoard.legalMoves(p2).contains(Position.at(5,4)));
		assertEquals(3, p1WinningBoard.legalMoves(p2).size());


		//player2WinningBoard
		assertTrue(p2WinningBoard.legalMoves(p1).contains(Position.at(3,5)));
		assertTrue(p2WinningBoard.legalMoves(p1).contains(Position.at(6,3)));
		assertTrue(p2WinningBoard.legalMoves(p1).contains(Position.at(4,6)));
		assertTrue(p2WinningBoard.legalMoves(p1).contains(Position.at(2,5)));
		assertTrue(p2WinningBoard.legalMoves(p1).contains(Position.at(5,5)));
		assertEquals(5, p2WinningBoard.legalMoves(p1).size());

		assertTrue(p2WinningBoard.legalMoves(p2).contains(Position.at(4,2)));
		assertTrue(p2WinningBoard.legalMoves(p2).contains(Position.at(5,2)));
		assertTrue(p2WinningBoard.legalMoves(p2).contains(Position.at(2,3)));
		assertTrue(p2WinningBoard.legalMoves(p2).contains(Position.at(3,2)));
		assertTrue(p2WinningBoard.legalMoves(p2).contains(Position.at(2,2)));
		assertEquals(5, p2WinningBoard.legalMoves(p2).size());
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#view(
	 * com.othellog4.game.board.Position)}.
	 */
	@Test
	public void testView() {
		//PIECE A value
		assertEquals(Optional.of(Piece.PIECE_A), board.view(Position.at(3,3)));

		//PIECE B value
		assertEquals(Optional.of(Piece.PIECE_B), board.view(Position.at(3,4)));

		//null value
		assertEquals(Optional.empty(), board.view(Position.at(0,0)));
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#flip(
	 * int, int, com.othellog4.game.board.Piece)}.
	 */
	@Test
	public void testFlip() {
		board.flip(2, 4, p1); 

		GameBoard newBoard = new GameBoard(new Piece[][]{
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, p1, p1, null, null, null },
				{ null, null, null, p2, p1, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null }
		});

		assertEquals(newBoard.count(p1), board.count(p1));
		assertEquals(newBoard.count(p2), board.count(p2));

		assertEquals(
				newBoard.view(Position.at(3, 3)),
				board.view(Position.at(3, 3))
				);
		assertEquals(
				newBoard.view(Position.at(3, 4)),
				board.view(Position.at(3, 4))
				);
		assertEquals(
				newBoard.view(Position.at(4, 3)),
				board.view(Position.at(4, 3))
				);
		assertEquals(
				newBoard.view(Position.at(4, 4)),
				board.view(Position.at(4, 4))
				);
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#winning()}.
	 */
	@Test
	public void testWinning() {
		assertEquals(null, board.winning());
		assertEquals(p1, p1WinningBoard.winning());
		assertEquals(p2, p2WinningBoard.winning());
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#losing()}.
	 */
	@Test
	public void testLosing() {
		assertEquals(null, board.losing());
		assertEquals(p2, p1WinningBoard.losing());
		assertEquals(p1, p2WinningBoard.losing());
	}

	/**
	 * Test method for {@link com.othellog4.game.board.GameBoard#tryPut(
	 * com.othellog4.game.board.Position, com.othellog4.game.board.Piece)}.
	 */
	@Test
	public void testTryPut() {
		GameBoard newBoard = null;
		try {
			newBoard = board.tryPut(Position.at(2, 4), p1);
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		}

		//Check to see if the original board has not been changed
		assertEquals(2, board.count(p1));
		assertEquals(2, board.count(p2));

		//Check to see if newBoard has been changed with correct Pieces
		assertEquals(4, newBoard.count(p1));
		assertEquals(1, newBoard.count(p2));
	}

}
