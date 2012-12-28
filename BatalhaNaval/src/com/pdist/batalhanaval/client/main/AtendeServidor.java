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
		JOptionPane.showMessageDialog(jogoFrame, "RUN RUN RUN RUN (thread)");
		//tratar as mensagens recebidas
		while(true){
			try{
				Mensagem msg = (Mensagem) in.readObject(); //ERRO IOEXCEPTION AO RECEBER CONVITE
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
		System.out.println("Convite Recebido");		
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
			out.flush();
			out.writeObject(msg);
			out.flush();
		        
	        return;
		}else if(opcao == JOptionPane.NO_OPTION){
			//JOptionPane.showMessageDialog(contentPanel, "NAO"); //remover depois
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
	
	
}
