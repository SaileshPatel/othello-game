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
 * <p>
 * {@code Game} is package private.
 * </p>
 * 
 * @author 	159014260 John Berg 
 * @author 	Eastwood
 * @author  Arvinder Chatha
 * @since 	18/10/2017
 * @version 25/01/2018
 * @see GameBoard
 * @see Piece
 */
class Game
{
	//=========================================================================
	//Fields.
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
	 * 
	 */
	private GameState state;
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
		if(board == null)
			throw new NullPointerException();
		if(currentPiece == null)
			throw new NullPointerException();
		turn = 0;
		this.board = board;
		current = currentPiece;
		state = GameState.initial();
		listeners = new LinkedHashSet<>();
	}
	//=========================================================================
	//Methods.
	/**
	 * 
	 */
	public final void start()
	{
		state = state.start();
		update(GameEvent.BEGIN);
	}
	/**
	 * 
	 */
	public final void pause()
	{
		state = state.pause();
		update(GameEvent.PAUSED);
	}
	/**
	 * 
	 */
	public final void end()
	{
		state = state.end();
		update(GameEvent.END);
	}
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
	private void nextTurn()
	{
		if(isGameOver())
			update(GameEvent.END);
		else if(!board.legalMoves(current.flip()).isEmpty())
		{
			current = current.flip();
			++turn;
			update(GameEvent.NEXT_TURN);
		}
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
	 */
	private synchronized void update(final GameEvent event)
	{
		for(final GameListener listener: listeners)
			listener.update(event);
	}
	/**
	 * Check if the game is over.
	 * 
	 * @return <code>true</code> if the game has ended, otherwise, returns
	 * 			<code>false</code>.
	 */
	public final boolean isGameOver()
	{
		if(board.isEnd())
			state = GameState.GAME_OVER;
		return state == GameState.GAME_OVER;
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
		board.put(position, getCurrent());
		nextTurn();
	}
	/**
	 * Add a {@link GameListener} to be updated about events that occur in
	 * <code>this</code> {@code Game}.
	 * 
	 * @param listener The {@link GameListener} to be added to
	 * 			<code>this</code>.
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
	 */
	public final Piece getPlayer1()
	{
		return Piece.PIECE_A;
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
	 */
	public final Piece getPlayer2()
	{
		return Piece.PIECE_B;
	}
	/**
	 * Get the {@link Piece} which current turn it is.
	 * 
	 * @return The {@link Piece} of the player whom's turn it currently is.
	 */
	public final Piece getCurrent()
	{
		return current;
	}
	/**
	 * 
	 * @return
	 */
	public final GameState getCurrentState()
	{
		return state;
	}
	/**
	 * Get the {@link BoardView} of the board which is contained in
	 * <code>this</code> {@code Game}.
	 * 
	 * @return A {@link BoardView} object of the {@link GameBoard} for
	 * 			<code>this</code> game.
	 */
	public final BoardView getBoard()
	{
		return board.getView();
	} //getBoard()
} //Game