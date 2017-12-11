package com.othellog4.game.player;

import java.util.Optional;

import com.othellog4.game.GameException;
import com.othellog4.game.GameSession;
import com.othellog4.game.board.Position;
import com.othellog4.game.command.Put;

/**
 * This is the AI class. It will eventually have some AI implementation in
 * order to make smart decisions.
 * 
 * @author 	Sailesh Patel
 * @author 	Zak Hirsi
 * @since 	23/10/2017
 * @version 21/11/2017
 */
public class AutomaticPlayer implements Participant
{
	//=========================================================================
	//Fields.
	/**
	 * 
	 */
	private final GameStrategy strategy;
	/**
	 * 
	 * @param strategy
	 */
	public AutomaticPlayer(final GameStrategy strategy)
	{
		this.strategy = strategy;
	}
	//=========================================================================
	//Overriden methods.
	/**
	 * 
	 */
	@Override
	public final void notifyTurn(GameSession session)
	{
		try
		{
			final Position position = strategy.evaluate(
					session.current(),
					session.getBoard());
			session.accept(new Put(this, position.col, position.row));
		}
		catch(final GameException e)
		{
			//We might want to change this for later verions.
			System.err.println(e);
		}
	}
	/**
	 * 
	 */
	@Override
	public final Optional<Participant.Control> getControl()
	{
		return Optional.empty();
	}
}