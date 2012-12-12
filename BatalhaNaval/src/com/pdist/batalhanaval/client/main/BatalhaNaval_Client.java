package com.pdist.batalhanaval.client.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.*;

import com.pdist.batalhanaval.client.dialogs.NovoJogo_Ip;
import com.pdist.batalhanaval.client.dialogs.NovoJogo_Multicast;
import com.pdist.batalhanaval.server.macros.Macros;

import java.awt.Toolkit;
import java.awt.SystemColor;


public class BatalhaNaval_Client implements ActionListener {

	private JFrame BatalhaNavalUI;
	private JButton[][] botao = new JButton[12][12];
	private JButton[][] botaoAdv = new JButton[12][12];
	
	private ImageIcon aguaAlvo = new ImageIcon("Imagens/aguaFail.png");
	private ImageIcon agua = new ImageIcon("Imagens/agua.png");
	private ImageIcon explosao = new ImageIcon("Imagens/explosao.png");
	private ImageIcon mira = new ImageIcon("Imagens/mira.png");
	//Barcos
	private ImageIcon barcoPequeno1 = new ImageIcon("Imagens/barcos/barcoPequeno_1.png");
	private ImageIcon barcoPequeno2 = new ImageIcon("Imagens/barcos/barcoPequeno_2.png");	
	private ImageIcon barcoPequenoFIRE1 = new ImageIcon("Imagens/barcos/barcoPequenoFIRE_1.png");
	private ImageIcon barcoPequenoFIRE2 = new ImageIcon("Imagens/barcos/barcoPequenoFIRE_2.png");
	private ImageIcon barcoMedio1 = new ImageIcon("Imagens/barcos/barcoMedio_1.png");
	private ImageIcon barcoMedio2 = new ImageIcon("Imagens/barcos/barcoMedio_2.png");
	private ImageIcon barcoMedio3 = new ImageIcon("Imagens/barcos/barcoMedio_3.png");
	private ImageIcon barcoMedio4 = new ImageIcon("Imagens/barcos/barcoMedio_4.png");
	
	private static JLabel lblJogador_1 = new JLabel("<nome>");
	private static JLabel lblJogador_2 = new JLabel("<nome>");
	private static JLabel lblEstado = new JLabel("a aguardar login... (teste)");
	
	private ArrayList<Integer> quadAtacados = new ArrayList<Integer>(); //para saber que coordenadas ja foram atacadas	
	//PARA LIGA��ES
	private Socket socket = null;

	public static void main(String[] args)
	{		
		BatalhaNaval_Client window = new BatalhaNaval_Client();
		window.BatalhaNavalUI.setVisible(true);				
	}


	public BatalhaNaval_Client() {		
		initialize();
	}

	
	private void initialize() {
		BatalhaNavalUI = new JFrame();
		BatalhaNavalUI.setIconImage(Toolkit.getDefaultToolkit().getImage(BatalhaNaval_Client.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		BatalhaNavalUI.setResizable(false);
		BatalhaNavalUI.setTitle("Batalha Naval v0.1");
		BatalhaNavalUI.getContentPane().setBackground(SystemColor.controlHighlight);
		BatalhaNavalUI.setBounds(100, 100, 775, 500);
		BatalhaNavalUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BatalhaNavalUI.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.setBounds(0, 0, 800, 21);
		BatalhaNavalUI.getContentPane().add(menuBar);
		
		JMenu mnJogo = new JMenu("Jogo");
		menuBar.add(mnJogo);
		
		//Depois alterar esta forma?
		JMenuItem mntmNovoJogo = new JMenuItem("Novo Jogo - IP/Port");
		mnJogo.add(mntmNovoJogo);
		//Depois alterar esta forma?
		JMenuItem mntmNovoJogo2 = new JMenuItem("Novo Jogo - Multicast");
		mnJogo.add(mntmNovoJogo2);		
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mnJogo.add(mntmSair);
		mntmSair.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent evt) {						   
				   System.exit(0);
			   }
		});
		
		JMenu mnOpes = new JMenu("Op\u00E7\u00F5es");
		menuBar.add(mnOpes);
		
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
		
