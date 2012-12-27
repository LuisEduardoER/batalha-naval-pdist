package com.pdist.batalhanaval.client.main;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

import com.pdist.batalhanaval.client.listeners.MenuActionListener;
import com.pdist.batalhanaval.client.jogo.Jogo;

import java.awt.Toolkit;
import java.awt.SystemColor;


public class BatalhaNaval_Client {

	private JFrame BatalhaNavalUI;
	private static JLabel lblJogador_1 = new JLabel("<nome>");
	private static JLabel lblJogador_2 = new JLabel("<nome>");
	private static JLabel lblEstado = new JLabel("a aguardar login... (teste)");

	private MenuActionListener menuListener = new MenuActionListener();
	


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
		BatalhaNavalUI.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagens/barcos/barcoPequenoFIRE_1.png"));
		BatalhaNavalUI.setResizable(false);
		BatalhaNavalUI.setTitle("Batalha Naval v0.4");
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
		
		
		JMenuItem mntmNovoJogo = new JMenuItem("Novo Jogo - IP/Port");
		mnJogo.add(mntmNovoJogo);

		JMenuItem mntmNovoJogo2 = new JMenuItem("Novo Jogo - Multicast");
		mnJogo.add(mntmNovoJogo2);		
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mnJogo.add(mntmSair);
		
		
		//ACTION LISTENER
		mntmSair.addActionListener(menuListener);
		mntmNovoJogo.addActionListener(menuListener);
		mntmNovoJogo2.addActionListener(menuListener);
		
		/*mntmSair.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent evt) {						   
				   System.exit(0);
			   }
		});*/
		
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
		
		
		
//=========So iniciar depois de login.. blabla =========
		new Jogo(BatalhaNavalUI);
//=========So iniciar depois de login.. blabla =========
	
	
	
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
