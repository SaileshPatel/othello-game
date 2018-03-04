package com.othellog4.environment;

import com.othellog4.game.player.AutomaticPlayer;
import com.othellog4.game.player.Participant;
import com.othellog4.game.player.Player;
import com.othellog4.game.player.ai.DelayStrategies;
import com.othellog4.game.player.ai.EvaluationStrategies;
import com.othellog4.game.player.ai.SearchStrategies;

/**
 * The {@code PlayerType} enumeration is the collection of players which can
 * be selected when creating a new game.
 * 
 * @author 	159014260 John Berg
 * @since 	03/03/2018
 * @version 03/03/2018
 */
public enum PlayerType
{
	//=========================================================================
	//Enum constants.
	/**
	 * The {@code PlayerType} object which represents a human player.
	 * 
	 * <p>
	 * Generates {@link Player} objects.
	 * </p>
	 */
	USER
	{
		@Override
		final Participant get()
		{
			return new Player();
		}
	},
	/**
	 * The {@code PlayerType} object which represents an AI of the easiest
	 * difficulty.
	 * 
	 * <p>
	 * Provides access to a shared instance of an {@link AutomaticPlayer}
	 * object.
	 * </p>
	 */
	AI_EASY
	{
		private final Participant ai = new AutomaticPlayer(
				null,
				SearchStrategies.RANDOM_SELECTION,
				DelayStrategies.WAIT_ONE_SEC);
		@Override
		final Participant get()
		{
			return ai;
		}
	},
	/**
	 * The {@code PlayerType} object which represents an AI of medium
	 * difficulty.
	 * 
	 * <p>
	 * Provides access to a shared instance of an {@link AutomaticPlayer}
	 * object.
	 * </p>
	 */
	AI_MEDIUM
	{
		private final Participant ai = new AutomaticPlayer(
				EvaluationStrategies.COUNT,
				SearchStrategies.BEST_IMMIDIATE,
				DelayStrategies.WAIT_ONE_SEC);
		@Override
		final Participant get()
		{
			return ai;
		}
	},
	/**
	 * The {@code PlayerType} object which represents an AI of the hardest
	 * available difficulty.
	 * 
	 * <p>
	 * Provides access to a shared instance of an {@link AutomaticPlayer}
	 * object.
	 * </p>
	 */
	AI_HARD
	{
		@Override
		final Participant get()
		{
			//NO AI existing yet...
			return null;
		}
	};
	//=========================================================================
	//Abstract methods.
	/**
	 * Get the {@link Participant} object which is corresponding to the
	 * {@code PlayerType} object.
	 * 
	 * <p>
	 * Package private to only allow certain classes to access this method.
	 * </p>
	 * 
	 * <p>
	 * Redefined in each {@code PlayerType} instance.
	 * </p>
	 * 
	 * @return The {@link Participant} which corresponds to the
	 * 			{@code PlayerType} object.
	 * @see Participant
	 */
	abstract Participant get();
}
