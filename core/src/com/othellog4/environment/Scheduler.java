package com.othellog4.environment;

import java.util.function.Consumer;

/**
 * The {@code Scheduler} class is a global service which manages scheduling
 * of execution of {@link Runnable} tasks.
 *
 * <p>
 * The {@code Scheduler} class is an instance of the singleton design pattern
 * which restricts instance creation of {@code Scheduler} to one. The
 * {@link Scheduler#get()} method can be used to obtain an instance of the
 * {@code Scheduler} class.
 * </p>
 *
 * @author	159014260 John Berg
 * @since 	14/02/2018
 * @version 16/02/2018
 */
public class Scheduler
{
	//=========================================================================
	//Static fields.
	/**
	 * The instance of {@code Scheduler} which is the only instance to be
	 * allowed.
	 *
	 * <p>
	 * For internal use only!
	 * </p>
	 *
	 * <p>
	 * Lazily initialised.
	 * </p>
	 */
	private static Scheduler instance;
	//=========================================================================
	//Constructor.
	/**
	 * Construct a {@code Scheduler} object.
	 *
	 * <p>
	 * Private access to allows for only one instance to be created.
	 * </p>
	 *
	 *  <p>
	 *  For internal use only!
	 *  </p>
	 */
	private Scheduler()
	{
	}
	//=========================================================================
	//Methods.
	/**
	 * Run a {@link Runnable} object from the {@code Scheduler}.
	 *
	 * @param task The {@link Runnable} object to be executed.
	 */
	public final void run(final Runnable task)
	{
		delay(task, 0);
	}
	/**
	 * Run a {@link Runnable} object from the {@code Scheduler}.
	 *
	 * @param task The {@link Runnable} object to be executed.
	 * @param onThrow The {@link Consumer} object called if a {@link Throwable}
	 * 			object is thrown.
	 */
	public final void run(
			final Runnable task,
			final Consumer<Throwable> noThrow)
	{
		delay(task, 0, noThrow);
	}
	/**
	 * Schedule the execution of a {@link Runnable} object by a specified
	 * delay amount.
	 *
	 * @param task The {@link Runnable} object to run.
	 * @param delay The <code>long</code> value which represents the delay in
	 * 			mili-seconds.
	 */
	public final void delay(
			final Runnable task,
			final long delay)
	{
		delay(task, delay, Throwable::printStackTrace);
	}
	/**
	 * Schedule the execution of a {@link Runnable} object by a specified
	 * delay amount.
	 *
	 * @param task The {@link Runnable} object to run.
	 * @param delay The <code>long</code> value which represents the delay in
	 * 			mili-seconds.
	 * @param onThrow The {@link Consumer} object called if a {@link Throwable}
	 * 			object is thrown.
	 */
	public final void delay(
			final Runnable task,
			final long delay,
			final Consumer<Throwable> onThrow)
	{
		new Thread(() ->
		{
			try
			{
				Thread.sleep(delay);
				task.run();
			}
			catch (final Throwable e)
			{
				onThrow.accept(e);
			}
		}).start();
	}
	//=========================================================================
	//Static methods.
	/**
	 * Get the {@code Scheduler} object.
	 *
	 * <p>
	 * Get the singleton instance of the {@code Scheduler} object.
	 * </p>
	 *
	 * <p>
	 * This method is <code>synchronized</code>.
	 * </p>
	 *
	 * @return The {@code Scheduler} instance.
	 */
	public static final synchronized Scheduler get()
	{
		return instance == null
				?instance = new Scheduler()
				:instance;
	}
}
