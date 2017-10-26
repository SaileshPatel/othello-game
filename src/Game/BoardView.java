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
	public Boolean isEnd();
	
	public int size();
	
	public int countFlips();
	
	public Set<Position> legalMoves(Piece piece);
	
	public Piece view(Position boardPosition);
}
