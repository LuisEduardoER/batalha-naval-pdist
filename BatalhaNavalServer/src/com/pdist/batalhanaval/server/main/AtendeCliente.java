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
	protected boolean logIn;
	protected Cliente cliente;
	
	public AtendeCliente(Socket s){
		socket = s;
		criaCliente();				
		logIn = false;
		
		
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Conexão falhou");	
			return;
		}
		
	}
	
	public void run(){
		System.out.println("NOVO CLIENTE");
		while(true){			
			//tratar mensagens recebidas e enviar respostas
			try {					
				
				Mensagem msg = (Mensagem) in.readObject();					
				switch(msg.getType()){
					case Macros.MSG_LOGIN_REQUEST: //utilizador pede para se logar
						getLoginRequest(msg);	
						break;
					case Macros.MSG_LISTA_ONLINE:
						sendListaOnline(msg);
						break;
					case Macros.MSG_LISTA_JOGOS:
						sendListaJogos(msg);
						break;
					case Macros.MSG_INICIAR_JOGO:
						sendInvite(msg);
						break;
					
				}					
			} catch (IOException | NullPointerException e) {
				System.out.println("Cliente desligou");
				VarsGlobais.ClientesOn.remove(cliente);		
				VarsGlobais.nClientes--;
				break;
			} catch (ClassNotFoundException  e) {
				System.out.println("ERR: Não é mensagem");				
			} 
		}

		
		VarsGlobais.threads.remove(this);
		VarsGlobais.nThreads--;
	}
	
	
	private boolean validaLogin(Mensagem msg){
		boolean result = true;
		
		for(int i = 0;i<VarsGlobais.ClientesOn.size();i++){
			if(VarsGlobais.ClientesOn.get(i).getNome().compareToIgnoreCase(msg.getMsgText())==0){
				result = false;
				break;
			}
		}
		
		if(result)
			cliente.setNome(msg.getMsgText());
		
		return result;
	}
	
	private void criaCliente(){
		cliente = new Cliente(socket.getInetAddress());
		cliente.setMySocket(socket);
		cliente.setOnGame(false);		
	}
	
	
	private void getLoginRequest(Mensagem msg) throws IOException{
		
		if(!logIn){
			if(!validaLogin(msg)){
				msg.setType(Macros.MSG_LOGIN_FAIL);
				out.flush();
				out.writeObject(msg);
				out.flush();		
				System.out.println("O Login falhou");
			}else{
				msg.setType(Macros.MSG_LOGIN_VALIDATED);
				out.flush();
				out.writeObject(msg);
				out.flush();
				logIn = true;
				VarsGlobais.ClientesOn.add(cliente);
				VarsGlobais.nClientes++;
				
				System.out.println("O cliente "+VarsGlobais.ClientesOn.get(VarsGlobais.nClientes-1).getNome()+" esta logado.");
				System.out.println("Estão logados "+VarsGlobais.nClientes+" clientes");
			}
		}else{
			msg.setType(Macros.MSG_LOGIN_LOGGED);
			out.flush();
			out.writeObject(msg);
			out.flush();
			System.out.println("Ja esta logado");
		}
	}

	private void sendListaOnline(Mensagem msg) throws IOException{
		msg.setType(Macros.MSG_ONLINE_RESPONSE);
		
		for(int i = 0;i<VarsGlobais.nClientes;i++)
			msg.addNomesClientes(VarsGlobais.ClientesOn.get(i).getNome());
		
		out.flush();
		out.writeObject(msg);
		out.flush();
	}
	
	private void sendListaJogos(Mensagem msg) throws IOException{
		msg.setType(Macros.MSG_JOGOS_RESPONSE);
		
		for(int i = 0;i<VarsGlobais.nJogos;i++){
			msg.addNomesJogadores1(VarsGlobais.jogos.get(i).getC1().getNome());
			msg.addNomesJogadores2(VarsGlobais.jogos.get(i).getC2().getNome());
			String nomeJogo = "Jogo num "+(i+1);
			msg.addNomesJogos(nomeJogo);
		}
		
		out.flush();
		out.writeObject(msg);
		out.flush();
	}
	
	private void sendInvite(Mensagem msg) throws IOException{
		msg.setType(Macros.MSG_PEDIDO_JOGO);
		Socket s = null;
		ObjectOutputStream out2 = null;
		
		
		for(int i = 0;i<VarsGlobais.nClientes;i++){
			if(VarsGlobais.ClientesOn.get(i).getNome().equalsIgnoreCase(msg.getMsgText())){
				s = VarsGlobais.ClientesOn.get(i).getMySocket();
				out2 = new ObjectOutputStream(s.getOutputStream());
				msg.setMsgText(cliente.getNome());
				break;
			}				
		}
		
		if(out2 != null){
			out2.flush();
			out2.writeObject(msg);
			out2.flush();
		}
	}
}
