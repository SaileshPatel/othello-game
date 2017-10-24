package Game;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

/**
 * Main game GUI. Creates the board using a grid layout.  
 * 
 * @author 	Zakeria Hirsi
 */

public class Main {
	
	/**
	 * This is the main class. In this class, the game is run
	 * @param args command line arguments to be taken
	 */
	public static void main(String[] args) {
		// param does not work
		GameBoard board = new GameBoard(8);
		
		
//		board.put(new Position(1,1), Piece.PIECE_A);
//		board.put(new Position(5,5), Piece.PIECE_B);
		Game game = new Game(board);
		Player player1 = new Player(game.getControl());
		player1.run();
	}
	

}
