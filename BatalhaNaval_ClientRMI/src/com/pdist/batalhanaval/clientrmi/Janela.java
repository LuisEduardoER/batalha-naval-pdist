package com.pdist.batalhanaval.clientrmi;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;




public class Janela {

	
	private JFrame frame = new JFrame("");
	private JPanel contentPanel = new JPanel();
	
	private JButton btnVerInfo = new JButton("Ver informação");
	
	private JList<String> listaJogos = new JList<String>();
	private JList<String> listaJogadores = new JList<String>();
	
	private JTextArea textArea = new JTextArea();
	
	//construtor
	public Janela(){}
	
	
	//GETs e SETs
	public JFrame getFrame() {return frame;}
	public void setFrame(JFrame frame) {this.frame = frame;}

	public JPanel getContentPanel() {return contentPanel;}
	public void setContentPanel(JPanel contentPanel) {this.contentPanel = contentPanel;}

	public JButton getBtnVerInfo() {return btnVerInfo;}
	public void setBtnVerInfo(JButton btnVerInfo) {this.btnVerInfo = btnVerInfo;}

	public JList<String> getListaJogos() {return listaJogos;}
	public void setListaJogos(JList<String> listaJogos) {this.listaJogos = listaJogos;}

	public JList<String> getListaJogadores() {return listaJogadores;}
	public void setListaJogadores(JList<String> listaJogadores) {this.listaJogadores = listaJogadores;}

	public JTextArea getTextArea() {return textArea;}
	public void setTextArea(JTextArea textArea) {this.textArea = textArea;}


	public void criarJanela(){
		
		frame.setResizable(false);  
		frame.setTitle("Cliente RMI");
		frame.setBounds(100, 100, 550, 450);
		frame.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		frame.setVisible(true);
		
		JLabel lblListaDeJogos = new JLabel("Jogos Activos:");
		lblListaDeJogos.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblListaDeJogos.setBounds(77, 13, 158, 26);
		contentPanel.add(lblListaDeJogos);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(276, 0, 2, 300);
		contentPanel.add(separator);
		
		
		btnVerInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnVerInfo.setBounds(350, 350, 128, 33);
		contentPanel.add(btnVerInfo);
		
		JLabel lblConvidarJogadores = new JLabel("Jogadores Online:");
		lblConvidarJogadores.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblConvidarJogadores.setBounds(338, 13, 158, 26);
		contentPanel.add(lblConvidarJogadores);
		
		
		listaJogos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaJogos.setSelectedIndex(0); //selecionar o 1º
		listaJogos.setLayoutOrientation(JList.VERTICAL); 		
		listaJogos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		listaJogos.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));	
		JScrollPane listaScroll = new JScrollPane(listaJogos);   //para aparecer o scroll na lista
		listaScroll.setBounds(49, 50, 181, 135);		
		contentPanel.add(listaScroll);
		
		
		listaJogadores.setEnabled(false);
		//listaJogadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//listaJogadores.setSelectedIndex(0); //selecionar o 1º
		listaJogadores.setLayoutOrientation(JList.VERTICAL); 		
		listaJogadores.setFont(new Font("Tahoma", Font.PLAIN, 14));
		listaJogadores.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));	
		JScrollPane listaScroll2 = new JScrollPane(listaJogadores);   //para aparecer o scroll na lista
		listaScroll2.setBounds(327, 51, 181, 135);		
		contentPanel.add(listaScroll2);
		

		
		//JLabel lblInfo = new JLabel();
		//lblInfo.setBounds(20, 300, 300, 100);
		//lblInfo.setText("olaola man");
		//contentPanel.add(lblInfo);
		textArea.setBounds(20,300, 300, 100);
		textArea.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPanel.add(textArea);
		
		contentPanel.repaint();
	}
	
	
}
