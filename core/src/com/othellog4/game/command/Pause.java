package com.othellog4.game.command;

import com.othellog4.game.Game;
import com.othellog4.game.GameException;
import com.othellog4.game.player.Participant;

/**
 * The {@code Pause} class is a subclass of the {@link GameCommand} class, and
 * encapsulates the action of pausing a {@link Game}.
 *
 * <p>
 * The {@code Pause} object is {@link CommandType#TURN_RESTRICTED}.
 * </p>
 *
 * @author 	159014260 John Berg
 * @since	04/03/2018
 * @version 05/03/2018
 * @see GameCommand
 */
public class Pause extends GameCommand
{
	//=========================================================================
	//Constructors.
	/**
	 * Create a {@code Pause} command which pauses a game.
	 *
	 * @param source The {@link Participant} which issued the {@code Pause}
	 * 			command.
	 * @see Participant
	 */
	public Pause(final Participant source)
	{
		super(source, CommandType.TURN_RESTRICTED);
	}
	//=========================================================================
	//Overriden methods.
	/**
	 * Execute the {@code Pause} command on a {@link Game} object.
	 *
	 * @param game The {@link Game} object to execute <code>this</code>
	 * 			{@code Pause} command onto.
	 * @throws GameException If a {@link GameException} occurs.
	 * @see NullPointerException If <code>game</code> is <code>null</code>.
	 * @see Game
	 * @see Game#pause()
	 * @see GameException
	 */
	@Override
	public void execute(final Game game)
			throws
			GameException,
			NullPointerException
	{
		if(game == null)
			throw new NullPointerException();
		try
		{
			game.pause();
		}
		catch(final RuntimeException e)
		{
			throw new GameException(e);
		}
	}
}
