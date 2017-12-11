package com.othellog4.tutorial;

import java.util.ArrayList;

import com.othellog4.game.board.Position;

public class AssistedMoveState implements TutorialState , HighlightableState{
	ArrayList<Position> validMoves;
	boolean highlight;
	boolean messageActive;
	DialogueState incorrectMessage;
	
	public AssistedMoveState(ArrayList<Position> validMoves, boolean highlight, DialogueState incorrectMessage) {
		this.validMoves = validMoves;
		this.highlight = highlight;
		this.incorrectMessage = incorrectMessage;
		messageActive = false;
	}
	
	public ArrayList<Position> getHighlightPositions() {
		if(highlight) return validMoves;
		else return null;
	}
	
	public boolean isValidMove(Position pos) {
		for(Position validPos: validMoves) {
			if(validPos.equals(pos)) {
				return true;
			}
		}
		return false;
	}
	
	public DialogueState getIncorrectMessage() {
		return incorrectMessage;
	}
	
	public boolean isMessageActive() {
		return messageActive;
	}
	
	public void setMessageActive(boolean bool) {
		messageActive = bool;
	}
}
