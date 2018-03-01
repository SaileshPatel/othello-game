package com.othellog4.game.board;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The GameBoard is where actions are completed. 
 * @author 	Charlie Sims
 * @since 	23/10/2017
 * @version 01/03/2017
 */
public final class GameBoard implements BoardView, Cloneable, Serializable {
	private Piece[][] grid;
	/**
	 * The {@link Set} of the {@link FlipEvent} objects which were flipped
	 * by placing a {@link Piece} object.
	 * 
	 * <p>
	 * Is <code>null</code> if there has been no {@link Piece} objects placed.
	 * </p>
	 * 
	 * @see FlipEvent
	 */
	private Set<FlipEvent[]> flipEvents;
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
		flipEvents = null;

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
	 * Check if a specified location composed of two <code>int</code> values,
	 * is a valid position on <code>this</code> {@code GameBoard}.
	 * 
	 * @param col The column index.
	 * @param row The row index.
	 * @return <code>true</code> if and only if <code>col</code> and
	 * 			<code>row</code> are between the range of <code>0</code> and
	 * 			the value returned from {@link GameBoard#size()}, otherwise,
	 * 			returns <code>false</code>.
	 */
	private boolean onBoard(final int col, final int row)
	{
		return 0 <= col && col < size() && 0 <= row && row < size();
	}
	/**
	 * Write a {@link Piece} object to a specified location of
	 * <code>this</code> {@code GameBoard}.
	 * 
	 * @param col The column index.
	 * @param row The row index.
	 * @param piece The {@link Piece} object to write to the location.
	 */
	private void write(
			final int col,
			final int row,
			final Piece piece)
	{
		grid[col][row] = piece;
	}
	/**
	 * Read the {@link Piece} object of a specified location of
	 * <code>this</code> {@code GameBoard}.
	 * 
	 * @param col The column index.
	 * @param row The row index.
	 * @return The {@link Piece} object at the specified location.
	 */
	private Piece read(
			final int col,
			final int row)
	{
		return grid[col][row];
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
	public void put(Position position, Piece piece)
			throws InvalidMoveException{
		if(!legalMoves(piece).contains(position))
			throw new InvalidMoveException(position, piece);
		flip(position.col,position.row, piece);
		write(position.col, position.row, piece);
	}

	@Override
	public boolean isEnd() {
		// TODO Auto-generated method stub
		return legalMoves(Piece.PIECE_A).isEmpty()
				&& legalMoves(Piece.PIECE_B).isEmpty();
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
	/**
	 * Get the {@link FlipEvent} objects from the previous move which was made
	 * on <code>this</code> {@code GameBoard}.
	 * 
	 * @return The {@link Set} of a sequence of {@link FlipEvents}.
	 */
	@Override
	public final Set<FlipEvent[]> flips()
	{
		return Collections.unmodifiableSet(
				flipEvents == null
						? new HashSet<>()
						:flipEvents);
	}
	@Override
	public Set<Position> legalMoves(Piece piece) {
		Set<Position> validMoves = new HashSet<Position>();
		for (int x = 0; x < grid.length; x++)
			for (int y = 0; y < grid.length; y++)
				if(read(x, y) == null && locationValid(x,y,piece))
					validMoves.add(Position.at(x,y));
		return validMoves;
	}

	private boolean locationValid(int x, int y, Piece player){
		return countFlips(x, y, player) != 0;
	}

	@Override
	public Optional<Piece> view(Position pos) {
		// TODO Auto-generated method stub
		
		return Optional.ofNullable(read(pos.col, pos.row));
	}

	public void flip(int x, int y, final Piece piece){
		//left up right down
		flipEvents = new HashSet<>();
		flipLine(x,y,0,-1, piece);
		flipLine(x,y,1,-1, piece);
		flipLine(x,y,1,0, piece);
		flipLine(x,y,1,1, piece);
		flipLine(x,y,0,1, piece);
		flipLine(x,y,-1,1, piece);
		flipLine(x,y,-1,0, piece);
		flipLine(x,y,-1,-1, piece);
	}

	/**
	 * 
	 * @param x position in the array
	 * @param y position in the array
	 * @param up 1 for up 0 for no chance and -1 for down
	 * @param right1 for up 0 for no chance and -1 for down
	 */
	private void flipLine(int x, int y, int up, int right, final Piece piece){
		
		if(onBoard(x, y))
		{
			final FlipEvent[] flips =
					new FlipEvent[flipTest(x, y, up, right, piece)];
			for(int i = 1;i <= flips.length; ++i)
			{
				final Position pos = Position.at(x + right * i, y + up * i);
				flips[i - 1] = new FlipEvent(read(pos.col, pos.row), pos);
				write(pos.col, pos.row, piece);
			}
			flipEvents.add(flips);
		}
	}
	private int flipTest(int x, int y, int up, int right, Piece type){
		
		if(onBoard(x, y) && read(x, y) != null)
			return 0;
		int flips = 0;
		for(int i = 1;; ++i)
		{
			final int xPos = x + right * i;
			final int yPos = y + up * i;
			if(!onBoard(xPos, yPos))
				return 0;
			final Piece piece = read(xPos, yPos);
			if(type.flip() == piece)
				++flips;
			else if(type == piece)
				break;
			else if(piece == null)
				return 0;
		}
		return flips;
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
	 * the clone, or vice versa.
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
