package com.othellog4.game.player;

import java.util.Optional;

import com.othellog4.game.GameException;
import com.othellog4.game.GameSession;
import com.othellog4.game.command.Put;
import com.othellog4.game.player.ai.DelayStrategy;
import com.othellog4.game.player.ai.EvaluationStrategy;
import com.othellog4.game.player.ai.SearchStrategy;
import com.othellog4.game.player.ai.Tactic;

/**
 * The {@code AutomaticPlayer} class is a representation of a player which is
 * controlled by the computer.
 * 
 * <p>
 * The {@code AutomaticPlayer} class is emulating the decision making of a
 * human, and hence is an AI.
 * </p>
 * 
 * <p>
 * The {@code AutomaticPlayer} class is immutable.
 * </p>
 * 
 * @author 	Sailesh Patel
 * @author 	Zak Hirsi
 * @since 	23/10/2017
 * @version 15/02/2017
 */
public final class AutomaticPlayer implements Participant
{
	//=========================================================================
	//Fields.
	/**
	 * The {@link Tactic} object which <code>this</code>
	 * {@code AutomaticPlayer} object will be using to search for possible
	 * moves.
	 */
	private final Tactic tactic;
	/**
	 * The {@link DelayStrategy} which defines how much delay <code>this</code>
	 * {@code AutomaticPlayer} object will be waiting before completing a move.
	 */
	private final DelayStrategy delay;
	//=========================================================================
	//Constructors.
	/**
	 * Construct a new {@code AutomaticPlayer} object by specifying the
	 * desired {@link EvaluationStrategy}, {@link SearchStrategy} and a
	 * {@link DelayStrategy} which will be used when emulating decision making.
	 * 
	 * @param eval The {@link EvaluationStrategy} used to evaluate a
	 * 			{@link BoardView}.
	 * @param search The {@link SearchStrategy} used to search a
	 * 			{@link BoardView}.
	 * @param delay The {@link DelayStrategy} used to potentially delay the
	 * 			response of the {@code AutomaticPlayer} object.
	 */
	public AutomaticPlayer(
			final EvaluationStrategy eval,
			final SearchStrategy search,
			final DelayStrategy delay)
	{
		tactic = new Tactic(eval, search);
		this.delay = delay;
	}
	//=========================================================================
	//Overriden methods.
	/**
	 * Notify <code>this</code> {@code AutomaticPlayer} that the
	 * {@link GameSession} is waiting for a move.
	 * 
	 * @param session The {@link GameSession} which <code>this</code>
	 * 			{@code AutomaticPlayer} object will be processing
	 * 			a turn for. 
	 */
	@Override
	public final void notifyTurn(final GameSession session)
	{
		delay.delay(tactic.plan(
				session.getBoard(),
				session.current(),
				p ->
		{
			try
			{
				session.accept(new Put(this, p.col, p.row));
			}
			catch(final GameException e)
			{
				//We might want to change this for later verions.
				System.err.println(e);
			}
		}));
	}
	/**
	 * No {@link Participant.Control} object is available for the
	 * {@code AutomaticPlayer} class.
	 * 
	 * @return {@link Optional#empty()} as an {@code AutomaticPlayer} object
	 * 			cannot be controlled.
	 */
	@Override
	public final Optional<Participant.Control> getControl()
	{
		return Optional.empty();
	}
}