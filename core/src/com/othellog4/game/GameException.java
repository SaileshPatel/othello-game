package com.othellog4.game;

/**
 * The {@code GameException} class is a wrapper class for the
 * {@code GameCommand} class.
 * 
 * <p>
 * The {@code GameException} is used to translate specific exceptions to a
 * common type, which allows the abstract class {@link GameCommand} to
 * have a generic throws clause so that subclasses can translate specific
 * exceptions into instance of {@code GameException}.
 * </p>
 * 
 * @author 	159014260 John Berg
 * @since	24/11/2017
 * @version 24/11/2017
 */
public class GameException extends Exception
{
	//=========================================================================
	//Static fields.
	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -7608313267761615883L;
	//=========================================================================
	//Fields.
	/**
	 * The {@link Exception} wrapped by the {@code GameException}.
	 */
	private final Exception cause;
	//=========================================================================
	//Constructors.
	/**
	 * Create a {@code GameException} which wraps an {@link Exception} which is
	 * the cause of the created {@code GameException}.
	 * 
	 * @param cause The {@link Exception} which caused the created
	 * 			{@code GameException} to be thrown.
	 */
	public GameException(final Exception cause)
	{
		this.cause = cause;
	}
	//=========================================================================
	//Methods.
	/**
	 * Get the {@link Exception} which caused <code>this</code>
	 * {@code GameException}.
	 * 
	 * @return The {@link Exception} which was the cause of <code>this<code>
	 * 			{@code GameException}.
	 */
	public final Exception getCause()
	{
		return cause;
	}
	//=========================================================================
	//Overriden methods.
	/**
	 * Get the {@link String} of the cause of <code>this</code>
	 * {@link GameException}.
	 * 
	 * @return The {@link String} representation of the cause.
	 */
	@Override
	public final String toString()
	{
		return cause.toString();
	}
}
