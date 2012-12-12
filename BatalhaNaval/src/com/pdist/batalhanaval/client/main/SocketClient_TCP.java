package com.pdist.batalhanaval.client.main;

import java.net.Socket;

public class SocketClient_TCP {

	private static Socket socket = null; //isto nao cria o socket so armazena
	
	
	public SocketClient_TCP(){}
	
	public static Socket getSocket(){
		return socket;
	}
	
	public static void setSocket(Socket socket){
		SocketClient_TCP.socket = socket;
	}
	
}
