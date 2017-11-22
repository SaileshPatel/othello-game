package othello.game.command;
import othello.game.Game;

/**
 * The {@code GameCommand} class is an abstract class which represents a
 * command to be executed on a {@link Game}.
 * 
 * <p>
 * The {@code GameCommand} represents an abstract action which can be performed
 * against a {@link Game}, such as:
 * <ul>
 * 		<li>Place a piece</li>
 * 		<li>Pause the game</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Classes which wish to extend the {@code GameCommand} class, must implement the
 * {@link #execute(Game)} method. Subclasses of {@code GameCommand} should also
 * exist in the same package, to be able to access the package private
 * {@link #GameCommand(Object)} constructor.
 * </p>
 * 
 * @author 	159014260 John Berg
 * @since 	20/11/2017
 * @version 20/11/2017
 * @see #GameCommand(Object)
 * @see #execute(Game)
 * @see Game
 */
public abstract class GameCommand
{
	//=========================================================================
	//Static fields.
	/**
	 * The {@link String} which contains the message describing that the issuer
	 * of a {@code GameCommand} was <code>null</code>.
	 */
	private static final String NULL_SOURCE =
			"Issuer of the GameCommand was null";
	private static final String ISSUED_BY = " issued by: ";
	//=========================================================================
	//Fields.
	/**
	 * The source which issued the {@code GameCommand}.
	 */
	private final Object source;
	//=========================================================================
	//Constructors.
	/**
	 * Create a {@code GameCommand} by specifying an {@link Object} which is
	 * the issuer of the command.
	 * 
	 * <p>
	 * This constructor is only visible to classes which exist in the same
	 * package, which means that classes which wish to extend the
	 * {@code GameCommand} class must exist in the same folder as the
	 * {@code GameCommand} class.
	 * </p>
	 * 
	 * @param source The issuer of the created {@code GameCommand}.
	 * @throws NullPointerException If <code>source</code> is
	 * 			<code>null</code>.
	 */
	GameCommand(final Object source)
			throws
			NullPointerException
	{
		if(source == null)
			throw new NullPointerException(NULL_SOURCE);
		this.source = source;
	}
	//=========================================================================
	//Abstract methods.
	/**
	 * Execute the command.
	 * 
	 * @param game The {@link Game} object to execute the command on.
	 * @see Game
	 */
	public abstract void execute(final Game game);
	//=========================================================================
	//Methods.
	/**
	 * Get the source of <code>this</code> {@code GameCommand}.
	 * 
	 * @return The issuer of <code>this</code> {@code GameCommand}.
	 */
	public final Object getSource()
	{
		return source;
	}
	//=========================================================================
	//Overriden methods.
	/**
	 * Get the {@link String} representation of <code>this</code>
	 * {@code GameCommand}.
	 * 
	 * <p>
	 * The {@link String} representation of <code>this</code> should contain
	 * the simple name of the class of the {@code GameCommand} and a brief
	 * description of issued, including the {@link String} representation of
	 * the source of <code>this</code> {@code GameCommand}.
	 * </p>
	 * 
	 * @return The {@link String} representation of <code>this</code>
	 * 			{@link GameCommand}.
	 */
	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ISSUED_BY + source.toString();
	}
}