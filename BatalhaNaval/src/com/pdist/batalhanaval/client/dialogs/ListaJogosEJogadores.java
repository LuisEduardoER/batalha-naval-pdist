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
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.pdist.batalhanaval.client.main.SocketClient_TCP;
import com.pdist.batalhanaval.client.main.VarsGlobais;
import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;


public class ListaJogosEJogadores extends JDialog {

	
	private static final long serialVersionUID = 4394138207569213899L;
	private final JPanel contentPanel = new JPanel();
	private ArrayList<String> nomeJogos = null;
	private ArrayList<String> nomeJogadores = null;
	
	private String nomeJogador;
	
	private DefaultListModel<String> modelListaJogos = new DefaultListModel<String>();  //LISTA JOGOS
	private JList<String> listaJogos = new JList<String>(modelListaJogos);  //LISTA JOGOS
	
	private DefaultListModel<String> modelListaJogadores = new DefaultListModel<String>();  //LISTA JOGADORES
	private JList<String> listaJogadores = new JList<String>(modelListaJogadores); //LISTA JOGADORES
	

	
	public ListaJogosEJogadores(String nomeJogador) throws IOException {
		
		setResizable(false);  setModal(true);
		setTitle("Novo Jogo - Listar Jogos/Jogadores");
		setBounds(100, 100, 552, 310);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		this.nomeJogador = nomeJogador;
		
		
		//TODO Receber e enviar convites e estar a sincronizar de X em X segundos (criar thread e colocar la os metodos daqui)
		
			
		try {
			getListaJogos(sendListaJogosRequest()); //envia pedido ao servidor e recebe lista de jogos
		} catch (IOException e) {
			JOptionPane.showMessageDialog(contentPanel,"PEDIR LISTA JOGOS - Erro na ligação ao servidor");
			return;
		}   
		
		
		try {
			getListaJogadores(sendListaJogadoresRequest()); //envia pedido ao servidor e recebe lista de jogadores
		} catch (IOException e) {
			JOptionPane.showMessageDialog(contentPanel,"PEDIR LISTA JOGADORES - Erro na ligação ao servidor");
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
		
		
		JLabel lblConvidarJogadores = new JLabel("Jogadores Online:");
		lblConvidarJogadores.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblConvidarJogadores.setBounds(338, 13, 158, 26);
		contentPanel.add(lblConvidarJogadores);
		
//DEPOIS ALTERAR PARA PACKAGE LISTENERS...	
		btnEnviarConvite.addActionListener(new ActionListener() { 
			   public void actionPerformed(ActionEvent evt) {	
				   
				   JOptionPane.showMessageDialog(contentPanel,"CRIAR - TODO!");	
				   
				  
				   
				   //verificar se o user está a convidar-se a si proprio
				   if( getNomeJogador().equals(listaJogadores.getSelectedValue()) ){
					   JOptionPane.showMessageDialog(contentPanel, "Não se pode convidar a si mesmo!");
					   return;
				   }
				   
				   
				   //Enviar msg ao servidor para convidar o X jogador
				   //Se o jogador aceitar (ter timeout) o jogo é iniciado
				   try{
					   //enviar convite
					   sendConvite();
					   //aguardar resposta (timeout 15s)
					   receberRespostaConvite();
					   //se tiver sido aceite, fecha janela dos convites e começa o jogo
				   }catch(SocketTimeoutException e){
					   JOptionPane.showMessageDialog(contentPanel, "Não foi obtida resposta do outro jogador");
				   }catch(IOException e){
					   JOptionPane.showMessageDialog(contentPanel, "Erro a enviar convite.");
				   }
				  
				   /* posto no AtendeServidor
				  //TODO Receber convites
				   try{
					   
					   Mensagem msg = (Mensagem) SocketClient_TCP.getIn().readObject();
					   if(msg.getType() == Macros.MSG_INICIAR_RESPONSE) //se for um convite
						   receberConvites(msg);
					   
				   }catch(IOException e){
					   JOptionPane.showMessageDialog(contentPanel, "Erro a receber convite.");
				   }catch(ClassNotFoundException e){
					   JOptionPane.showMessageDialog(contentPanel, "erro: classNotFound");
				   }*/
				   
				   
			   }
		});
		
			
	}
	
	/* posto no AtendeServidor
	//receber os convites feitos por outros jogadores
	public void receberConvites(Mensagem msg) throws IOException{
		
		msg.setType(Macros.MSG_PEDIDO_RESPONSE);
		
		int opcao; //opcao escolhida na dialog box
		Object[] options = {"Aceitar", "Rejeitar", "Ignorar"};
		String msgConvite = "Recebeu um convite de: " + msg.getMsgText();
		String title = "Convite";
		opcao = JOptionPane.showOptionDialog(contentPanel, msgConvite, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);
		
		if(opcao == JOptionPane.YES_OPTION){
			JOptionPane.showMessageDialog(contentPanel, "SIM");//remover depois
			msg.setResponseText(Macros.ACEITAR_PEDIDO);
			
			//enviar mensagem ao servidor
			SocketClient_TCP.getOut().flush();
			SocketClient_TCP.getOut().writeObject(msg);
	        SocketClient_TCP.getOut().flush();
	        
	        return;
		}
		if(opcao == JOptionPane.NO_OPTION){
			JOptionPane.showMessageDialog(contentPanel, "NAO"); //remover depois
			msg.setResponseText(Macros.REJEITAR_PEDIDO);
			
			//enviar resposta ao servidor
			SocketClient_TCP.getOut().flush();
			SocketClient_TCP.getOut().writeObject(msg);
	        SocketClient_TCP.getOut().flush();
			
			return;
		}
		//o ignorar nao envia nada, quem enviou o convite faz timeout
			
	}*/
	
	
	//GET (por causa do actionListener)
	public String getNomeJogador(){
		return nomeJogador;
	}
	
	//enviar um pedido ao servidor para que este envie um pedido (convite) a um certo jogador
	public void sendConvite() throws IOException{
		
		//dizer ao servidor que se quer iniciar um jogo e passar o nome do jogador a convidar
		Mensagem msg = new Mensagem(Macros.MSG_INICIAR_JOGO, listaJogadores.getSelectedValue());
				
		//enviar mensagem
		SocketClient_TCP.getOut().flush();
		SocketClient_TCP.getOut().writeObject(msg);
        SocketClient_TCP.getOut().flush();
        
	}
	
	//receber a resposta do servidor (sobre o convite que foi feito anteriormente)
	public void receberRespostaConvite() throws IOException{
		
		//definir timeout para 15 segundos (tem 15 segundos para aceitar/rejeitar o convite)
      	SocketClient_TCP.getSocket().setSoTimeout(15000);
		
		try{
			//deve receber uma Mensagem estilo ( Mensagem(int type, String msg_text) )
			Mensagem msg = (Mensagem) SocketClient_TCP.getIn().readObject();
			if(msg.getType() == Macros.MSG_INICIAR_RESPONSE){
				if(msg.getMsgText().equals(Macros.ACEITAR_PEDIDO)){ //pedido ACEITE
					JOptionPane.showMessageDialog(contentPanel, "Convite aceite! \n a iniciar jogo..");
					//TODO iniciar jogo!! ou entao é iniciado logo pelo server
				}
				if(msg.getMsgText().equals(Macros.REJEITAR_PEDIDO)){ //pedido RECUSADO
					JOptionPane.showMessageDialog(contentPanel, "Convite rejeitado.");
					return;
				}
			}
		}catch(ClassNotFoundException e){
			JOptionPane.showMessageDialog(contentPanel, "ListaJogosEJogadores.java->receberRespostaConvite: erro classNotFound");
		}
		
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
		
		listaJogos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		listaJogos.setSelectedIndex(0); //selecionar o 1º
		listaJogos.setLayoutOrientation(JList.VERTICAL); 		
		listaJogos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		listaJogos.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));	
		JScrollPane listaScroll = new JScrollPane(listaJogos);   //para aparecer o scroll na lista
		listaScroll.setBounds(49, 50, 181, 135);		
		contentPanel.add(listaScroll);
	}
	
	
	   public Mensagem sendListaJogadoresRequest() throws IOException{   	  
	    	  
       	
           Mensagem msg = new Mensagem(Macros.MSG_LISTA_ONLINE);
           msg.setMsgText(nomeJogador);      
           
           
           SocketClient_TCP.getOut().flush();
           SocketClient_TCP.getOut().writeObject(msg);
           SocketClient_TCP.getOut().flush();
           
           try{
             	
               msg = (Mensagem) SocketClient_TCP.getIn().readObject(); 
               
               } catch (Exception  e) {                                                    
                   JOptionPane.showMessageDialog(contentPanel,"Erro ao obter lista de jogadores!");
                   VarsGlobais.NovoJogoThreadCreated = false;                     
               }
           
           return msg;
   }
	
	
	public void getListaJogadores(Mensagem listajogadores) //lista de jogadores que NAO estao a jogar..
	{
		nomeJogadores = listajogadores.getNomesClientes();		
		
		
		for(int i=0; i<nomeJogadores.size();i++)
		{
			modelListaJogadores.addElement(nomeJogadores.get(i)); 
		}
		
		listaJogadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		listaJogadores.setSelectedIndex(0); //selecionar o 1º
		listaJogadores.setLayoutOrientation(JList.VERTICAL); 		
		listaJogadores.setFont(new Font("Tahoma", Font.PLAIN, 14));
		listaJogadores.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));	
		JScrollPane listaScroll = new JScrollPane(listaJogadores);   
		listaScroll.setBounds(327, 51, 181, 135);		
		contentPanel.add(listaScroll);
	}
	
}
