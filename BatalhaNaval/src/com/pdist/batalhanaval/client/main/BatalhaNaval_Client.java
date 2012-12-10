package com.pdist.batalhanaval.client.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.*;

import com.pdist.batalhanaval.client.dialogs.NovoJogo;
import java.awt.Toolkit;


public class BatalhaNaval_Client implements ActionListener {

	private JFrame BatalhaNavalUI;
	private JButton[][] botao = new JButton[12][12];
	private JButton[][] botaoAdv = new JButton[12][12];
	private ImageIcon aguaAlvo = new ImageIcon("Imagens/aguaAlvo.jpg");
	private ImageIcon agua = new ImageIcon("Imagens/agua.jpg");	  

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
		BatalhaNavalUI.getContentPane().setBackground(Color.LIGHT_GRAY);
		BatalhaNavalUI.setBounds(100, 100, 800, 600);
		BatalhaNavalUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BatalhaNavalUI.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.setBounds(0, 0, 800, 21);
		BatalhaNavalUI.getContentPane().add(menuBar);
		
		JMenu mnJogo = new JMenu("Jogo");
		menuBar.add(mnJogo);
		
		JMenuItem mntmNovoJogo = new JMenuItem("Novo Jogo");
		mnJogo.add(mntmNovoJogo);
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mnJogo.add(mntmSair);
		
		JMenu mnOpes = new JMenu("Op\u00E7\u00F5es");
		menuBar.add(mnOpes);
		
		JLabel lblJogador = new JLabel("Jogador 1");
		lblJogador.setFont(new Font("Arial", Font.PLAIN, 26));
		lblJogador.setBounds(170, 32, 138, 30);
		BatalhaNavalUI.getContentPane().add(lblJogador);
		
		JLabel lblJogador_1 = new JLabel("Jogador 2");
		lblJogador_1.setFont(new Font("Arial", Font.PLAIN, 26));
		lblJogador_1.setBounds(497, 32, 138, 30);
		BatalhaNavalUI.getContentPane().add(lblJogador_1);
		
		
		//======(PROVISORIO) EXEMPLO INTERFACE JOGO============
		criaMapaUtilizador(70,70); 				
		criaMapaAdversario(400,70); 		
		
		
	//----Novo Jogo - Evento---
	mntmNovoJogo.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent evt) {	
			   			   
			   try {			
					if(!VarsGlobais.NovoJogoThreadCreated){
						NovoJogo dialog = new NovoJogo();
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
				   
				   if(botao[i][j].getBounds().x == x &&
						   botao[i][j].getBounds().y == y)
				   {				 		
					   
					   botao[i][j].setIcon(aguaAlvo);					   
						//  BatalhaNavalUI.repaint();	  
				   
				   }
				   
				   else if(botaoAdv[i][j].getBounds().x == x &&
						   botaoAdv[i][j].getBounds().y == y)
				   {				 		
					   
					   botaoAdv[i][j].setIcon(aguaAlvo);					   
						//  BatalhaNavalUI.repaint();	  
				   
				   }
				   
				   
			   }
	     }
	 }
	 
	 public void criaMapaUtilizador(int x,int y) //TESTE
	 {
		  for(int i=0; i<10; i++){
			   for(int j=0; j<10; j++){			   
				 
			    botao[i][j] = new JButton(agua);		 
			    botao[i][j].setBounds(x+(j*30), y+(i*30), 32, 32);
			    botao[i][j].addActionListener((ActionListener) this);			 
			    BatalhaNavalUI.getContentPane().add(botao[i][j]);
			   }
		  }
	 }

	 public void criaMapaAdversario(int x,int y) //TESTE
	 {
		  for(int i=0; i<10; i++){
			   for(int j=0; j<10; j++){			   
				 
			    botaoAdv[i][j] = new JButton(agua);		 
			    botaoAdv[i][j].setBounds(x+(j*30), y+(i*30), 32, 32);
			    botaoAdv[i][j].addActionListener((ActionListener) this);				    		    
			    BatalhaNavalUI.getContentPane().add(botaoAdv[i][j]);
			   }
		  }
	 }
}
