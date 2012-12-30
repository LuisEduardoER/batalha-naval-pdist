package com.pdist.batalhanaval.client.jogo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


import com.pdist.batalhanaval.client.main.AtendeServidor;
import com.pdist.batalhanaval.client.main.BatalhaNaval_Client;
import com.pdist.batalhanaval.client.main.SocketClient_TCP;
import com.pdist.batalhanaval.server.controlo.Letra;
import com.pdist.batalhanaval.server.controlo.Numero;
import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;

public class Jogo implements ActionListener{
	
	private JButton[][] botao = new JButton[12][12];
	private JButton[][] botaoAdv = new JButton[12][12];
	
	private JFrame BatalhaNavalUI;
	
	private static JLabel lblJogador_1 = new JLabel("<nome?>"); //label JOGADOR 1
	private static JLabel lblJogador_2 = new JLabel("<nome?>"); //laber JOGADOR 2
	
	private ImageIcon aguaAlvo = new ImageIcon("Imagens/aguaFail.png");
	private ImageIcon agua = new ImageIcon("Imagens/agua.png");
	private ImageIcon explosao = new ImageIcon("Imagens/explosao.png");
	private ImageIcon mira = new ImageIcon("Imagens/mira.png");
	//Barcos
	private ImageIcon barcoEsq = new ImageIcon("Imagens/barcos/barco_esq.png");
	private ImageIcon barcoMeio = new ImageIcon("Imagens/barcos/barco_meio.png");
	private ImageIcon barcoDir = new ImageIcon("Imagens/barcos/barco_dir.png");	
	
	private ImageIcon barcoEsqFogo = new ImageIcon("Imagens/barcos/fogo/barco_esq_f.png");
	private ImageIcon barcoMeioFogo = new ImageIcon("Imagens/barcos/fogo/barco_meio_f.png");
	private ImageIcon barcoDirFogo = new ImageIcon("Imagens/barcos/fogo/barco_dir_f.png");	

	
	private String background = "Imagens/outros/background2.jpg";
	
		
	
