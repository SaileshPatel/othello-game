package com.othellog4.game.player;

import java.util.Optional;

import com.othellog4.game.GameException;
import com.othellog4.game.GameSession;
import com.othellog4.game.command.Put;

/**
 * The {@link Player} class is a subclass of the {@link Participant} interface
 * which specialises as a representation of a human player.
 * 
 * @author 	######### Sailesh Patel
 * @author 	######### Zak Hirsi
 * @author 	159014260 John Berg
 * @author 	######### Arvinder Chatha
 * @since 	23/10/2017
 * @version 20/11/2017
 */
public class Player implements Participant
{
	//=========================================================================
	//Fields.
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
	//=========================================================================
	//Constructors.
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
	//=========================================================================
	//Overriden methods.
	/**
	 * Notify <code>this</code> {@code Player} object that it is it's turn to
	 * make a move in a {@link GameSession}.
	 * 
	 * <p>
	 * If <code>this</code> {@code Player} is already making a move for another
	 * then nothing happens.
	 * </p>
	 * 
	 * <p>
	 * This method may be updated to throw an exception when a
	 * {@link GameSession} is already in pending for a turn.
	 * </p>
	 * 
	 * @param session The {@link GameSession} which <code>this</code>
	 * 			{@code Player} is to make a move for.
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
	public final Optional<Participant.Control> getControl()
	{
		return Optional.of(control);
	}
	//=========================================================================
	//Inner classes.
	/**
	 * The {@code Control} class is a subclass of the
	 * {@link Participant.Control} interface which is responsible for making
	 * decisions for a {@link Player} object.
	 * 
	 * @author 	159014260 John Berg
	 * @since	1/12/2017
	 * @version 1/12/2017
	 */
	public static final class Control implements Participant.Control
	{
		//=====================================================================
		//Fields.
		/**
		 * The {@link Player} object that <code>this</code> {@code Control} is
		 * representing.
		 */
		private final Player player;
		//=====================================================================
		//Constructors.
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
		//=====================================================================
		//Overriden methods.
		/**
		 * Put a piece on a board at a specific column and row.
		 * 
		 * <p>
		 * Provide the {@link GameSession} of the {@link Player} object
		 * associated with <code>this</code> {@code Control} with a
		 * {@link Put} command on the behalf of the {@link Player} contained in
		 * <code>this</code>.
		 * </p>
		 * 
		 * @param x The column of the board to place a piece on.
		 * @param y The row of the board to place a piece on.
		 * @throws GameException If a {@link GameException} occurs.
		 */
		@Override
		public void put(int x, int y)
				throws
				GameException
		{
			/*
			 * Store a local copy of player.session to avoid the original being
			 * set to null.
			 */
			final GameSession session = player.session;
			if(session != null)
				session.accept(new Put(player, x, y));
			player.session = null;
		}
	}
}
