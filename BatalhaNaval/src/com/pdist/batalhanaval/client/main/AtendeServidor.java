package com.pdist.batalhanaval.client.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;

public class AtendeServidor extends Thread{

	protected JFrame jogoFrame;
	
	//CONSTRUTOR
	public AtendeServidor(JFrame jFrame){

		jogoFrame = jFrame;
		
		//desactivar o timeout do socket COMO ESTA PODERA DAR PROBLEMAS MAIS TARDE se o timeout for alterado a meio!!
		try {
			SocketClient_TCP.getSocket().setSoTimeout(0);
		} catch (SocketException e) {
			JOptionPane.showMessageDialog(jogoFrame, "Erro no construtor da thread ao definir o timeout do socket");
		}
		
	}
	
	
	//RUN
	public void run(){
		JOptionPane.showMessageDialog(jogoFrame, "RUN RUN RUN RUN (thread)");
		//tratar as mensagens recebidas
		while(true){
			try{
				Mensagem msg = (Mensagem) SocketClient_TCP.getIn().readObject(); //ERRO IOEXCEPTION AO RECEBER CONVITE
				JOptionPane.showMessageDialog(jogoFrame, "msg.type ->" + msg.getType()); //remover depois
				
				switch(msg.getType()){
					case Macros.MSG_PEDIDO_JOGO: //user recebeu um convite
						receberConvites(msg);	
						break;
				}
				
			}catch(SocketTimeoutException e){
				JOptionPane.showMessageDialog(jogoFrame, "Erro Timeout socket na thread");
			}catch(NullPointerException e){
				JOptionPane.showMessageDialog(jogoFrame, "Erro NULLPOINTER na thread");
			}catch(IOException e){
				JOptionPane.showMessageDialog(jogoFrame, "Erro de leitura na thread");
			}catch(ClassNotFoundException e){
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
