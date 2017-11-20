package othello.game;

import othello.game.board.BoardView;
import othello.game.board.GameBoard;
import othello.game.board.Piece;
import othello.game.board.Position;

/**
 * The {@code Game} is a class which models a game of Othello, which has the
 * responsibility of managing and maintaining the flow of the game session.
 * 
 * 
 * @author 	159014260 John Berg 
 * @author 	Eastwood
 * @author  Arvinder Chatha
 * @since 	18/10/2017
 * @version 20/11/2017
 */
public class Game
{
	//=========================================================================
	//Fields.
	/**
	 * The {@link GameBoard} of <code>this</code> game.
	 */
	private final GameBoard board;
	/**
	 * The ...
	 */
	private Piece current;
	//=========================================================================
	//Constructors.
	/**
	 * 
	 * @param board The {@link GameBoard} for <code>this</code> game.
	 * @throws NullPointerException If <code>board</code> is
	 * 			<code>null</code>.
	 */
	public Game(final GameBoard board)
			throws NullPointerException
	{
		if(board == null)
			throw new NullPointerException();
		this.board = board;
		current = Piece.PIECE_A;
	} //Game(GameBoard)
	//=========================================================================
	//Methods.
	/**
	 * Go to the next turn.
	 * 
	 * <p>
	 * If a {@link Piece} does not have any legal moves, then the turn will
	 * go back to the current {@link Piece} object's turn.
	 * </p>
	 */
	private void nextTurn()
	{
		if(!board.legalMoves(current.flip()).isEmpty())
			current = current.flip();
	}
	/**
	 * Check if the game is over.
	 * 
	 * @return <code>true</code> if the game has ended, otherwise, returns
	 * 			<code>false</code>.
	 */
	public final boolean isGameOver()
	{
		return board.isEnd();
	}
	/**
	 * 
	 * @param position
	 */
	public final void put(final Position position)
	{
		board.put(position, getCurrent());
		nextTurn();
	}
	/**
	 * Get the {@link Piece} representing the first player.
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
	 * 
	 * @return A {@link BoardView} object of the {@link GameBoard} for
	 * 			<code>this</code> game.
	 */
	public final BoardView getBoard()
	{
		return board.getView();
	} //getBoard()
	//=========================================================================
	//Overidden methods.
	/**
	 * 
	 */
	@Override
	public final boolean equals(Object o)
	{
		return false;
	} //equals(Object)
	/**
	 * 
	 */
	@Override
	public final int hashCode()
	{
		return 0;
	} //hashCode()
	/**
	 * 
	 */
	@Override
	public final String toString()
	{
		return "";
	} //toString()
} //Game