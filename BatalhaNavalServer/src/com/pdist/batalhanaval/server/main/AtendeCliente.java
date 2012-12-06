package com.pdist.batalhanaval.server.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.pdist.batalhanaval.server.controlo.Cliente;
import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;

public class AtendeCliente extends Thread{
	
	protected Socket socket;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;
	protected boolean logIn = false;
	protected boolean waitingResponse;
	protected Cliente cliente;
	
	public AtendeCliente(Socket s){
		socket = s;
		CriaCliente();
		
		waitingResponse = false;
		
		try {
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
			
			if(waitingResponse){
				Mensagem msg = (Mensagem) in.readObject();
				if(msg.getType() == Macros.MSG_LOGIN_RESPONSE){
					if(!validaLogin(msg)){
						msg.setType(Macros.MSG_LOGIN_FAIL);
						out.flush();
						out.writeObject(msg);
						out.flush();												
					}else{
						logIn = true;
						waitingResponse = false;
						VarsGlobais.ClientesOn.add(cliente);
					}
				}
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// se o objecto nao for mensagem
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		while(true){
			//tratar mensagens recebidas e enviar respostas
		}
	}
	
	
	private boolean validaLogin(Mensagem msg){
		boolean result = true;
		
		for(int i = 0;i<VarsGlobais.ClientesOn.size();i++){
			if(msg.getCliente().getNome().compareToIgnoreCase(msg.getMsgText())==0){
				result = false;
				break;
			}
		}
		
		if(result)
			cliente.setNome(msg.getMsgText());
		
		
		return result;
	}
	
	private void CriaCliente(){
		cliente = new Cliente(socket.getInetAddress());
		cliente.setOnGame(false);		
	}
	

}
