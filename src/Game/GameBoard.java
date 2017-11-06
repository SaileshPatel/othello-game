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
	public int countFlips() {
		//In the nicest way WTF does this do?
		// TODO Auto-generated method stub
		return 0;
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

		Piece temp = player;
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
							return true;
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
							return true;
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
							return true;
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
							return true;
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
							return true;
						}
					}
					break;
				}
			}

			//top right
			if(grid.length - x < y){
				tempRepeats = grid.length - x - 1;
			} else {
				tempRepeats = y - 1;
			}
			for (int i = 0;i < tempRepeats; i++){
				//if piece is equal/null
				if(grid[x+(i+1)][y-(i+1)] == null || grid[x+(i+1)][y-(i+1)] == temp){
					//if piece is equal
					if (grid[x+(i+1)][y-(i+1)] == temp){
						//if it has already passed over the other color
						if(i > 0){
							//flip pieces in the middle
							return true;
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
							return true;
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
							return true;
						}
					}
					break;
				}
			}

		}	catch(Exception e){
			System.out.println(e.toString());
		}	finally {

		}
		return false;
	}

	@Override
	public Piece view(Position boardPosition) {
		// TODO Auto-generated method stub
		Piece temp;
		temp = grid[boardPosition.row][boardPosition.col];
		return temp;
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
								grid[x][y-(j+1)] = grid[x][y-(j+1)].flip();
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
								grid[x][y+(j+1)] = grid[x][y+(j+1)].flip();
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
								grid[x-(j+1)][y] = grid[x-(j+1)][y].flip();
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
								grid[x+(j+1)][y] = grid[x+(j+1)][y].flip();
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
								grid[x-(j+1)][y-(j+1)] = grid[x-(j+1)][y-(j+1)].flip();
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
				tempRepeats = y - 1;
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
								grid[x+(j+1)][y-(j+1)] = grid[x+(j+1)][y-(j+1)].flip();
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
								grid[x+(j+1)][y+(j+1)] = grid[x+(j+1)][y+(j+1)].flip();
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
								grid[x-(j+1)][y+(j+1)] = grid[x-(j+1)][y+(j+1)].flip();
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
