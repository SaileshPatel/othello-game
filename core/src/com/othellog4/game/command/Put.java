package com.othellog4.game.command;

import com.othellog4.game.Game;
import com.othellog4.game.GameException;
import com.othellog4.game.board.InvalidMoveException;
import com.othellog4.game.board.Position;

/**
 * The {@code Put} class is a subclass of the {@link GameCommand} class, which
 * represents the action of putting something at a position.
 * 
 * @author 	159014260 John Berg
 * @since 	20/11/2017
 * @version 26/02/2017
 * @see GameCommand
 * @see Position
 */
public class Put extends GameCommand
{
	//=========================================================================
	//Static fields.
	/**
	 * The {@link String} containing a message for when <code>null</code> is
	 * being passed as a {@link Game} argument to the {@link #execute(Game)}
	 * method.
	 */
	private static final String NULL_GAME_RECIEVER =
			"The Game to execute put on was null";
	//=========================================================================
	//Fields.
	/**
	 * The {@link Position} object which represents the position to put
	 * something.
	 * 
	 * @see Position
	 */
	private final Position position;
	//=========================================================================
	//Constructors.
	/**
	 * Create a {@code Put} object which represents a source putting something
	 * at a specified position.
	 * 
	 * @param source The issued of {@code Put}.
	 * @param position The {@link Position} object which represents the
	 * 			location.
	 * @throws NullPointerException If <code>source</code> or
	 * 			<code>position</code> is <code>null</code>.
	 */
	public Put(
			final Object source,
			final Position position)
			throws
			NullPointerException
	{
		//May throw a NullPointerException.
		this(source, position.col, position.row);
	}
	/**
	 * Create a {@code Put} object which represents a source putting something
	 * at a specified position.
	 * 
	 * @param source The issuer of {@code Put}.
	 * @param x The column position of the {@code Put}.
	 * @param y The row position of the {@code Put}.
	 * @throws NullPointerException If <code>source</code> is
	 * 			<code>null</code>.
	 */
	public Put(
			final Object source,
			final int x,
			final int y)
			throws
			NullPointerException
	{
		//May throw NullPointerException.
		super(source);
		position = Position.at(x, y);
	}
	//=========================================================================
	//Methods.
	/**
	 * Get the {@link Position} object which represents the position where
	 * <code>this</code> {@code Put} will attempt to place a {@link Piece}
	 * object.
	 * 
	 * @return The {@link Position} which <code>this</code> {@code Put} object
	 * 			is targeting.
	 */
	public final Position position()
	{
		return position;
	}
	//=========================================================================
	//Overriden methods.
	/**
	 * Execute <code>this</code> {@code Put} command on a {@link Game} object.
	 * 
	 * <p>
	 * When executing the {@link Game#put(Position)} method will be called.
	 * </p>
	 * 
	 * @param game The {@link Game} to execute the {@code Put} command on.
	 * @throws NullPointerException If <code>game</code> is <code>null</code>.
	 * @see Game
	 * @see Game#put(Position)
	 * @see GameCommand#execute(Game)
	 * @see GameException
	 */
	@Override
	public void execute(final Game game)
			throws
			GameException,
			NullPointerException
	{
		if(game == null)
			throw new NullPointerException(NULL_GAME_RECIEVER);
		try
		{
			game.put(position);
		}
		catch(final InvalidMoveException e)
		{
			throw new GameException(e);
		}
	}
	/**
	 * Get the {@link String}} representation of <code>this</code> {@code Put}
	 * command.
	 * 
	 * <p>
	 * The {@link String} representation will contain the {@link String}
	 * produced by {@link Game#toString()}, followed by the {@link String}
	 * from {@link Position#toString()}.
	 * </p>
	 * 
	 * @return The {@link String} representation of <code>this</code>
	 * 			{@code Put} command.
	 * @see GameCommand#toString()
	 * @see Position#toString()
	 */
	@Override
	public final String toString()
	{
		return super.toString() + position.toString();
	}
}