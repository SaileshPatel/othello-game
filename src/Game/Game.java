package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * The {@code Game} is a class which models a game of Othello, which has the
 * responsibility of managing and maintaining the flow of the game session.
 * 
 * 
 * @author 	John Berg 
 * @author 	Eastwood
 * @since 	18/10/2017
 * @version 19/10/2017
 */
public class Game
{
	//=========================================================================
	//Fields.
	private int rows = 8;
	private int columns = 8;
	private Color colour1 = new Color(0,150,0);
	private Color colour2 = new Color(0,150,0);
	private static final Color PLAYER1_TOKEN_COLOR = Color.BLACK;
	private static final Color PLAYER2_TOKEN_COLOR = Color.WHITE;
	private JPanel boardTiles[][] = new JPanel[rows][columns];
	 
	
	
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
	/**
	 * 
	 */
	private Player p1;
	/**
	 * 
	 */
	private Player p2;
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
				boardTiles[i][j] = tile;
				
			}
		
		}
		mainFrame.setVisible(true);
		mainFrame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e)
			{
			}
			@Override
			public void windowIconified(WindowEvent e)
			{
			}
			@Override
			public void windowDeiconified(WindowEvent e)
			{
			}
			@Override
			public void windowDeactivated(WindowEvent e)
			{
			}
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
			@Override
			public void windowClosed(WindowEvent e)
			{
			}
			@Override
			public void windowActivated(WindowEvent e)
			{
			}
		});
		if(board == null)
			throw new NullPointerException();
		this.board = board;
		
		updateGUI(board);
	} //Game(GameBoard)
	//=========================================================================
	//Methods.
	
	/**
	 * Update the GUI to show the current state of a gameboard object
	 * @param board The board to render
	 * 
	 */
	private void updateGUI (BoardView board) {
		int size = board.size();
		
		for(int x=0; x<size;x++) {
			for(int y=0; y<size;y++) {
				Piece piece = board.view(new Position(x,y));
				
				if( piece == null ) {
					//Set tile colour to background colour
					boardTiles[x][y].setBackground(colour1);
				} else {
					//Probably a more efficient way to do this but we don't know how to use enums
				switch (piece) {
					case PIECE_A:
						boardTiles[x][y].setBackground(PLAYER1_TOKEN_COLOR);
						break;	
					case PIECE_B:
						boardTiles[x][y].setBackground(PLAYER2_TOKEN_COLOR);
						break;
					}
				}
			}
		}	
	}
	
	
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
		//if(!board.legalMoves(control.piece).isEmpty())
		{
			control.notifyTurn();
			while(!control.ready());
			board.put(control.getPosition(), control.piece);
		} //if
		
		updateGUI(board);
	} //turn()
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
			updateGUI(board);
		} //while
		
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