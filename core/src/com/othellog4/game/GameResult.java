package com.othellog4.game;

import com.othellog4.game.board.Piece;
import com.othellog4.game.extension.GameExtension;

/**
 * The {@code GameResult} class is a record type which contains the results
 * generated from a {@link GameExtension} object.
 *
 * @author 	159014260 John Berg
 * @since 	06/03/2018
 * @version 06/03/2018
 */
public final class GameResult
{
	//=========================================================================
	//Fields.
	/**
	 * The {@link Class} object which corresponds to the {@link GameExtension}
	 * object <code>this</code> {@code GameResult} will extract the result
	 * from.
	 *
	 * @see GameExtension
	 */
	public final Class<? extends GameExtension> type;
	/**
	 * The {@link GameExtension} object which is the source for
	 * <code>this</code> {@code GameResult} object.
	 *
	 * @see GameExtension
	 */
	private final GameExtension source;
	//=========================================================================
	//Constructors.
	/**
	 * Create a {@code GameResult} object from a {@link GameExtension} object
	 * as a source
	 *
	 * @param source The {@link GameExtension} to get the result from.
	 * @throws NullPointerException If <code>source</code> is
	 * 			<code>null</code>.
	 * @throws IllegalArgumentException If {@link GameExtension#hasResult()}
	 * 			returns <code>false</code> when invoked on <code>source</code>.
	 * @see GameExtension
	 */
	public GameResult(final GameExtension source)
	{
		if(!source.hasResult())
			throw new IllegalArgumentException();
		this.type = source.getClass();
		this.source = source;
	}
	//=========================================================================
	//Methods.
	/**
	 * Get the result for the first player form <code>this</code>
	 * {@code GameResult} object.
	 *
	 * @return The result for the {@link Piece} representing the first player.
	 */
	public final int player1Result()
	{
		return result(Piece.player1());
	}
	/**
	 * Get the result for the second player form <code>this</code>
	 * {@code GameResult} object.
	 *
	 * @return The result for the {@link Piece} representing the second player.
	 */
	public final int player2Result()
	{
		return result(Piece.player2());
	}
	/**
	 * Get the <code>int</code> result for a specified {@link Piece} object.
	 *
	 * @param piece The {@link Piece} object to get the result for.
	 * @return The result for the <code>piece</code> object.
	 */
	public final int result(final Piece piece)
	{
		return source.result(piece);
	}
}
