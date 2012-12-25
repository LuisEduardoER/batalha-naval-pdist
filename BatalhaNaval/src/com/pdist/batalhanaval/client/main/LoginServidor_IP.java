package com.pdist.batalhanaval.client.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;
import com.pdist.batalhanaval.client.dialogs.ListarJogoseJogadores;
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
	protected Mensagem msg = null;
	protected int TIMEOUT=1500;
	public static SocketClient_TCP socketclass;
                
	private final JPanel contentPanel = new JPanel();  

                
	public LoginServidor_IP(String IP, String nome, String porto) throws IOException{
                        
                    this.servAddr = InetAddress.getByName(IP);
                    this.servPort = Integer.parseInt(porto);
                    this.nome = nome;
                    this.logIn = false;   
                    
                                        
                    socketclass = new SocketClient_TCP(servAddr,servPort,contentPanel);  //armazena o socket na class static (depois de criado)  
                                        
                    this.socket = SocketClient_TCP.getSocket();
	}                       
        

                
        @Override
        public void run() {
                
                 try{
                       	
             			
             			socket.setSoTimeout(TIMEOUT); //timeout para o socket (deve estar fora da classe tcp para fzr return aqui)
             			
             	            }catch(Exception e)
                            { 
                            	JOptionPane.showMessageDialog(contentPanel,"Erro na ligação ao servidor - TIMEOUT");
                                 VarsGlobais.NovoJogoThreadCreated = false;  
                                return;
                            }
                                                                
                                                                        
                                
                                out = SocketClient_TCP.getOut();
								in = SocketClient_TCP.getIn();
                
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
                                                                                       
                                                
                                                ListarJogoseJogadores dialog = new ListarJogoseJogadores(nome);
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
