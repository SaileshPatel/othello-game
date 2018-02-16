package com.othellog4.game.command;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class GameCommandTest
{
	@Test(expected = NullPointerException.class)
	public final void testGetSource_NullArg()
	{
		fail();
	}
	@Test
	public final void  testGetSource_Valid()
	{
		fail();
	}
}
