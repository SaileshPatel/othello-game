package Game;

public class GameViewControl
{
	private final BoardView board;
	public final Piece piece;
	public GameViewControl(final BoardView board, final Piece piece)
	{
		this.board = board;
		this.piece = piece;
	}
	/**
	 * FOR GAME to use!!
	 * 
	 * @return
	 */
	public final boolean ready()
	{
		return false;
	}
	public final void put(int row, int col)
	{
		
	}
	/**
	 * FOR GAME to use!!
	 * 
	 * @return
	 */
	public final Position getPosition()
	{
		
		return null;
	}
	public final BoardView getBoard()
	{
		return board;
	}
}