package com.othellog4.game.extension;

import com.othellog4.environment.Scheduler;
import com.othellog4.game.GameEvent;
import com.othellog4.game.GameException;
import com.othellog4.game.GameManager;
import com.othellog4.game.command.Surrender;

/**
 * 
 * @author John
 *
 */
public class TimeoutTimer extends Timer
{
	private final int timeoutLimit;
	private GameManager manager;
	public TimeoutTimer(final int timeoutLimit)
	{
		this.timeoutLimit = timeoutLimit;
	}
	@Override
	public final synchronized void onEvent(
			final GameEvent event,
			final GameManager manager)
	{
		if(this.manager == null)
		{
			this.manager = manager;
			Scheduler.get().run(() ->
			{
					while(realTime() <= timeoutLimit)
						/*WAIT*/;
					try
					{
						manager.execute(new Surrender(
								manager.current(),
								manager.game().getCurrent()));
					}
					catch (GameException e)
					{
						e.printStackTrace();
					}
			});
		}
		super.onEvent(event, manager);
	}
}
