package com.pdist.batalhanaval.client.main;

import java.net.Socket;

public class SocketCliente {

	private static Socket socket = new Socket();
	
	
	public SocketCliente(){}
	
	public Socket getSocket(){
		return socket;
	}
	
	public void setSocket(Socket socket){
		this.socket = socket;
	}
	
}
