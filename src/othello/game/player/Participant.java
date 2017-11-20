package othello.game.player;

import othello.game.GameSession;

/**
 * 
 * 
 *
 */
public interface Participant
{
	public void notifyTurn(final GameSession session);
}
