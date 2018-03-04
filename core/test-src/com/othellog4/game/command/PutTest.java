package com.othellog4.game.command;

import static org.junit.Assert.*;

import org.junit.Test;

import com.othellog4.game.board.Position;

public class PutTest
{
	@Test
	public final void testPosition()
	{
		final Put put = new Put(new Object(), Position.at(0, 0));
		assertEquals(Position.at(0, 0), put.position());
	}
	@Test
	public final void testExecute()
	{
		fail();
	}
	@Test
	public final void testToString()
	{
		fail();
	}
}
