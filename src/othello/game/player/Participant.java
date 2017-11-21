package othello.game.player;

import othello.game.GameSession;

/**
 * 
 * @since 	??/10/2017
 * @version 20/11/2017
 */
public interface Participant
{
	public void notifyTurn(final GameSession session);
}
