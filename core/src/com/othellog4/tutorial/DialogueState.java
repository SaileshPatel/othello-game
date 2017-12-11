package com.othellog4.tutorial;

public class DialogueState implements TutorialState {
	MascotExpression expression;
	String message;
	
	public DialogueState(String message, MascotExpression expression) {
		this.message = message;
		this.expression = expression;
	}
	
	public String getMessage() {
		return message;
	}
	
	public MascotExpression getExpression() {
		return expression;
	}
}