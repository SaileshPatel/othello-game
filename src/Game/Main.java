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
	private static int rows = 8;
	private static int columns = 8;
	private static Color colour1 = Color.WHITE;
	private static Color colour2 = Color.WHITE;
	
	public static void main(String[] args) {
		

		
			
				JFrame mainFrame = new JFrame();	
				JPanel board = new JPanel();
				
				mainFrame.setLayout(new BorderLayout());		// Create main frame to hold the board
				((JPanel)mainFrame.getContentPane()).setBorder(new EmptyBorder(0, 0, 0, 0));
				mainFrame.setSize(800, 800);
				
				board.setLayout(new GridLayout(rows,columns));
				mainFrame.add(board); //Created mainframe and added the board container to it
				
			
				Color tileColour = null;
				
				for (int i = 0; i < rows; i++){ 
					if ((i%2) == 0){	//alternate the colour of the individual tile for each row
						tileColour = colour1; 
					}else{
						tileColour = colour2;
					}
					
					for (int j = 0; j < columns; j++){
						
						JPanel tile = new JPanel();	//Create individual tile
						tile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
						tile.setBackground(tileColour);		//Add colour to the tile
						
						
						if(tileColour.equals(colour1)){
							tileColour = colour2;				//alternate the colour of the individual tiles for each column
						}else{
							tileColour = colour1;
						}
						
						board.add(tile);				//add tile to the board
						
					}
				
				}
				mainFrame.setVisible(true);
				
			}
	

}
