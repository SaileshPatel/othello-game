package com.othellog4.game.board;

import java.util.Optional;
import java.util.Set;

/**
 * This is the BoardView. This will eventually be used to control the board. 
 * @author 	159014260 John Berg
 * @author 	James Shorthouse
 * @since 	23/10/2017
 * @version 25/01/2017
 *
 */
public interface BoardView {
	/**
	 * This method determines if the game has ended
	 * @return true if ended, otherwise false
	 */
	public boolean isEnd();
	
	/**
	 * This method returns the size of the board
	 * @return the size of the board
	 */
	public int size();
	/**
	 * Count the number of {@link Piece} object which exist on the
	 * {@link BoardView}.
	 * 
	 * @param piece The {@link Piece} object to be counted.
	 * @return The number of instances of that <code>piece</code>.
	 */
	public int count(final Piece piece);
	/**
	 * This method counts the number of flips needed
	 * @return the number of flips needed
	 */
	public int countFlips(int x, int y, Piece player);
	/**
	 * This method determines all legal moves a player can take
	 * @param piece the Piece to determine legal moves from
	 * @return a set of legal Positions to move to
	 */
	public Set<Position> legalMoves(Piece piece);
	
	/**
	 * This method allows the board to view the position of a Piece
	 * @param boardPosition The {@link Position} object representing a
	 * 			position on the {@code BoardView}.
	 * @return The {@link Piece} object at the position.
	 */
	public Optional<Piece> view(Position boardPosition);
	/**
	 * Get the {@link BoardView} of <code>this</code>.
	 * 
	 * @return The {@link BoardView} representing <code>this</code>.
	 */
	public BoardView getView();
}
