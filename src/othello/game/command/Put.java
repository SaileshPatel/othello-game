package othello.game.command;

import othello.game.Game;
import othello.game.board.Position;

/**
 * 
 * @author 	159014260 John Berg
 * @since 	20/11/2017
 * @version 20/11/2017
 */
public class Put extends GameCommand
{
	/**
	 * 
	 */
	private final Position position;
	/**
	 * 
	 * @param source
	 * @param x
	 * @param y
	 */
	public Put(final Object source, int x, int y)
	{
		super(source);
		position = Position.at(x, y);
	}
	/**
	 * 
	 */
	@Override
	public void execute(final Game game)
	{
		game.put(position);
	}

}