package com.pdist.batalhanaval.client.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;

public class AtendeServidor extends Thread{

	protected JFrame jogoFrame;
	protected Socket mySocket;
	protected ObjectInputStream in;
	protected ObjectOutputStream out;
	
	//CONSTRUTOR
	public AtendeServidor(JFrame jFrame, Socket mySocket){

		jogoFrame = jFrame;
		this.mySocket = mySocket;
		try {
			SocketClient_TCP.getSocket().setSoTimeout(0);
			mySocket.setSoTimeout(0);
			in = new ObjectInputStream(mySocket.getInputStream());
			out = new ObjectOutputStream(mySocket.getOutputStream());
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	
	//RUN
	public void run(){
		JOptionPane.showMessageDialog(jogoFrame, "RUN RUN RUN RUN (thread)"); //so para mostrar que a thread ta a correr
		//tratar as mensagens recebidas
		while(true){
			try{
				
				Mensagem msg = (Mensagem) in.readObject();
				JOptionPane.showMessageDialog(jogoFrame, "msg.type ->" + msg.getType()); //so para testes
				
				switch(msg.getType()){
					case Macros.MSG_PEDIDO_JOGO: //user recebeu um convite
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
		opcao = JOptionPane.showOptionDialog(jogoFrame, msgConvite, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);	
		
		if(opcao == JOptionPane.YES_OPTION){
			JOptionPane.showMessageDialog(jogoFrame, "SIM");//remover depois
			msg.setResponseText(Macros.ACEITAR_PEDIDO);
				
			//enviar mensagem ao servidor
			out.flush();
			out.writeObject(msg);
			out.flush();
		        
	        return;
		}else if(opcao == JOptionPane.NO_OPTION){
			JOptionPane.showMessageDialog(jogoFrame, "NAO"); //remover depois
			msg.setResponseText(Macros.REJEITAR_PEDIDO);
			
			//enviar resposta ao servidor
			out.flush();
			out.writeObject(msg);
			out.flush();
				
			return;
		}else{
			msg.setResponseText(Macros.IGNORAR_PEDIDO);
			
			//enviar resposta ao servidor
			out.flush();
			out.writeObject(msg);
			out.flush();
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
						
		}
	
	
}
