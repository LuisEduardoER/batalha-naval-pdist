package com.pdist.batalhanaval.server.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class BatalhaNaval_Server {

	/*Tem de aceitar ligações tcp e responder a multicast UDP 
	 * */	
	
	private static ServerSocket ss;
	//===========Porta fixa do servidor==============
	private static int ServerPort = 5001;
	//===========Porta fixa do servidor==============
	
	public static void main(String[] args) throws IOException {
		
	System.out.println("===== Inicio Servidor (Port:"+ServerPort+")=====");
	int numCliente=0;
	Socket client = null;
	
	MulticastThread multicast = new MulticastThread();
	multicast.start();
	
	try{ 
			
			ss = new ServerSocket(ServerPort);
			
			while(true){
				client = ss.accept();
				
				System.out.println("===== Cliente #"+(++numCliente)+" =====");
				Thread t = new AtendeCliente(client);
				
				VarsGlobais.threads.add(t);		
				VarsGlobais.nThreads++;
				t.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			if(client !=  null)
				client.close();
			
		} 
		
	}

}
