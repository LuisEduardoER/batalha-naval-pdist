package com.pdist.batalhanaval.client.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;
import com.pdist.batalhanaval.client.dialogs.ListarJogos;
import com.pdist.batalhanaval.client.main.BatalhaNaval_Client;
import com.pdist.batalhanaval.client.main.SocketClient_TCP;


public class LoginServidor_IP implements Runnable {
        
        
	protected Socket socket;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;
	protected boolean logIn;
	protected InetAddress servAddr = null;
	protected int servPort;
	protected String nome;
	public    Mensagem msg = null;
	protected int TIMEOUT=1500;
                
	private final JPanel contentPanel = new JPanel();  

                
	public LoginServidor_IP(String IP, String nome, String porto) throws IOException{
                        
                    this.servAddr = InetAddress.getByName(IP);
                    this.servPort = Integer.parseInt(porto);
                    this.nome = nome;
                    this.logIn = false;   
                    socket = new Socket(servAddr,servPort);  
                    SocketClient_TCP.setSocket(socket);    //armazena o socket na class static (depois de criado)                   
                    
	}                       
        

                
        @Override
        public void run() {
                
                 try{
        
                	 socket.setSoTimeout(TIMEOUT); //necessario para alguns trycatch
                	 
                            }catch(Exception e)
                            { 
                                 JOptionPane.showMessageDialog(contentPanel,"Erro na ligação ao servidor");
                                 VarsGlobais.NovoJogoThreadCreated = false;  
                                         return;
                            }
                                
                                
                                                                        
                                
                                try {
                                        out = new ObjectOutputStream(socket.getOutputStream());
                                        in = new ObjectInputStream(socket.getInputStream());
                                } catch (IOException e1) {
                                        e1.printStackTrace(); }
                
                while(true){                    
                        try {                                           
                                if(!logIn){
                                        sendLoginRequest();                                     
                                }
                                
                                msg = (Mensagem) in.readObject();
                                switch(msg.getType()){                                  
                                        case Macros.MSG_LOGIN_FAIL:
                                                sendLoginResponse();
                                                break;
                                        case Macros.MSG_LOGIN_LOGGED:
                                                noteAlreadyLogged();
                                                break;
                                        case Macros.MSG_LOGIN_VALIDATED:
                                                logIn = true;
                                                JOptionPane.showMessageDialog(contentPanel,"(Servidor) Estás logado!");
                                                //Teste=====
                                                BatalhaNaval_Client.setNomeJogador1(nome);
                                                BatalhaNaval_Client.setNomeJogador2("A aguardar..");
                                                BatalhaNaval_Client.setEstado("A aguardar jogador 2...");
                                                
                                                //Cria Lista de Jogos dialog
                                                msg=sendListaJogosRequest();                                               
                                                
                                                ListarJogos dialog = new ListarJogos(msg);
                        						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        						dialog.setVisible(true);	                                               
                                                
                                                
                                                //========                                                
                                                break;
                                }                                       
                                
                        } catch (IOException  e) {
                                
                                 JOptionPane.showMessageDialog(contentPanel,"Erro na ligação ao servidor");
                                 VarsGlobais.NovoJogoThreadCreated = false;  
                                 return;
                        } catch(ClassNotFoundException e){
                                 JOptionPane.showMessageDialog(contentPanel,"Erro ao receber a mensagem");
                                 VarsGlobais.NovoJogoThreadCreated = false;  
                                 return;                                
                        }
                }               
        }       
        
        public void sendLoginResponse() throws IOException{
                //nome = pede novo nome;
                sendLoginRequest();
        }
        
        public Mensagem sendListaJogosRequest() throws IOException{
        	
            Mensagem msg = new Mensagem(Macros.MSG_LISTA_JOGOS);
            msg.setMsgText(nome);
            
            out.flush();
            out.writeObject(msg);
            out.flush();
            
            try{
              	
                msg = (Mensagem) in.readObject(); 
                
                } catch (Exception  e) {                                                    
                    JOptionPane.showMessageDialog(contentPanel,"Erro ao obter lista de jogos");
                    VarsGlobais.NovoJogoThreadCreated = false;                     
                }
            
            return msg;
    }
        
        public void sendLoginRequest() throws IOException{
                Mensagem msg = new Mensagem(Macros.MSG_LOGIN_REQUEST);
                msg.setMsgText(nome);
                out.flush();
                out.writeObject(msg);
                out.flush();
        }
        
        public void noteAlreadyLogged(){
                //avisa o utilizador que já está logado
        }
}
