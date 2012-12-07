package com.pdist.batalhanaval.client.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import com.pdist.batalhanaval.client.dialogs.NovoJogo;

public class BatalhaNaval_Client {

	private JFrame frmBatalhaNavalV;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BatalhaNaval_Client window = new BatalhaNaval_Client();
					window.frmBatalhaNavalV.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BatalhaNaval_Client() {		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBatalhaNavalV = new JFrame();
		frmBatalhaNavalV.setTitle("Batalha Naval v0.1");
		frmBatalhaNavalV.getContentPane().setBackground(Color.GRAY);
		frmBatalhaNavalV.setBounds(100, 100, 450, 300);
		frmBatalhaNavalV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBatalhaNavalV.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.setBounds(0, 0, 434, 21);
		frmBatalhaNavalV.getContentPane().add(menuBar);
		
		JMenu mnJogo = new JMenu("Jogo");
		menuBar.add(mnJogo);
		
		JMenuItem mntmNovoJogo = new JMenuItem("Novo Jogo");
		mnJogo.add(mntmNovoJogo);
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mnJogo.add(mntmSair);
		
		JMenu mnOpes = new JMenu("Op\u00E7\u00F5es");
		menuBar.add(mnOpes);
		
	
	mntmNovoJogo.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent evt) {	
			   			   
			   try {			
					if(!VarsGlobais.NovoJogoThreadCreated){
						NovoJogo dialog = new NovoJogo();
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);	
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			  
		   }
	});
	
	
	}
}
