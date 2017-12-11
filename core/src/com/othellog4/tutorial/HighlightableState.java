package com.othellog4.tutorial;

import java.util.ArrayList;

import com.othellog4.game.board.Position;

public interface HighlightableState {
	public abstract ArrayList<Position> getHighlightPositions();
}
