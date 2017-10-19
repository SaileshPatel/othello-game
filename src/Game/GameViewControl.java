package Game;

public class GameViewControl
{
	private final Game game;
	private final Piece piece;
	private final Player player;
	private Position position;
	
	public GameViewControl(final Game game, final Piece piece, Player player)
	{
		this.game = game;
		this.piece = piece;
		this.player = player;
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
		return game.getBoard();
	}
	
}