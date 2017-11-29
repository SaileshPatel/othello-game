package com.othellog4.game;

import java.util.HashMap;
import java.util.Map;

import com.othellog4.game.board.BoardView;
import com.othellog4.game.board.Piece;
import com.othellog4.game.command.GameCommand;
import com.othellog4.game.player.Participant;

/**
 * The {@code GameSession} class models a game session of a game of Othello,
 * between two players. {@code GameSession} also has the responsibility  for
 * allowing only the current {@link Participant} to make decisions which will
 * affect the {@code Game} such as placing a {@link Piece}.
 * 
 * <p>
 * The {@code GameSession} class works with the {@link Game} class to translate
 * {@link Piece} objects to {@link Participants}, which allows for translation
 * from the current {@link Piece} to the corresponding {@link Participant}.
 * </p>
 * 
 * <p>
 * {@code GameSession} also acts as a point of communication between
 * {@link Participant} objects and a {@link Game} object, which prevents
 * methods from the {@link Game} class to be directly accessed by
 * {@link Participant} objects, as the {@link Game} class does not consider
 * the turn of a {@link Participant}.
 * </p>
 * 
 * <p>
 * The {@link GameCommand} class is used to allow objects such as
 * {@link Participant} objects, to interact with the {@link Game} class,
 * through {@code GameSession}. {@code GameSession} also checks
 * the issuer of a {@link GameCommand} before executing it on a {@link Game}
 * as it is the {@code GameSession} class's responsibility to only allow
 * the current player to make certain decisions.
 * </p>
 * 
 * @author 	15901426 John Berg
 * @since 	20/11/2017
 * @version 25/11/2017
 * @see Game
 * @see Participant
 * @see Piece
 * @see GameCommand
 */
public final class GameSession
{
	//=========================================================================
	//Fields.
	/**
	 * The {@link Game} instance that {@code GameSession} is using to forward
	 * queries.
	 * 
	 * @see Game
	 */
	private final Game game;
	/**
	 * The {@link Map} which maps {@link Piece} objects to the corresponding
	 * {@link Participant} object.
	 * 
	 * @see Piece
	 * @see Participant
	 */
	private final Map<Piece, Participant> playerMap;
	//=========================================================================
	//Constructors.
	/**
	 * Create a {@code GameSession} with a {@link Game} that manages the turns
	 * of the {@code GameSession}, and two {@link Participant} objects which
	 * represent the first and second player respectively.
	 * 
	 * @param game The {@link Game} class which the created {@code GameSession}
	 * 			will use to 
	 * @param player1 The {@link Participant} which correspond to
	 * 			{@link Piece#PIECE_A}.
	 * @param player2 The {@link Participant} which correspond to
	 * 			{@link Piece#PIECE_B}.
	 * @throws NullPointerException If either <code>game</code>,
	 * 			<code>player1</code> or <code>player2</code> is
	 * 			<code>null</code>.
	 * @see Game
	 * @see Participant
	 */
	public GameSession(
			final Game game,
			final Participant player1,
			final Participant player2)
			throws
			NullPointerException
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
	 * Accept a {@link GameCommand} to be executed on the {@link Game} in
	 * <code>this</code> {@code GameSession}.
	 * 
	 * <p>
	 * The {@link GameCommand} may not be accepted if the issuer of the command
	 * is not accepted by <code>this</code> {@code GameSession}; such as the
	 * issuer being a {@link Participant} attempting to make a move during the
	 * opponents turn; the {@link GameCommand} issued by the
	 * {@link Participant} would simply be ignored.
	 * </p>
	 * 
	 * @param command The {@link GameCommand} to be invoked onto the
	 * 			{@link Game} instance contained in <code>this</code>.
	 * @throws GameException If executing the <code>command</code> causes an
	 * 			exception to be thrown by {@link Game}.
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
	 * Get the read-only {@link BoardView} which represent the
	 * {@link GameBoard} of <code>this</code> {@code GameSession}.
	 * 
	 * @return The {@link BoardView} of <code>this</code> {@code GameSession}.
	 * @see BoardView
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