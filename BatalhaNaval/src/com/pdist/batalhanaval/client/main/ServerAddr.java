package com.pdist.batalhanaval.client.main;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerAddr {

	private static String servAddr;
	private static String porto;
	
	
	public ServerAddr(){}
	
	public ServerAddr(String servAddr, String porto){
		this.servAddr = servAddr;
		this.porto = porto;
	}
	

	//get e set SERV_ADDR
	public static InetAddress getServAddr() throws UnknownHostException {
		return InetAddress.getByName(servAddr);
	}
	public static void setServAddr(String servAddr) {
		ServerAddr.servAddr = servAddr;
	}

	//get e set PORTO
	public static int getPorto() {
		return Integer.parseInt(porto);
	}
	public static void setPorto(String porto) {
		ServerAddr.porto = porto;
	}
	
	
	
}
