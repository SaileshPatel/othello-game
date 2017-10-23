package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * The {@code Game} is a class which models a game of Othello, which has the
 * responsibility of managing and maintaining the flow of the game session.
 * 
 * <>
 * 
 * </p>
 * 
 * @author 	John Berg 159014260
 * @author 	<Add your name here EastWood>
 * @since 	18/10/2017
 * @version 19/10/2017
 */
public class Game
{
	//=========================================================================
	//Fields.
	private int rows = 8;
	private int columns = 8;
	private Color colour1 = Color.GRAY;
	private Color colour2 = Color.GRAY;
	
	/**
	 * The {@link GameBoard} of <code>this</code> game.
	 */
	private final GameBoard board;
	/**
	 * 
	 */
	private GameViewControl player1;
	/**
	 * 
	 */
	private GameViewControl player2;
	//=========================================================================
	//Constructors.
	/**
	 * 
	 * @param board The {@link GameBoard} for <code>this</code> game.
	 * @throws NullPointerException If <code>board</code> is
	 * 			<code>null</code>.
	 */
	public Game(final GameBoard board)
			throws NullPointerException
	{
		
		
		JFrame mainFrame = new JFrame();	
		JPanel visualBoard = new JPanel();
		
		// Create main frame to hold the board
		mainFrame.setLayout(new BorderLayout());
		((JPanel)mainFrame.getContentPane())
		.setBorder(new EmptyBorder(0, 0, 0, 0));
		mainFrame.setSize(800, 800);
		
		visualBoard.setLayout(new GridLayout(rows,columns));
		//Created mainframe and added the board container to it
		mainFrame.add(visualBoard);
		
	
		Color tileColour = null;
		
		for (int i = 0; i < rows; i++){ 
			//alternate the colour of the individual tile for each row
			if ((i%2) == 0){
				tileColour = colour1; 
			}else{
				tileColour = colour2;
			}
			
			for (int j = 0; j < columns; j++){
				
				JPanel tile = new JPanel();	//Create individual tile
				tile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				tile.setBackground(tileColour);	//Add colour to the tile
				
				
				if(tileColour.equals(colour1)){
					//alternate the colour of the individual tiles for each column
					tileColour = colour2;
				}else{
					tileColour = colour1;
				}
				
				//add tile to the board
				visualBoard.add(tile);
				
			}
		
		}
		mainFrame.setVisible(true);
		
		if(board == null)
			throw new NullPointerException();
		this.board = board;
	} //Game(GameBoard)
	//=========================================================================
	//Methods.
	/**
	 * 
	 * <p>
	 * <b>For internal use only!</b>
	 * </p>
	 * 
	 * @param control The {@link GameViewControl} which current turn it is.
	 */
	private void turn(final GameViewControl control)
	{
		if(!board.legalMoves(control.piece).isEmpty())
		{
			//notify controller about turn (TBA).
			while(!control.ready());
			board.put(control.getPosition(), control.piece);
		} //if
	}
	/**
	 * Begin playing the game.
	 * 
	 * <p>
	 * Prior to playing the game, exactly two {@link GameViewControl} objects
	 * must have been created from <code>this</code> game.
	 * </p>
	 * 
	 * @see #getControl()
	 */
	public void play()
	{
		//Game loop.
		while(!board.isEnd()) 
		{
			turn(player1);
			turn(player2);
		}
	} //play()
	/**
	 * 
	 * <p>
	 * This method must be called twice before invoking the {@link #play()}
	 * method.
	 * </p>
	 * 
	 * @return A {@link GameViewControl} object which correspond to
	 * 			<code>this</code> game.
	 * @throws IllegalStateException If this method is called more than two
	 * 			times.
	 * @see GameViewControl
	 */
	public final  GameViewControl getControl()
			throws IllegalStateException
	{
		if(player1 == null)
			return player1 = new GameViewControl(getBoard(), Piece.PIECE_A);
		if(player2 == null)
			return player2 = new GameViewControl(getBoard(), Piece.PIECE_B);
		throw new IllegalStateException();
	} //getControl()
	/**
	 * 
	 * 
	 * @return A {@link BoardView} object of the {@link GameBoard} for
	 * 			<code>this</code> game.
	 */
	public final BoardView getBoard()
	{
		return board;
	} //getBoard()
	//=========================================================================
	//Overidden methods.
	/**
	 * 
	 */
	@Override
	public final boolean equals(Object o)
	{
		return false;
	} //equals(Object)
	@Override
	public final int hashCode()
	{
		return 0;
	} //hashCode()
	/**
	 * 
	 */
	@Override
	public final String toString()
	{
		return "";
	} //toString()
} //Game