package com.pdist.batalhanaval.server.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.pdist.batalhanaval.server.controlo.Jogo;
import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;

public class GameThread extends Thread{

	private Jogo jogo;
	
	private Socket jogador1;
	private Socket jogador2;
	private ObjectOutputStream out1;
	private ObjectOutputStream out2;
	private ObjectInputStream in1;
	private ObjectInputStream in2;
	

	public GameThread(Jogo jogo, Socket jogador1, Socket jogador2, ObjectInputStream in1, ObjectOutputStream out1,  ObjectInputStream in2 , ObjectOutputStream out2){
		this.jogo = jogo;
		this.jogador1 = jogador1;
		this.jogador2 = jogador2;
		
		//Alterado
		this.out1 = out1;
		this.out2 = out2;
		this.in1 = in1;
		this.in2 = in2;
		
			
		
		VarsGlobais.jogos.add(jogo);
		VarsGlobais.nJogos++;
		VarsGlobais.nClientes = VarsGlobais.nClientes-2;
		VarsGlobais.ClientesOn.remove(jogo.getC1());
		VarsGlobais.ClientesOn.remove(jogo.getC2());
		
				
	}
	
	@Override
	public void run() {
		Mensagem msg = null;
		
		
		
		if(!jogo.isStarted()){
			System.out.println("Inicia Mensagens Game Thread...");  //teste
			msg = new Mensagem(Macros.MSG_GET_TABULEIRO);
			try {
				
				out1.flush();
				out1.writeObject(msg);
				out1.flush();
				
				out2.flush();
				out2.writeObject(msg);
				out2.flush();			
				
			} catch (IOException e) {				
				System.out.println("Ligação caiu");
				VarsGlobais.jogos.remove(jogo);
				VarsGlobais.nJogos--;
				VarsGlobais.nClientes = VarsGlobais.nClientes+2;
				VarsGlobais.ClientesOn.add(jogo.getC1());
				VarsGlobais.ClientesOn.add(jogo.getC2());
				return;
			}
			
		}
		
		while(!jogo.isFim()){
			
			
		}		
	}

	public Jogo getJogo(){return jogo;}
	public void notifyAtack(int jog) throws IOException{
		if(jog == 1){
			Mensagem msg = new Mensagem(Macros.MSG_ACTUALIZAR_YOUR_TAB);
			ArrayList<Integer> array = new ArrayList<Integer>();
			for(int i = 0;i < jogo.getC1().getTabuleiro().getTabuleiro().size();i++)
				array.add(jogo.getC1().getTabuleiro().getTabuleiro().get(i).getImage());
			
			out1.flush();
			out1.writeObject(msg);
			out1.flush();
		}else{
			Mensagem msg = new Mensagem(Macros.MSG_ACTUALIZAR_YOUR_TAB);
			ArrayList<Integer> array = new ArrayList<Integer>();
			for(int i = 0;i < jogo.getC2().getTabuleiro().getTabuleiro().size();i++)
				array.add(jogo.getC2().getTabuleiro().getTabuleiro().get(i).getImage());
			
			out2.flush();
			out2.writeObject(msg);
			out2.flush();
		}
	}
	
}
