package com.othellog4.game;

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
	public final int turn()
	{
		return turn;
	}
}
