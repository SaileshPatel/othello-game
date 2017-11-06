package Game;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

/**
 * Main game GUI. Creates the board using a grid layout.  
 * 
 * @author 	Zakeria Hirsi
 * @author 	John Berg 159014269
 */

public class Main {
	
	/**
	 * This is the main class. In this class, the game is run
	 * @param args command line arguments to be taken
	 */
	public static void main(String[] args) {
		// param does not work
		GameBoard board = new GameBoard(8);
		
		System.out.println("Welcome to... \n");
		System.out.println("                      ,,                 ,,    ,,           ");
		System.out.println("  .g8\"\"8q.     mm   `7MM               `7MM  `7MM           ");
		System.out.println(".dP'    `YM.   MM     MM                 MM    MM           ");
		System.out.println("dM'      `MM mmMMmm   MMpMMMb.  .gP\"Ya   MM    MM  ,pW\"Wq.  ");
		System.out.println("MM        MM   MM     MM    MM ,M'   Yb  MM    MM 6W'   `Wb ");
		System.out.println("MM.      ,MP   MM     MM    MM 8M\"\"\"\"\"\"  MM    MM 8M     M8 ");
		System.out.println("`Mb.    ,dP'   MM     MM    MM YM.    ,  MM    MM YA.   ,A9 ");
		System.out.println("  `\"bmmd\"'     `Mbmo.JMML  JMML.`Mbmmd'.JMML..JMML.`Ybmd9'  \n\n");
		
		
//		board.put(new Position(1,1), Piece.PIECE_A);
//		board.put(new Position(5,5), Piece.PIECE_B);
		Game game = new Game(board);
		Runnable p1 = new Player(game.getControl());
		Runnable p2 = new Player(game.getControl());
		new Thread(p1).start();
		new Thread(p2).start();
		game.play();
	}
	

}
