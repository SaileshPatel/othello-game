package othello.game.board;

public class InvalidMoveException extends Exception {
	
	//private Piece type;
	private Position cord;
	
	public InvalidMoveException(Position cord ,Piece type){
		super();
		//this.type = type;
		this.cord = cord;
	}
	
	@Override
	public String toString(){
		String temp;
		temp = "The move " + cord.toString() + " is not a valid move, please try again";
		return temp;
	}
}
