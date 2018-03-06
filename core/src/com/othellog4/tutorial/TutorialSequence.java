package com.othellog4.tutorial;

import java.util.ArrayList;

/**
 * Sequence of {@code TutorialState}s
 * <p>
 * Represents an entire tutorial sequence
 * <p>
 * Also keeps track of contains methods for incrementing the current state
 *
 * @author James Shorthouse
 * @version 11/12/2017
 *
 */
public class TutorialSequence {
	ArrayList<TutorialState> stateList;
	int currentState;

	/**
	 * Constructor.
	 * <p>
	 * Sets current state to 0.
	 *
	 * @param stateList Ordered list of all states
	 */
	public TutorialSequence(ArrayList<TutorialState> stateList) {
		this.stateList = stateList;
		currentState = 0;
	}

	/**
	 * Get the number of states
	 * @return number of states
	 */
	public TutorialState getCurrentState() {
		if(currentState < stateList.size()) {
			return stateList.get(currentState);
		} else {
			return null;
		}
	}

	/**
	 * Increment the current state ID by 1
	 */
	public void incrementState() {
		// Max value of currentState is 1 over max index
		if(currentState < stateList.size()) {
			currentState++;
		}
	}

	/**
	 * Add a new state to the end of the sequence
	 * @param state State to add
	 */
	public void addState(TutorialState state) {
		stateList.add(state);
	}
}
