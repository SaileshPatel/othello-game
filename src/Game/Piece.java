package Game;

public enum Piece {
	
	PIECE_A{
		@Override
		public Piece flip(){
			return PIECE_B;
		}
	},
	PIECE_B{
		@Override
		public Piece flip(){
			return PIECE_A;
		}
	};
	
	private Piece(){}
	
	public abstract Piece flip();
}
