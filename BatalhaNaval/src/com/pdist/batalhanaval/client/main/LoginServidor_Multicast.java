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


public class LoginServidor_Multicast implements Runnable {
        
        
	protected Socket socket;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;
	protected boolean logIn;
	protected InetAddress servAddr = null;
	protected int servPort;
	protected String nome;
                
	private final JPanel contentPanel = new JPanel();       
                
        
                
	public LoginServidor_Multicast(String IP, String nome) throws IOException{
                        
                    this.servAddr = InetAddress.getByName(IP);
                    this.nome = nome;
                    this.logIn = false;                 
                        
                }                       
        

                
        @Override
        public void run() {
                
                
        }       
        
        public void sendLoginResponse() throws IOException{
                //nome = pede novo nome;
                sendLoginRequest();
        }
        
        public void sendLoginRequest() throws IOException{
               
        }
        
        public void noteAlreadyLogged(){
                //avisa o utilizador que já está logado
        }
}
