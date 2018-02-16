package com.othellog4.environment;

public class Scheduler
{
	//=========================================================================
	//Static fields.
	/**
	 * 
	 */
	private static Scheduler instance;
	//=========================================================================
	//Constructor.
	/**
	 * 
	 */
	private Scheduler()
	{
	}
	//=========================================================================
	//Methods.
	/**
	 * 
	 * @param task
	 * @param delay
	 */
	public void delay(
			final Runnable task,
			final long delay)
	{
		new Thread(() ->
		{
			try
			{
				Thread.sleep(delay);
				task.run();
			}
			catch (final InterruptedException e)
			{
				e.printStackTrace();
			}
		}).start();
	}
	//=========================================================================
	//Static methods.
	/**
	 * 
	 * @return
	 */
	public static final synchronized Scheduler get()
	{
		return instance == null? (instance = new Scheduler()): instance;
	}
}
