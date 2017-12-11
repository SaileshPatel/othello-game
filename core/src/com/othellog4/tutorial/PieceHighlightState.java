package com.othellog4.tutorial;

import java.util.ArrayList;

import com.othellog4.game.board.Position;

/**
 * {@code TutorialState} in which the board is displayed to the player.
 * <p>
 * Stores a list of positions which will appear highlighted on the board.
 * 
 * @author James Shorthouse
 * @version 11/12/2017
 */
public class PieceHighlightState implements TutorialState, HighlightableState {
	ArrayList<Position> highlightPositions;
	
	/**
	 * Constructor.
	 * 
	 * @param highlightPositions Positions to appear highlighted
	 */
	public PieceHighlightState(ArrayList<Position> highlightPositions) {
		this.highlightPositions = highlightPositions;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public ArrayList<Position> getHighlightPositions() {
		return highlightPositions;
	}
}
