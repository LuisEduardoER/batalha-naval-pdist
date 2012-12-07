package com.pdist.batalhanaval.client.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.pdist.batalhanaval.client.macros.Macros;
import com.pdist.batalhanaval.client.mensagens.Mensagem;

public class LoginServidor implements Runnable {
	
	
		protected Socket socket;
		protected ObjectOutputStream out;
		protected ObjectInputStream in;
		protected boolean logIn = false;
		protected boolean waitingResponse;
		protected InetAddress servAddr = null;
		protected int servPort;
		public final static int TIMEOUT = 1500;
		
	
		
		public LoginServidor(String IP, String Nome) throws IOException{
			
			 servAddr = InetAddress.getByName(IP);
	         servPort = Integer.parseInt("5001"); //alterar
	         
			socket = new Socket(servAddr,servPort);	
			socket.setSoTimeout(TIMEOUT);
			
			waitingResponse = false;
			
		
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());
				
				if(!logIn && !waitingResponse){
					//enviar confirmação de ligação e pedido de login
					Mensagem msg = new Mensagem(Macros.MSG_LOGIN_REQUEST);
					out.flush();
					out.writeObject(msg);
					out.flush();
					waitingResponse = true;
				}
		}
			
	

		
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
		
}

	