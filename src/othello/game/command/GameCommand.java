package othello.game.command;
import othello.game.Game;

/**
 * 
 * @author 	159014260 John Berg
 * @since 	20/11/2017
 * @version 20/11/2017
 */
public abstract class GameCommand
{
	/**
	 * The source which issued the <code>GameCommand</code>.
	 */
	private final Object source;
	/**
	 * 
	 * @param source The issuer of the <code>GameCommand</code>.
	 */
	GameCommand(final Object source)
	{
		this.source = source;
	}
	/**
	 * Execute the command.
	 * 
	 * @param game The {@link Game} object to execute the command on.
	 */
	public abstract void execute(final Game game);
	/**
	 * Get the source of the <code>GameCommand</code>.
	 * 
	 * @return The issuer of the <code>GameCommand</code>.
	 */
	public final Object getSource()
	{
		return source;
	}
}