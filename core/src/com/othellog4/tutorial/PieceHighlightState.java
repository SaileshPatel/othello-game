package com.othellog4.tutorial;

import java.util.ArrayList;

import com.othellog4.game.board.Position;

public class PieceHighlightState implements TutorialState, HighlightableState {
	ArrayList<Position> highlightPositions;
	
	public PieceHighlightState(ArrayList<Position> highlightPositions) {
		this.highlightPositions = highlightPositions;
	}
	
	public ArrayList<Position> getHighlightPositions() {
		return highlightPositions;
	}
}
