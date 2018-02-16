package com.othellog4.game.board;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The GameBoard is where actions are completed. 
 * @author 	Charlie Sims
 * @since 	23/10/2017
 * @version 20/11/2017
 */
public final class GameBoard implements BoardView, Cloneable, Serializable {
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
	public GameBoard(Piece[][] prebuilt){
		grid = new Piece[prebuilt.length][];
		for(int i = 0; i < prebuilt.length; ++i)
			grid[i] = prebuilt[i].clone();;
	}
	/**
	 * Copy constructor.
	 * 
	 * 
	 * 
	 * @param board
	 */
	public GameBoard(final GameBoard board)
	{
		this(board.grid);
	}
	/**
	 * Check if <code>this</code> {@code GameBoard} is in a draw.
	 * 
	 * <p>
	 * A draw occurs when there are the same amount of {@code Piece#PIECE_A}
	 * objects as there are {@link Piece#PIECE_B} objects.
	 * </p>
	 * 
	 * @return <code>true</code> If the state of <code>this</code>
	 * 			{@code GameBoard} is a draw.
	 */
	@Override
	public final boolean isDraw()
	{
		return winning() == null;
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
		return legalMoves(Piece.PIECE_A).isEmpty() && legalMoves(Piece.PIECE_B).isEmpty();
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
	/**
	 * Get the {@link Piece} object which is winning, based on the current
	 * state of <code>this</code> {@code GameBoard}.
	 * 
	 * @return The {@link Piece} object which has the most instances on
	 * 			<code>this</code> {@code GameBoard}. Returns <code>null</code>
	 * 			if the state of the board is a draw.
	 */
	@Override
	public Piece winning(){
		int a = count(Piece.PIECE_A);
		int b = count(Piece.PIECE_B);
		if(a == b)
			return null;
		if(a < b)
			return Piece.PIECE_B;
		return Piece.PIECE_A;
	}
	/**
	 * Get the {@link Piece} object which is losing, based on the current
	 * state of <code>this</code> {@code GameBoard}.
	 * 
	 * @return The {@link Piece} object which has the least instances on
	 * 			<code>this</code> {@code GameBoard}. Returns <code>null</code>
	 * 			if the state of the board is a draw.
	 */
	@Override
	public final Piece losing()
	{
		final Piece piece = winning().flip();
		return piece == null? null: piece.flip();
	}
	/**
	 * Try placing a {@link Piece} object at a specified {@link Position},
	 * without modifying <code>this</code> {@code GameBoard} object.
	 * 
	 * <p>
	 * This method will return a projection of <code>this</code>
	 * {@code GameBoard} object, which represents <code>this</code>
	 * {@code GameBoard} if a {@link Piece} were to be placed at the specified
	 * {@link Position}.
	 * </p>
	 * 
	 * @param pos
	 * @param piece
	 * @return A projection of <code>this</code> {@code GameBoard} which
	 * 			represents the outcome of placing the <code>piece</code> at
	 * 			<code>pos</code>.
	 * @throws InvalidMoveException 
	 */
	@Override
	public final GameBoard tryPut(
			final Position pos,
			final Piece piece)
			throws
			InvalidMoveException
	{
		final GameBoard board = clone();
		board.put(pos, piece);
		return board;
	}
	/**
	 * Clone <code>this</code> {@code GameBoard}.
	 * 
	 * <p>
	 * Clones will have no referential equality with the original
	 * {@code GameBoard}.
	 * </p>
	 * 
	 * <p>
	 * Modifications made to the original {@code GameBoard} will not affect
	 * the clone, ot vice versa.
	 * </p>
	 * 
	 * @return The {@code GameBoard} object which is a clone of
	 * 			<code>this</code> {@code GameBoard}.
	 * 			
	 */
	@Override
	public final GameBoard clone()
	{
		return new GameBoard(this);
	}
}
