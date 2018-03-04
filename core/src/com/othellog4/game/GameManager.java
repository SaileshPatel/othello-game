package com.othellog4.game;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import com.othellog4.game.board.Piece;
import com.othellog4.game.command.GameCommand;
import com.othellog4.game.extension.GameExtension;
import com.othellog4.game.player.Participant;

/**
 * The {@code GameManager} is responsible for maintaining a mapping of
 * {@link Piece} objects the corresponding {@link Participant} object.
 * 
 * <p>
 * {@code GameManager} is immutable and does not allow for modification after
 * creation, which means that {@code GameManager} is thread-safe.
 * </p>
 * 
 * @author 	159014260 John Berg
 * @since	02/12/2017
 * @since	17/12/2017
 */
public final class GameManager
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
	 * The {@link Game} which <code>this</code> {@code GameManager} is
	 * managing the mapping of {@link Participant} objects for.
	 * 
	 * @see Game
	 */
	private final Game game;
	/**
	 * The {@link Map} which maps {@link Piece} objects to the corresponding
	 * {@link Participant}.
	 * 
	 * @see Piece
	 * @see Participant
	 */
	private final Map<Piece, Participant> playerMap;
	/**
	 * The {@link GameExtension} objects which are managed by <code>this</code>
	 * {@code GameManager}.
	 * 
	 * @see GameExtension
	 */
	private final Set<GameExtension> extensions;
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
	 * @param extensions The array of {@link GameExtension} objects which
	 * 			provide additional functionality.
	 * @throws NullPointerException If <code>game</code>, <code>player1</code>
	 * 			or <code>player2</code> is <code>null</code>.
	 * @see Game
	 * @see GameExtension
	 * @see Participant
	 */
	public GameManager(
			final Game game,
			final Participant player1,
			final Participant player2,
			final GameExtension... extensions)
	{
		if(game == null)
			throw new NullPointerException();
		if(player1 == null)
			throw new NullPointerException();
		if(player2 == null)
			throw new NullPointerException();
		this.game = game;
		this.game.addListener(this::update);
		playerMap = new HashMap<>(MAP_CAPACITY);
		playerMap.put(game.getPlayer1(), player1);
		playerMap.put(game.getPlayer2(), player2);
		this.extensions = new LinkedHashSet<>();
		Stream.of(extensions).forEach(this.extensions::add);
	}
	//=========================================================================
	//Methods.
	/**
	 * Update all the {@link GameExtension} objects in <code>this</code>
	 * {@code GameManager} about a {@link GameEvent}.
	 * 
	 * @param event The {@link GameEvent} object to notify all
	 * 			{@link GameExtension} objects about.
	 */
	private void update(final GameEvent event)
	{
		for(final GameExtension e: extensions)
			e.onEvent(event, this);
	}
	/**
	 * Update all the {@link GameExtension} objects in <code>this</code>
	 * {@code GameManager} about a {@link GameCommand}.
	 * 
	 * @param command The {@link GameCommand} object to notify all
	 * 			{@link GameExtension} object about.
	 */
	private void update(final GameCommand command)
	{
		for(final GameExtension e: extensions)
			e.onCommand(command, this);
	}
	/**
	 * Calculate the score from the {@link GameExtension} objects which
	 * are managed by <code>this</code> {@code GameManager}.
	 * 
	 * @param piece The {@link Piece} object to calculate the score for.
	 * @return The score for the <code>piece</code> object.
	 */
	public final int calculateScore(final Piece piece)
	{
		return extensions.stream()
				.mapToInt(e -> e.getScore(piece))
				.sum();
	}
	/**
	 * Execute a {@link GameCommand} on the {@link Game} managed by
	 * <code>this</code> {@code GameManager}.
	 * 
	 * @param command The {@link GameCommand} to execute.
	 * @throws GameException If the execution of the {@link GameCommand}
	 * 			throws a {@link GameException}.
	 */
	public final void execute(final GameCommand command)
			throws
			GameException
	{
		if(command.canExecute(current()))
		{
			update(command);
			command.execute(game());
		}
	}
	/**
	 * Get the first {@link Participant} of the {@link Game} managed by
	 * <code>this</code> {@code GameManager}.
	 * 
	 * @return The first {@link Participant} of the {@link Game} managed by
	 * 			<code>this</code> {@code GameManager}.
	 */
	public final Participant player1()
	{
		return playerOf(game.getPlayer1());
	}
	/**
	 * Get the second {@link Participant} of the {@link Game} managed by
	 * <code>this</code> {@code GameManager}.
	 * 
	 * @return The second {@link Participant} of the {@link Game} managed by
	 * 			<code>this</code> {@code GameManager}.
	 */
	public final Participant player2()
	{
		return playerOf(game.getPlayer2());
	}
	/**
	 * Get the current {@link Participant} of the {@link Game} managed by
	 * <code>this</code> {@code GameManager}.
	 * 
	 * @return The current {@link Participant} of the {@link Game} managed by
	 * 			<code>this</code> {@code GameManager}.
	 */
	public final Participant current()
	{
		return playerOf(game.getCurrent());
	}
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
	/**
	 * Get the {@link Piece object corresponded to a {@link Participant}
	 * object.
	 * 
	 * <p>
	 * Since a {@link Participant} may be associated with more than one
	 * {@link Piece}, it is possible that the {@link Piece} object returned is
	 * arbitrarily selected.
	 * </p>
	 * 
	 * @param player The {@link Participant} object to find the {@link Piece}
	 * 			object for.
	 * @return The {@link Piece} object corresponding to the
	 * 			{@link Participant} object.
	 */
	public final Optional<Piece> pieceOf(final Participant player)
	{
		return playerMap.entrySet().stream()
				.filter(e -> e.getValue().equals(player))
				.map(Map.Entry::getKey)
				.findFirst();
	}
	/**
	 * Get the {@link GameScore} of the {@link Game} managed by
	 * <code>this</code> {@code GameManager}.
	 * 
	 * @return The {@link GameScore} for the {@link Game}.
	 */
	public final GameScore score()
	{
		return new GameScore(this);
	}
	/**
	 * Get the {@link Game} which <code>this</code> {@code GameManager} is
	 * managing.
	 * 
	 * @return The {@link Game} of <code>this</code> {@code GameManager}.
	 */
	public final Game game()
	{
		return game;
	}
	/**
	 * Get the result {@link String} objects from the {@link GameExtension}
	 * objects managed by <code>this</code> {@code GameScore}.
	 * 
	 * @return The {@link String} array which represents the results.
	 */
	public final String[] getResult()
	{
		return extensions.stream()
				.map(Object::toString)
				.filter(s -> s != null)
				.toArray(String[]::new);
	}
	/**
	 * Get the result {@link String} objects from the {@link GameExtension}
	 * objects for a specified {@link Piece} object, managed by
	 * <code>this</code> {@code GameScore}.
	 * 
	 * @param piece The {@link Piece} object to get the result {@link String}
	 * 			from.
	 * @return The array of {@link String} objects which represent the results.
	 */
	public final String[] getResults(final Piece piece)
	{
		return extensions.stream()
				.map(e -> e.getResult(piece))
				.filter(s -> s != null)
				.toArray(String[]::new);
	}
}
