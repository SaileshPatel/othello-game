package com.othellog4.game.command;

import com.othellog4.game.Game;
import com.othellog4.game.GameException;
import com.othellog4.game.board.Piece;
import com.othellog4.game.player.Participant;

/**
 * The {@code Surrender} class is a subclass of the {@link GameCommand} which
 * encapsulates the action of surrendering a game.
 * 
 * <p>
 * The {@code Surrender} command is {@link CommandType#TURN_UNRESTRICTED}.
 * </p>
 * 
 * @author 	159014260 John Berg
 * @since	04/03/2018
 * @version	04/03/2018
 * @see GameCommand
 */
public final class Surrender extends GameCommand
{
	//=========================================================================
	//Fields.
	/**
	 * The {@link Piece} object which <code>this</code> {@code Surrender}
	 * command will make surrender.
	 * 
	 * @see Piece
	 */
	private final Piece piece;
	//=========================================================================
	//Constructors.
	/**
	 * Create a {@code Surrender} command which makes a {@link Piece} object
	 * surrender.
	 * 
	 * @param source The {@link Participant} which issued the {@code Surrender}
	 * 			command.
	 * @param piece The {@link Piece} object which is surrendering, should be
	 * 			the {@link Piece} which corresponds to <code>source</code>.
	 * @see Participant
	 * @see Piece
	 */
	public Surrender(
			final Participant source,
			final Piece piece)
	{
		super(source, CommandType.TURN_UNRESTRICTED);
		this.piece = piece;
	}
	//=========================================================================
	//Overriden methods.
	/**
	 * Execute <code>this</code> {@code Surrender} command on a {@link Game}
	 * object.
	 * 
	 * @param game The {@link Game} object to invoke <code>this</code>
	 * 			{@code Surrender} command on.
	 * @throws GameException If a {@link GameException} occurs.
	 * @see Game
	 * @see Game#surrender(Piece)
	 * @see GameException
	 */
	@Override
	public final void execute(Game game)
			throws GameException
	{
		game.surrender(piece);
	}
	/**
	 * Get the {@link String} representation of <code>this</code>
	 * {@code Surrender} command.
	 * 
	 * @return The {@link String} which represents <code>this</code>
	 * 			{@code Surrender} command.
	 */
	@Override
	public final String toString()
	{
		return super.toString() + " "  + piece.name();
	}
}
