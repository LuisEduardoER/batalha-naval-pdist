package com.pdist.batalhanaval.server.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class BatalhaNavalServer {

	/*Tem de aceitar ligações tcp e responder a multicast UDP 
	 * */	
	
	private static ServerSocket ss;
	public static ArrayList<Thread> threads = new ArrayList<Thread>();
	
	public static void main(String[] args) {
		
		try {
			ss = new ServerSocket(5001);
			
			while(true){				
				Socket client = ss.accept();
				Thread t = new AtendeCliente(client);
				threads.add(t);
				t.start();
				t.join();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
