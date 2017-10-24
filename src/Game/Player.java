package Game;

import java.util.Scanner;

/**
 * The player class interacts with the Game view the GameView Control 
 * 
 * @author Sailesh Patel
 * @author Zak Hirsi
 * @since 23/10/2017
 * @version 24/10/2017
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
		this.gvc = gvc;
	}
	
	/**
	 * This method sets the position of the player
	 * @param row - the row of the player
	 * @param col - the column of the player
	 */
	public void setPosition(int row, int col){
		gvc.setPosition(row, col);
	}
	
	/**
	 * This method returns the position of the player. 
	 * @return the position of the player
	 */
	public Position getPosition(){
		return gvc.getPosition();
	}
}
