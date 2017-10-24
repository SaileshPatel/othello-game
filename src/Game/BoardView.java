package Game;

import java.util.Optional;
import java.util.Set;

/**
 * This is the BoardView. This will eventually be used to control the board. 
 * 
 *
 */
public interface BoardView {
	public Boolean isEnd();
	
	public int size();
	
	public int countFlips();
	
	public Set<Position> legalMoves(Piece piece);
	
	public Piece view(Position boardPosition);
}
