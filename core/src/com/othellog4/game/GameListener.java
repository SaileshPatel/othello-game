package com.othellog4.game;

/**
 * The {@code GameListener} interface which is to be used to receive updates
 * from a {@link Game}.
 * 
 * <p>
 * {@code GameListener} is a functional interface which does only have one
 * method.
 * </p>
 * 
 * @author 	159014260 John
 * @since	03/12/2017
 * @version 03/12/2017
 */
@FunctionalInterface
public interface GameListener
{
	//=========================================================================
	//Abstract methods.
	/**
	 * Update <code>this</code> {@code GameListener} when notified about an
	 * update from a {@link Game}.
	 * 
	 * <p>
	 * Must be implemented.
	 * </p>
	 * 
	 * @param game The {@link Game} which issued the update.
	 */
	public void update(final Game game);
}
