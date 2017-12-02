package com.othellog4.game.player;

import java.util.Optional;

import com.othellog4.game.GameException;
import com.othellog4.game.GameSession;
import com.othellog4.game.command.Put;

/**
 * 
 * 
 * @author 	######### Sailesh Patel
 * @author 	######### Zak Hirsi
 * @author 	159014260 John Berg
 * @author 	######### Arvinder Chatha
 * @since 	23/10/2017
 * @version 20/11/2017
 */
public class Player implements Participant {
	
	/**
	 * The {@link GameSession} which <code>this</code> {@code Player} object is
	 * currently making a move for.
	 * 
	 * <p>
	 * When this is <code>null</code> it means that currently it is not
	 * <code>this</code> {@code Player} object's turn to make a move.
	 * </p>
	 */
	private GameSession session;
	/**
	 * The {@link Control} for <code>this</code> {@code Player}.
	 */
	private final Control control;
	/**
	 * Create a {@code Player} object.
	 * 
	 * <p>
	 * Upon creation, the {@code Player} object. Does not have a pending turn
	 * to any {@link GameSession}.
	 * The created {@code Player} object will have its own unique
	 * {@link Control}.
	 * </p>
	 */
	public Player()
	{
		session = null;
		control = new Control(this);
	}
	/**
	 * 
	 */
	@Override
	public final void notifyTurn(final GameSession session)
	{
		this.session = session;
	}
	/**
	 * Get the {@link Control} object for <code>this</code> {@code Player}.
	 * 
	 * @return The {@link Optional} with a value always.
	 */
	@Override
	public final Optional<? extends Participant.Control> getControl()
	{
		return Optional.of(control);
	}
	/**
	 * The {@code Control} class is ...
	 * 
	 * 
	 * @author 	159014260 John Berg
	 * @since	1/12/2017
	 * @version 1/12/2017
	 */
	public static final class Control implements Participant.Control
	{
		/**
		 * The {@link Player} object that <code>this</code> {@code Control}
		 * is representing.
		 */
		private final Player player;
		/**
		 * Create a {@code Control} object for a given {@link Player}.
		 * 
		 * @param player The {@link Player} object that <code>this</code>
		 * 			{@code Control} represents.
		 */
		private Control(final Player player)
		{
			this.player = player;
		}
		/**
		 * 
		 */
		@Override
		public void put(int x, int y)
				throws
				GameException
		{
			if(player.session != null)
				player.session.accept(new Put(player, x, y));
			player.session = null;
		}
	}
}
