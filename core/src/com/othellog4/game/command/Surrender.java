package com.othellog4.game.command;

import com.othellog4.game.Game;
import com.othellog4.game.GameException;

public class Surrender extends GameCommand
{
	public Surrender(final Object source)
	{
		//May throw NullPointerException.
		super(source);
	}
	@Override
	public void execute(Game game)
			throws
			GameException
	{
		
	}
}
