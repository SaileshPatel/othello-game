package othello.game;

import java.util.HashMap;
import java.util.Map;

import othello.game.board.BoardView;
import othello.game.board.Piece;
import othello.game.command.GameCommand;
import othello.game.player.Participant;

/**
 * 
 * 
 * 
 * @author 	15901426 John Berg
 * @since 	20/11/2017
 * @version 21/11/2017
 */
public final class GameSession
{
	//=========================================================================
	//Fields.
	/**
	 * The {@link Game} instance that {@code GameSession} is using to forward
	 * queries.
	 */
	private final Game game;
	/**
	 * 
	 */
	private final Map<Piece, Participant> playerMap;
	//=========================================================================
	//Constructors.
	/**
	 * 
	 * @param game
	 * @param player1
	 * @param player2
	 */
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
	//=========================================================================
	//Methods.
	/**
	 * Notify the {@link Participant} which is associated with the current
	 * {@link Piece}.
	 * 
	 * <p>
	 * <b>For internal use only!</b>
	 * </p>
	 */
	private void notifyCurrent()
	{
		playerMap.get(current()).notifyTurn(this);
	}
	/**
	 * 
	 * @param command
	 * @throws GameException
	 * @throws NullPointerException If <code>command</code> is
	 * 			<code>null</code>.
	 * @see GameCommand
	 */
	public final void accept(final GameCommand command)
			throws
			GameException,
			NullPointerException
	{
		if(playerMap.get(current()).equals(command.getSource()))
			//May throw GameException.
			command.execute(game);
		notifyCurrent();
	}
	/**
	 * 
	 * @return The {@link BoardView} of <code>this</code> {@code GameSession}.
	 */
	public final BoardView getBoard()
	{
		return game.getBoard();
	}
	/**
	 * Get the {@link Piece} of the current player.
	 * 
	 * @return The {@link Piece} which corresponds to the current
	 * 			{@link Participant}.
	 */
	public final Piece current()
	{
		return game.getCurrent();
	}
}