package com.othello.network;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection {

	protected DataOutputStream writer;
	protected DataInputStream reader;
	protected Socket s;
	public Connection(String IP) {
		try {
			s = new Socket(IP,6789);
			reader = new DataInputStream(s.getInputStream());
			writer = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			try {
				s = new Socket("www.othellog4.com",5678);
				reader = new DataInputStream(s.getInputStream());
				writer = new DataOutputStream(s.getOutputStream());
				writer.writeBytes(IP);
				s = new Socket(reader.readUTF(),6789);
				reader = new DataInputStream(s.getInputStream());
				writer = new DataOutputStream(s.getOutputStream());
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected Connection() {
	}

	public void send(String output) {
		try {
			writer.writeUTF(output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String read() {

		try {
			return reader.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return null;

	}

	public void close() {
		try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isOn() {
		return s.isClosed();
	}
}


