package com.pdist.batalhanaval.clientrmi;


import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import com.pdist.batalhanaval.server.rmi.ClientObserverInterface;
import com.pdist.batalhanaval.server.rmi.BatalhaNavalRMIInterface;



public class ClienteRMI extends UnicastRemoteObject implements ClientObserverInterface{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 4167120501002699583L;

	
	public static ClienteRMI ClientObserver;
	public static BatalhaNavalRMIInterface batalhaNavalRMIService;
	public static Janela janela;

	//NEW
	public ClienteRMI () throws RemoteException { }

	    @SuppressWarnings("unchecked")
		public void actualizarInfo() throws RemoteException {
	        
	         System.out.println("Recebido pedido do servidor para actualizar janela...");
	         
	       //quantas unidades rebentadas tem o jogador 1 do jogo 0
				
				janela.getTextArea().append("Numero de KABOOM: " + batalhaNavalRMIService.getNumeroExplosoes(0, 1)+"\n");
				//System.out.println(batalhaNavalRMIService.getNumeroExplosoes(0, 1));
				
				//lista de jogadores que nao estao a jogar
				@SuppressWarnings("rawtypes")
				DefaultListModel listModel = new DefaultListModel();
				int aux = batalhaNavalRMIService.getJogadores().size();
				if(aux != 0){
					for(int i=0; i< aux; i++){
						listModel.addElement(batalhaNavalRMIService.getJogadores().get(i));
					}
					janela.getListaJogadores().setModel(listModel);
				}
				
				System.out.println(batalhaNavalRMIService.getJogadores());
				
				//lista de jogos a decorrer
				@SuppressWarnings("rawtypes")
				DefaultListModel listModel2 = new DefaultListModel();
				int aux2 = batalhaNavalRMIService.getListaJogos().size();
				if(aux2 != 0){
					for(int i=0; i< aux2; i++){
						listModel2.addElement(batalhaNavalRMIService.getListaJogos().get(i));
					}
					janela.getListaJogos().setModel(listModel2);
				}
				System.out.println(batalhaNavalRMIService.getListaJogos());
				
	            
	    }

	
	public static void main(String[] args) {

		//criar interface gráfico
		janela = new Janela();
		janela.criarJanela();
		
		
		try{
			
			ClientObserver = new ClienteRMI();
			 
			
		
			String objectUrl = "rmi://localhost/BatalhaNavalRMI"; //rmiRegistry no localhost (por default)
		
			if(args.length > 0)
				objectUrl = "rmi://" + args[0] + "/BatalhaNavalRMI"; //receber o endereço por argumento
			
			batalhaNavalRMIService = (BatalhaNavalRMIInterface) Naming.lookup(objectUrl);
			
			
			//NEW envia ref do objecto
			batalhaNavalRMIService.addObserver(ClientObserver);		
			
			ClientObserver.actualizarInfo(); //a primeira vez só.. depois é o servidor ke faz..
				
			
		}catch(RemoteException e){
			System.out.println("Erro remoto - " + e); //JOptionPane...
			JOptionPane.showMessageDialog(janela.getContentPanel(),"Erro remoto - " + e);
        }catch(NotBoundException e){
        	System.out.println("Servico remoto desconhecido - " + e); //JOptionPane...
        	JOptionPane.showMessageDialog(janela.getContentPanel(),"Servico remoto desconhecido - " + e);
        }catch(Exception e){
        	System.out.println("Erro - " + e); //JOptionPane...
        	JOptionPane.showMessageDialog(janela.getContentPanel(),"Erro - " + e);
        } 
	
	}
	
	

	
}
