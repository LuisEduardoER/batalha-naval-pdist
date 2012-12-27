package com.pdist.batalhanaval.client.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;

public class AtendeServidor extends Thread{

	protected Socket socket;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;
	protected JFrame jogoFrame;
	
	//CONSTRUTOR
	public AtendeServidor(Socket s, JFrame jFrame){

		socket = s;
		jogoFrame = jFrame;
		
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Conexão falhou");	
			return;
		}
	}
	
	
	//RUN
	public void run(){
		JOptionPane.showMessageDialog(jogoFrame, "RUN RUN RUN RUN");
		//tratar as mensagens recebidas
		while(true){
			try{
				
				Mensagem msg = (Mensagem) in.readObject();		
				switch(msg.getType()){
					case Macros.MSG_INICIAR_RESPONSE: //user recebeu um convite
						receberConvites(msg);	
						break;
				}
						
			}catch(IOException e){
				//JOptionPane.showMessageDialog(contentPanel, "Erro a receber convite.");
				JOptionPane.showMessageDialog(jogoFrame, "Erro de leitura na thread");
			}catch(ClassNotFoundException e){
				//JOptionPane.showMessageDialog(contentPanel, "erro: classNotFound");
				JOptionPane.showMessageDialog(jogoFrame, "Erro na thread: classNotFound");
			}//fim try catch	
		}//fim while(true)
	}
	
	//receber os convites feitos por outros jogadores
	public void receberConvites(Mensagem msg) throws IOException{
			
		msg.setType(Macros.MSG_PEDIDO_RESPONSE);
			
		int opcao; //opcao escolhida na dialog box
		Object[] options = {"Aceitar", "Rejeitar", "Ignorar"};
		String msgConvite = "Recebeu um convite de: " + msg.getMsgText();
		String title = "Convite";
		//opcao = JOptionPane.showOptionDialog(contentPanel, msgConvite, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);
		opcao = JOptionPane.showOptionDialog(jogoFrame, msgConvite, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);	
		
		if(opcao == JOptionPane.YES_OPTION){
			//JOptionPane.showMessageDialog(contentPanel, "SIM");//remover depois
			JOptionPane.showMessageDialog(jogoFrame, "SIM");//remover depois
			msg.setResponseText(Macros.ACEITAR_PEDIDO);
				
			//enviar mensagem ao servidor
			SocketClient_TCP.getOut().flush();
			SocketClient_TCP.getOut().writeObject(msg);
	        SocketClient_TCP.getOut().flush();
		        
	        return;
		}
		if(opcao == JOptionPane.NO_OPTION){
			//JOptionPane.showMessageDialog(contentPanel, "NAO"); //remover depois
			JOptionPane.showMessageDialog(jogoFrame, "NAO"); //remover depois
			msg.setResponseText(Macros.REJEITAR_PEDIDO);
			
			//enviar resposta ao servidor
			SocketClient_TCP.getOut().flush();
			SocketClient_TCP.getOut().writeObject(msg);
	        SocketClient_TCP.getOut().flush();
				
			return;
		}
		//o ignorar nao envia nada, quem enviou o convite faz timeout
				
	}
	
	
}
