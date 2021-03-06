package com.othellog4.game;

import java.util.LinkedHashSet;
import java.util.Set;

import com.othellog4.game.board.BoardView;
import com.othellog4.game.board.GameBoard;
import com.othellog4.game.board.InvalidMoveException;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;

/**
 * The {@code Game} is a class which models a game of Othello, which has the
 * responsibility of managing and maintaining the flow of the game.
 *
 * <p>
 * The {@code Game} provides the capability to get information regarding the
 * current {@link Piece} which is to make a move, additionally some methods
 * allow for interaction with the {@link GameBoard} class using the current
 * {@link Piece}.
 * </p>
 *
 * @author 	159014260 John Berg
 * @author  Arvinder Chatha
 * @since 	18/10/2017
 * @version 16/03/2018
 * @see GameBoard
 * @see Piece
 */
public class Game
{
	//========================================================================
	//Static fields.
	/**
	 * The <code>int</code> which represent the first turn of the {@code Game}.
	 */
	private static final int FIRST_TURN = 1;
	/**
	 * The {@link String} constant which represents the message for when a
	 * {@link Position} is <code>null</code>.
	 */
	private static final String NULL_POSITION = "Position cannot be null";
	/**
	 * The {@link String} constant which represents the message for when a
	 * {@link Piece} is <code>null</code>.
	 */
	private static final String NULL_PIECE = "Piece cannot be null";
	//=========================================================================
	//Fields.
	/**
	 * The flag which represent if events are enabled or not.
	 */
	private boolean enableEvent;
	/**
	 * The <code>int</code> which represent the number of the turn of
	 * <code>this</code> {@code Game}.
	 */
	private int turn;
	/**
	 * The {@link GameBoard} of <code>this</code> game.
	 *
	 * @see GameBoard
	 */
	private final GameBoard board;
	/**
	 * The {@link Piece} object which represents the {@link Piece} of the
	 * current player.
	 *
	 * @see Piece
	 */
	private Piece current;
	/**
	 * The {@link GameConclusion} of the {@code Game}.
	 *
	 * <p>
	 * Must be set to signal the conclusion.
	 * </p>
	 *
	 * @see GameConclusion
	 */
	private GameConclusion conclusion;
	/**
	 * The current {@link GameState} which represent the state of
	 * <code>this</code> {@code Game} object.
	 *
	 * @see GameState.
	 */
	private GameState state;
	/**
	 * The last {@link GameEvent} which was not broadcasted.
	 */
	private volatile GameEvent lastEvent;
	/**
	 * The {@link Set} of {@link GameListener} objects to update.
	 *
	 * @see GameListener
	 */
	private final Set<GameListener> listeners;
	//=========================================================================
	//Constructors.
	/**
	 * Create a {@code Game} object by specifying a {@link GameBoard} that
	 * a {@code Game} should be played on.
	 *
	 * <p>
	 * The current {@link Piece} will be set to {@link Piece#PIECE_A} as
	 * {@link Piece#PIECE_A} should go first.
	 * </p>
	 *
	 * @param board The {@link GameBoard} for <code>this</code> game.
	 * @throws NullPointerException If <code>board</code> is
	 * 			<code>null</code>.
	 * @see GameBoard
	 */
	public Game(final GameBoard board)
			throws
			NullPointerException
	{
		//May throw NullPointerException.
		this(board, Piece.PIECE_A);
	}
	/**
	 * Create a {@code Game} by with a specific {@link GameBoard} and the
	 * {@link Piece} which current turn it is.
	 *
	 * <p>
	 * This constructor can be used to load games which are partially completed
	 * or to create custom games and game modes with specially created
	 * {@link GameBoard} and non-fixed first players.
	 * </p>
	 *
	 * @param board The {@link GameBoard} which will be used to play the
	 * 			{@link Game}.
	 * @param currentPiece The {@link Piece} for which player's turn it
	 * 			currently is.
	 * @throws NullPointerException If either <code>board</code> or
	 * 			<code>currentPiece</code> is <code>null</code>.
	 * @see GameBoard
	 * @see Piece
	 */
	public Game(
			final GameBoard board,
			final Piece currentPiece)
			throws
			NullPointerException
	{
		//May throw NullPointerException.
		this(board, currentPiece, FIRST_TURN);
	}
	/**
	 * Create a {@code Game} by with a specific {@link GameBoard} and the
	 * {@link Piece} which current turn it is.
	 *
	 * <p>
	 * This constructor can be used to load games which are partially completed
	 * or to create custom games and game modes with specially created
	 * {@link GameBoard} and non-fixed first players.
	 * </p>
	 *
	 * @param board The {@link GameBoard} which will be used to play the
	 * 			{@link Game}.
	 * @param currentPiece The {@link Piece} for which player's turn it
	 * 			currently is.
	 * @param turn The current turn of the {@code Game}.
	 * @throws NullPointerException If either <code>board</code> or
	 * 			<code>currentPiece</code> is <code>null</code>.
	 * @see GameBoard
	 * @see Piece
	 */
	public Game(
			final GameBoard board,
			final Piece currentPiece,
			final int turn)
			throws
			NullPointerException
	{
		if(board == null)
			throw new NullPointerException();
		if(currentPiece == null)
			throw new NullPointerException();
		this.enableEvent = true;
		this.turn = turn;
		this.board = board;
		conclusion = null;
		current = currentPiece;
		state = GameState.initial();
		listeners = new LinkedHashSet<>();
	}
	//=========================================================================
	//Methods.
	/**
	 * Go to the next turn.
	 *
	 * <p>
	 * If a {@link Piece} does not have any legal moves, then the turn will
	 * go back to the current {@link Piece} object's turn.
	 * </p>
	 *
	 * <p>
	 * <b>For internal use only!</b>
	 * </p>
	 */
	private void advance()
	{
		if(isGameOver())
			update(GameEvent.END);
		else if(!board.legalMoves(current.flip()).isEmpty())
		{
			current = current.flip();
			++turn;
			update(GameEvent.NEXT_TURN);
		}
		else
			update(GameEvent.NEXT_TURN);
	}
	/**
	 * If the last event is not <code>null</code>, then forward the
	 * {@link GameEvent} which was ready to be issued, then set the last event
	 * to <code>null</code>. 
	 */
	private synchronized void resume()
	{
		if(lastEvent != null)
		{
			final GameEvent temp = lastEvent;
			lastEvent = null;
			update(temp);
		}
	}
	/**
	 * Set the {@link GameState} of <code>this</code> {@code Game}.
	 *
	 * <p>
	 * For internal use only!
	 * </p>
	 *
	 * @param state The {@link GameState} to set the current state to.
	 * @see GameState
	 */
	private void setState(final GameState state)
	{
		this.state = state;
	}
	/**
	 * Update all the {@link GameListener} objects in <code>this</code>
	 * {@code Game}.
	 *
	 * <p>
	 * This method should be called every time an action which will affect the
	 * {@link GameBoard} is executed.
	 * </p>
	 *
	 * <p>
	 * <b>For internal use only!</b>
	 * </p>
	 *
	 * @param event The {@link GameEvent} which was triggered.
	 * @see GameEvent
	 */
	private synchronized void update(final GameEvent event)
	{
		if(enableEvent)
		{
			for(final GameListener listener: listeners)
				listener.update(event);
			if(event == GameEvent.NEXT_TURN || event == GameEvent.BEGIN)
				update(GameEvent.STANDBY);
		}
		else if(lastEvent == null)
			lastEvent = event;
	}
	/**
	 * Set the {@link GameConclusion} of <code>this</code>
	 * {@link GameConclusion}.
	 *
	 * <p>
	 * Once a {@link GameConclusion} has not been set, it cannot be set again.
	 * </p>
	 *
	 * <p>
	 * <b>For internal use only!</b>
	 * </p>
	 *
	 * @param conclusion The {@link GameConclusion} of <code>this</code>
	 * 			{@code Game}.
	 * @see GameConclusion
	 */
	private void conclude(final GameConclusion conclusion)
	{
		if(this.conclusion == null)
			this.conclusion = conclusion;
	}
	/**
	 * Check if the {@code Game} has been started.
	 * 
	 * @return <code>true</code> if <code>this</code> {@code Game} has been
	 * 			started, otherwise, returns <code>false</code>.
	 */
	public final boolean isStarted()
	{
		return getCurrentState() != GameState.READY;
	}
	/**
	 * Check if the {@code Game} object is currently playing.
	 *
	 * @return <code>true</code> if <code>this</code> {@code Game} is playing,
	 * 			otherwise, returns <code>false</code>.
	 */
	public final boolean isPlaying()
	{
		return getCurrentState() == GameState.PLAYING;
	}
	/**
	 * Check if the {@code Game} object is currently paused.
	 * 
	 * @return <code>true</code> if <code>this</code> {@code Game} is paused,
	 * 			otherwise, returns <code>false</code>.
	 */
	public final boolean isPaused()
	{
		return getCurrentState() == GameState.PAUSED;
	}
	/**
	 * Check if the game is over.
	 *
	 * @return <code>true</code> if the game has ended, otherwise, returns
	 * 			<code>false</code>.
	 */
	public final boolean isGameOver()
	{
		if(conclusion != null)
			return true;
		else if(board.isEnd())
		{
			setState(GameState.GAME_OVER);
			conclude(GameConclusion.winner(board.winning()));
		}
		return getCurrentState() == GameState.GAME_OVER;
	}
	/**
	 * Check if the ability to issue {@link GameEvent} objects is enabled.
	 * 
	 * @return <code>true</code> if the events are enabled, otherwise, return
	 * 			<code>false</code>.
	 */
	public final synchronized boolean isEventEnabled()
	{
		return enableEvent;
	}
	/**
	 * Get the current turn of <code>this</code> {@code Game}.
	 *
	 * @return The current turn of <code>this</code> {@code Game}.
	 */
	public final int turn()
	{
		return turn;
	}
	/**
	 * Run <code>this</code> {@code Game} by setting the <code>this</code>
	 * game to the {@link GameState#PLAYING}.
	 */
	public final void start()
	{
		setState(state.start());
		update(GameEvent.BEGIN);
	}
	/**
	 * Suspend <code>this</code> {@code Game} by setting the <code>this</code>
	 * game to the {@link GameState#PAUSED}.
	 */
	public final void pause()
	{
		setState(state.pause());
		update(GameEvent.PAUSED);
	}
	/**
	 * End <code>this</code> {@code Game} by setting the <code>this</code>
	 * game to the {@link GameState#GAME_OVER}.
	 */
	public final void end()
	{
		setState(state.end());
		conclude(board.isDraw()
				?GameConclusion.draw()
				:GameConclusion.winner(board.winning()));
		update(GameEvent.END);
	}
	/**
	 * Enable or disable the ability to signal {@link GameEvent} objects.
	 * 
	 * @param enable The status to set the event enable status to.
	 */
	public final synchronized void enableEvent(final boolean enable)
	{
		boolean temp = isEventEnabled();
		enableEvent = enable;
		if(!temp && enableEvent)
			resume();
	}
	/**
	 * Put the current {@link Piece} at a specific {@link Position}.
	 *
	 * <p>
	 * After a move has been completed, the {@code Game} will move the the
	 * next turn.
	 * </p>
	 *
	 * @param position The {@link Position} to place the {@link Piece} of the
	 * 			current player's {@link Piece}.
	 * @throws InvalidMoveException If the current {@link Piece} cannot be
	 * 			placed at <code>position</code>.
	 * @throws NullPointerException If <code>position</code> is
	 * 			<code>null</code>.
	 * @see #getCurrent()
	 * @see Position
	 */
	public final void put(final Position position)
			throws
			InvalidMoveException,
			NullPointerException
	{
		if(position == null)
			throw new NullPointerException(NULL_POSITION);
		if(GameState.PLAYING == getCurrentState())
		{
			board.put(position, getCurrent());
			update(GameEvent.ACCEPTED_PLACMENT);
			advance();
		}
	}
	/**
	 * Surrenders the game.
	 *
	 * @param piece The {@link Piece} object which surrenders.
	 * @throws NullPointerException If <code>piece</code> is <code>null</code>.
	 * @throws IllegalStateException If {@link Game#isGameOver()} returns
	 * 			<code>true</code>.
	 * @see Piece
	 */
	public final void surrender(final Piece piece)
			throws
			NullPointerException,
			IllegalStateException
	{
		if(piece == null)
			throw new NullPointerException(NULL_PIECE);
		if(isGameOver())
			throw new IllegalStateException("Game already over");
		setState(GameState.GAME_OVER);
		conclude(GameConclusion.loser(piece));
		update(GameEvent.END);
	}
	/**
	 * Add a {@link GameListener} to be updated about events that occur in
	 * <code>this</code> {@code Game}.
	 *
	 * @param listener The {@link GameListener} to be added to
	 * 			<code>this</code>.
	 * @see GameListener
	 */
	public final synchronized void addListener(final GameListener listener)
	{
		listeners.add(listener);
	}
	/**
	 * Remove a {@link GameListener} existing in <code>this</code>
	 * {@code Game}.
	 *
	 * @param listener The {@link GameListener} to be removed from
	 * 			<code>this</code>.
	 * @see GameListener
	 */
	public final synchronized void removeListener(final GameListener listener)
	{
		listeners.remove(listener);
	}
	/**
	 * Remove all {@link GameListener} objects existing in <code>this</code>
	 * {@code Game} object.
	 */
	public final synchronized void removeAllListeners()
	{
		listeners.clear();
	}
	/**
	 * Get the {@link Piece} representing the first player.
	 *
	 * <p>
	 * The first player will always have {@link Piece#PIECE_A} as their
	 * {@link Piece}.
	 * </p>
	 *
	 * @return The {@link Piece} of the first player.
	 * @see Piece
	 */
	public final Piece getPlayer1()
	{
		return Piece.player1();
	}
	/**
	 * Get the {@link Piece} representing the second player.
	 *
	 * <p>
	 * The second player will always have {@link Piece#PIECE_B} as their
	 * {@link Piece}.
	 * </p>
	 *
	 * @return The {@link Piece} of the second player.
	 * @see Piece
	 */
	public final Piece getPlayer2()
	{
		return Piece.player2();
	}
	/**
	 * Get the {@link Piece} which current turn it is.
	 *
	 * @return The {@link Piece} of the player whom's turn it currently is.
	 * @see Piece
	 */
	public final Piece getCurrent()
	{
		return current;
	}
	/**
	 * Get the {@link GameConclusion} of <code>this</code> {@code Game}.
	 *
	 * <p>
	 * If the {@link Game#isGameOver()} returns <code>false</code>, then
	 * <code>this</code> {@code Game} has no conclusion.
	 * </p>
	 *
	 * @return The {@link GameConclusion} of <code>this</code> {@code Game}.
	 * @throws IllegalStateException If the {@code Game} has not been
	 * 			concluded.
	 * @see GameConclusion
	 */
	public final GameConclusion getConclusion()
			throws
			IllegalStateException
	{
		if(conclusion == null)
			throw new IllegalStateException();
		return conclusion;
	}
	/**
	 * Get the current {@link GameState} object of <code>this</code>
	 * {@code Game} object.
	 *
	 * @return The current {@link GameState}.
	 * @see GameState
	 */
	final GameState getCurrentState()
	{
		return state;
	}
	/**
	 * Get the {@link BoardView} of the board which is contained in
	 * <code>this</code> {@code Game}.
	 *
	 * @return A {@link BoardView} object of the {@link GameBoard} for
	 * 			<code>this</code> game.
	 * @see BoardView
	 */
	public final BoardView getBoard()
	{
		return board.getView();
	}
}