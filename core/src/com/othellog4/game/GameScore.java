package com.othellog4.game;

import com.othellog4.game.extension.GameExtension;
import com.othellog4.game.player.Participant;

/**
 * 
 * @author 	1159014260 John Berg
 * @since	17/02/2018
 * @version	20/02/2018
 */
public final class GameScore
{
	//=========================================================================
	//Fields.
	/**
	 * 
	 */
	private final int turn;
	/**
	 * 
	 * @param manager
	 */
	GameScore(final GameManager manager)
	{
		turn = manager.game().turn();
	}
	//=========================================================================
	//Methods.
	/**
	 * 
	 * @return
	 */
	public final boolean isDraw()
	{
		return false;
	}
	/**
	 * 
	 * @return
	 */
	public final int turn()
	{
		return turn;
	}
	/**
	 * 
	 * @return
	 */
	public final Participant winner()
	{
		return null;
	}
	/**
	 * 
	 * @return
	 */
	public final Participant loser()
	{
		return null;
	}
	/**
	 * 
	 * @param klass
	 * @return
	 */
	public final <E extends GameExtension> String get(final Class<E> klass)
	{
		return "";
	}
}
