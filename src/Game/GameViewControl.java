package Game;

public class GameViewControl
{
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
	 * @return
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
	
	public final void setPosition(int row, int col)
	{
		position = new Position(row, col);
	}
	/**
	 * FOR GAME to use!!
	 * 
	 * @return
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