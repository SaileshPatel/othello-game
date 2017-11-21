package othello.game.board;

import java.util.Set;

/**
 * 
 * @author	John
 * @since	20/11/2017
 * @version 20/11/2017
 */
public final class ProxyGameBoard implements BoardView
{
	private final BoardView board;
	public ProxyGameBoard(final BoardView board)
	{
		this.board = board;
	}

	@Override
	public Boolean isEnd()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countFlips(int x, int y, Piece player)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<Position> legalMoves(Piece piece)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Piece view(Position boardPosition)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardView getView()
	{
		// TODO Auto-generated method stub
		return null;
	}
}