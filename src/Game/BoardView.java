package Game;

import java.util.Optional;
import java.util.Set;

public interface BoardView {
	public Boolean isEnd();
	
	public int size();
	
	public int countFlips();
	
	public Set<Position> legalMoves(Piece piece);
	
	public Optional<Piece> view(Position boardPosition);
}
