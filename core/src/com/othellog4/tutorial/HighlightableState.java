package com.othellog4.tutorial;

import java.util.ArrayList;

import com.othellog4.game.board.Position;

/**
 * Interface used by all {@code TutorialState} objects that contain highlight positions
 *
 * @author James Shorthouse
 * @version 11/12/2017
 */
public interface HighlightableState {
	/**
	 * Get all positions to be highlighted.
	 * @return positions
	 */
	public abstract ArrayList<Position> getHighlightPositions();
}
