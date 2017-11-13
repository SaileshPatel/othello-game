package Game;

import java.util.HashSet;
import java.util.Set;

/**
 * The GameBoard is where actions are completed. 
 * @author Charlie Sims
 * @since 23/10/2017
 */
public class GameBoard implements BoardView{
	private Piece[][] grid;

	public GameBoard(int size){
		grid = new Piece[size][size];
		grid[size/2][size/2] = Piece.PIECE_A;
		grid[size/2][size/2-1] = Piece.PIECE_B;
		grid[size/2-1][size/2-1] = Piece.PIECE_A;
		grid[size/2-1][size/2] = Piece.PIECE_B;

	}

	public void put(Position position, Piece piece){
		grid[position.row][position.col] = piece;
		flip(position.row,position.col);
	}

	@Override
	public Boolean isEnd() {
		// TODO Auto-generated method stub
		for (int i = 0;i < grid.length;i++){
			for (int j = 0;j < grid.length;j++){
				if(grid[i][j] == null){
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return grid.length;
	}

	@Override
	public int countFlips(int x, int y, Piece player) {
		int temp = 0;
		temp += flipTest(x, y, 0, 1, player);
		temp += flipTest(x, y, 1, 1, player);
		temp += flipTest(x, y, -1, 1, player);
		temp += flipTest(x, y, 0, -1, player);
		temp += flipTest(x, y, 1, -1, player);
		temp += flipTest(x, y, -1, -1, player);
		temp += flipTest(x, y, 1, 0, player);
		temp += flipTest(x, y, -1, 0, player);
		return temp;
	}

	@Override
	public Set<Position> legalMoves(Piece piece) {
		Set<Position> validMoves = new HashSet<Position>();
		//validMoves.add(new Position(1,2));
		try {
			int x;
			int y;
			Piece temp = piece;

			for (int i = 0;i < grid.length; i++){
				x = i;
				for (int j = 0;j < grid.length; j++){
					y = j;
					if(grid[x][y] == null){
						if(locationValid(x,y,temp)){
							validMoves.add(new Position(x,y));
						}
					}


				}
			}
			return validMoves;

		} catch (Exception e) {
			e.getMessage();
		} finally {

		}

		return null;
	}

	private boolean locationValid(int x, int y, Piece player){
		int temp = 0;
		temp += flipTest(x, y, 0, 1, player);
		temp += flipTest(x, y, 1, 1, player);
		temp += flipTest(x, y, -1, 1, player);
		temp += flipTest(x, y, 0, -1, player);
		temp += flipTest(x, y, 1, -1, player);
		temp += flipTest(x, y, -1, -1, player);
		temp += flipTest(x, y, 1, 0, player);
		temp += flipTest(x, y, -1, 0, player);
		if (temp == 0){
			return false;
		}
		return true;
	}

	@Override
	public Piece view(Position boardPosition) {
		// TODO Auto-generated method stub
		Piece temp;
		temp = grid[boardPosition.row][boardPosition.col];
		return temp;
	}

	public void flip(int x, int y){
		
		flipLine(x,y,0,1);
		flipLine(x,y,1,1);
		flipLine(x,y,-1,1);
		flipLine(x,y,0,-1);
		flipLine(x,y,1,-1);
		flipLine(x,y,-1,-1);
		flipLine(x,y,1,0);
		flipLine(x,y,-1,0);
	}
	
	/**
	 * 
	 * @param x position in the array
	 * @param y position in the array
	 * @param up 1 for up 0 for no chance and -1 for down
	 * @param right1 for up 0 for no chance and -1 for down
	 */
	private void flipLine(int x, int y, int up, int right){
		int i = 1;
		Piece type = grid[x][y];
		Piece temp;
		try {
			while (true){
				temp = grid[x + (up * i)][y + (right * i)];
				if(temp != type.flip()){
					if(temp == type){
						for (int j = 1;j < i;j++){
							grid[x + (up * j)][y + (right * j)] = type;
						}
					}
					break;
				}
				i++;
			}
		} catch (Exception e){
			
		} finally {
			
		}
	}
	
	private int flipTest(int x, int y, int up, int right, Piece type){
		int i = 1;
		Piece temp;
		try {
			while (true){
				temp = grid[x + (up * i)][y + (right * i)];
				if(temp != type.flip()){
					if(temp == type){
						return i-1;
					}
					break;
				}
				i++;
			}
		} catch (Exception e){
			
		} finally {
			
		}
		return 0;
	}
	
}
