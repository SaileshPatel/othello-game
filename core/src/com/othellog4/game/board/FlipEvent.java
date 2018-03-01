package com.othellog4.game.board;

/**
 * The {@code FlipEvent} class is a record which hold information about the
 * flipped {@link Piece} objects at a specific {@link Position}.
 * 
 * @author 	159014260 John Berg
 * @since 	01/03/2018
 * @version 01/03/2018
 */
public final class FlipEvent
{
	/**
	 * The {@link Piece} object which was flipped.
	 * 
	 * @see Piece
	 */
	public final Piece initial;
	/**
	 * The {@link Position} object which represents the location of the
	 * {@link Piece}.
	 * 
	 * @see Position
	 */
	public final Position at;
	/**
	 * Create a new {@code FlipEvent} for a {@link Piece} which will be flipped
	 * at the {@link Position} location.
	 * 
	 * @param initial The {@link Piece} object before the flip.
	 * @param at The {@link Position} object of the <code>initial</code>
	 * 			{@link Piece} object.
	 */
	public FlipEvent(
			final Piece initial,
			final Position at)
	{
		this.initial = initial;
		this.at = at;
	}
}
