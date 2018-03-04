package com.othellog4.game.command;

import com.othellog4.game.Game;
import com.othellog4.game.GameException;
import com.othellog4.game.player.Participant;

/**
 * The {@code Resume} class is a subclass of the {@link GameCommand} class,
 * which represents the action of resuming a paused game.
 * 
 * <p>
 * The {@code Resume} command is {@link CommandType#TURN_UNRESTRICTED}.
 * </p>
 * 
 * @author 	159014260 John Berg
 * @since 	04/03/2018
 * @version 04/03/2018
 * @see GameCommand
 */
public class Resume extends GameCommand
{
	//=========================================================================
	//Constructors.
	/**
	 * Create a {@code Resume} command to resume a paused game.
	 * 
	 * @param source The {@link Participant} which issued the {@code Resume}
	 * 			command.
	 * @see Participant
	 */
	public Resume(final Participant source)
	{
		super(source, CommandType.TURN_UNRESTRICTED);
	}
	//=========================================================================
	//Overriden methods.
	/**
	 * Execute <code>this</code> {@code Resume} command on a {@link Game}
	 * object.
	 * 
	 * @param game The {@link Game} object to execute the {@code Resume}
	 * 			command onto.
	 * @throws GameException If a {@code GameException} occurs.
	 * @see Game
	 * @see Game#start()
	 * @see GameException
	 */
	@Override
	public final void execute(final Game game)
			throws
			GameException
	{
		try
		{
			game.start();
		}
		catch(final RuntimeException e)
		{
			throw new GameException(e);
		}
	}
}
