package othello.game;

/**
 * 
 * 
 * 
 * @author 	159014260 John Berg
 * @since	24/11/2017
 * @version 24/11/2017
 */
public class GameException extends Exception
{
	//=========================================================================
	//Fields.
	/**
	 * 
	 */
	private final Exception cause;
	//=========================================================================
	//Constructors.
	/**
	 * 
	 * @param cause
	 */
	public GameException(final Exception cause)
	{
		this.cause = cause;
	}
	//=========================================================================
	//Methods.
	/**
	 * @return 
	 */
	public final Exception getCause()
	{
		return cause;
	}
	//=========================================================================
	//Overriden methods.
	/**
	 * 
	 * 
	 * @return The {@link String} representation of the cause.
	 */
	@Override
	public final String toString()
	{
		return cause.toString();
	}
}
