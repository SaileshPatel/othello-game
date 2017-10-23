package Game;

public class AI implements Participant{
    private Tree<Data> tree;
    
	public AI(){
	    Node<Data> root = new Node<Data>(new Data(board, whiteTurn)); // we need the board position and who's turn it is
		tree = new Tree<Data>(root);
	}

	@Override
	public boolean ready() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		return null;
	}
}