		JLabel label3 = new JLabel("Estado:");
		label3.setFont(new Font("Tahoma", Font.BOLD, 14));
		label3.setBounds(316, 428, 66, 21);
		BatalhaNavalUI.getContentPane().add(label3);
		
		
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEstado.setBounds(378, 432, 202, 14);
		BatalhaNavalUI.getContentPane().add(lblEstado);
		
		
		
//======(PROVISORIO) EXEMPLO INTERFACE JOGO============
		criaMapaUtilizador(70,70); 				
		criaMapaAdversario(400,70); 		
		
		
//----Novo Jogo - Evento---
	mntmNovoJogo.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent evt) {	
			   			   
			   try {			
					if(!VarsGlobais.NovoJogoThreadCreated){
						NovoJogo_Ip dialog = new NovoJogo_Ip();
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);	
					}
					
				} catch (Exception e1) {
					//TRATAR
					e1.printStackTrace();
				}
			  
		   }
	});
	
	mntmNovoJogo2.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent evt) {	
			   			   
			   try {			
					if(!VarsGlobais.NovoJogoThreadCreated){
						NovoJogo_Multicast dialog = new NovoJogo_Multicast();
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);
						
					}
					
				} catch (Exception e1) {
					//TRATAR
					e1.printStackTrace();
				}
			  
		   }
	});
		
	
	
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
					   
					   
					   //SE NAO ESTA A JOGAR NAO PODERA INTERAGIR COM OS BOTOES!
					   
					//   if(VarsGlobais.NovoJogoThreadCreated == false)
						//   return;
					   
					   //SE NAO FOR O SEU TURNO NAO PODERA INTERAGIR COM OS BOTOES!
					   //if(VarsGlobais.MeuTurno == false)
					   //	   return;
					   
					   //verificar se ja atacou esta coordenada
					     
					   
					   //verificar se ja atacou esta coordenada
					   if(quadAtacados.isEmpty() != true){
						   for(int k=0; k<quadAtacados.size(); k++){
							   if(quadAtacados.get(k) == (( (i+1)*10 ) + (j+1)) ){
								   return; //ja foi atacada
							   }
						   }
					   }
					   
					   
					   //enviar coordenada a atacar ao servidor
					   //receber resposta, se acertou ou nao
					   //mudar icone conforme

					   try{
						   	//socket para enviar as coordenadas
					   	   	socket = SocketClient_TCP.getSocket(); //verificar se nao ta null(se ja foi criado no login)
					   	   	socket.setSoTimeout(1500); //timeout 1,5s
					   	   	//enviar coordenadas
					   	   	ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
					   	   	out.writeObject(new Integer(( (i+1)*10 ) + (j+1)) ); //ex.: enviar x6, y3 -> 60+3 = 63 (os +1 � para acertar o index)
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
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/
					   
					   botaoAdv[i][j].setIcon(aguaAlvo);					   
						//  BatalhaNavalUI.repaint();		  
				   
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
		  botao[2][1].setIcon(barcoPequeno1); botao[2][1].setDisabledIcon(barcoPequeno1);
		  botao[2][2].setIcon(barcoPequeno2); botao[2][2].setDisabledIcon(barcoPequeno2);	
		  
		  botao[8][7].setIcon(barcoPequenoFIRE1); botao[8][7].setDisabledIcon(barcoPequenoFIRE1);
		  botao[8][8].setIcon(barcoPequenoFIRE2); botao[8][8].setDisabledIcon(barcoPequenoFIRE2);
		  
		  botao[5][4].setIcon(barcoMedio1); botao[5][4].setDisabledIcon(barcoMedio1);	
		  botao[5][5].setIcon(barcoMedio2); botao[5][5].setDisabledIcon(barcoMedio2);
		  botao[5][6].setIcon(barcoMedio3); botao[5][6].setDisabledIcon(barcoMedio3);
		  botao[5][7].setIcon(barcoMedio4); botao[5][7].setDisabledIcon(barcoMedio4);
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
	 
	 public static void setNomeJogador1(String nome)
	 {
		 lblJogador_1.setText(nome);			 
	 }
	 public static void setNomeJogador2(String nome)
	 {
		 lblJogador_2.setText(nome);			 
	 }
	 public static void setEstado(String estado)
	 {
		 lblEstado.setText(estado);			 
	 }
}
