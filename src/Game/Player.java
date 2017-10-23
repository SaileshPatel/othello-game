package Game;

/**
 * The player class interacts with the Game view the GameView Control 
 * 
 * Take gameview as Parameter
 * @author Sailesh Patel
 * @author Zak Hirsi
 * @since 23/10/2017
 * @version 0.0.1
 *
 */
public class Player implements Participant{
	
	private GameViewControl gvc;
	
	// wait for text box to be implemented
	/**
	 * The constructor for the player, which requires a GameViewController
	 * @param gvc - the GameViewController which controls the game from the perspective of the player
	 */
	public Player(GameViewControl gvc){
		// the player has a game view controller
		this.gvc = gvc;
	}
	
	
	// set position method
	// when there is a move, use getPosition
	// game will use setPosition 
	/**
	 * This method sets the position of the player i.e the Piece
	 * @param row - the row of the player
	 * @param col - the column of the player
	 */
	public void setPosition(int row, int col){
		gvc.setPosition(row, col);
	}
}
