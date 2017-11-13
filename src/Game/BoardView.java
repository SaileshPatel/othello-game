package Game;

import java.util.Optional;
import java.util.Set;

/**
 * This is the BoardView. This will eventually be used to control the board. 
 * @author John Berg
 * @author James Shorthouse
 * @since 23/10/2017
 *
 */
public interface BoardView {
	/**
	 * This method determines if the game has ended
	 * @return true if ended, otherwise false
	 */
	public Boolean isEnd();
	
	/**
	 * This method returns the size of the board
	 * @return the size of the board
	 */
	public int size();
	
	/**
	 * This method counts the number of flips needed
	 * @return the number of flips needed
	 */
	public int countFlips(int x, int y, Piece player);
	
	/**
	 * This method determines all legal moves a player can take
	 * @param piece the Piece to determine legal moves from
	 * @return a set of legal Positions to move to
	 */
	public Set<Position> legalMoves(Piece piece);
	
	/**
	 * This method allows the board to view the position of a Piece
	 * @param boardPosition 
	 * @return a piece
	 */
	public Piece view(Position boardPosition);
}
