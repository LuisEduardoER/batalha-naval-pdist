package com.pdist.batalhanaval.client.controlo;

import java.net.InetAddress;

public class Cliente {
	private InetAddress ip;
	private String nome;
	private boolean onGame;

	
	//Constructores
	public Cliente(InetAddress ip){
		this.ip = ip;
		nome = " ";
		onGame = false;
	
	}	
	public Cliente(InetAddress ip, String nome){
		this.ip = ip;
		this.nome = nome;
		onGame = false;
	
	}
	
	//Getters
	public InetAddress getIp() {return ip;}
	public String getNome() {return nome;}
	public boolean isOnGame() {return onGame;}

	
	
	//Setters
	public void setIp(InetAddress ip) {this.ip = ip;}
	public void setNome(String nome) {this.nome = nome;}
	public void setOnGame(boolean onGame) {this.onGame = onGame;}
	
	
	
	
}
