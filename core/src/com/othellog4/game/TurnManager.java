package com.othellog4.game;

import java.util.HashMap;

import java.util.Map;

import com.othellog4.game.board.Piece;
import com.othellog4.game.player.Participant;

/**
 * The {@code TurnManager} is responsible for maintaining a mapping of
 * {@link Piece} objects the corresponding {@link Participant} object.
 * 
 * <p>
 * {@code TurnManager} is immutable and does not allow for modification after
 * creation, which means that {@code TurnManager} is thread-safe.
 * </p>
 * 
 * @author 	159014260 John Berg
 * @since	02/12/2017
 * @since	03/12/2017
 */
public final class TurnManager
{
	//=========================================================================
	//Static fields.
	/**
	 * The capacity of the {@link #playerMap}.
	 */
	private static final int MAP_CAPACITY = 3;
	//=========================================================================
	//Fields.
	/**
	 * The {@link Map} which maps {@link Piece} objects to the corresponding
	 * {@link Participant}.
	 */
	private final Map<Piece, Participant> playerMap;
	//=========================================================================
	//Constructors.
	/**
	 * Create a {@code TurnManager} object.
	 * 
	 * <p>
	 * Creation of a {@code TurnManager} requires a {@link Game} which
	 * determines which {@link Piece} objects corresponds to the first and
	 * second player, and two {@link Participants} which then correspond to the
	 * {@link Piece} objects which represent player 1 and player 2.
	 * </p>
	 * 
	 * @param game The {@link Game} which determines do {@link Piece} objects
	 * 			which correspond to the first and second player.
	 * @param player1 The {@link Participant} representing the first player.
	 * @param player2 The {@link Participant} representing the first player.
	 * @throws NullPointerException If <code>game</code>, <code>player1</code>
	 * 			or <code>player2</code> is <code>null</code>.
	 * @see Game
	 * @see Game#getPlayer1()
	 * @see Game#getPlayer2()
	 * @see Participant
	 */
	public TurnManager(
			final Game game,
			final Participant player1,
			final Participant player2)
	{
		if(game == null)
			throw new NullPointerException();
		if(player1 == null)
			throw new NullPointerException();
		if(player2 == null)
			throw new NullPointerException();
		playerMap = new HashMap<>(MAP_CAPACITY);
		playerMap.put(game.getPlayer1(), player1);
		playerMap.put(game.getPlayer2(), player2);
	}
	//=========================================================================
	//Methods.
	/**
	 * Get the {@link Participant} object which corresponds to a {@link Piece}
	 * object.
	 * 
	 * @param piece The {@link Piece} to get the corresponding
	 * 			{@link Participant}.
	 * @return The {@link Participant} which represents the <code>piece</code>.
	 * @throws NullPointerException If <code>piece</code> is <code>null</code>.
	 */
	public final Participant playerOf(final Piece piece)
	{
		if(piece == null)
			throw new NullPointerException();
		return playerMap.get(piece);
	}
}
