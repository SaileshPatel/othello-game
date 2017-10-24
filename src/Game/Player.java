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
	private Scanner scan;
	
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
	
	
	public void run(){
		// put a loop
		// read input
			// check if it's valid
		boolean valid = true;
		Scanner scan = new Scanner(System.in);
		int v1 = scan.nextInt();
		int v2 = scan.nextInt();

	}
	
}
