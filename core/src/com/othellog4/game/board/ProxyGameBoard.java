package com.othellog4.game.board;

import java.util.Optional;
import java.util.Set;

/**
 * The {@code ProxyGameBoard} is a representative of the {@link GameBoard}
 * class.
 * 
 * <p>
 * The {@code ProxyGameBoard} functions as a wrapper class for the
 * {@link GameBoard} class.
 * </p>
 * 
 * <p>
 * {@code ProxyGameBoard} implements the {@link BoardView} interface, so that
 * {@code ProxyGameBoard} must override all the methods in {@link BoardView}.
 * </p>
 * 
 * @author	159014260 John Berg
 * @since	20/11/2017
 * @version 25/11/2017
 * @see BoardView
 * @see GameBoard
 */
public final class ProxyGameBoard implements BoardView
{
	//=========================================================================
	//Fields.
	/**
	 * The wrapee {@link GameBoard} which is used to delegate the methods calls
	 * from <code>this</code> {@code ProxyGameBoard}.
	 * 
	 * @see Board
	 */
	private final GameBoard board;
	//=========================================================================
	//Constructors.
	/**
	 * Create a {@code ProxyGameBoard} which is a read-only version of a
	 * {@link GameBoard}.
	 * 
	 * @param board The {@link GameBoard} which the created
	 * 			{@code ProxyGameBoard} is a read-only version of.
	 * @throws NullPointerException If <code>board</code> is <code>null</code>
	 */
	public ProxyGameBoard(final GameBoard board)
			throws
			NullPointerException
	{
		if(board == null)
			throw new NullPointerException();
		this.board = board;
	}
	//=========================================================================
	//Overriden methods.
	/**
	 * Check if it is still possible to place {@link Piece} objects on the
	 * {@link GameBoard}.
	 * 
	 * <p>
	 * Delegates the call to {@link GameBoard}.
	 * </p>
	 * 
	 * @return The value that {@link GameBoard#isEnd()} returns.
	 * @see GameBoard#isEnd()
	 */
	@Override
	public boolean isEnd()
	{
		return board.isEnd();
	}
	/**
	 * Get the size of the {@link GameBoard}.
	 * 
	 * <p>
	 * Delegates the call to {@link GameBoard}.
	 * </p>
	 * 
	 * @return The result of {@link GameBoard#size()}.
	 * @see GameBoard#size()
	 */
	@Override
	public int size()
	{
		return board.size();
	}
	/**
	 * Count the number of {@link Piece} objects.
	 * 
	 * <p>
	 * Delegates call to {@link GameBoard}.
	 * </p>
	 * 
	 * @param piece The {@link Piece} object to be counted.
	 * @return The total number of <code>piece</code> instances.
	 */
	public final int count(final Piece piece)
	{
		return board.count(piece);
	}
	/**
	 * Count the flips which would be the result of a specific move.
	 * 
	 * <p>
	 * Delegates the call to {@link GameBoard}.
	 * </p>
	 * 
	 * @param x The column.
	 * @param y The row.
	 * @param player The {@link Piece} to be placed.
	 * @return The number of flips which would be the result of placing a
	 * 			{@link Piece} at a specific position.
	 * @throws NullPointerException If <code>player</code> is
	 * 			<code>null</code>.
	 * @see GameBoard#countFlips(int, int, Piece)
	 */
	@Override
	public int countFlips(int x, int y, Piece player)
			throws
			NullPointerException
	{
		return board.countFlips(x, y, player);
	}
	/**
	 * Get the legal {@link Position} objects which represent the positions on
	 * the {@link GameBoard} which are legal positions for a specific
	 * {@link Piece}.
	 * 
	 * <p>
	 * Delegates the call to {@link GameBoard}.
	 * </p>
	 * 
	 * @param piece The {@link Piece} to find the legal moves for.
	 * @return The {@link Set} of {@link Position} objects where a
	 * 			{@link Piece} of type <code>piece</code>.
	 * @throws NullPointerException If <code>piece</code> is <code>null</code>.
	 * @see GameBoard#legalMoves(Piece)
	 */
	@Override
	public Set<Position> legalMoves(Piece piece)
			throws
			NullPointerException
	{
		return board.legalMoves(piece);
	}
	/**
	 * Get the {@link Piece} object at a specific {@link Position}.
	 * 
	 * <p>
	 * Delegates the call to the {@link GameBoard}.
	 * </p>
	 * 
	 * @return The {@link Optional} {@link Piece} object which exists at a
	 * 			specific {@link Position}.
	 * @see GameBoard#view(Position)
	 */
	@Override
	public Optional<Piece> view(Position boardPosition)
	{
		return board.view(boardPosition);
	}
	/**
	 * Get the {@link BoardView} of <code>this</code>.
	 * 
	 * <p>
	 * {@code ProxyGameBoard} will always return itself as its own
	 * {@link BoardView}.
	 * </p>
	 * 
	 * @return <code>this</code>.
	 */
	@Override
	public BoardView getView()
	{
		return this;
	}
}