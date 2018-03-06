package com.othellog4.game.extension;

import com.othellog4.game.GameEvent;
import com.othellog4.game.GameManager;
import com.othellog4.game.GameResult;
import com.othellog4.game.board.Piece;
import com.othellog4.game.command.GameCommand;

/**
 * The {@code GameExtension} class is an abstract class which defines the
 * methods which should be invoked when events occur.
 * 
 * <p>
 * Only classes which exist in the same package as {@code GameExtension} can
 * extend the {@code GameExtension} class.
 * </p>
 * 
 * @author 	159014260 John Berg
 * @since	14/02/2018
 * @version 06/03/2018
 */
public abstract class GameExtension
{
	//=========================================================================
	//Constructors.
	/**
	 * Construct a {@code GameExtension} object.
	 * 
	 * <p>
	 * Package private to restrict the ability to inherit the
	 * {@code GameExtension} class.
	 * </p>
	 */
	GameExtension()
	{
	}
	//=========================================================================
	//Methods.
	/**
	 * Get the <code>int</code> representation of the result from
	 * <code>this</code> {@code GameExtension} object for the first player.
	 * 
	 * @return The <code>int</code> result for the first player.
	 */
	public final int player1Result()
	{
		return result(Piece.player1());
	}
	/**
	 * Get the <code>int</code> representation of the result from
	 * <code>this</code> {@code GameExtension} object for the second player.
	 * 
	 * @return The <code>int</code> result for the second player.
	 */
	public final int player2Result()
	{
		return result(Piece.player2());
	}
	//=========================================================================
	//Abstract methods.
	/**
	 * Check if <code>this</code> {@code GameExtension} object can generate
	 * a result.
	 * 
	 * @return <code>true</code> if a result can be generated, otherwise,
	 * 			returns <code>false</code>.
	 */
	public abstract boolean hasResult();
	/**
	 * Get the <code>int</code> result for a specific {@link Piece} object.
	 * 
	 * @param piece The {@link Piece} object to get the result for.
	 * @return The result for the <code>piece</code> object.
	 */
	public abstract int result(final Piece piece);
	/**
	 * This methods will be invoked when a {@link GameEvent} occurs.
	 * 
	 * <p>
	 * Classes which extend {@code GameExtension} will have to provide
	 * implementation on how to handle {@link GameEvent} objects.
	 * </p>
	 * 
	 * @param event The {@link GameEvent} which has been triggered.
	 * @param manager The {@link GameManager} which <code>this</code>
	 * 			{@code GameExtension} belongs.
	 */
	public abstract void onEvent(
			final GameEvent event,
			final GameManager manager);
	/**
	 * This methods will be invoked when a {@link GameCommand} is issued.
	 * 
	 * <p>
	 * Classes which extend {@code GameExtension} will have to provide
	 * implementation on how to handle {@link GameCommand} objects.
	 * </p>
	 * 
	 * @param command The {@link GameCommand} which has been issued.
	 * @param manager The {@link GameManager} which <code>this</code>
	 * 			{@code GameExtension} belongs.
	 */
	public abstract void onCommand(
			final GameCommand command,
			final GameManager manager);
}
