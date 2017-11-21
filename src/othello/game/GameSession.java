package othello.game;

import java.util.HashMap;
import java.util.Map;

import othello.game.board.BoardView;
import othello.game.board.Piece;
import othello.game.command.GameCommand;
import othello.game.player.Participant;

/**
 * 
 * @author 	John
 * @since 	20/11/2017
 * @version 21/11/2017
 */
public final class GameSession
{
	private final Game game;
	private final Map<Piece, Participant> playerMap;
	public GameSession(
			final Game game,
			final Participant player1,
			final Participant player2)
	{
		this.game = game;
		playerMap = new HashMap<>();
		playerMap.put(game.getPlayer1(), player1);
		playerMap.put(game.getPlayer2(), player2);
		notifyCurrent();
	}
	private void notifyCurrent()
	{
		playerMap.get(current()).notifyTurn(this);
	}
	public final void accept(final GameCommand command)
	{
		if(playerMap.get(current()).equals(command.getSource()))
			command.execute(game);
		notifyCurrent();
	}
	public final BoardView getBoard()
	{
		return game.getBoard();
	}
	public final Piece current()
	{
		return game.getCurrent();
	}
}