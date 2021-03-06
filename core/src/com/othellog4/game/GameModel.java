package com.othellog4.game;

import java.util.Observable;
import java.util.Optional;

import com.othellog4.game.board.BoardView;
import com.othellog4.game.board.Piece;
import com.othellog4.game.extension.GameExtension;
import com.othellog4.game.player.Participant;
import com.othellog4.game.player.Player;

/**
 * The {@code GameModel} class is a model of a game of Othello, which allows
 * for interaction using method calls.
 *
 * <p>
 * The {@code GameModel} functions as a boundary object for interactions with
 * a {@link Game} object.
 * </p>
 *
 * <p>
 * The {@code GameManager} encapsulates the details of how the communication
 * between internals and externals functions.
 * </p>
 *
 * @author 	159014260 John Berg
 * @since 	01/12/2017
 * @version 16/03/2018
 */
public class GameModel extends Observable
{
	//========================================================================
	//Fields.
	/**
	 * The {@link GameManager} object which manages the details of the
	 * {@link Participant} objects involved in the {@link GameModel} and
	 * the {@link GameExtension} objects/
	 *
	 * @see GameManager
	 */
	private final GameManager manager;
	//=========================================================================
	//Constructors.
	/**
	 * Construct a {@code GameModel} object which will handle higher-level
	 * interactions with a {@link Game} object.
	 *
	 * <p>
	 * The creation also allows for {@link GameExtensions} to be included.
	 * </p>
	 *
	 * @param game The {@link Game} which the {@code GameModel} will act as
	 * 			the boundary for.
	 * @param player1 The {@link Participant} representing the first player.
	 * @param player2 The {@link Participant} representing the second player.
	 * @param extensions The {@link GameExtension} objects which the
	 * 			{@code GameModel} will include.
	 * @see Game
	 * @see Participant
	 * @see GameExtension
	 */
	public GameModel(
			final Game game,
			final Participant player1,
			final Participant player2,
			final GameExtension... extensions)
	{
		manager = new GameManager(game, player1, player2, extensions);
		new GameSession(manager);
		manager.game().addListener(this::update);
	}
	//=========================================================================
	//Methods.
	/**
	 * Notify all objects which are observing <code>this</code>
	 * {@link GameModel} object.
	 *
	 * <p>
	 * This method is intended as a callback as a {@link GameListener},
	 * which forwards events to other objects.
	 * </p>
	 *
	 * @param event The {@link GameEvent} object.
	 * @see GameListener
	 * @see GameEvent
	 */
	private void update(final GameEvent event)
	{
		super.setChanged();
		super.notifyObservers(event);
	}
	/**
	 * Check if the {@link GameModel} has started the game.
	 *
	 * @return <code>true</code> if the game has been started, otherwise,
	 * 			returns <code>false</code>.
	 */
	public final boolean isStarted()
	{
		return manager.game().isStarted();
	}
	/**
	 * Check if the game is currently being played.
	 *
	 * @return <code>true</code> if the game is currently being played,
	 * 			otherwise, return <code>false</code>.
	 */
	public final boolean isPlaying()
	{
		return manager.game().isPlaying();
	}
	/**
	 * Check if the game is currently paused.
	 * 
	 * @return <code>true</code> if the game is paused, otherwise, returns
	 * 			<code>false</code>.
	 */
	public final boolean isPaused()
	{
		return manager.game().isPaused();
	}
	/**
	 * Check if the {@link GameModel} has a game which has ended.
	 *
	 * @return <code>true</code> if the game has ended, otherwise, returns
	 * 			<code>false</code>.
	 */
	public final boolean isGameOver()
	{
		return manager.game().isGameOver();
	}
	/**
	 * Check if <code>this</code> {@code GameModel} is waiting for an input.
	 *
	 * @return <code>true</code> if <code>this</code> {@code GameModel} is
	 * 			awaiting input, otherwise, returns <code>false</code>.
	 */
	public final boolean isWaiting()
	{
		return getCurrent().getControl().isPresent();
	}
	/**
	 * Check if inputs are enabled.
	 * 
	 * @return The input enabled status.
	 */
	public final boolean isInputEnabled()
	{
		return manager.isInputEnabled();
	}
	/**
	 * Check if events are enabled.
	 * 
	 * @return The status which represents if events are enabled.
	 */
	public final boolean isEventEnabled()
	{
		return manager.game().isEventEnabled();
	}
	/**
	 * Get the current turn of the game.
	 *
	 * @return The current turn of the game.
	 */
	public final int turn()
	{
		return manager.game().turn();
	}
	/**
	 * Enable or disable input such that it is not possible to progress the
	 * game.
	 * 
	 * @param enable The enable status.
	 */
	public final void enableInput(final boolean enable)
	{
		manager.enableInput(enable);
	}
	/**
	 * Set the status for the ability to singnal {@link GameEvent} objects.
	 * 
	 * @param enable The status of the ability to signal {@link GameEvent}
	 * 			object.
	 */
	public final void enableEvent(final boolean enable)
	{
		manager.game().enableEvent(enable);
	}
	/**
	 * Start the game.
	 * 
	 * <p>
	 * This method does nothing if the game has already been started.
	 * </p>
	 */
	public final void start()
	{
		if(!isStarted())
			manager.game().start();
	}
	/**
	 * Signal the current {@link Participant} to pause the game.
	 * 
	 * <p>
	 * This method cannot carry out the task if {@link #isWaiting()} returns
	 * <code>false</code>.
	 * </p>
	 * 
	 * @throws GameException If pausing is not possible.
	 */
	public final void pause() throws GameException
	{
		if(isWaiting())
			getCurrent().getControl().get().pause();
	}
	/**
	 * Signal the current {@link Participant} to resume the game.
	 * 
	 * <p>
	 * This method cannot carry out the task if {@link #isWaiting()} returns
	 * <code>false</code>.
	 * </p>
	 * 
	 * @throws GameException If resuming is not possible.
	 */
	public final void resume() throws GameException
	{
		if(isStarted() && isWaiting())
			getCurrent().getControl().get().resume();
	}
	/**
	 * Surrender the game.
	 *
	 * <p>
	 * The current {@link Participant} surrenders the game if it is
	 * controllable.
	 * </p>
	 */
	public final void surrender()
	{
		if(manager.current().getControl().isPresent())
			manager.game().surrender(manager.game().getCurrent());
	}
	/**
	 * Issue a command to attempt to place a {@link Piece} object at a
	 * specified location.
	 *
	 * <p>
	 * This method will have no effect if the {@link GameModel#getCurrent()}
	 * is not controllable.
	 * </p>
	 *
	 * @param x The column of the board.
	 * @param y The row of the board.
	 * @throws GameException
	 */
	public final void put(int x, int y)
			throws
			GameException
	{
		final Optional<Participant.Control> control = getCurrent()
				.getControl();
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
	 * @see Participant
	 */
	public final Participant getCurrent()
	{
		return manager.current();
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
	 * @see Participant
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
	 * @see Participant
	 */
	public final Participant getPlayer2()
	{
		return manager.player2();
	}
	/**
	 * Get the {@link Piece} of the current player.
	 *
	 * @return The {@link Piece} of the current player.
	 * @see Piece
	 */
	public final Piece getCurrentPiece()
	{
		return manager.game().getCurrent();
	}
	/**
	 * Get the {@link Piece} object which represents the first player.
	 *
	 * @return The {@link Piece} which represents the first player.
	 * @see Piece
	 */
	public final Piece getPlayer1Piece()
	{
		return manager.game().getPlayer1();
	}
	/**
	 * Get the {@link Piece} object which represents the second player.
	 *
	 * @return The {@link Piece} which represents the second player.
	 * @see Piece
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
	 * @see BoardView
	 */
	public final BoardView getBoard()
	{
		return manager.game().getBoard();
	}
	/**
	 * Get the {@link GameScore} object from the game.
	 *
	 * @return The {@link GameScore} for <code>this</code> {@code GameModel}
	 * 			object.
	 * @see GameScore
	 */
	public final GameScore score()
	{
		return manager.score();
	}
}