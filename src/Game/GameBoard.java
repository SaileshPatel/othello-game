package Game;

import java.util.Optional;
import java.util.Set;

/**
 * The GameBoard is where actions are completed. 
 * @author saileshpatel
 *
 */
public class GameBoard implements BoardView{
	private Piece[][] grid;

	public GameBoard(int size){
		grid = new Piece[size][size];
	}

	public void put(Position position, Piece piece){
		grid[position.row][position.col] = piece;
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

	public void flip(int x, int y){
		/*
		Using this space for reminders:
		Check that no index out of bounds occur, you have the try catch but better to be sure now
		Might be a way to use this for the validation
		Not going to validation until this works 100% since code reuse may be needed
		*/
		Piece temp = grid[x][y];
		int tempRepeats; //used to work out how many times in a diagonal to go
		try{
			//UP
			for (int i = 0;i < y; i++){
				//if piece is equal/null
				if(grid[x][y-i-1] == null || grid[x][y-i-1] == temp){
					//if piece is equal
					if (grid[x][y-i-1] == temp){
						//if it has already passed over the other color
						if(i > 0){
							//flip pieces in the middle
							for (int j = 0;j < i;j++){
								grid[x][y-(j+1)].flip();
							}
						}
					}
					break;
				}
			}
			//DOWN
			for (int i = 0;i < grid.length - y - 1; i++){
				//if piece is equal/null
				if(grid[x][y+i+1] == null || grid[x][y+i+1] == temp){
					//if piece is equal
					if (grid[x][y+i+1] == temp){
						//if it has already passed over the other color
						if(i > 0){
							//flip pieces in the middle
							for (int j = 0;j < i;j++){
								grid[x][y+(j+1)].flip();
							}
						}
					}
					break;
				}
			}

			//LEFT
			for (int i = 0;i < x; i++){
				//if piece is equal/null
				if(grid[x-(i+1)][y] == null || grid[x-(i+1)][y] == temp){
					//if piece is equal
					if (grid[x-(i+1)][y] == temp){
						//if it has already passed over the other color
						if(i > 0){
							//flip pieces in the middle
							for (int j = 0;j < i;j++){
								grid[x-(i+1)][y].flip();
							}
						}
					}
					break;
				}
			}

			//RIGHT
			for (int i = 0;i < grid.length - x - 1; i++){
				//if piece is equal/null
				if(grid[x+(i+1)][y] == null || grid[x+(i+1)][y] == temp){
					//if piece is equal
					if (grid[x+(i+1)][y] == temp){
						//if it has already passed over the other color
						if(i > 0){
							//flip pieces in the middle
							for (int j = 0;j < i;j++){
								grid[x+(i+1)][y].flip();
							}
						}
					}
					break;
				}
			}

			//Top left
			if(x < y){
				tempRepeats = x;
			} else {
				tempRepeats = y;
			}
			for (int i = 0;i < tempRepeats; i++){
				//if piece is equal/null
				if(grid[x-(i+1)][y-(i+1)] == null || grid[x-(i+1)][y-(i+1)] == temp){
					//if piece is equal
					if (grid[x-(i+1)][y-(i+1)] == temp){
						//if it has already passed over the other color
						if(i > 0){
							//flip pieces in the middle
							for (int j = 0;j < i;j++){
								grid[x-(i+1)][y-(i+1)].flip();
							}
						}
					}
					break;
				}
			}
			
			//top right
			if(grid.length - x < y){
				tempRepeats = grid.length - x - 1;
			} else {
				tempRepeats = y;
			}
			for (int i = 0;i < tempRepeats; i++){
				//if piece is equal/null
				if(grid[x+(i+1)][y-(i+1)] == null || grid[x+(i+1)][y-(i+1)] == temp){
					//if piece is equal
					if (grid[x+(i+1)][y-(i+1)] == temp){
						//if it has already passed over the other color
						if(i > 0){
							//flip pieces in the middle
							for (int j = 0;j < i;j++){
								grid[x+(i+1)][y-(i+1)].flip();
							}
						}
					}
					break;
				}
			}
			

			//down Right
			if(grid.length - x < grid.length - y){
				tempRepeats = grid.length - x - 1;
			} else {
				tempRepeats = grid.length - y - 1;
			}
			for (int i = 0;i < tempRepeats; i++){
				//if piece is equal/null
				if(grid[x+(i+1)][y+(i+1)] == null || grid[x+(i+1)][y+(i+1)] == temp){
					//if piece is equal
					if (grid[x+(i+1)][y+(i+1)] == temp){
						//if it has already passed over the other color
						if(i > 0){
							//flip pieces in the middle
							for (int j = 0;j < i;j++){
								grid[x+(i+1)][y+(i+1)].flip();
							}
						}
					}
					break;
				}
			}
			
			//down Left
			if(x < grid.length - y){
				tempRepeats = x;
			} else {
				tempRepeats = grid.length - y - 1;
			}
			for (int i = 0;i < tempRepeats; i++){
				//if piece is equal/null
				if(grid[x-(i+1)][y+(i+1)] == null || grid[x-(i+1)][y+(i+1)] == temp){
					//if piece is equal
					if (grid[x-(i+1)][y+(i+1)] == temp){
						//if it has already passed over the other color
						if(i > 0){
							//flip pieces in the middle
							for (int j = 0;j < i;j++){
								grid[x-(i+1)][y+(i+1)].flip();
							}
						}
					}
					break;
				}
			}

		}	catch(Exception e){
			System.out.println(e.getMessage());
		}	finally {

		}
	}
}
