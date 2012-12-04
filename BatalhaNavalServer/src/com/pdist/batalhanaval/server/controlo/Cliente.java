package com.pdist.batalhanaval.server.controlo;

import java.net.InetAddress;

public class Cliente {
	private InetAddress ip;
	private String nome;
	private boolean onGame;
	private Tabuleiro tabuleiro;
	
	//Constructores
	public Cliente(InetAddress ip){
		this.ip = ip;
		nome = " ";
		onGame = false;
		tabuleiro = null;
	}	
	public Cliente(InetAddress ip, String nome){
		this.ip = ip;
		this.nome = nome;
		onGame = false;
		tabuleiro = null;
	}
	
	//Getters
	public InetAddress getIp() {return ip;}
	public String getNome() {return nome;}
	public boolean isOnGame() {return onGame;}
	public Tabuleiro getTabuleiro(){return tabuleiro;}
	
	
	//Setters
	public void setIp(InetAddress ip) {this.ip = ip;}
	public void setNome(String nome) {this.nome = nome;}
	public void setOnGame(boolean onGame) {this.onGame = onGame;}
	public void setTabuleiro(Tabuleiro tabuleiro){this.tabuleiro = tabuleiro;}
	
	
	
	
}