	private ArrayList<Integer> quadAtacados = new ArrayList<Integer>(); //para saber que coordenadas ja foram atacadas	

		
	private Thread AtendeServidor; //thread do AtendeServidor
	
	
	public Jogo(JDialog listajogadores) {
		
		
		BatalhaNavalUI = BatalhaNaval_Client.getBatalhaNavalUI();			
			
		BatalhaNaval_Client.loadBackground(background);		
		BatalhaNaval_Client.loadMenuBar();	
				
		
		//======(PROVISORIO) EXEMPLO INTERFACE JOGO============
				criaMapaUtilizador(70,70); 				
				criaMapaAdversario(400,70);
				
				
		criaLabels();
								
				
		BatalhaNavalUI.repaint(); //necessario fazer repaint depois..			
		
		
		//CRIAR A THREAD DE ATENDIMENTO de pedidos do servidor
		
		AtendeServidor = new AtendeServidor(BatalhaNavalUI,listajogadores);
		AtendeServidor.start();
				
		
	}
	
	
	//EVENTO BOTOES
		 public void actionPerformed(ActionEvent e) {	
			 
			 StringTokenizer tokens = new StringTokenizer(new String(e.getSource().toString()),",");
			 tokens.nextToken().trim();
			 int x = Integer.parseInt(tokens.nextToken().trim());
		     int y = Integer.parseInt(tokens.nextToken().trim());	 
			  
		     for(int i=0; i<10; i++){
				   for(int j=0; j<10; j++){	
					   
					   //Botoes do MAPA ESQUERDO (so para o jogador visualizar o seu mapa)
					   if(botao[i][j].getBounds().x == x &&
							   botao[i][j].getBounds().y == y)
					   {	
						   botao[i][j].setIcon(aguaAlvo);					   
							//  BatalhaNavalUI.repaint();	
						   
						   //Gerar aleatoriamente os barcos do cliente
					   
					   }
					   
					   
					   //==================
					   
					   
					   
					   //Botoes do MAPA DIREITO (para o jogador ter a possibilidade de acertar nos barcos do inimigo)
					   
					   else if(botaoAdv[i][j].getBounds().x == x &&
							   botaoAdv[i][j].getBounds().y == y)
					   {				 		
						   
						   
					   //=======TUDO FEITO PELO SERVER - remover depois
						   //SE NAO ESTA A JOGAR NAO PODERA INTERAGIR COM OS BOTOES!
						//   if(VarsGlobais.NovoJogoThreadCreated == false)
							//   return;
						   //SE NAO FOR O SEU TURNO NAO PODERA INTERAGIR COM OS BOTOES!
						   //if(VarsGlobais.MeuTurno == false)
						   //	   return;
						   //verificar se ja atacou esta coordenada
						   /*if(quadAtacados.isEmpty() != true){
							   for(int k=0; k<quadAtacados.size(); k++){
								   if(quadAtacados.get(k) == (( (i+1)*10 ) + (j+1)) ){
									   return; //ja foi atacada
								   }
							   }
						   }*/
						   
						   
						   
						  //====ATACAR UMA COORDENADA===
						   
						   //verificar se é o turno do jogador (FEITO PELO SERVER)
						   
						   //enviar coordenada a atacar ao servidor
						      //Letra y = new Letra(char, numero);
						      //Numero x = new Numero(char, numero);
						   Letra coord_y = new Letra('a', y);
						   Numero coord_x = new Numero('1', x);
						   
						   Mensagem msg = new Mensagem(Macros.MSG_ATACAR, coord_y, coord_x); //atacar, Y(letra), X(numero)
						   try{
							   SocketClient_TCP.getOut().flush();
							   SocketClient_TCP.getOut().writeObject(msg);
							   SocketClient_TCP.getOut().flush();
						   }catch(IOException exp){
							   JOptionPane.showMessageDialog(BatalhaNavalUI, "ERRO a enviar a coordenada a atacar(IOException)");
						   }
						   
						   //recebe a resposta (acertou?) no AtendeServidor
							   
						  //=====ATACAR UMA COORDENADA (fim)=================
						   
						   /**ISTO É FEITO NO ATENDE SERVIDOR !!!!!!*/						    
						   
						   /*
						   //enviar coordenada a atacar ao servidor
						   //receber resposta, se acertou ou nao
						   //mudar icone conforme

						   try{
							   	//socket para enviar as coordenadas				   
							    
							    SocketClient_TCP.getSocket().setSoTimeout(1500); //timeout 1,5s
						   	   	//enviar coordenadas
						   	   	ObjectOutputStream out = new ObjectOutputStream(SocketClient_TCP.getSocket().getOutputStream());
						  //#############AS MENSAGENS ESTAO PRE-DEFINIDAS E TÊM ESTRUTURA FIXA. TÊM DE VER COMO ESTÀ A SE TRATADO NO SERVIDOR !#########
						   	   	out.writeObject(new Integer(( (i+1)*10 ) + (j+1)) ); //ex.: enviar x6, y3 -> 60+3 = 63 (os +1 é para acertar o index)
						   	   	out.flush();
						   	   	}catch(Exception exc){ 
						   	   		JOptionPane.showMessageDialog(BatalhaNavalUI.getContentPane(),"Erro a enviar coordenada ao servidor");
						   	   		//VarsGlobais.MeuTurno = true;
						   	   		//return;
						   	   	}
						   		
						   //receber resposta do server(acertou num barco?)
						   try{
						   		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
						   		int resposta = (Integer) in.readObject();	//resposta do servidor: 1001-barco 1000-agua
						   			
						   		if(resposta == 1001) //1001 -> barco
						   			botaoAdv[i][j].setIcon(explosao);
						   		else
						   			botaoAdv[i][j].setIcon(aguaAlvo); //agua
						   			
						   		//atacou a coordenada com sucesso
						   		quadAtacados.add( ( (i+1)*10 ) + (j+1) ); //ex.: atacou x6, y3 -> 60+3 = 63
						   			
						   }catch(Exception exc2){
						   		JOptionPane.showMessageDialog(BatalhaNavalUI.getContentPane(),"Erro a receber a resposta do servidor(ao enviar coordenada)");
						   		//VarsGlobais.MeuTurno = true;
						   		//return;
						   }			   		
						   //RETIRAR, APENAS PARA TESTES!!!	
						   //fechar socket
						   /*try {
								socket.close();
							} catch (IOException e1) {
							
								e1.printStackTrace();
							}*/
						   
						   
						 //SO PARA TESTES, o server é que depois diz qual o icon novo
						   botaoAdv[i][j].setIcon(aguaAlvo);				   
							  
					   
					   }
					   
					   
				   }
		     }
		 }
		 
