package com.othellog4.environment;

import com.othellog4.game.extension.FlipCounter;
import com.othellog4.game.extension.GameExtension;
import com.othellog4.game.extension.Logger;

/**
 * The {@code GameMode} enumeration is a collection of modes which are
 * predefined configurations of {@link GameExtension} objects.
 *
 * @author 	159015260 John Berg
 * @since	04/03/2018
 * @version 04/03/2018
 */
public enum GameMode
{
	/**
	 * The {@code GameMode} which is used to help during debugging.
	 *
	 * <p>
	 * Provides a {@link Logger} to log the events of a game.
	 * </p>
	 */
	DEBUGGING
	{
		@Override
		final GameExtension[] get()
		{
			return new GameExtension[]
			{
					new Logger(System.out::println)
			};
		}
	},
	/**
	 * The {@code GameMode} which does not contain any {@link GameExtension}
	 * objects.
	 *
	 * <p>
	 * Used to setup simple games.
	 * </p>
	 */
	BASIC
	{
		@Override
		final GameExtension[] get()
		{
			return new GameExtension[0];
		}
	},
	/**
	 *
	 */
	CASUAL
	{
		@Override
		final GameExtension[] get()
		{
			return new GameExtension[]
			{
					new FlipCounter()
			};
		}
	},
	/**
	 *
	 */
	COMPETATIVE
	{
		@Override
		final GameExtension[] get()
		{
			return null;
		}
	};
	//=========================================================================
	//Abstract methods.
	/**
	 * Get the {@link GameExtension} objects from <code>this</code>
	 * {@code GameMode} object.
	 *
	 * <p>
	 * Package private to only allow certain classes to access this method.
	 * </p>
	 *
	 * <p>
	 * Redefined in each {@code Mode} instance.
	 * </p>
	 *
	 * @return The {@link GameExtension} objects to be applied to a game.
	 */
	abstract GameExtension[] get();
}
