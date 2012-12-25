package com.pdist.batalhanaval.server.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.pdist.batalhanaval.server.controlo.Cliente;
import com.pdist.batalhanaval.server.controlo.Jogo;
import com.pdist.batalhanaval.server.controlo.Tabuleiro;
import com.pdist.batalhanaval.server.controlo.UnidadeTabuleiro;
import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;

public class AtendeCliente extends Thread{
	
	protected Socket socket;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;
	protected boolean logIn;
	protected Cliente cliente;
	protected GameThread game;
	
	public AtendeCliente(Socket s){
		game = null;
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
		while(true){			
			//tratar mensagens recebidas e enviar respostas
			try {					
				
				//TODO O jogador quer criar um novo jogo / seleciona um jogo e entra
				//TODO rever a cena do tabuleiro, n vale a pena complicar muito
				
				Mensagem msg = (Mensagem) in.readObject();					
				switch(msg.getType()){
					case Macros.MSG_LOGIN_REQUEST: //utilizador pede para se logar
						getLoginRequest(msg);	
						break;
					case Macros.MSG_LISTA_ONLINE: //o utilizador pede a lista de jogadores
						sendListaOnline(msg);
						break;
					case Macros.MSG_LISTA_JOGOS: //o utilizador pede a lista de jogos a decorrer
						sendListaJogos(msg);
						break;
					case Macros.MSG_INICIAR_JOGO: //o utilizador faz um pedido de jogo
						sendInvite(msg);
						break;
					case Macros.MSG_PEDIDO_RESPONSE: //Resposta do 2º utilizador ao pedido de jogo
						getResponse(msg);
						break;
					case Macros.MSG_SET_TABULEIRO: //Recebeu tabuleiro do utilizador
						setTabuleiro(msg);
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
		
		//ver se o nome não é repetido
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
		cliente.setMyThread(this);
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
		
		/*
		for(int i = 0;i<VarsGlobais.nJogos;i++){
			msg.addNomesJogadores1(VarsGlobais.jogos.get(i).getC1().getNome());
			msg.addNomesJogadores2(VarsGlobais.jogos.get(i).getC2().getNome());
			String nomeJogo = "Jogo num "+(i+1);
			msg.addNomesJogos(nomeJogo);
		}*/
		
		//PROVISARIO (pa teste)		
		msg.addNomesJogos(" ( Exemplo only )");
		msg.addNomesJogos("Jogador 1 vs Jogador 2");
		msg.addNomesJogos("Mitra vs Cigano");
		msg.addNomesJogos("Xino vs Americano");
		msg.addNomesJogos("barbosa vs duraes");	
		msg.addNomesJogos("batatas vs ketchup");
		msg.addNomesJogos("3");
		msg.addNomesJogos("4");
		msg.addNomesJogos("5");
		msg.addNomesJogos("6");
		msg.addNomesJogos("7");
		msg.addNomesJogos("8");


		out.flush();
		out.writeObject(msg);
		out.flush();
		
		System.out.println("Responde a Lista de Jogos");
	}
	
	private void sendInvite(Mensagem msg) throws IOException{
		msg.setType(Macros.MSG_PEDIDO_JOGO);
		Socket s = null;
		ObjectOutputStream out2 = null;
		
		//no campo MsgText vem o nome do jogador a convidar e depois vai o nome de quem convidou
		
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

	private void getResponse(Mensagem msg) throws IOException{
		msg.setType(Macros.MSG_INICIAR_RESPONSE);
		
		//se for ignoarar o próprio cliente deve ter um timeout para fechar o pedido
		if(msg.getResponseText() != Macros.IGNORAR_PEDIDO)
			out.writeObject(msg);
		
		if(msg.getResponseText() == Macros.ACEITAR_PEDIDO){
			Cliente c2 = null;
			for(int i = 0; i< VarsGlobais.nClientes;i++)
				if(VarsGlobais.ClientesOn.get(i).getNome().equalsIgnoreCase(msg.getMsgText())){
					c2 = VarsGlobais.ClientesOn.get(i);
					break;
				}
			
			startNewGame(cliente,c2);
		}
	}
	
	private void startNewGame(Cliente jog1, Cliente jog2){
		Jogo j = new Jogo();
		j.setC1(jog1);
		j.setC2(jog2);
				
		GameThread jogo = new GameThread(j,jog1.getMySocket(), jog2.getMySocket());
		
		jog2.getMyThread().setGame(jogo);		
		game = jogo;
	}

	@SuppressWarnings("null")
	private void setTabuleiro(Mensagem msg){
		
		Tabuleiro tab = new Tabuleiro();
		ArrayList<Integer> t = msg.getTabuleiro();
			
		UnidadeTabuleiro uni = null;
			
		for(int i = 0; i<Macros.SIZE_X;i++){
			for(int j = 0;j<Macros.SIZE_Y;j++){
				uni.setImage(t.get((i*10)+j));
				uni.setX(i*30);
				uni.setY(j*30);
				if(uni.getImage() == Macros.IMAGEM_BARCO_1 || uni.getImage() == Macros.IMAGEM_BARCO_2)
					uni.setOcupied(true);
				else
					uni.setOcupied(false);
					
				
				
				if(uni.getImage() == Macros.IMAGEM_SHOTED)
					uni.setShooted(true);
				else
					uni.setShooted(false);
				
				
				
				tab.getTabuleiro().add(uni);					
			}
		}
			
		
		
		if(cliente.getNome().equalsIgnoreCase(game.getJogo().getC1().getNome())){
			game.getJogo().getC1().setTabuleiro(tab);
			game.getJogo().getC1().getTabuleiro().setShipsOnTab();
		}else{
			game.getJogo().getC2().setTabuleiro(tab);
			game.getJogo().getC2().getTabuleiro().setShipsOnTab();
		}
		
		if(game.getJogo().getC1().getTabuleiro() != null && game.getJogo().getC2().getTabuleiro() != null)
			game.getJogo().setStarted(true);
		
	}

	private void setGame(GameThread game){this.game = game;}
}



