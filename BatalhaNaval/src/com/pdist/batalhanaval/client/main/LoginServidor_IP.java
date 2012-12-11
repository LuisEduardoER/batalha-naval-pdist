package com.pdist.batalhanaval.client.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.pdist.batalhanaval.server.macros.Macros;
import com.pdist.batalhanaval.server.mensagens.Mensagem;
import com.pdist.batalhanaval.client.main.BatalhaNaval_Client;


public class LoginServidor_IP implements Runnable {
        
        
	protected Socket socket;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;
	protected boolean logIn;
	protected InetAddress servAddr = null;
	protected int servPort;
	protected String nome;
                
	private final JPanel contentPanel = new JPanel();                     
    
	private SocketCliente SocketServer = new SocketCliente(); //armazena o socket com ligação ao server (socket)
                
	public LoginServidor_IP(String IP, String nome, String porto) throws IOException{
                        
                    this.servAddr = InetAddress.getByName(IP);
                    this.servPort = Integer.parseInt(porto);
                    this.nome = nome;
                    this.logIn = false;                 
                      
                    SocketServer.setSocket(socket); //armazena o socket com lig. ao server
                    
	}                       
        

                
        @Override
        public void run() {
                
                 try{
                                socket = new Socket(servAddr,servPort); 
                            }catch(Exception e)
                            { 
                                 JOptionPane.showMessageDialog(contentPanel,"Erro na ligação ao servidor");
                                 VarsGlobais.NovoJogoThreadCreated = false;  
                                         return;
                            }
                                //socket.setSoTimeout(TIMEOUT);
                                
                                                                        
                                
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
                                
                                Mensagem msg = (Mensagem) in.readObject();
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
