package com.pdist.batalhanaval.server.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import com.pdist.batalhanaval.server.macros.Macros;

public class MulticastThread extends Thread{
	protected MulticastSocket s;
    protected DatagramPacket pkt;
    protected DatagramPacket send;
    protected int port = 5001;
    protected InetAddress ip;//Nosso ip
    protected InetAddress group;//ip de broadcast
    protected InetAddress ip_client;//ip do cliente que pede
    protected boolean running;
    protected byte[] barray;
    
    
    public MulticastThread(){
    	try {
			group = InetAddress.getByName("230.1.1.1");
			ip = InetAddress.getLocalHost();
			
			s = new MulticastSocket(port);
			s.joinGroup(group);
			running = true;
			
			barray = new byte[Macros.MAX_SIZE];
	        pkt = new DatagramPacket(barray,Macros.MAX_SIZE);
			this.setDaemon(true);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    }
    
    public void run(){
    	
    	if(s == null || running == false)
    		return;
    	
    	while(running){
    		try {
				s.receive(pkt);
				
				if(pkt.getData().toString().equals(Macros.askIP)){
					ip_client = pkt.getAddress();
					String ipS = ip.toString();
					barray = ipS.getBytes();
					send.setData(barray);
					send.setAddress(ip_client);
					send.setPort(pkt.getPort());
					s.send(send);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
    		
    	}
    	
    }

    public void terminate(){
    	running = false;
    }
    
    
}
