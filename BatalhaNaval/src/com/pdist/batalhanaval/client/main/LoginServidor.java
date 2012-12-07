package com.pdist.batalhanaval.client.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;


public class LoginServidor implements Runnable {
	
	
		protected Socket socket;
		protected ObjectOutputStream out;
		protected ObjectInputStream in;
		protected boolean logIn;
		protected InetAddress servAddr = null;
		protected int servPort;
		protected String nome;
		
		
	
		
		public LoginServidor(String IP, String nome) throws IOException{
			
			servAddr = InetAddress.getByName(IP);
		    servPort = Integer.parseInt("5001"); //alterar
		         
			socket = new Socket(servAddr,servPort);	
			//socket.setSoTimeout(TIMEOUT);
			
			this.nome = nome;
			
			logIn = false;	
			
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
				
			
		}
			
	

		
	@Override
	public void run() {
		while(true){			
			try {						
				if(!logIn){
					sendLoginRequest();					
				}
				
				Mensagem msg = (Mensagem) in.readObject();
				switch(msg.getType()){					
					case Macros.MSG_LOGIN_FAIL:
						sendLoginResponse();
						break;
					case Macros.MSG_LOGIN_LOGGED:
						noteAlreadyLogged();
						break;
					case Macros.MSG_LOGIN_VALIDATED:
						logIn = true;
						break;
				}					
				
			} catch (IOException  e) {
				System.out.println("Erro na ligação");
				break;
			} catch(ClassNotFoundException e){
				System.out.println("Erro ao receber mensagem");				
			}
		}		
	}	
	
	public void sendLoginResponse() throws IOException{
		//nome = pede novo nome;
		sendLoginRequest();
	}
	
	public void sendLoginRequest() throws IOException{
		Mensagem msg = new Mensagem(Macros.MSG_LOGIN_REQUEST);
		msg.setMsgText(nome);
		out.flush();
		out.writeObject(msg);
		out.flush();
	}
	
	public void noteAlreadyLogged(){
		//avisa o utilizador que já está logado
	}
}

	