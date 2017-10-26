package Game;

/**
 * This class controls the game play which is then fed to the Game class 
 * @author John Berg
 *
 */
public class GameViewControl
{
	/**
	 * A flag which indicates if it is the current controllers
	 * turn.
	 */
	private volatile boolean isTurn;
	private final BoardView board;
	public final Piece piece;
	private volatile Position position;
	
	public GameViewControl(final BoardView board, final Piece piece)
	{
		this.board = board;
		this.piece = piece;
	}
	/**
	 * FOR GAME to use!!
	 * 
	 * @return whether Game is ready to be played or not
	 */
	public final boolean ready()
	{
		if (position == null) {
			return false;
		}
		else {
			return true;
		}
	}
	/**
	 * Check to see if it is currently <code>this</code> controllers turn,
	 * 
	 * @return <code>true</code> if it is currently <code>this</code>
	 * 			controllers turn, otherwise, return <code>false</code>.
	 */
	public final boolean isTurn()
	{
		return isTurn;
	}
	/**
	 * FOR GAME to use!!
	 * 
	 * Set the {@link #isTurn} flag to <code>turn</code>.
	 */
	public final void notifyTurn()
	{
		isTurn = true;
	}
	/**
	 * Set the position and set the {@link GameViewControl#isTurn() isTurn} flag to <code>false</code>.
	 * 
	 * @param row The row of the position.
	 * @param col The column of the position.
	 */
	public final void setPosition(int row, int col)
	{
		isTurn = false;
		position = new Position(row, col);
	}
	/**
	 * FOR GAME to use!!
	 * 
	 * @return the position of the game
	 */
	public final Position getPosition()
	{
		Position tempPos = position;
		position = null;
		return tempPos;
		
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public final BoardView getBoard()
	{
		//TODO return proxy board instead
		return board;
	}
	
}