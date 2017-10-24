package Game;

public class Position {
	public static int row;
	public static int col;
	
	/**
	 * The constructor. The values of the row and column are set here
	 * @param row the row number
	 * @param col the column number
	 */
	public Position(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	
	/**
	 * A method to set the row
	 * @param row the row number
	 */
	public void setRow (int row){
		this.row = row;
	}
	
	/**
	 * A method to set the column 
	 * @param col the column number 
	 */
	public void setCol (int col){
		this.col = col;
	}
	
}
