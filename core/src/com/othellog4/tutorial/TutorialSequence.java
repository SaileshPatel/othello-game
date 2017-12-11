package com.othellog4.tutorial;

import java.util.ArrayList;

public class TutorialSequence {
	ArrayList<TutorialState> stateList;
	int currentState;
	
	public TutorialSequence(ArrayList<TutorialState> stateList) {
		this.stateList = stateList;
		currentState = 0;
	}
	
	public TutorialState getCurrentState() {
		if(currentState < stateList.size()) {
			return stateList.get(currentState);
		} else {
			return null;
		}
	}
	
	public void incrementState() {
		// Max value of currentState is 1 over max index
		if(currentState < stateList.size()) {
			currentState++;
		}
	}
	
	public void addState(TutorialState state) {
		stateList.add(state);
	}
}
