package com.othellog4.game;

import com.othellog4.game.board.Piece;

/**
 * The {@code GameConclusion} class is the conclusion of a {@link Game}, which
 * describes the conclusion of that game.
 *
 * <p>
 * The information contained in a {@code GameConclusion} object concerns the
 * outcome of a {@link Game}, such as, the winner of the game or if the game
 * is a draw.
 * </p>
 *
 * <p>
 * Objects of the {@code GameConclusion} class are immutable.
 * </p>
 *
 * @author 	159014260 John Berg
 * @since	10/02/2018
 * @version 11/02/2018
 */
public class GameConclusion
{
	//=========================================================================
	//Static fields.
	/**
	 * The {@link String} which represent the error message for attempting to
	 * get the winner or loser from a {@code GameConclusion} which is a draw.
	 */
	private static final String DRAW_CONCLUSION = "The conclusion is a draw "
			+ "which has no winner/loser";
	//=========================================================================
	//Fields.
	/**
	 * The {@link Piece} object which is the winner of a {@link Game}.
	 *
	 * <p>
	 * If the {@link Piece} object has a value of <code>null</code> then the
	 * {@code GameConclusion} is representing a draw.
	 * </p>
	 *
	 * @see Piece
	 */
	private final Piece winner;
	//=========================================================================
	//Constructors.
	/**
	 * Construct a {@code GameConclusion} which represents a {@link Game} which
	 * is in a draw.
	 *
	 * <p>
	 * <b>For internal use only!</b>
	 * </p>
	 */
	private GameConclusion()
	{
		this(null);
	}
	/**
	 * Construct a {@code GameConclusion} with a specified {@link Piece}
	 * object as the winner of a {@link Game}.
	 *
	 * <p>
	 * If the value of the {@link Piece} object  is <code>null</code> the
	 * resulting {@code GameConclusion} is a draw.
	 * </p>
	 *
	 * <p>
	 * <b>For internal use only!</b>
	 * </p>
	 *
	 * @param winner The {@link Piece} object which is set as the winner of the
	 * 				game.
	 */
	private GameConclusion(final Piece winner)
	{
		this.winner = winner;
	}
	//=========================================================================
	//Methods.
	/**
	 * Check if <code>this</code> {@code GameConclusion} is representing a
	 * conclusion of a game which is in a  draw state.
	 *
	 * @return <code>true</code> if <code>this</code> {@code GameConclusion} is
	 * 			a draw, otherwise returns <code>false</code>.
	 */
	public final boolean isDraw()
	{
		return winner == null;
	}
	/**
	 * Get the {@link Piece} object which represents the winner of a
	 * {@link Game}.
	 *
	 * @return The winning {@link Piece} object.
	 * @throws IllegalStateException If <code>this</code> {@link GameConclusion}
	 * 			is a draw.
	 */
	public final Piece getWinner()
			throws
			IllegalStateException
	{
		if(isDraw())
			throw new IllegalStateException(DRAW_CONCLUSION);
		return winner;
	}
	/**
	 * Get the {@link Piece} object which represents the loser of a
	 * {@link Game}.
	 *
	 * @return The losing {@link Piece} object.
	 * @throws IllegalStateException If <code>this</code>
	 * 			{@link GameConclusion}
	 * 			is a draw.
	 */
	public final Piece getLoser()
	{
		if(isDraw())
			throw new IllegalStateException(DRAW_CONCLUSION);
		return winner.flip();
	}
	//=========================================================================
	//Overriden methods.
	/**
	 * Check if an {@link Object} is equal to <code>this</code>
	 * {@code GameConclusion}.
	 *
	 * <p>
	 * For equality, the object being tested must have the same state as
	 * <code>this</code>.
	 * </p>
	 *
	 * @return <code>true</code> if <code>o</code> is an instance of
	 * 		{@link GameConclusion}, and <code>o</code> and <code>this</code>
	 * 		have the same state; otherwise returns <code>false</code>.
	 */
	@Override
	public final boolean equals(final Object o)
	{
		if(o instanceof GameConclusion)
		{
			final GameConclusion gc = (GameConclusion) o;
			if(isDraw())
				return gc.isDraw();
			else
				return gc.isDraw()? false: getWinner().equals(gc.getWinner());
		}
		return false;
	}
	/**
	 * Get the hash value from <code>this</code> {@code GameConclusion}.
	 *
	 * @return 0 if <code>this</code> {@code GameConclusion} is a draw,
	 * 			otherwise, returns the hash code of the winner {@link Piece}
	 * 			object.
	 */
	@Override
	public final int hashCode()
	{
		return isDraw()? 0: getWinner().hashCode();
	}
	/**
	 * Get the {@link String} representation of <code>this</code>
	 * {@link GameConclusion}.
	 *
	 * <p>
	 * Returns "Draw" if its a draw, otherwise, returns the name of the
	 * {@link Piece} objct which is the winner.
	 * </p>
	 *
	 * @return The {@link String} representation of <code>this</code>.
	 */
	@Override
	public final String toString()
	{
		return isDraw()? "Draw": getWinner().name();
	}
	//=========================================================================
	//Static methods.
	/**
	 * Create a {@code GameConclusion} object which represents a conclusion of
	 * a {@link Game} which is in a draw.
	 *
	 * @return A {@code GameConclusion} object which is a draw.
	 */
	public static final GameConclusion draw()
	{
		return new GameConclusion();
	}
	/**
	 * Create a {@link GameConclusion} object using a {@link Piece} object,
	 * which represents the winning state for that {@link Piece} object.
	 *
	 * @param winner The {@link Piece} object which is the winner of a
	 * 			{@link Game}.
	 * @return The {@code GameConclusion} which represents the win of the
	 * 			<code>loser</code> {@link Piece} object.
	 */
	public static final GameConclusion winner(final Piece winner)
	{
		assert winner != null:
			"null should not be used as an arguemtn to "
			+ "GameConclusion.winner(Piece)";
		return new GameConclusion(winner);
	}
	/**
	 * Create a {@link GameConclusion} object using a {@link Piece} object,
	 * which represents the losing state for that {@link Piece} object.
	 *
	 * @param loser The {@link Piece} object which is the loser of a
	 * 			{@link Game}.
	 * @return The {@code GameConclusion} which represents the loss of the
	 * 			<code>loser</code> {@link Piece} object.
	 */
	public static final GameConclusion loser(final Piece loser)
	{
		assert loser != null:
			"null should not be used as an arguemtn to "
			+ "GameConclusion.loser(Piece)";
		return new GameConclusion(loser.flip());
	}
}
