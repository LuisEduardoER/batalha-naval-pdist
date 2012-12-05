package com.pdist.batalhanaval.server.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AtendeCliente extends Thread{
	
	protected Socket client;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;
	
	public AtendeCliente(Socket s){
		client = s;
		
		try {
			out = new ObjectOutputStream(s.getOutputStream());
			in = new ObjectInputStream(s.getInputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		while(true){
			//tratar mensagens recebidas e enviar respostas
		}
	}
	
	

}
