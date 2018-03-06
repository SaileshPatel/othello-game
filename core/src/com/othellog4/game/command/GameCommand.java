package com.othellog4.game.command;

import com.othellog4.game.Game;
import com.othellog4.game.GameException;
import com.othellog4.game.player.Participant;

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
 * @version 04/03/2018
 * @see #GameCommand(Participant)
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
	/**
	 * The {@link String} which describes what issued the {@code GameCommand}.
	 */
	private static final String ISSUED_BY = " issued by: ";
	/**
	 * The {@link String} which marks the end of the {@link String}
	 * representation of {@code GameCommand}.
	 */
	private static final String END_OF_HEADER = ";";
	//=========================================================================
	//Fields.
	/**
	 * The source which issued the {@code GameCommand}.
	 *
	 * @see Participant
	 */
	private final Participant source;
	/**
	 * The {@link CommandType} of <code>this</code> {@code GameCommand}
	 * which is used to determine when the {@code GameCommand} can be
	 * executed.
	 *
	 * @see CommandType
	 */
	private final CommandType type;
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
	 * @param type The {@link CommandType} of the {@code GameCommand}.
	 * @throws NullPointerException If <code>source</code> is
	 * 			<code>null</code>.
	 * @see Participant
	 * @see CommandType
	 */
	GameCommand(
			final Participant source,
			final CommandType type)
			throws
			NullPointerException
	{
		if(source == null)
			throw new NullPointerException(NULL_SOURCE);
		this.source = source;
		this.type = type;
	}
	//=========================================================================
	//Abstract methods.
	/**
	 * Execute the command.
	 *
	 * @param game The {@link Game} object to execute the command on.
	 * @throws GameException If <code>game</code> throws an exception.
	 * @see Game
	 * @see GameException
	 */
	public abstract void execute(final Game game)
			throws
			GameException;
	//=========================================================================
	//Methods.
	/**
	 * Check if the {@code GameCommand} can be executed.
	 *
	 * @param current The current {@link Participant} object.
	 * @return <code>true</code> if the issuer if <code>this</code> is the
	 * 			player with the current turn; otherwise, returns
	 * 			<code>false</code>.
	 * @see Participant
	 */
	public final boolean canExecute(final Participant current)
	{
		return type.canExecute(getSource(), current);
	}
	/**
	 * Get the source of <code>this</code> {@code GameCommand}.
	 *
	 * @return The issuer of <code>this</code> {@code GameCommand}.
	 * @see Participant
	 */
	public final Participant getSource()
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
		return getClass().getSimpleName()
				+ ISSUED_BY
				+ source.toString()
				+ END_OF_HEADER;
	}
	//=========================================================================
	//Inner classes.
	/**
	 * The {@code CommandType} enumeration contains constants to mark a
	 * {@link GameCommand} and define when a {@link GameCommand} can be
	 * executed.
	 *
	 * @author 	159014260 John Berg
	 * @since	04/03/2018
	 * @version 04/03/2018
	 */
	enum CommandType
	{
		/**
		 * The {@code CommandType} which will only allow a {@link GameCommand}
		 * to be executed during the issuers turn.
		 */
		TURN_RESTRICTED
		{
			@Override
			public final boolean canExecute(
					final Participant issuer,
					final Participant current)
			{
				return issuer.equals(current);
			}
		},
		/**
		 * The {@code CommandType} which will always allow a
		 * {@link GameCommand} to be executed.
		 */
		TURN_UNRESTRICTED
		{
			@Override
			public final boolean canExecute(
					final Participant issuer,
					final Participant current)
			{
				return true;
			}
		};
		//=====================================================================
		//Abstract methods.
		/**
		 * Check if a {@link GameCommand} can be executed.
		 *
		 * @param issuer The {@link Participant} which is the issuer of the
		 * 			{@link GameCommand}.
		 * @param current The current {@link Participant} who's turn it is.
		 * @return <code>true</code> if the execution is permitted, otherwise,
		 * 			<code>false</code>.
		 * @see Participant
		 */
		public abstract boolean canExecute(
				final Participant issuer,
				final Participant current);
	}
}