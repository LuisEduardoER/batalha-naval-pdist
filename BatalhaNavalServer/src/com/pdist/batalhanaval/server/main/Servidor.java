package com.pdist.batalhanaval.server.main;

import java.util.ArrayList;

import com.pdist.batalhanaval.server.controlo.Cliente;
import com.pdist.batalhanaval.server.controlo.Jogo;

public class Servidor {
	private ArrayList<Cliente> clientes;
	private ArrayList<Jogo> jogos;
	private int nClientes;
	private int nJogos;
	
	
	//Cosntrutor
	public Servidor(){
		clientes = new ArrayList<Cliente>();
		jogos = new ArrayList<Jogo>();
		nClientes = nJogos = 0;
	}


	//Getters
	public ArrayList<Cliente> getClientes() {return clientes;}
	public ArrayList<Jogo> getJogos() {return jogos;}
	public int getnClientes() {return nClientes;}
	public int getnJogos() {return nJogos;}


	//Setters
	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
		nClientes = clientes.size();
	}
	public void setJogos(ArrayList<Jogo> jogos) {
		this.jogos = jogos;
		nJogos = jogos.size();
	}
	
}
