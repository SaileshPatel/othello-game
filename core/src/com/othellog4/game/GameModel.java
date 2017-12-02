package com.othellog4.game;

import java.util.Observable;
import java.util.Optional;

import com.othellog4.game.board.BoardView;
import com.othellog4.game.player.Participant;

/**
 * 
 * @author 	159014260 John Berg
 * @since 	01/12/2017
 * @version 01/12/2017
 */
public class GameModel extends Observable
{
	//========================================================================
	//Fields.
	/**
	 * 
	 */
	private final Game game;
	/**
	 * 
	 */
	private final TurnManager turnManager;
	/**
	 * 
	 */
	private final GameSession session;
	//=========================================================================
	//Constructors.
	/**
	 * 
	 */
	public GameModel(
			final Game game,
			final Participant player1,
			Participant player2)
	{
		this.game = game;
		this.turnManager = new TurnManager(game, player1, player2);
		this.session = new GameSession(game, turnManager);
	}
	//=========================================================================
	//Methods.
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public final void put(int x, int y)
			throws
			GameException
	{
		final Optional<Participant.Control> control = getCurrent().getControl();
		if(control.isPresent())
			control.get().put(x, y);
	}
	/**
	 * 
	 * @return
	 */
	public final Participant getCurrent()
	{
		return turnManager.playerOf(session.current());
	}
	/**
	 * 
	 * @return
	 */
	public final Participant getPlayer1()
	{
		return turnManager.playerOf(game.getPlayer1());
	}
	/**
	 * 
	 * @return
	 */
	public final Participant getPlayer2()
	{
		return turnManager.playerOf(game.getPlayer1());
	}
	/**
	 * 
	 * @return
	 */
	public final BoardView getBoard()
	{
		return session.getBoard();
	}
}