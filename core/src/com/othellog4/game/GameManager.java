package com.othellog4.game;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
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
 * @since	12/03/2017
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
	 * The flag which indicates if it is possible to execute a
	 * {@link GameCommand} on <code>this</code> {@code GameManager}.
	 */
	private volatile boolean inputEnable;
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
		inputEnable = true;
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
	 * Enable or disable input such that it is not possible to progress the
	 * game.
	 * 
	 * <p>
	 * When enabled, it will prompt the {@link Game} to update with the last
	 * event.
	 * </p>
	 * 
	 * @param enable The enable status.
	 */
	public final void enableInput(final boolean enable)
	{
		inputEnable = enable;
	}
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
	 * Execute a {@link GameCommand} on the {@link Game} managed by
	 * <code>this</code> {@code GameManager}.
	 *
	 * @param command The {@link GameCommand} to execute.
	 * @return <code>true</code> If the command was allowed to be executed,
	 * 			otherwise <code>false</code>.
	 * @throws GameException If the execution of the {@link GameCommand}
	 * 			throws a {@link GameException}.
	 */
	public final boolean execute(final GameCommand command)
			throws
			GameException
	{
		if(inputEnable && command.canExecute(current()))
		{
			update(command);
			command.execute(game());
			return true;
		}
		else
			return false;
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
	 * Get the {@link GameResult} objects from the {@link GameExtension}
	 * objects for a specified {@link Piece} object, managed by
	 * <code>this</code> {@code GameScore}.
	 *
	 * <p>
	 * Package private to restrict access to this method.
	 * </p>
	 *
	 * @return The {@link Map} of {@link Class} and {@link GameResult} objects
	 * 			which represent the class and results generated from a
	 * 			{@link GameExtension} object.
	 */
	final Map<Class<? extends GameExtension>, GameResult> getResults()
	{
		return extensions.stream()
				.filter(GameExtension::hasResult)
				.map(GameResult::new)
				.collect(Collectors
						.toMap(GameResult::type, Function.identity()));
	}
}
