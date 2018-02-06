package com.othellog4.game;

/**
 * The {@code GameState} enumeration is a listing of the possible states that a
 * {@link Game} can be in.
 * 
 * <p>
 * The {@code GameState} enumeration provides methods for moving between
 * {@code GameState} objects.
 * </p>
 * 
 * <p>
 * Methods for moving between {@code GameState} objects:
 * </p>
 * 
 * <p>
 * <ul>
 * 		<li>{@link GameState#start()}</li>
 * 		<li>{@link GameState#pause()}</li>
 * 		<li>{@link GameState#end()}</li>
 * </ul>
 * </p>
 * 
 * <p>
 * The {@link GameState#initial()} can be used to get the starting
 * {@code GameState}.
 * </p>
 * 
 * <p>
 * {@code GameState} is package private.
 * </p>
 * 
 * @author 	159014260 John Berg
 * @since 	05/02/2017
 * @version 05/02/2017
 */
enum GameState 
{
	//=========================================================================
	//Enum constants.
	/**
	 * The {@code GameState} which represents a {@link Game} in the state
	 * before the beginning of the game.
	 * 
	 * <p>
	 * The starting state of {@link Game}.
	 * </p>
	 */
	READY
	{
		@Override
		public final GameState start()
		{
			return GameState.PLAYING;
		}
		@Override
		public final GameState pause()
		{
			throw invalidTransition(this, GameState.PAUSED);
		}
		@Override
		public final GameState end()
		{
			return GameState.GAME_OVER;
		}
	},
	/**
	 * The {@code GameState} which represents a {@link Game} in the state of
	 * playing.
	 * 
	 * <p>
	 * The active state of {@link Game}.
	 * </p>
	 */
	PLAYING
	{
		@Override
		public final GameState start()
		{
			throw invalidTransition(this, this);
		}
		@Override
		public final GameState pause()
		{
			return GameState.PAUSED;
		}
		@Override
		public final GameState end()
		{
			return GameState.GAME_OVER;
		}
	},
	/**
	 * The {@code GameState} which represents a {@link Game} in the state of
	 * playing but currently in an inactive state.
	 * 
	 * <p>
	 * The inactive state of {@link Game}.
	 * </p>
	 */
	PAUSED
	{
		@Override
		public final GameState start()
		{
			return GameState.PLAYING;
		}
		@Override
		public final GameState pause()
		{
			throw invalidTransition(this, this);
		}
		@Override
		public final GameState end()
		{
			return GameState.GAME_OVER;
		}
	},
	/**
	 * The {@code GameState} which represents a {@link Game} in the state
	 * after completion.
	 * 
	 * <p>
	 * The end state of {@link Game}.
	 * </p>
	 * 
	 * <p>
	 * {@link GameState#GAME_OVER} cannot be transitioned out of.
	 * </p>
	 */
	GAME_OVER
	{
		@Override
		public final GameState start()
		{
			throw invalidTransition(this, GameState.PLAYING);
		}
		@Override
		public final GameState pause()
		{
			throw invalidTransition(this, GameState.PAUSED);
		}
		@Override
		public final GameState end()
		{
			throw invalidTransition(this, this);
		}
	};
	//=========================================================================
	//Abstract methods.
	/**
	 * Transition to {@link GameState#PLAYING}.
	 * 
	 * <p>
	 * Defines the transition from a {@code GameState} to the
	 * {@link GameState#PLAYING}.
	 * </p>
	 * 
	 * @return The {@link GameState#PLAYING} {@code GameState}.
	 * @throws IllegalStateException If the transition is not possible.
	 */
	public abstract GameState start() throws IllegalStateException;
	/**
	 * Transition to {@link GameState#PAUSED}.
	 * 
	 * <p>
	 * Defines the transition from a {@code GameState} to the
	 * {@link GameState#PAUSED}.
	 * </p>
	 * 
	 * @return The {@link GameState#PAUSED} {@code GameState}.
	 * @throws IllegalStateException If the transition is not possible.
	 */
	public abstract GameState pause() throws IllegalStateException;
	/**
	 * Transition to {@link GameState#GAME_OVER}.
	 * 
	 * <p>
	 * Defines the transition from a {@code GameState} to the
	 * {@link GameState#GAME_OVER}.
	 * </p>
	 * 
	 * @return The {@link GameState#GAME_OVER} {@code GameState}.
	 * @throws IllegalStateException If the transition is not possible.
	 */
	public abstract GameState end() throws IllegalStateException;
	//=========================================================================
	//Static methods.
	/**
	 * Get the starting {@code GameState}.
	 * 
	 * @return {@link GameState#READY}.
	 */
	public static final GameState initial()
	{
		return GameState.READY;
	}
	/**
	 * Generate an {@link IllegalStateException} from one {@code GameState} to
	 * another {@code GameState}.
	 * 
	 * @param from The initial {@code GameState}.
	 * @param to The next {@code GameState}.
	 * @return The {@link IllegalStateException} generated for the transition
	 * 			from <code>from</code> to <code>to</code>.
	 */
	private static final IllegalStateException invalidTransition(
			final GameState from,
			final GameState to)
	{
		return new IllegalStateException(
				"Cannot transition from "
				+ from.name()
				+ " to "
				+ to.name());
	}
}
