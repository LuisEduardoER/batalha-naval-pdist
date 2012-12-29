package com.pdist.batalhanaval.client.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
		try {
			SocketClient_TCP.getSocket().setSoTimeout(0);
			
						
		} catch (IOException e1) {
			//SERVIDOR DESLIGOU
			return;
		}
		
		
	}
	
	
	//RUN
	public void run(){
		JOptionPane.showMessageDialog(jogoFrame, "RUN (thread)"); //so para mostrar que a thread ta a correr
		Mensagem msg;
		//tratar as mensagens recebidas
		while(true){
			try{				
				msg = (Mensagem) SocketClient_TCP.getIn().readObject();
				JOptionPane.showMessageDialog(jogoFrame, "msg.type ->" + msg.getType()); //so para testes
				
				switch(msg.getType()){
					case Macros.MSG_PEDIDO_JOGO: //user recebeu um convite
						System.out.println("Convite Recebido");
						receberConvites(msg);	
						break;
					case Macros.MSG_INICIAR_RESPONSE: //resposta do convite (aceitou? rejeitou?)
						receberRespostaConvite(msg);
						break;
				}
				
			}catch(SocketTimeoutException e){
				JOptionPane.showMessageDialog(jogoFrame, "Erro Timeout socket na thread");
			}catch(NullPointerException e){
				JOptionPane.showMessageDialog(jogoFrame, "Erro NULLPOINTER na thread");
				return;
			}catch(IOException e){
				JOptionPane.showMessageDialog(jogoFrame, "Erro de leitura na thread");
				return;
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
		opcao = JOptionPane.showOptionDialog(jogoFrame, msgConvite, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);	
		
		if(opcao == JOptionPane.YES_OPTION){
			JOptionPane.showMessageDialog(jogoFrame, "SIM");//remover depois
			msg.setResponseText(Macros.ACEITAR_PEDIDO);
				
			//enviar mensagem ao servidor
			SocketClient_TCP.getOut().flush();
			SocketClient_TCP.getOut().writeObject(msg);
			SocketClient_TCP.getOut().flush();
		        
		}else if(opcao == JOptionPane.NO_OPTION){
			JOptionPane.showMessageDialog(jogoFrame, "NAO"); //remover depois
			msg.setResponseText(Macros.REJEITAR_PEDIDO);
			
			//enviar resposta ao servidor
			SocketClient_TCP.getOut().flush();
			SocketClient_TCP.getOut().writeObject(msg);
			SocketClient_TCP.getOut().flush();
				
		}else{
			msg.setResponseText(Macros.IGNORAR_PEDIDO);
			
			//enviar resposta ao servidor
			SocketClient_TCP.getOut().flush();
			SocketClient_TCP.getOut().writeObject(msg);
			SocketClient_TCP.getOut().flush();
		}
				
	}
	
	
	//receber a RESPOSTA do servidor (sobre o convite que foi feito anteriormente)
		public void receberRespostaConvite(Mensagem msg) throws IOException{
					
			if(msg.getMsgText().equals(Macros.ACEITAR_PEDIDO)){ //pedido ACEITE
				JOptionPane.showMessageDialog(jogoFrame, "Convite aceite! \n a iniciar jogo..");
				//TODO iniciar jogo!! ou entao é iniciado logo pelo server
			}
			if(msg.getMsgText().equals(Macros.REJEITAR_PEDIDO)){ //pedido RECUSADO
				JOptionPane.showMessageDialog(jogoFrame, "Convite rejeitado.");
				return;
			}						
		
			if(msg.getMsgText().equals(Macros.IGNORAR_PEDIDO)){ //pedido Ignorado
				JOptionPane.showMessageDialog(jogoFrame, "Convite ignorado.");
				return;
			}
		}
	
	
}
