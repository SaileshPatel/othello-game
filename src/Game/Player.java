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
	
	public Player(GameViewControl gvc){
		// the player has a game view controller
		this.gvc = gvc;
	}
	
	
	// set position method
	// when there is a move, use getPosition
	// game will use setPosition 
	
	public void setPosition(){
		
	}
}
