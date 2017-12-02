package com.othellog4.game;

import java.util.Observable;

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
	private final GameSession session;
	/**
	 * 
	 */
	private final Participant player1;
	/**
	 * 
	 */
	private final Participant player2;
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
		this.session = new GameSession(game, player1, player2);
		this.player1 = player1;
		this.player2 = player2;
	}
	//=========================================================================
	//Methods.
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public final void put(int x, int y)
	{
		//control = current.getControl();
		//if(control.isPresent())
		//	control.put(x, y);
	}
	/**
	 * 
	 * @return
	 */
	public final Participant getPlayer1()
	{
		return player1;
	}
	/**
	 * 
	 * @return
	 */
	public final Participant getPlayer2()
	{
		return player2;
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