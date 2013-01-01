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
			in = new ObjectInputStream(socket.getInputStream());    //so pode ser criado uma vez (no cria cliente ja actualizei)
			out = new ObjectOutputStream(socket.getOutputStream());
			
			//meti no criaCliente() tb mas nao tava a dar nada, dps alterar isto ou deixar tar
			cliente.setIn(in);
			cliente.setOut(out);
			
		} catch (IOException e) {
			System.out.println("Conex�o falhou");	
			return;
		}
		
	}
	
	public void run(){		
		while(true){			
			//tratar mensagens recebidas e enviar respostas
			try {					
				
				//TODO O jogador quer criar um novo jogo
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
					case Macros.MSG_PEDIDO_RESPONSE: //Resposta do 2� utilizador ao pedido de jogo						
						getResponse(msg);
						break;
					case Macros.MSG_SET_TABULEIRO: //Recebeu tabuleiro do utilizador	
						setTabuleiro(msg);
						break;
					case Macros.MSG_ATACAR:
						System.out.println("atacar?"); //so para testes
						if(game!=null)
							if(game.getJogo().isStarted()){
								System.out.println("o jogo esta iniciado! ATACAAAAR!!"); //so para testes
								setAtaque(msg);
							}
						break;
					
				}					
			} catch (IOException | NullPointerException e) {
				System.out.println("Cliente desligou");
				VarsGlobais.ClientesOn.remove(cliente);		
				VarsGlobais.nClientes--;
				break;
			} catch (ClassNotFoundException  e) {
				System.out.println("ERR: N�o � mensagem");				
			} 
		}

		
		VarsGlobais.threads.remove(this);
		VarsGlobais.nThreads--;
	}
	
	
	private boolean validaLogin(Mensagem msg){
		boolean result = true;
		
		//ver se o nome n�o � repetido
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
		
		//New.. IN e OUT
		cliente.setIn(in);
		cliente.setOut(out);
		
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
				
				notifyChanges();
				
				System.out.println("O cliente "+VarsGlobais.ClientesOn.get(VarsGlobais.nClientes-1).getNome()+" esta logado.");
				System.out.println("Est�o logados "+VarsGlobais.nClientes+" clientes");
			}
		}else{
			msg.setType(Macros.MSG_LOGIN_LOGGED);
			out.flush();
			out.writeObject(msg);
			out.flush();
			System.out.println("Ja esta logado");
		}
	}
		
	private void notifyChanges(){
		for(int i = 0; i<VarsGlobais.ClientesOn.size()-2;i++){
			Mensagem msg = new Mensagem(Macros.MSG_NOTIFY_CHANGES);
			try {
				ObjectOutputStream o = (ObjectOutputStream) VarsGlobais.ClientesOn.get(i).getMySocket().getOutputStream();
			
				o.flush();
				o.writeObject(msg);
				o.flush();
				
			} catch (IOException e) {
				//admite-se que o cliente fechou
				try {
					VarsGlobais.ClientesOn.get(i).getMySocket().close();				
					VarsGlobais.ClientesOn.get(i).getMyThread().finalize();
					VarsGlobais.ClientesOn.remove(i);
					VarsGlobais.nClientes--;
				} catch (Throwable e1) {					
					e1.printStackTrace();
				}			
			}			
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
		
		System.out.println("Responde a Lista de Jogos");
	}
	
	private void sendInvite(Mensagem msg) throws IOException{
		msg.setType(Macros.MSG_PEDIDO_JOGO);
		System.out.println("Enviado convite de: "+cliente.getNome()+"\nPara: "+msg.getMsgText());
		
		//no campo MsgText vem o nome do jogador a convidar e depois vai o nome de quem convidou
		for(int i = 0;i<VarsGlobais.nClientes;i++){
			if(VarsGlobais.ClientesOn.get(i).getNome().equalsIgnoreCase(msg.getMsgText())){
				msg.setMsgText(cliente.getNome());
				VarsGlobais.ClientesOn.get(i).getMyThread().out.flush();
				VarsGlobais.ClientesOn.get(i).getMyThread().out.writeObject(msg);
				System.out.println("SENT");
				break;
			}				
		}
		
		
	}

	private void getResponse(Mensagem msg) throws IOException{
		msg.setType(Macros.MSG_INICIAR_RESPONSE);
		
		for(int i = 0;i<VarsGlobais.nClientes;i++){
			if(VarsGlobais.ClientesOn.get(i).getNome().equalsIgnoreCase(msg.getMsgText())){				
				VarsGlobais.ClientesOn.get(i).getMyThread().out.flush();
				VarsGlobais.ClientesOn.get(i).getMyThread().out.writeObject(msg);	
				VarsGlobais.ClientesOn.get(i).getMyThread().out.flush();
				break;
			}				
		}
				
		if(msg.getResponseText().equals(Macros.ACEITAR_PEDIDO)){
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
		j.setStarted(true);
				
		System.out.println("==\nNovo Jogo!\n"+jog1.getNome()+" VS "+jog2.getNome());
		

		GameThread jogo = new GameThread(j,jog1.getMySocket(), jog2.getMySocket()
				,jog1.getIn(), jog1.getOut()
				,jog2.getIn(), jog2.getOut());
		
		jog2.getMyThread().setGame(jogo);		
		game = jogo;
		notifyChanges();
		jogo.start(); //inicia a thread aqui...
	}

	private void setTabuleiro(Mensagem msg){
	/*	
		Tabuleiro tab = new Tabuleiro();
		//ArrayList<Integer> t = msg.getTabuleiro();
		tab = msg.getTabuleiro();
		
		//TEM DE SER ALTERADO!! O TABULEIRO ESTA A SER CRIADO NA GAMETHREAD
		
		
		UnidadeTabuleiro uni = new UnidadeTabuleiro(); //TODO UnidadeTabuleiro uni = null; ???, UnidadeTabuleiro uni = new UnidadeTabuleiro(); nao?
			
		for(int i = 0; i<Macros.SIZE_X;i++){
			for(int j = 0;j<Macros.SIZE_Y;j++){
				uni.setImage(t.get((i*10)+j)); // i*10+ j pk? //TODO isto ta correcto? 
				uni.setX(i*30);
				uni.setY(j*30);
				if(uni.getImage() == Macros.IMAGEM_BARCO_ESQ || uni.getImage() == Macros.IMAGEM_BARCO_MEIO || uni.getImage() == Macros.IMAGEM_BARCO_DIR )
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
		*/
	}

	private void setGame(GameThread game){this.game = game;}
	
	private void setAtaque(Mensagem msg){
		
		int t = 0;
		int imagem = 0; //imagem da quadricula
		
		int posX = msg.getNumero().getPosX();
		int posY = msg.getLetra().getPosY();
		
		
		//ATACA NO TABULEIRO ADVERSARIO
		if(game.getJogo().getC1().getNome().equalsIgnoreCase(cliente.getNome())){ //jogador 1
			if(game.getJogo().getTurn() == 1){
				System.out.println("JOGADOR1 TURNO");
				if(game.getJogo().getC2().getTabuleiro().getUnidade(posY, posX).isShooted() == true){
					msg.setType(Macros.MSG_ATACAR_COORD_REPETIDA);
					System.out.println("tentou atacar coordenada repetida");
				}else{
					if(game.getJogo().getC2().getTabuleiro().getUnidade(posY, posX).isBoat() == true)
						msg.setType(Macros.MSG_ATACAR_SUCCESS); //se for um barco
					else
						msg.setType(Macros.MSG_ATACAR_FAIL); //se nao for um barco (agua)
						
					game.getJogo().getC2().getTabuleiro().getUnidade(posY, posX).setShooted(true);
					game.getJogo().setTurn(2);	
					t = 2;
					
					
					imagem = game.getJogo().getC2().getTabuleiro().getUnidade(posY, posX).getImage();
				}
			}
			
			
		}else{												//jogador 2
			
			if(game.getJogo().getTurn() == 2){
				System.out.println("JOGADOR 2 TURNO"); //teste
				if(game.getJogo().getC1().getTabuleiro().getUnidade(posY, posX).isShooted() == true){
					msg.setType(Macros.MSG_ATACAR_COORD_REPETIDA);
					System.out.println("tentou atacar coordenada repetida");
				}else{
						if(game.getJogo().getC1().getTabuleiro().getUnidade(posY, posX).isBoat() == true)
							msg.setType(Macros.MSG_ATACAR_SUCCESS); //se for um barco
						else
							msg.setType(Macros.MSG_ATACAR_FAIL); //se nao for um barco (agua)
						
						
						game.getJogo().getC1().getTabuleiro().getUnidade(posY, posX).setShooted(true);
						game.getJogo().setTurn(1);	
						t = 1;
						
						imagem = game.getJogo().getC1().getTabuleiro().getUnidade(posY, posX).getImage();
				}
			}
		}
			
		try {
			out.flush();
			out.writeObject(msg);
			out.flush();
			
			System.out.println(msg.getType()); //para testes
			
			
			if( (msg.getType() == Macros.MSG_ATACAR_SUCCESS) || (msg.getType() == Macros.MSG_ATACAR_FAIL) )
				game.notifyAtack(t, posY, posX, imagem);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
			
	
}



