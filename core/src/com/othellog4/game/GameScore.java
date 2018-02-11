package com.othellog4.game;

public final class GameScore
{
	public final int turn;
	public GameScore(final GameManager manager)
	{
		turn = manager.game().turn();
	}
}
