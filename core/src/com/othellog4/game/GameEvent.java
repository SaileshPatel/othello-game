package com.othellog4.game;

/**
 * The {@code GameEvent} enumeration is a listing of events which may occur
 * during a {@link Game}.
 *
 * @author 	159014260 John Berg
 * @since	25/01/2018
 * @version 16/03/2018
 */
public enum GameEvent
{
	/**
	 * Signal the beginning of playing the game.
	 */
	BEGIN,
	/**
	 * Signal the pausing of the game.
	 */
	PAUSED,
	/**
	 * Signal the end of a game.
	 */
	END,
	/**
	 * Signals the event of the next turn.
	 */
	NEXT_TURN,
	/**
	 * Signals the game is ready to continue.
	 */
	STANDBY,
	/**
	 * Signal the acceptance of a placement.
	 */
	ACCEPTED_PLACMENT;
}