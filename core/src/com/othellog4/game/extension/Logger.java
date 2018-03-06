package com.othellog4.game.extension;

import java.util.function.Consumer;

import com.othellog4.game.GameEvent;
import com.othellog4.game.GameManager;
import com.othellog4.game.board.Piece;
import com.othellog4.game.command.GameCommand;

/**
 * The {@code Logger} class is a sublcass of the {@link GameExtension}, which
 * purpose is to log information and events that occur in a {@link Game}.
 *
 * <p>
 * The {@code Logger} will not generate scores or result {@link String}
 * objects.
 * </p>
 *
 * <p>
 * The {@code Logger} class is mainly intended as a tool for aiding debugging.
 * </p>
 *
 * @author	159014260 John Berg
 * @since	27/02/2018
 * @version 01/03/2018
 */
public final class Logger extends GameExtension
{
	/**
	 * The {@link String} object which is a line to indicate the entry of a
	 * new entry.
	 */
	private final String LINE = "--------------------------------------------";
	//=========================================================================
	//Fields.
	/**
	 * The callback {@link Consumer} function which accepts a {@link String}
	 * object, to be logged.
	 */
	private final Consumer<String> log;
	/**
	 * Create a {@code Logger} object which logs events of a {@link Game} to
	 * a specified callback {@link Consumer} function.
	 *
	 * @param log The {@link Consumer} function object which will be called
	 * 			to log events that occur.
	 */
	public Logger(final Consumer<String> log)
	{
		this.log = log;
	}
	//=========================================================================
	//Methods.
	/**
	 * Log the beginning of a new entry.
	 *
	 * <p>
	 * Internal use only!
	 * </p>
	 */
	private void logEntry()
	{
		log.accept(LINE);
	}
	/**
	 * Log the information contained in a {@link GameManager} object.
	 *
	 * <p>
	 * Internal use only!
	 * </p>
	 *
	 * @param manager The {@link GameManager} to log information from.
	 */
	private void logManager(final GameManager manager)
	{
		log.accept("Turn: " + manager.current().toString());
	}
	//=========================================================================
	//Overriden methods.
	/**
	 * The {@code Logger} class does not provide a result.
	 *
	 * @return <code>false</code>.
	 */
	@Override
	public final boolean hasResult()
	{
		return false;
	}
	/**
	 * The {@code Logger} class does not generate a result.
	 *
	 * @param piece Ignored.
	 * @return Should not return.
	 * @throws UnsupportedOperationException When called.
	 */
	@Override
	public final int result(final Piece piece)
			throws
			UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}
	/**
	 * Log the occurance of a {@link GameEvent} object.
	 *
	 * @param event The {@link GameEvent} which has occured.
	 * @param manager The {@link GameManager} which <code>this</code>
	 * 			{@code Logger} belongs to.
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
	 * Log the occurance of a {@link GameCommand} object.
	 *
	 * @param command The {@link GameCommand} which has been issued.
	 * @param manager The {@link GameManager} which <code>this</code>
	 * 			{@code Logger} belongs to.
	 */
	@Override
	public void onCommand(
			final GameCommand command,
			final GameManager manager)
	{
		logEntry();
		log.accept("Command: " + command.toString());
	}
}
