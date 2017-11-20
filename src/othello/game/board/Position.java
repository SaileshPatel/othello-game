package othello.game.board;

/**
 * The {@code Position} class with the responsibility of representing a
 * position of a grid-based board.
 * 
 * <p>
 * The {@code Position} class is immutable, after initialisation, it is no
 * longer possible to set or modify the state of the {@code Position} object.
 * Due to the immutability, the {@code Position} class provides no setter
 * methods, nor getter methods; as all the fields within the class are of
 * primitive types and <code>final</code>, all fields can be made
 * <code>public</code>. The immutablility also guarantees that a
 * {@code Position} object is thread-safe.
 * </p>
 * 
 * <p>
 * The fields available are:
 * <ul>
 * 		<li>{@link #col}</li>
 * 		<li>{@link #row}</li>
 * </ul>
 * </p>
 * 
 * <p>
 * The {@code Position} overrides:
 * <ul>
 * 		<li>{@link #equals(Object)}</li>
 * 		<li>{@link #hashCode()}</li>
 * 		<li>{@link #toString()}</li>
 * </ul>
 * </p>
 * 
 * <p>
 * The available constructor is {@link #Position(int, int)}, but initialisation
 * using the {@link #at(int, int)} method is preferred.
 * </p>
 * 
 * @author 	159014260 John Berg
 * @since 	16/10/2017
 * @version 27/10/2017
 */
public final class Position {
	//=========================================================================
	//Fields
	/**
	 * The <code>int</code> representation of the row index of
	 * <code>this</code> {@code Position}.
	 */
	public final int row;
	/**
	 * The <code>int</code> representation of the column index of
	 * <code>this</code> {@code Position}.
	 */
	public final int col;
	//=========================================================================
	//Constructors
	/**
	 * Initialise a new {@code Position} object.
	 * 
	 * <p>
	 * To create an a {@code Position} two <code>int</code> values
	 * </p>
	 * 
	 * <p>
	 * This constructor is deprecated and should be avoided due to the order of
	 * the parameters being row and column, rather than column and row.
	 * </p>
	 * 
	 * <p>
	 * This constructor exists to support code which may still rely on this
	 * constructor.
	 * </p>
	 * 
	 * <p>
	 * Using {@link #at(int, int)} is recommended as it allows for
	 * initialisation by specifying the column, and then the row.
	 * </p>
	 * 
	 * @param row The row index of the {@code Position}.
	 * @param col The column index of the {@code Position}.
	 * @see #at(int, int)
	 */
	@Deprecated
	public Position(int row, int col){
		this.row = row;
		this.col = col;
	}
	//=========================================================================
	//Overidden methods
	/**
	 * Compare <code>this</code> {@code Position} against another
	 * {@code Object}, to for equality.
	 * 
	 * @param o The object to compare <code>this</code> with.
	 * @return <code>true</code> if <code>o</code> is an instance of
	 * 			{@code Position} and, {@link #col} and {@link #row}
	 * 			are equal in both <code>this</code> and <code>o</code>
	 * 			respectively; otherwise returns <code>false</code>.
	 */
	@Override
	public final boolean equals(final Object o)
	{
		if(o instanceof Position)
		{
			final Position pos = (Position) o;
			return col == pos.col && row == pos.row;
		} //if
		return false;
	}
	/**
	 * Get hash code of <code>this</code> {@code Position}.
	 * 
	 * The hash code is computed by performing an xor on the {@link #col} and
	 * the {@link #row} of <code>this</code> {@code Position}. 
	 * 
	 * @return The hash value of <code>this</code>
	 */
	@Override
	public final int hashCode()
	{
		return col ^ row;
	}
	/**
	 * Get the {@link String} representation of <code>this</code>
	 * {@code Position}.
	 * 
	 * <p>
	 * The produced format of the {@link String} should be:
	 * <p>
	 * 
	 * <p>
	 * "({@link #col}, {@link #row})"
	 * </p>
	 * 
	 * @return The {@link String} representation of <code>this</code>.
	 */
	@Override
	public final String toString()
	{
		return "("
				+ col
				+ ", "
				+ row
				+ ")";
	}
	//=========================================================================
	//Static methods
	/**
	 * Initialise a new {@code Position} which represents a position at a given
	 * column and row.
	 * 
	 * @param col The column index of the {@code Position}.
	 * @param row The row index of the {@code Position}.
	 * @return A {@code Position} object which represents the position of
	 * 			<code>col</code> and <code>row</code>.
	 * @see #Position(int, int)
	 */
	public static final Position at(
			final int col,
			final int row)
	{
		return new Position(row, col);
	}
} //Position