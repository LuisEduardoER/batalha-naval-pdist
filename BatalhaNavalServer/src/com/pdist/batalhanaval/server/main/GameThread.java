package com.pdist.batalhanaval.server.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.pdist.batalhanaval.server.controlo.Jogo;
import com.pdist.batalhanaval.server.controlo.Letra;
import com.pdist.batalhanaval.server.controlo.Numero;
import com.pdist.batalhanaval.server.controlo.Ships;
import com.pdist.batalhanaval.server.controlo.Tabuleiro;
import com.pdist.batalhanaval.server.controlo.UnidadeTabuleiro;
import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;

public class GameThread extends Thread{

	private Jogo jogo;
	
	//private Socket jogador1;
	//private Socket jogador2;
	private ObjectOutputStream out1;
	private ObjectOutputStream out2;
//	private ObjectInputStream in1;
//	private ObjectInputStream in2;
	

	public GameThread(Jogo jogo, Socket jogador1, Socket jogador2, ObjectInputStream in1, ObjectOutputStream out1,  ObjectInputStream in2 , ObjectOutputStream out2){
		this.jogo = jogo;
	//	this.jogador1 = jogador1;
	//	this.jogador2 = jogador2;
		
		//Alterado
		this.out1 = out1;
		this.out2 = out2;
//		this.in1 = in1;
//		this.in2 = in2;
		
			
		
		VarsGlobais.jogos.add(jogo);
		VarsGlobais.nJogos++;
		VarsGlobais.nClientes = VarsGlobais.nClientes-2;
		VarsGlobais.ClientesOn.remove(jogo.getC1());
		VarsGlobais.ClientesOn.remove(jogo.getC2());
		
				
	}
	
	@Override
	public void run() {
		Mensagem msg = null;
		
		Tabuleiro tab1 = gerarTabuleiro();
		Tabuleiro tab2 = gerarTabuleiro();
		jogo.getC1().setTabuleiro(tab1);
		jogo.getC2().setTabuleiro(tab2);
		
		
		if(jogo.isStarted()){
			System.out.println("Inicia Mensagens Game Thread...");  //teste
			
			//a mensagem pode ser a mesma porque o tabuleiro não é random (caso contrario é preciso enviar uma mensagem diferente para cada jogador)
			//msg = new Mensagem(Macros.MSG_GET_TABULEIRO, gerarTabuleiro());
			try {
				
				msg = new Mensagem(Macros.MSG_GET_TABULEIRO, tab1);
				
				out1.flush();
				out1.writeObject(msg);
				out1.flush();
				
				msg = new Mensagem(Macros.MSG_GET_TABULEIRO, tab2);
				
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
	//public void notifyAtack(int jog) throws IOException{
	public void notifyAtack(int jog, int y, int x, int img) throws IOException{	
	
		System.out.println("-notifyAtack-"); //so para testes
		
		Letra coord_y = new Letra('a', y);
		Numero coord_x = new Numero('1', x);
		
		Mensagem msg = new Mensagem(Macros.MSG_ACTUALIZAR_YOUR_TAB, coord_y, coord_x);
		msg.setImagem(img);
		
		if(jog == 1){
					
			out1.flush();
			out1.writeObject(msg);
			out1.flush();
			
		}else{

			out2.flush();
			out2.writeObject(msg);
			out2.flush();

		}
	}
	
	//ATENCAO! ISTO NAO ESTA A GERAR ALEATORIAMENTE, OS BARCOS SAO FIXOS
	public Tabuleiro gerarTabuleiro(){
		
		Tabuleiro tab;
		ArrayList<UnidadeTabuleiro> unidades = new ArrayList<UnidadeTabuleiro>();
		
		//criar ArrayList de UnidadesTabuleiro 10x10
		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				unidades.add(new UnidadeTabuleiro(j+1, i+1) ); //x-j, y-i
			}
		}
		
		
		//gerar posição dos barcos OU (neste caso) definir posições fixas
		ArrayList<UnidadeTabuleiro> unidades_barco1 = new ArrayList<UnidadeTabuleiro>();
		ArrayList<UnidadeTabuleiro> unidades_barco2 = new ArrayList<UnidadeTabuleiro>();
		ArrayList<UnidadeTabuleiro> unidades_barco3 = new ArrayList<UnidadeTabuleiro>();
		
		  //22,23,24,25 -> posições (y=3 e x=3,4,5,6)
		unidades.get(22).setOcupied(true);
		unidades.get(23).setOcupied(true);
		unidades.get(24).setOcupied(true);
		unidades.get(25).setOcupied(true);
		
		unidades.get(22).setImage(Macros.IMAGEM_BARCO_ESQ);
		unidades.get(23).setImage(Macros.IMAGEM_BARCO_MEIO);
		unidades.get(24).setImage(Macros.IMAGEM_BARCO_MEIO);
		unidades.get(25).setImage(Macros.IMAGEM_BARCO_DIR);
		
		unidades_barco1.add( unidades.get(22) );
		unidades_barco1.add( unidades.get(23) );
		unidades_barco1.add( unidades.get(24) );
		unidades_barco1.add( unidades.get(25) );
		  //criar o barco em si
		Ships barco1 = new Ships(unidades_barco1);
		
		  //74,75,76,77,78 -> posições (y=8 e x=5,6,7,8,9)
		unidades.get(74).setOcupied(true);
		unidades.get(75).setOcupied(true);
		unidades.get(76).setOcupied(true);
		unidades.get(77).setOcupied(true);
		unidades.get(78).setOcupied(true);
		
		unidades.get(74).setImage(Macros.IMAGEM_BARCO_ESQ);
		unidades.get(75).setImage(Macros.IMAGEM_BARCO_MEIO);
		unidades.get(76).setImage(Macros.IMAGEM_BARCO_MEIO);
		unidades.get(77).setImage(Macros.IMAGEM_BARCO_MEIO);
		unidades.get(78).setImage(Macros.IMAGEM_BARCO_DIR);
		
		unidades_barco2.add( unidades.get(74) );
		unidades_barco2.add( unidades.get(75) );
		unidades_barco2.add( unidades.get(76) );
		unidades_barco2.add( unidades.get(77) );
		unidades_barco2.add( unidades.get(78) );
		
		  //criar o barco em si
		Ships barco2 = new Ships(unidades_barco2);
		
		  //5,6 -> posições (y=1 e x=6,7)
		unidades.get(5).setOcupied(true);
		unidades.get(6).setOcupied(true);
		unidades.get(5).setImage(Macros.IMAGEM_BARCO_ESQ);
		unidades.get(6).setImage(Macros.IMAGEM_BARCO_DIR);
		
		unidades_barco3.add( unidades.get(5) );
		unidades_barco3.add( unidades.get(6) );
		
		  //criar o barco em si
		Ships barco3 = new Ships(unidades_barco3);
		
		  //criar o arraylist dos barcos
		ArrayList<Ships> barcos = new ArrayList<Ships>();
		barcos.add(barco1);
		barcos.add(barco2);
		
		//criar o tabuleiro
		tab = new Tabuleiro(unidades, barcos);
		
		//CASO O TABULEIRO SEJA ALEATORIO É PRECISO ALTERAR ISTO
		//jogo.getC1().setTabuleiro(tab);
		//Tabuleiro tab2 = new Tabuleiro(unidades, barcos);
		//jogo.getC2().setTabuleiro(tab2);
		
		return tab;
	}
	
	
	
}
