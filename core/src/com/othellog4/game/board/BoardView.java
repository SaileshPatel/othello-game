package com.othellog4.game.board;

import java.util.Optional;
import java.util.Set;

/**
 * The {@code BoardView} interface is a specification of a board in Othello
 * which can be inspected, but not modified.
 *
 * <p>
 * The operations declared in the {@code BoardView} interface is operations
 * which are safe, as in that they do not modify the state of the board.
 * </p>
 *
 * @author 	159014260 John Berg
 * @author 	James Shorthouse
 * @since 	23/10/2017
 * @version 01/03/2017
 */
public interface BoardView {
	/**
	 * This method determines if the game has ended
	 * @return true if ended, otherwise false
	 */
	public boolean isEnd();
	/**
	 * Check if the {@code BoardView} is in a draw.
	 *
	 * @return <code>true</code> If it is a draw, otherwise, returns
	 * 			<code>false</code>.
	 */
	public boolean isDraw();

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
	 * Get the {@link FlipEvent} objects which represent the {@link Piece}
	 * objects that were flipped.
	 *
	 * @return A {@link Set} of a sequence of {@link FlipEvent} objects of
	 * 			{@link Piece} objects which were flipped.
	 */
	public Set<FlipEvent[]> flips();
	/**
	 * This method determines all legal moves a player can take
	 * @param piece the Piece to determine legal moves from
	 * @return a set of legal Positions to move to
	 */
	public Set<Position> legalMoves(Piece piece);
	/**
	 * Get the {@link Piece} object which has the most instances.
	 *
	 * @return The {@link Piece} which is currently dominating the
	 * 			{@code BoardView}. Returns <code>null</code> if there is
	 * 			no {@link Piece} object currently dominating.
	 */
	public Piece winning();
	/**
	 * Get the {@link Piece} object which has the fewest instances.
	 *
	 * @return The {@link Piece} which has the fewest instances on the
	 * 			{@code BoardView}. Returns <code>null</code> if there is no
	 * 			{@link Piece} object which has the fewest instances.
	 */
	public Piece losing();
	/**
	 * This method allows the board to view the position of a Piece
	 * @param boardPosition The {@link Position} object representing a
	 * 			position on the {@code BoardView}.
	 * @return The {@link Piece} object at the position.
	 */
	public Optional<Piece> view(Position boardPosition);
	/**
	 * Try placing a {@link Piece} object at a specific {@link Position}.
	 *
	 * <p>
	 * This method does not make any modifications to the real
	 * {@code BoardView}.
	 * </p>
	 *
	 * @param pos The {@link Position} to try.
	 * @param piece The {@link Piece} object to put.
	 * @return The resulting {@code BoardView} object.
	 * @throws InvalidMoveException If the attempted move is not allowed.
	 */
	public BoardView tryPut(
			final Position pos,
			final Piece piece)
			throws
			InvalidMoveException;
	/**
	 * Get the {@link BoardView} of <code>this</code>.
	 *
	 * @return The {@link BoardView} representing <code>this</code>.
	 */
	public BoardView getView();
}
