package Game;

/**
 * 
 * @author John Berg 159014260
 * @author <Add your name here EastWood>
 *
 */
public class Game {
	/**
	 * 
	 */
	private GameBoard board;
	/**
	 * 
	 */
	private GameViewControl player1;
	/**
	 * 
	 */
	private GameViewControl player2;
	/**
	 * 
	 * @param board
	 * @param player1
	 * @param player2
	 */
	public Game(final GameBoard board)
	{
		this.board = board;
	} //Game(GameBoard)
	/**
	 * 
	 */
	public void play(){
		
	} //play()
	public final  GameViewControl getControl()
	{
		return null;
	}
	public final BoardView getBoard()
	{
		return null;
	}
} //Game