		 public void criaMapaUtilizador(int x,int y) //TESTE
		 {
			  for(int i=0; i<Macros.SIZE_X; i++){
				   for(int j=0; j<Macros.SIZE_Y; j++){					   
					 
				    botao[i][j] = new JButton(agua);			    	
				    botao[i][j].setBounds(x+(j*29), y+(i*29), Macros.TAM_X, Macros.TAM_Y);
				    botao[i][j].addActionListener((ActionListener) this);			
				    botao[i][j].setDisabledIcon(agua); //senao aparecia cinzento quando nao esta em "enabled"
				    botao[i][j].setEnabled(false);				  
				
				    BatalhaNavalUI.getContentPane().add(botao[i][j]);
				   }
			  }
		      //=====Teste BARCOS===	
			  botao[2][1].setIcon(barcoEsq); botao[2][1].setDisabledIcon(barcoEsq);
			  botao[2][2].setIcon(barcoMeio); botao[2][2].setDisabledIcon(barcoMeio);	
			  botao[2][3].setIcon(barcoDir); botao[2][3].setDisabledIcon(barcoDir);	
			  
			  botao[8][7].setIcon(barcoEsqFogo); botao[8][7].setDisabledIcon(barcoEsqFogo);
			  botao[8][8].setIcon(barcoMeioFogo); botao[8][8].setDisabledIcon(barcoMeioFogo);
			  botao[8][9].setIcon(barcoDirFogo); botao[8][9].setDisabledIcon(barcoDirFogo);
			  
			  botao[5][4].setIcon(barcoEsq); botao[5][4].setDisabledIcon(barcoEsq);	
			  botao[5][5].setIcon(barcoMeio); botao[5][5].setDisabledIcon(barcoMeio);
			  botao[5][6].setIcon(barcoMeio); botao[5][6].setDisabledIcon(barcoMeio);
			  botao[5][7].setIcon(barcoDir); botao[5][7].setDisabledIcon(barcoDir);
		 }

		 public void criaMapaAdversario(int x,int y) //TESTE
		 {
			  for(int i=0; i<10; i++){
				   for(int j=0; j<10; j++){			   
					 
				    botaoAdv[i][j] = new JButton(agua);		 
				    botaoAdv[i][j].setBounds(x+(j*29), y+(i*29), Macros.TAM_X, Macros.TAM_Y);
				    botaoAdv[i][j].addActionListener((ActionListener) this);	
				    botaoAdv[i][j].setRolloverIcon(mira);
				    BatalhaNavalUI.getContentPane().add(botaoAdv[i][j]);				    
				  
				   }
			  }
		 }
	
		 
		 public void criaLabels()
		 {
			    JLabel label = new JLabel("Jogador:");
				label.setForeground(Color.BLACK);
				label.setFont(new Font("Tahoma", Font.BOLD, 16));
				label.setBounds(89, 32, 83, 37);
				BatalhaNavalUI.getContentPane().add(label);
				
			    //Nome Jogador 1
				lblJogador_1.setForeground(Color.BLACK);
				lblJogador_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblJogador_1.setBounds(169, 32, 163, 37);
				BatalhaNavalUI.getContentPane().add(lblJogador_1);		
				
				JLabel label_1 = new JLabel("Inimigo:");
				label_1.setForeground(Color.BLACK);
				label_1.setFont(new Font("Tahoma", Font.BOLD, 16));
				label_1.setBounds(415, 29, 73, 43);
				BatalhaNavalUI.getContentPane().add(label_1);			
				
				 //Nome Jogador 2
				lblJogador_2.setForeground(Color.BLACK);
				lblJogador_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblJogador_2.setBounds(490, 29, 192, 43);
				BatalhaNavalUI.getContentPane().add(lblJogador_2);
				
		 }
		 
		 public static void setNomeJogador1(String nome)
		 {
			 lblJogador_1.setText(nome);			 
		 }
		 
		 public static void setNomeJogador2(String nome)
		 {
			 lblJogador_2.setText(nome);			 
		 }
		 
		
	
}
