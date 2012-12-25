package com.pdist.batalhanaval.client.dialogs;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.pdist.batalhanaval.client.main.SocketClient_TCP;
import com.pdist.batalhanaval.client.main.VarsGlobais;
import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;


public class ListarJogoseJogadores extends JDialog {

	
	private static final long serialVersionUID = 4394138207569213899L;
	private final JPanel contentPanel = new JPanel();
	private ArrayList<String> nomeJogos = null;
	
	private String nomeJogador;
	
	private DefaultListModel<String> modelListaJogos = new DefaultListModel<String>();  //novo ListModel
	private JList<String> listaJogos = new JList<String>(modelListaJogos);
	

	

	public ListarJogoseJogadores(String nomeJogador) throws IOException {
		
		setResizable(false);  setModal(true);
		setTitle("Novo Jogo - Listar Jogos/Jogadores");
		setBounds(100, 100, 552, 310);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		this.nomeJogador = nomeJogador;
		
			
		try {
			getListaJogos(sendListaJogosRequest()); //envia pedido ao servidor e recebe lista de jogos
		} catch (IOException e) {
			JOptionPane.showMessageDialog(contentPanel,"PEDIR LISTA JOGOS - Erro na ligação ao servidor");
			return;
		}   
		
			
		JLabel lblListaDeJogos = new JLabel("Jogos Activos:");
		lblListaDeJogos.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblListaDeJogos.setBounds(77, 13, 158, 26);
		contentPanel.add(lblListaDeJogos);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(276, 0, 2, 285);
		contentPanel.add(separator);
		
		JButton btnEnviarConvite = new JButton("Enviar Convite");
		btnEnviarConvite.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEnviarConvite.setBounds(358, 216, 128, 33);
		contentPanel.add(btnEnviarConvite);
		
		/*
		JList<String> list = new JList<String>((ListModel) null);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setFont(new Font("Tahoma", Font.PLAIN, 14));
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list.setBounds(327, 51, 179, 135);
		contentPanel.add(list);
		*/
		
		JLabel lblConvidarJogadores = new JLabel("Jogadores Online:");
		lblConvidarJogadores.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblConvidarJogadores.setBounds(338, 13, 158, 26);
		contentPanel.add(lblConvidarJogadores);
		
//DEPOIS ALTERAR PARA PACKAGE LISTENERS...		
		btnEnviarConvite.addActionListener(new ActionListener() { 
			   public void actionPerformed(ActionEvent evt) {	
				   
				   JOptionPane.showMessageDialog(contentPanel,"CRIAR - TODO!");	
				   
				    //TODO
				   //Enviar msg ao servidor para convidar o X jogador
				   //Se o jogador aceitar (ter timeout) o jogo é iniciado
				   
				   //TODO Receber convites
							  
			   }
		});
		
		
		
		
	}
	
	
      public Mensagem sendListaJogosRequest() throws IOException{   	  
    	  
    	        	
           Mensagem msg = new Mensagem(Macros.MSG_LISTA_JOGOS);
           msg.setMsgText(nomeJogador);      
           
           
           SocketClient_TCP.getOut().flush();
           SocketClient_TCP.getOut().writeObject(msg);
           SocketClient_TCP.getOut().flush();
           
           try{
             	
               msg = (Mensagem) SocketClient_TCP.getIn().readObject(); 
               
               } catch (Exception  e) {                                                    
                   JOptionPane.showMessageDialog(contentPanel,"Erro ao obter lista de jogos");
                   VarsGlobais.NovoJogoThreadCreated = false;                     
               }
           
           return msg;
   }
	
	
	public void getListaJogos(Mensagem listajogos)
	{
		nomeJogos = listajogos.getNomesJogos();			
		
		
		for(int i=0; i<nomeJogos.size();i++)
		{
			modelListaJogos.addElement(nomeJogos.get(i)); //carregar info do jogos para a lista
		}
		
		//Lista Jogos 
		listaJogos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		listaJogos.setSelectedIndex(0); //selecionar o 1º
		listaJogos.setLayoutOrientation(JList.VERTICAL); 		
		listaJogos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		listaJogos.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		//lista.setBounds(49, 50, 181, 120);		
		JScrollPane listaScroll = new JScrollPane(listaJogos);   //para aparecer o scroll na lista
		listaScroll.setBounds(49, 50, 181, 135);		
		contentPanel.add(listaScroll);
	}
}
