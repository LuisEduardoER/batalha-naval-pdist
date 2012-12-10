package com.pdist.batalhanaval.server.main;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.pdist.batalhanaval.server.controlo.Jogo;
import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;

public class GameThread implements Runnable{

	private Jogo jogo;
	private Socket jogador1;
	private Socket jogador2;
	private ObjectOutputStream out1;
	private ObjectOutputStream out2;
	
	
	public GameThread(Jogo jogo, Socket jogador1, Socket jogador2){
		this.jogo = jogo;
		this.jogador1 = jogador1;
		this.jogador2 = jogador2;
		
		try {
			out1 = new ObjectOutputStream(jogador1.getOutputStream());
			out2 = new ObjectOutputStream(jogador2.getOutputStream());
		} catch (IOException e) {
			System.out.println("Conexão falhou");	
			return;
		}
		
		VarsGlobais.jogos.add(jogo);
		VarsGlobais.nJogos++;
		VarsGlobais.nClientes = VarsGlobais.nClientes-2;
		VarsGlobais.ClientesOn.remove(jogo.getC1());
		VarsGlobais.ClientesOn.remove(jogo.getC2());
		
		jogo.setStarted(true);
		
	}
	
	@Override
	public void run() {
		Mensagem msg = new Mensagem(Macros.MSG_JOGAR);
		
		while(!jogo.isFim()){
			try{
				if(jogo.getTurn() == 1)//jogador 1 a jogar
				{
					out1.flush();
					out1.writeObject(msg);
					out1.flush();
				}else // jogador 2 a jogar
				{
					out2.flush();
					out2.writeObject(msg);
					out2.flush();	
				}
			}catch(IOException e){
				System.out.println("Ligação caiu");
				VarsGlobais.jogos.remove(jogo);
				VarsGlobais.nJogos--;
				VarsGlobais.nClientes = VarsGlobais.nClientes+2;
				VarsGlobais.ClientesOn.add(jogo.getC1());
				VarsGlobais.ClientesOn.add(jogo.getC2());
				break;
			}
		}
		
	}

}
