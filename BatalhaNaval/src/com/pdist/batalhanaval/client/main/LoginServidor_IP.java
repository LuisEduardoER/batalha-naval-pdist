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


public class LoginServidor_IP implements Runnable {
        
        
	protected Socket socket;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;
	protected boolean logIn;
	protected InetAddress servAddr = null;
	protected int servPort;
	protected String nome;
                
	private final JPanel contentPanel = new JPanel();       
                
        
                
	public LoginServidor_IP(String IP, String nome, String porto) throws IOException{
                        
                    this.servAddr = InetAddress.getByName(IP);
                    this.servPort = Integer.parseInt(porto);
                    this.nome = nome;
                    this.logIn = false;                 
                        
                }                       
        

                
        @Override
        public void run() {
                
                 try{
                                socket = new Socket(servAddr,servPort); 
                            }catch(Exception e)
                            { 
                                 JOptionPane.showMessageDialog(contentPanel,"Erro na ligação ao servidor");
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
                                                break;
                                }                                       
                                
                        } catch (IOException  e) {
                                
                                 JOptionPane.showMessageDialog(contentPanel,"Erro na ligação ao servidor");
                                 return;
                        } catch(ClassNotFoundException e){
                                 JOptionPane.showMessageDialog(contentPanel,"Erro ao receber a mensagem");
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
