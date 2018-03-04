package com.othellog4.environment;

import com.othellog4.game.Game;
import com.othellog4.game.GameModel;
import com.othellog4.game.board.GameBoard;

/**
 * The {@code Launcher} class is a singleton which is responsible for setting
 * up games, and taking care of the details involved during the setup phase.
 * 
 * <p>
 * The {@code Launcher} also provides the facilities to suspend and resume
 * a single {@link GameModel}.
 * </p>
 * 
 * @author 	159014260 John Berg
 * @since	03/03/2018
 * @version 04/03/2018
 */
public final class Launcher
{
	//=========================================================================
	//Static fields.
	/**
	 * The singleton instance of the {@code Launcher} class.
	 * 
	 * <p>
	 * Lazily initialised.
	 * </p>
	 * 
	 * @see Launcher#get()
	 */
	private static Launcher instance = null;
	//=========================================================================
	//Fields.
	/**
	 * The {@link GameModel} which is cached by <code>this</code>
	 * {@code Launcher} object.
	 * 
	 * @see GameModel
	 */
	private GameModel cachedModel;
	//=========================================================================
	//Constructors.
	/**
	 * Create the {@code Launcher} object.
	 * 
	 * <p>
	 * Private to restrict access to creation.
	 * </p>
	 */
	private Launcher()
	{
		cachedModel = null;
	}
	//=========================================================================
	//Methods.
	/**
	 * Check if the {@code Launcher} currently has a {@link GameModel} cached.
	 * 
	 * @return <code>true</code> if a {@link GameModel} is cached, otherwise,
	 * 			returns <code>false</code>.
	 */
	public final synchronized boolean hasCache()
	{
		return cachedModel != null;
	}
	/**
	 * Clear the cached {@link GameModel}.
	 */
	public final synchronized void clear()
	{
		cachedModel = null;
	}
	/**
	 * Cache a {@link GameModel} object which can be resumed later.
	 * 
	 * <p>
	 * The {@code Launcher} only has capacity for one {@link GameModel} and
	 * will overwrite the currently cached {@link GameModel} if it exists.
	 * </p>
	 * 
	 * @param model The {@link GameModel} to cache.
	 * @throws NullPointerException If <code>model</code> is <code>null</code>.
	 * @see GameModel
	 */
	public final void cache(final GameModel model)
			throws
			NullPointerException
	{
		if(model == null)
			throw new NullPointerException();
		synchronized(this)
		{
			cachedModel = model;
		}
	}
	/**
	 * Retrieve the cached {@link GameModel} from <code>this</code>
	 * {@code Launcher} object.
	 * 
	 * <p>
	 * Retrieving the {@link GameModel} will clear the cached
	 * {@link GameModel}.
	 * </p>
	 * 
	 * @return The {@link GameModel} which was cached by <code>this</code>
	 * 			{@code Launcher}.
	 * @throws IllegalStateException If <code>this</code> {@code Launcher}
	 * 			contains no cached {@link GameModel}.
	 * @see GameModel
	 */
	public final synchronized GameModel release()
			throws
			IllegalStateException
	{
		if(!hasCache())
			throw new IllegalStateException();
		final GameModel model = cachedModel;
		clear();
		return model;
	}
	/**
	 * Create a {@link GameModel} for a new local game.
	 * 
	 * @param p1 The {@link PlayerType} which is the first player.
	 * @param p2 The {@link PlayerType} which is the second player.
	 * @param mode The {@link Mode} of the new {@link GameModel}.
	 * @return The {@link GameModel} of the new game.
	 * @see PlayerType
	 * @see Mode
	 * @see GameModel
	 */
	public GameModel newGame(
			final PlayerType p1,
			final PlayerType p2,
			final Mode mode)
	{
		return new GameModel(
				new Game(new GameBoard(8)),
				p1.get(),
				p2.get(),
				mode.get());
	}
	//=========================================================================
	//Static methods.
	/**
	 * Get the instance of the {@code Launcher} class.
	 * 
	 * <p>
	 * This method may initialise the {@code Launcher} instance if it does not
	 * exist.
	 * </p>
	 * 
	 * @return The only instance of the {@code Launcher} class.
	 */
	public static final synchronized Launcher get()
	{
		return instance == null
				?instance = new Launcher()
				:instance;
	}
}
