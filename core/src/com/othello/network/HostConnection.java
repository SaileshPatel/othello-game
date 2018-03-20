package com.othello.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HostConnection extends Connection {

	ServerSocket server;
	public HostConnection(String output) {
		super();

		try {
			try {
				Socket web = new Socket("www.othellog4.com",5678);
				writer = new DataOutputStream(web.getOutputStream());
				writer.writeUTF(output);
			} catch (Exception e) {

			}
			server = new ServerSocket(6789);
			s = server.accept();
			reader = new DataInputStream(s.getInputStream());
			writer = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			server.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
