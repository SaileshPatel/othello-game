package com.othellog4.game.extension;

import com.othellog4.game.GameEvent;
import com.othellog4.game.GameManager;
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
 * @version 17/02/2018
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
	//Abstract methods.
	/**
	 * Get the <code>int</code> which represents the score accumulated by
	 * <code>this</code> {@code GameExtension} object.
	 * 
	 * @param piece The {@link Piece} object to calculate the score for.
	 * @return The calculated score of <code>this</code> {@code GameExtension}.
	 */
	public abstract int getScore(final Piece piece);
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
	/**
	 * Get the {@link String} which contains the result from <code>this</code>
	 * {@code GameExtension} for a specified {@link Piece} object.
	 * 
	 * <p>
	 * Returns <code>null</code> if there is no result {@link String}.
	 * </p>
	 * 
	 * @param piece The <code>piece</code> object to get the result
	 * 			{@link String} from.
	 * @return The {@link String} which represents the results for the
	 * 			<code>piece</code> object.
	 */
	public abstract String getResult(final Piece piece);
	/**
	 * Get the {@link String} representation of <code>this</code>
	 * {@code GameExtension}.
	 * 
	 * @return The {@link String} representing the information form
	 * 			<code>this</code> {@code GameExtension}.
	 */
	@Override
	public abstract String toString();
}
