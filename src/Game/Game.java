package Game;

/**
 * The {@code Game} is a class which models a game of Othello, which has the
 * responsibility of managing and maintaining the flow of the game session.
 * 
 * <>
 * 
 * </p>
 * 
 * @author 	John Berg 159014260
 * @author 	<Add your name here EastWood>
 * @since 	18/10/2017
 * @version 19/10/2017
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
	 * 
	 */
	private GameViewControl player1;
	/**
	 * 
	 */
	private GameViewControl player2;
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
	} //Game(GameBoard)
	//=========================================================================
	//Methods.
	/**
	 * 
	 * <p>
	 * <b>For internal use only!</b>
	 * </p>
	 * 
	 * @param control The {@link GameViewControl} which current turn it is.
	 */
	private void turn(final GameViewControl control)
	{
		if(!board.legalMoves(control.piece).isEmpty())
		{
			//notify controller about turn (TBA).
			while(!control.ready());
			board.put(control.getPosition(), control.piece);
		} //if
	}
	/**
	 * Begin playing the game.
	 * 
	 * <p>
	 * Prior to playing the game, exactly two {@link GameViewControl} objects
	 * must have been created from <code>this</code> game.
	 * </p>
	 * 
	 * @see #getControl()
	 */
	public void play()
	{
		//Game loop.
		while(!board.isEnd()) 
		{
			turn(player1);
			turn(player2);
		}
	} //play()
	/**
	 * 
	 * <p>
	 * This method must be called twice before invoking the {@link #play()}
	 * method.
	 * </p>
	 * 
	 * @return A {@link GameViewControl} object which correspond to
	 * 			<code>this</code> game.
	 * @throws IllegalStateException If this method is called more than two
	 * 			times.
	 * @see GameViewControl
	 */
	public final  GameViewControl getControl()
			throws IllegalStateException
	{
		if(player1 == null)
			return player1 = new GameViewControl(getBoard(), Piece.PIECE_A);
		if(player2 == null)
			return player2 = new GameViewControl(getBoard(), Piece.PIECE_B);
		throw new IllegalStateException();
	} //getControl()
	/**
	 * 
	 * 
	 * @return A {@link BoardView} object of the {@link GameBoard} for
	 * 			<code>this</code> game.
	 */
	public final BoardView getBoard()
	{
		return board;
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