package com.othellog4.game.extension;

import java.util.HashMap;
import java.util.Map;

import com.othellog4.game.GameEvent;
import com.othellog4.game.GameManager;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;
import com.othellog4.game.command.GameCommand;
import com.othellog4.game.command.Put;

/**
 * The {@code FlipCounter} is a class which is concerned with counting the
 * flips which are generated by a {@link Put} {@link GameCommand}.
 *
 * <p>
 * The {@code FlipCounter} is an instance of the {@link GameExtension} class
 * which aims to provide additional functionality.
 * </p>
 *
 * @author 	159014260 John Berg
 * @since 	26/02/2018
 * @version 06/03/2018
 * @see		GameExtension
 * @see		GameCommand
 * @see		Put
 */
public final class FlipCounter extends GameExtension
{
	//=========================================================================
	//Static fields.
	/**
	 * The <code>int</code> which is the capacity of a {@link Map} which will
	 * contain the {@link Piece} objects.
	 */
	private static final int MAP_CAPACITY = 3;
	//=========================================================================
	//Fields.
	/**
	 * The {@link Map} object which maps the {@link Piece} objects to
	 * the corresponding {@link Integer} object which is the current total
	 * number of flips.
	 *
	 * @see Piece
	 */
	private final Map<Piece, Integer> scoreMap;
	//=========================================================================
	//Constructors.
	/**
	 * Create an instance of the {@code FlipCounter} class.
	 *
	 * <p>
	 * The newly created {@code FlipCounter} object will initialise the counter
	 * to 0.
	 * </p>
	 */
	public FlipCounter()
	{
		scoreMap = new HashMap<>(MAP_CAPACITY);
		scoreMap.put(Piece.PIECE_A, 0);
		scoreMap.put(Piece.PIECE_B, 0);
	}
	//=========================================================================
	//Overriden methods.
	/**
	 * The {@code FlipCounter} class can generate a result.
	 *
	 * @return <code>true</code>.
	 */
	@Override
	public final boolean hasResult()
	{
		return true;
	}
	/**
	 * Get the <code>int</code> result for a specific {@link Piece} object.
	 *
	 * @param piece The {@link Piece} object to get the result for.
	 * @return The total number of flips accumulated by the <code>piece</code>.
	 */
	@Override
	public final int result(final Piece piece)
	{
		return scoreMap.get(piece);
	}
	/**
	 * The {@code FlipCounter} does not handle {@link GameEvent} objects.
	 *
	 * @param event Unused.
	 * @param manager Unused.
	 * @see GameEvent
	 * @see GameManager
	 */
	@Override
	public final void onEvent(
			final GameEvent event,
			final GameManager manager)
	{
		//Events do nothing.
	}
	/**
	 * Handle a {@link GameCommand} when issued.
	 *
	 * <p>
	 * If the issued {@link GameCommand} is a {@link Put} command, then
	 * <code>this</code> {@code FlipCounter} will count the number of flips
	 * which the {@link Put} command results in and add it to the total.
	 * </p>
	 *
	 * @param command The {@link GameCommand} issued.
	 * @param manager The {@link GameManager} which <code>this</cod>
	 * 			{@link GameExtension} belongs to.
	 * @see GameCommand
	 * @see GameManager
	 */
	@Override
	public final void onCommand(
			final GameCommand command,
			final GameManager manager)
	{
		if(command instanceof Put)
		{
			final Piece piece = manager.game().getCurrent();
			final Position pos = ((Put) command).position();
			scoreMap.put(piece, scoreMap.get(piece) + manager
					.game().getBoard().countFlips(
								pos.col, pos.row,
								piece));
		}
	}
}
