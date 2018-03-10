package com.othello.network;

public class Network {

	Connection c;
	
	public Network(String connection) {
		setup(connection);
		
		
	}
	
	/**
	 * 
	 * @param input Expecting a host!:STRING or an ip
	 */
	private void setup(String input) {
		String[] name;
		input = input.toLowerCase();
		if(input.startsWith("host")) {
			name = input.split("!:");
			c = new HostConnection(name[1]);
		} else {
			c = new Connection(input);
		}
	}
}
