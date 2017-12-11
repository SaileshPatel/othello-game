package com.othellog4.tutorial;

import java.util.ArrayList;

import com.othellog4.game.board.Position;

/**
 * {@code TutorialState} in which the player is prompted to make a move.
 * <p>
 * If the move matches one in a list of valid positions, the move in considered valid.
 * There is an option to highlight all valid positions.
 * <p>
 * If the move is not valid, a given {@code DialogueState} with appropriate message is displayed to the player.
 *
 * @author James Shorthouse
 * @version 11/12/2017
 *
 */
public class AssistedMoveState implements TutorialState , HighlightableState{
	ArrayList<Position> validMoves;
	boolean highlight;
	boolean messageActive;
	DialogueState incorrectMessage;
	
	/**
	 * Constructor.
	 * 
	 * @param validMoves       All positions which you wish the player to choose.
	 * @param highlight        Whether to highlight valid positions on the board or not.
	 * @param incorrectMessage Message to be displayed to the player on making an invalid move.
	 */
	public AssistedMoveState(ArrayList<Position> validMoves, boolean highlight, DialogueState incorrectMessage) {
		this.validMoves = validMoves;
		this.highlight = highlight;
		this.incorrectMessage = incorrectMessage;
		messageActive = false;
	}
	
	/**
	 * {@inheritDoc}
	 * <p>
	 * Will return null if {@code highlight} is set to false on construction.
	 */
	public ArrayList<Position> getHighlightPositions() {
		if(highlight) return validMoves;
		else return null;
	}
	
	/**
	 * Check is a move is valid.
	 * @param pos Position to check
	 * @return true is the move is valid
	 */
	public boolean isValidMove(Position pos) {
		for(Position validPos: validMoves) {
			if(validPos.equals(pos)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get the incorrect message
	 * @return incorrect message
	 */
	public DialogueState getIncorrectMessage() {
		return incorrectMessage;
	}
	
	/**
	 * Get whether the message is active and should be rendered.
	 * @return true if message is active
	 */
	public boolean isMessageActive() {
		return messageActive;
	}
	
	/**
	 * Set whether the message is active and should be displayed.
	 * @param active if the message should be active
	 */
	public void setMessageActive(boolean active) {
		messageActive = active;
	}
}
