package com.pdist.batalhanaval.client.main;

import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.pdist.batalhanaval.client.dialogs.ListaJogosEJogadores;
import com.pdist.batalhanaval.client.jogo.Jogo;
import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;

public class AtendeServidor extends Thread{

	protected JFrame jogoFrame;
	protected JDialog listajogadores;
	
	
	//CONSTRUTOR
	public AtendeServidor(JFrame jFrame, JDialog listajogadores){
		jogoFrame = jFrame;
		this.listajogadores = listajogadores;
		try {
			SocketClient_TCP.getSocket().setSoTimeout(0);
			
						
		} catch (IOException e1) {
			//SERVIDOR DESLIGOU
			JOptionPane.showMessageDialog(jogoFrame, "O SERVIDOR DESLIGOU");
			return;
		}
		
		
	}
	
	
	//RUN
	public void run(){
		//JOptionPane.showMessageDialog(jogoFrame, "RUN (thread)"); //so para mostrar que a thread ta a correr
		Mensagem msg;
		//tratar as mensagens recebidas
		while(true){
			try{				
												
				msg = (Mensagem) SocketClient_TCP.getIn().readObject();
			//	JOptionPane.showMessageDialog(jogoFrame, "msg.type ->" + msg.getType()); //so para testes
				
				switch(msg.getType()){
					case Macros.MSG_PEDIDO_JOGO: //user recebeu um convite
						receberConvites(msg);	
						break;
					case Macros.MSG_INICIAR_RESPONSE: //resposta do convite (aceitou? rejeitou?)
						receberRespostaConvite(msg);
						break;
						
					case Macros.MSG_ATACAR_COORD_REPETIDA: //atacou uma coordenada que ja tinha atacado antes
						respostaAtaque(msg);
						break;
					case Macros.MSG_ATACAR_FAIL: //falhou o tiro (agua)
						respostaAtaque(msg);
						break;
					case Macros.MSG_ATACAR_SUCCESS: //acertou num barco
						respostaAtaque(msg);
						break;
					case Macros.MSG_ACTUALIZAR_YOUR_TAB: //fui atacado, actualizar tabuleiro
						//...
						break;
					case Macros.MSG_GET_TABULEIRO: //obter tabuleiro do jogo
						JOptionPane.showMessageDialog(jogoFrame, "TODO: GET TABULEIRO (agora falta gerar o tabuleiro random.. ou nao, e enviar para servidor..)");
						break;

				}
				
			}catch(SocketTimeoutException e){
				JOptionPane.showMessageDialog(jogoFrame, "Erro Timeout socket na thread \n" + e);
			}catch(NullPointerException e){
				JOptionPane.showMessageDialog(jogoFrame, "Erro NULLPOINTER na thread \n" + e);
				return;
			}catch(IOException e){
				JOptionPane.showMessageDialog(jogoFrame, "Erro de leitura na thread \n" + e);
				return;
			}catch(ClassNotFoundException e){
				JOptionPane.showMessageDialog(jogoFrame, "Erro na thread: classNotFound \n" + e);
			}//fim try catch	
		}//fim while(true)
		
	}
	
	//receber os convites feitos por outros jogadores
	public void receberConvites(Mensagem msg) throws IOException{		
		msg.setType(Macros.MSG_PEDIDO_RESPONSE);
			
		int opcao; //opcao escolhida na dialog box
		Object[] options = {"Aceitar", "Rejeitar", "Ignorar"};
		String msgConvite = "Recebeu um convite de: " + msg.getMsgText();	
		String title = "Convite para um novo jogo!";
		opcao = JOptionPane.showOptionDialog(jogoFrame, msgConvite, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);	
		
		if(opcao == JOptionPane.YES_OPTION){
			//JOptionPane.showMessageDialog(jogoFrame, "SIM");//remover depois
			msg.setResponseText(Macros.ACEITAR_PEDIDO);
			JOptionPane.showMessageDialog(jogoFrame, "Você aceitou o convite! \nA iniciar um novo jogo..");
			listajogadores.dispose(); //se aceite...para fexar dialog
			
			 Jogo.setNomeJogador2(msg.getMsgText());
             BatalhaNaval_Client.setEstado("A aguardar que o jogador (X) ataque!"); 
				
			//enviar mensagem ao servidor
			SocketClient_TCP.getOut().flush();
			SocketClient_TCP.getOut().writeObject(msg);
			SocketClient_TCP.getOut().flush();
		        
		}else if(opcao == JOptionPane.NO_OPTION){
			//JOptionPane.showMessageDialog(jogoFrame, "NAO"); //remover depois
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
						
			if(msg.getResponseText().equals(Macros.ACEITAR_PEDIDO)){ //pedido ACEITE
				JOptionPane.showMessageDialog(jogoFrame, "O convite foi aceite pelo jogador! \nA iniciar um novo jogo..");				
				listajogadores.dispose(); //para fechar dialog
				
				 Jogo.setNomeJogador2(ListaJogosEJogadores.nomeJogadorConvidado); //nome do jogador convidado 
	             BatalhaNaval_Client.setEstado("A aguardar que o jogador (X) ataque!"); 
				
				//TODO iniciar jogo!! ou entao é iniciado logo pelo server
			}
			if(msg.getResponseText().equals(Macros.REJEITAR_PEDIDO)){ //pedido RECUSADO
				JOptionPane.showMessageDialog(jogoFrame, "O convite foi rejeitado pelo jogador!");
				return;
			}						
		
			if(msg.getResponseText().equals(Macros.IGNORAR_PEDIDO)){ //pedido Ignorado
				JOptionPane.showMessageDialog(jogoFrame, "O convite foi ignorado pelo jogador.");
				return;
			}
		}
		
		
	//receber a resposta se acertou ou não num barco do inimigo
	public void respostaAtaque(Mensagem msg) throws IOException{
		
		//ATACOU COORDENADA REPETIDA
		if(msg.getType() == Macros.MSG_ATACAR_COORD_REPETIDA){
			JOptionPane.showMessageDialog(jogoFrame, "Já tinha atacado esta posição.\n Ataque outra.");					
			return;
		}
		
		//FALHOU
		if(msg.getType() == Macros.MSG_ATACAR_FAIL){
			JOptionPane.showMessageDialog(jogoFrame, "Água :(");

			//mudar o icone azul no tabuleiro para agua
			
			return;
		}
		
		//ACERTOU
		if(msg.getType() == Macros.MSG_ATACAR_SUCCESS){
			JOptionPane.showMessageDialog(jogoFrame, "Acertou!");
			
			//mudar o icone azul no tabuleiro para uma explosao
			
			return;
		}
			
	}
		
	
	
}
