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
	
	public static void main(String[] args) {
		GameBoard board = new GameBoard(8);
		Game game = new Game(board);
		game.play();
	}
	

}
