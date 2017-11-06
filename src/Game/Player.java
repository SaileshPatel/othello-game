package Game;

import java.util.Scanner;

/**
 * The player class interacts with the Game view the GameView Control 
 * 
 * @author Sailesh Patel
 * @author Zak Hirsi
 * @author John Berg
 * @author Arvinder Chatha
 * @since 23/10/2017
 * @version 04/11/2017
 *
 */
public class Player implements Participant, Runnable {
	
	private static final Scanner input = new Scanner(System.in);
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

	@Override
	public final void run()
	{

		for(;;)
		{
			while(!gvc.isTurn());
			
			String color = "";
			
			switch (gvc.getPiece()) {
			case PIECE_A:
				color = "Black";
				break;
			case PIECE_B:
				color = "White";
				break;
			}
			
			System.out.print( color + "'s turn (x, y): " );
			int row = input.nextInt() - 1; //-1 to align with game board
			int col = input.nextInt() - 1;
			setPosition(col, row);
		}
	}
	
}
