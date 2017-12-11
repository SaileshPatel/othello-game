package com.othellog4.tutorial;

/**
 * {@code TutorialState} in which the mascot is displayed along with a message
 * 
 * @author James Shorthouse
 * @version 11/12/2017
 */
public class DialogueState implements TutorialState {
	MascotExpression expression;
	String message;
	
	/**
	 * Constructor.
	 * 
	 * @param message    Message to be displayed
	 * @param expression Mascot's expression name
	 */
	public DialogueState(String message, MascotExpression expression) {
		this.message = message;
		this.expression = expression;
	}
	
	/**
	 * Get the message
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Get the mascot's expression
	 * @return name of expression
	 */
	public MascotExpression getExpression() {
		return expression;
	}
}