package com.othellog4.game.board;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The GameBoard is where actions are completed. 
 * @author 	Charlie Sims
 * @since 	23/10/2017
 * @version 20/11/2017
 */
public class GameBoard implements BoardView{
	private Piece[][] grid;

	public GameBoard(int size, int corners){
		grid = new Piece[size][size];
		grid[size/2][size/2] = Piece.PIECE_A;
		grid[size/2][size/2-1] = Piece.PIECE_B;
		grid[size/2-1][size/2-1] = Piece.PIECE_A;
		grid[size/2-1][size/2] = Piece.PIECE_B;
		/*for (int i = 0; i < corners; i++){
			for (int j = 0; j < corners; j++){
				grid[i][j] = Piece.PIECE_NULL; 
			}
		}
		for (int i = 0; i < corners; i++){
			for (int j = 0; j < corners; j++){
				grid[size - 1 - i][j] = Piece.PIECE_NULL; 
			}
		}
		for (int i = 0; i < corners; i++){
			for (int j = 0; j < corners; j++){
				grid[i][size - 1 - j] = Piece.PIECE_NULL; 
			}
		}
		for (int i = 0; i < corners; i++){
			for (int j = 0; j < corners; j++){
				grid[size - 1 - i][size - 1 - j] = Piece.PIECE_NULL; 
			}
		}*/

	}

	public void put(Position position, Piece piece) throws InvalidMoveException{
		
		Set<Position> validMoves = legalMoves(piece);
		if(!validMoves.contains(position)){
			throw new InvalidMoveException(position, piece);
		}
		
		
		grid[position.row][position.col] = piece;
		flip(position.row,position.col);
	}

	@Override
	public boolean isEnd() {
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
	public final int count(final Piece piece)
	{
		int count = 0;
		for(int i = 0; i < size(); ++i)
			for(int j = 0; j < size(); ++j)
				if(view(Position.at(i, j)).orElse(null) == piece)
						++count;
		return count;
	}
	@Override
	public int countFlips(int x, int y, Piece player) {
		int temp = 0;
		
		temp += flipTest(x,y,0,-1,player);
		temp += flipTest(x,y,1,-1,player);
		temp += flipTest(x,y,1,0,player);
		temp += flipTest(x,y,1,1,player);
		temp += flipTest(x,y,0,1,player);
		temp += flipTest(x,y,-1,1,player);
		temp += flipTest(x,y,-1,0,player);
		temp += flipTest(x,y,-1,-1,player);
		
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
		

			temp += flipTest(x,y,0,-1,player);
			temp += flipTest(x,y,1,-1,player);
			temp += flipTest(x,y,1,0,player);
			temp += flipTest(x,y,1,1,player);
			temp += flipTest(x,y,0,1,player);
			temp += flipTest(x,y,-1,1,player);
			temp += flipTest(x,y,-1,0,player);
			temp += flipTest(x,y,-1,-1,player);
			
		if (temp == 0){
			return false;
		}
		return true;
	}

	@Override
	public Optional<Piece> view(Position pos) {
		// TODO Auto-generated method stub
		
		Optional<Piece> temp = Optional.ofNullable(grid[pos.row][pos.col]);
		return temp;
		
	}

	public void flip(int x, int y){
		//left up right down

			flipLine(x,y,0,-1);
			flipLine(x,y,1,-1);
			flipLine(x,y,1,0);
			flipLine(x,y,1,1);
			flipLine(x,y,0,1);
			flipLine(x,y,-1,1);
			flipLine(x,y,-1,0);
			flipLine(x,y,-1,-1);
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
		} catch (IndexOutOfBoundsException e){

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
		} catch (IndexOutOfBoundsException e){

		}
		return 0;
	}
	@Override
	public final BoardView getView()
	{
		return new ProxyGameBoard(this);
	}
}
