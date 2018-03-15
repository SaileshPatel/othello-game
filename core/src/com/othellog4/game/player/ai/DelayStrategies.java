package com.othellog4.game.player.ai;

import com.othellog4.environment.Scheduler;

/**
 * The {@code DelayStrategies} enumeration is a class containing concrete
 * implementations of the {@link DelayStrategy} interface.
 *
 * @author 	159014260 John Berg
 * @since 	08/02/2018
 * @version 14/02/2018
 */
public enum DelayStrategies implements DelayStrategy
{
	//=========================================================================
	//Enum constants.
	/**
	 * Executes immediately.
	 */
	IMMEDIATE()
	{
		/**
		 * Execution a {@link Runnable} object immediately.
		 *
		 * @param r The {@link Runnable} object to run.
		 */
		@Override
		public final void delay(final Runnable r)
		{
			r.run();
		}
	},
	/**
	 * Delays execution for <code>0</code> seconds.
	 *
	 * <p>
	 * The execution will occur on a thread separate to the current thread.
	 * </p>
	 */
	WAIT_ZERO_SEC
	{
		/**
		 * Delay execution of a {@link Runnable} object by <code>0</code>
		 * seconds.
		 *
		 * @param r The {@link Runnable} object to run.
		 */
		@Override
		public final void delay(final Runnable r)
		{
			Scheduler.get().delay(r, MILI_TIME_ZERO_SEC);
		}
	},
	/**
	 * Delays execution for <code>1</code> seconds.
	 *
	 * <p>
	 * The execution will occur on a thread separate to current thread.
	 * </p>
	 */
	WAIT_ONE_SEC
	{
		/**
		 * Delay execution of a {@link Runnable} object by <code>1</code>
		 * seconds.
		 *
		 * @param r The {@link Runnable} object to run.
		 */
		@Override
		public final void delay(final Runnable r)
		{
			Scheduler.get().delay(r, MILI_TIME_ONE_SEC);
		}
	},
	/**
	 * Delay execution for <code>2</code> seconds.
	 *
	 * <p>
	 * The execution will occur on a thread separate to the current thread.
	 * </p>
	 */
	WAIT_TWO_SEC
	{
		/**
		 * Delays execution of a {@link Runnable} object by <code>2</code>
		 * seconds.
		 *
		 * @param r The {@link Runnable} object to run.
		 */
		@Override
		public final void delay(final Runnable r)
		{
			Scheduler.get().delay(r, MILI_TIME_TWO_SEC);
		}
	};
	//=========================================================================
	//Static fields.
	/**
	 * The <code>long</code> which represents <code>0</code> seconds in
	 * mili-seconds.
	 */
	private static final long MILI_TIME_ZERO_SEC = 0;
	/**
	 * The <code>long</code> which represents <code>1</code> seconds in
	 * mili-seconds.
	 */
	private static final long MILI_TIME_ONE_SEC = 1000;
	/**
	 * The <code>long</code> which represents <code>1</code> seconds in
	 * mili-seconds.
	 */
	private static final long MILI_TIME_TWO_SEC = 2000;
}
