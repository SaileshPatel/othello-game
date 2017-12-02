package com.othellog4.game;

import java.util.HashMap;

import java.util.Map;

import com.othellog4.game.board.Piece;
import com.othellog4.game.player.Participant;

public class TurnManager
{
	private final Map<Piece, Participant> playerMap;
	public TurnManager(
			final Game game,
			final Participant player1,
			final Participant player2)
	{
		playerMap = new HashMap<>(3);
		playerMap.put(game.getPlayer1(), player1);
		playerMap.put(game.getPlayer2(), player2);
	}
	public final Participant playerOf(final Piece piece)
	{
		return playerMap.get(piece);
	}
}
