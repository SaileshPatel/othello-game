package com.othellog4.game;

import java.util.Observable;
import java.util.Optional;

import com.othellog4.game.board.BoardView;
import com.othellog4.game.board.GameBoard;
import com.othellog4.game.board.Piece;
import com.othellog4.game.player.Participant;

/**
 * 
 * <p>
 * The {@code GameModel} functions as a boundry object.
 * </p>
 * 
 * @author 	159014260 John Berg
 * @since 	01/12/2017
 * @version 25/01/2017
 */
public class GameModel extends Observable
{
	//========================================================================
	//Fields.
	/**
	 * 
	 */
	private final GameManager manager;
	/**
	 * 
	 */
	private final GameSession session;
	//=========================================================================
	//Constructors.
	/**
	 * 
	 * 
	 * @param game 
	 * @param player1 The {@link Participant} representing the first player.
	 * @param player2 The {@link Participant} representing the second player.
	 */
	public GameModel(
			final Game game,
			final Participant player1,
			final Participant player2)
	{
		manager = new GameManager(game, player1, player2);
		this.session = new GameSession(manager);
		manager.game().addListener(this::update);
	}
	//=========================================================================
	//Methods.
	/**
	 * 
	 */
	private void update(final GameEvent event)
	{
		super.setChanged();
		super.notifyObservers(this);
	}
	/**
	 * 
	 */
	public final void start()
	{
		session.begin();
	}
	/**
	 * 
	 */
	public final void pause()
	{
		//TODO impl
	}
	/**
	 * 
	 */
	public final void quit()
	{
		//TODO implement
	}
	/**
	 * 
	 * @return
	 */
	public final boolean isPlaying()
	{
		return session.isPlaying();
	}
	/**
	 * ...
	 * 
	 * @return <code>true</code> if {@code GameModel} is ...
	 */
	public final boolean isWaiting()
	{
		return getCurrent().getControl().isPresent();
	}
	/**
	 * 
	 * @param x The column of the board.
	 * @param y The row of the board.
	 * @throws GameException
	 */
	public final void put(int x, int y)
			throws
			GameException
	{
		final Optional<Participant.Control> control = getCurrent().getControl();
		if(control.isPresent())
			control.get().put(x, y);
	}
	/**
	 * Get the {@link Participant} which current turn it is.
	 * 
	 * <p>
	 * The current {@link Participant} is the {@link Piece} object returned
	 * from the {@link Game} of <code>this</code>.
	 * </p>
	 * 
	 * @return The {@link Participant} which currently has it's turn.
	 */
	public final Participant getCurrent()
	{
		return manager.playerOf(session.current());
	}
	/**
	 * Get the {@link Participant} which is the first {@link Player} in
	 * <code>this</code>.
	 * 
	 * <p>
	 * The first player is the {@link Participant} which is associated with
	 * {@link Game#getPlaye1()}.
	 * </p>
	 * 
	 * @return The {@link Participant} which is the first player.
	 */
	public final Participant getPlayer1()
	{
		return manager.player1();
	}
	/**
	 * Get the {@link Participant} which is the second {@link Player} in
	 * <code>this</code>.
	 * 
	 * <p>
	 * The second player is the {@link Participant} which is associated
	 * with {@link Game#getPlaye2()}.
	 * </p>
	 * 
	 * @return The {@link Participant} which is the second player.
	 */
	public final Participant getPlayer2()
	{
		return manager.player2();
	}
	/**
	 * Get the {@link Piece} of the current player.
	 * 
	 * @return The {@link Piece} of the current player.
	 */
	public final Piece getCurrentPiece()
	{
		return session.current();
	}
	/**
	 * Get the {@link Piece} object which represents the first player.
	 * 
	 * @return The {@link Piece} which represents the first player.
	 */
	public final Piece getPlayer1Piece()
	{
		return manager.game().getPlayer1();
	}
	/**
	 * Get the {@link Piece} object which represents the second player.
	 * 
	 * @return The {@link Piece} which represents the second player.
	 */
	public final Piece getPlayer2Piece()
	{
		return manager.game().getPlayer2();
	}
	/**
	 * Get the {@link BardView} of the current {@link GameSession}.
	 * 
	 * @return The {@link BoardView} the current {@link GameSession} in
	 * 			<code>this</code> {@code GameModel}.
	 */
	public final BoardView getBoard()
	{
		return session.getBoard();
	}
	//=========================================================================
	//Static methods.
	/**
	 * 
	 * @param player1
	 * @param player2
	 * @return
	 */
	public static GameModel newGame(
			final Participant player1,
			final Participant player2)
	{
		return new GameModel(
				new Game(new GameBoard(8, 8)),
				player1,
				player2);
	}
}