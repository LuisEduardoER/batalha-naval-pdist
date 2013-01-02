package com.pdist.batalhanaval.client.jogo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


import com.pdist.batalhanaval.client.dialogs.ListaJogosEJogadores;
import com.pdist.batalhanaval.client.main.AtendeServidor;
import com.pdist.batalhanaval.client.main.BatalhaNaval_Client;
import com.pdist.batalhanaval.client.main.SocketClient_TCP;
import com.pdist.batalhanaval.server.controlo.Letra;
import com.pdist.batalhanaval.server.controlo.Numero;
import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;

public class Jogo implements ActionListener{
	
	//private JButton[][] botao = new JButton[12][12];
	private JButton[][] botaoAdv = new JButton[12][12];
	
	private JFrame BatalhaNavalUI;
	
	private static JLabel lblJogador_1 = new JLabel("<nome?>"); //label JOGADOR 1
	private static JLabel lblJogador_2 = new JLabel("<nome?>"); //laber JOGADOR 2
	
	//Imagens..
	
	private ImageIcon agua = new ImageIcon("Imagens/agua.png");
	private ImageIcon mira = new ImageIcon("Imagens/mira.png");	
		//Bg2
	private String background = "Imagens/outros/background2.jpg";	
		
	


		
	private Thread AtendeServidor; //thread do AtendeServidor
	
	
	public JButton getBotaoAdv(int y, int x){
		return botaoAdv[y][x];
	}
	
	
	public Jogo(ListaJogosEJogadores listajogadores) {
		
		
		BatalhaNavalUI = BatalhaNaval_Client.getBatalhaNavalUI();			
			
		BatalhaNaval_Client.loadBackground(background);		
		BatalhaNaval_Client.loadMenuBar();	
				
				
		criaMapaAdversario(400,70);
				
				
		criaLabels();
								
				
		BatalhaNavalUI.repaint(); //necessario fazer repaint depois..			
		
		
		//CRIAR A THREAD DE ATENDIMENTO de pedidos do servidor
		
		AtendeServidor = new AtendeServidor(BatalhaNavalUI,listajogadores, this);
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
					   
					   if(botaoAdv[i][j].getBounds().x == x && botaoAdv[i][j].getBounds().y == y)
					   {				 		
						   
						   Letra coord_y = new Letra('a', i+1);
						   Numero coord_x = new Numero('1', j+1);
						   
						   
						   Mensagem msg = new Mensagem(Macros.MSG_ATACAR, coord_y, coord_x); //atacar, Y(letra), X(numero)
						   try{
							   SocketClient_TCP.getOut().flush();
							   SocketClient_TCP.getOut().writeObject(msg);
							   SocketClient_TCP.getOut().flush();
						   }catch(NullPointerException exp){
							   JOptionPane.showMessageDialog(BatalhaNavalUI, "ERRO a enviar a coordenada a atacar(NullPointer)" + exp);
						   }catch(IOException exp){
							   JOptionPane.showMessageDialog(BatalhaNavalUI, "ERRO a enviar a coordenada a atacar(IOException)" + exp);
						   }
						   
					   
					   }
					   
					   
				   }
		     }
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
