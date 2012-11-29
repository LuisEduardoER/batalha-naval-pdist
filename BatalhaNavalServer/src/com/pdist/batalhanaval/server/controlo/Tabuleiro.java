package com.pdist.batalhanaval.server.controlo;

import java.util.ArrayList;

import com.pdist.batalhanaval.server.macros.Macros;

public class Tabuleiro {
	private ArrayList<UnidadeTabuleiro> tabuleiro;
	private ArrayList<Ships> barcos;
	private int ships;
	
	//construtores
	public Tabuleiro(){
		tabuleiro = new ArrayList<UnidadeTabuleiro>();
		barcos = new ArrayList<Ships>();
		ships = 0;
	}
	public Tabuleiro(ArrayList<UnidadeTabuleiro> tabuleiro){
		this.tabuleiro = tabuleiro;
		barcos = new ArrayList<Ships>();
		ships = 0;
	}
	public Tabuleiro(ArrayList<UnidadeTabuleiro> tabuleiro, ArrayList<Ships> barcos){
		this.tabuleiro = tabuleiro;
		this.barcos = barcos;
		ships = barcos.size();
	}
	
	//Getters
	public ArrayList<UnidadeTabuleiro> getTabuleiro() {return tabuleiro;}
	public ArrayList<Ships> getBarcos() {return barcos;}
	public int getShips() {return ships;}
	
	//Setters
	public void setTabuleiro(ArrayList<UnidadeTabuleiro> tabuleiro) {this.tabuleiro = tabuleiro;}
	public void setBarcos(ArrayList<Ships> barcos) {
		this.barcos = barcos;
		ships = barcos.size();
	}
	
	//Para o caso de se retirarem ou adicionarem barcos
	public void updateShips() {ships = barcos.size();}
	
	
	//para ir buscar a unidade certa
	public UnidadeTabuleiro getUnidade(Letra l, Numero n){
		UnidadeTabuleiro u = new UnidadeTabuleiro();
		int pos;
			
		pos = (l.getPosY()-1)*Macros.TAM_X + n.getPosX();
			
		if((pos-1)>=tabuleiro.size())
			return null;
			
		u = tabuleiro.get(pos-1);			
		return u;
	}
	
	
}
