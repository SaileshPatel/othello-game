package com.othellog4.game.player.ai;

/**
 * The {@code DelayStrategy} interface is a functional interface which
 * describes the delay before executing some task.
 * 
 * @author 	159014260 John Berg
 * @since 	09/02/2018
 * @version 09/02/2018
 */
@FunctionalInterface
public interface DelayStrategy
{
	/**
	 * Delay the execution of a {@link Runnable} object.
	 * 
	 * @param r The {@link Runnable} object to be potentially delayed.
	 */
	public void delay(final Runnable r);
}
