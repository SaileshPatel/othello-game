package com.othellog4.game.player;

import com.othellog4.game.GameSession;

/**
 * 
 * @since 	??/10/2017
 * @version 20/11/2017
 */
@FunctionalInterface
public interface Participant
{
	public void notifyTurn(final GameSession session);
}
