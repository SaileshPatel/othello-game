package Game;

public class Data {
	private int[][] board;
	private boolean whiteTurn;
	
	public Data(int[][] board, boolean whiteTurn) {
		this.board = board;
		this.whiteTurn = whiteTurn;
	}
	
	public int[][] getBoard() {
		return board;
	}
	
	public boolean whiteTurn() {
		return whiteTurn;
	}
}