package com.othellog4.game.extension;

import java.util.function.Consumer;

import com.othellog4.game.GameEvent;
import com.othellog4.game.GameManager;
import com.othellog4.game.board.Piece;
import com.othellog4.game.command.GameCommand;

/**
 * 
 * 
 * 
 * @author	159014260 John Berg
 * @since	27/02/2018
 * @version 27/02/2018
 */
public final class Logger extends GameExtension
{
	/**
	 * 
	 */
	private final String LINE = "--------------------------------------------";
	//=========================================================================
	//Fields.
	/**
	 * 
	 */
	private final Consumer<String> log;
	/**
	 * 
	 * @param log
	 */
	public Logger(final Consumer<String> log)
	{
		this.log = log;
	}
	//=========================================================================
	//Methods.
	/**
	 * 
	 */
	private void logEntry()
	{
		log.accept(LINE);
	}
	/**
	 * 
	 * @param manager
	 */
	private void logManager(final GameManager manager)
	{
		log.accept("Turn: " + manager.current().toString());
	}
	//=========================================================================
	//Overriden methods.
	/**
	 * The {@code Logger} class does not accumulate score.
	 * 
	 * @param piece Unused.
	 * @return <code>0</code>.
	 */
	@Override
	public int getScore(final Piece piece)
	{
		return 0;
	}
	/**
	 * 
	 */
	@Override
	public void onEvent(
			final GameEvent event,
			final GameManager manager)
	{
		logEntry();
		logManager(manager);
		log.accept("Event: " + event.toString());
	}
	/**
	 * 
	 */
	@Override
	public void onCommand(
			final GameCommand command,
			final GameManager manager)
	{
		logEntry();
		log.accept("Command: " + command.toString());
	}
	/**
	 * The {@code Logger} does not produce a result {@link String}.
	 * 
	 * @param piece Unused.
	 * @return <code>null</code>.
	 */
	@Override
	public String getResult(final Piece piece)
	{
		return null;
	}
	/**
	 * 
	 */
	@Override
	public String toString()
	{
		return null;
	}
}
