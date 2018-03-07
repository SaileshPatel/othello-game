package com.othellog4.game.extension;

import java.util.Map;
import java.util.HashMap;

import com.othellog4.environment.Scheduler;
import com.othellog4.game.GameEvent;
import com.othellog4.game.GameManager;
import com.othellog4.game.board.Piece;
import com.othellog4.game.command.GameCommand;

public final class Timer extends GameExtension
{
	//=========================================================================
	//Fields.
	/**
	 *
	 */
	private final Map<Piece, Long> timeRemaining;
	//=========================================================================
	//Constructors.
	/**
	 *
	 * @param time
	 */
	public Timer(final long time)
	{
		timeRemaining = new HashMap<>();
		timeRemaining.put(Piece.PIECE_A, time);
		timeRemaining.put(Piece.PIECE_B, time);
	}
	//=========================================================================
	//Overriden methods.
	/**
	 *
	 */
	@Override
	public final void onEvent(
			final GameEvent event,
			final GameManager manager)
	{
		Scheduler.get().run(() ->
		{

		});
	}
	/**
	 *
	 */
	@Override
	public final void onCommand(
			final GameCommand command,
			final GameManager manager)
	{
		//UNUSED.
	}
}
