package Game;

import java.util.Optional;
import java.util.Set;

public class GameBoard implements BoardView{
	private Piece[][] grid;
	
	public GameBoard(int size){
	}
	
	public void put(Position position, Piece piece){
		
	}

	@Override
	public Boolean isEnd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countFlips() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<Position> legalMoves(Piece piece) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Piece> view(Position boardPosition) {
		// TODO Auto-generated method stub
		return null;
	}
}
