package com.othello.network;


import com.othellog4.game.board.Position;

public class MyRunnable implements Runnable {

	public Position messageOut;
	public Position messageIn;
	private Network n;
	
	public MyRunnable(String IP) {
		messageIn = null;
		messageOut = null;
		n = new Network(IP);
	}
	
	public void run() {
		if(messageOut != null) {
			n.sendMove(messageOut);
		}
		messageIn = n.getMove();
	}
}
