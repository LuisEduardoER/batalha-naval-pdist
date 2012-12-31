package com.pdist.batalhanaval.server.controlo;

import java.io.Serializable;
import java.util.ArrayList;

import com.pdist.batalhanaval.server.macros.Macros;

public class Tabuleiro implements Serializable{
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
	
	
	public void setShipsOnTab(){
		//pesquisar barcos horizontais
		for(int i=0; i<Macros.SIZE_Y-1;i++){
			for(int j=0;j<Macros.SIZE_X-1;j++){
				
				if(tabuleiro.get(i*10+j).getImage() == Macros.IMAGEM_BARCO_ESQ){ 
					if(tabuleiro.get(i*10+j+1).getImage() == Macros.IMAGEM_BARCO_DIR){
						Ships barco = new Ships();
						barco.addUnidade(tabuleiro.get(i*10+j));
						barco.addUnidade(tabuleiro.get(i*10+j+1));
						j=j+2;
						while(tabuleiro.get(i*10+j).getImage() != Macros.IMAGEM_AGUA && j<Macros.SIZE_X){
							barco.addUnidade(tabuleiro.get(i*10+j));
							j++;
						}							
						barcos.add(barco);						
					}
				}	
			}			
		}
		
		//pesquisar barcos verticais
		for(int i=0; i<Macros.SIZE_Y-1;i++){
			for(int j=0;j<Macros.SIZE_X-1;j = j+Macros.SIZE_X){						
				if(tabuleiro.get(i*10+j).getImage() == Macros.IMAGEM_BARCO_ESQ){
					if(tabuleiro.get(i*10+j+Macros.SIZE_X).getImage() == Macros.IMAGEM_BARCO_DIR){
						Ships barco = new Ships();
						barco.addUnidade(tabuleiro.get(i*10+j));
						barco.addUnidade(tabuleiro.get(i*10+j+Macros.SIZE_X));
						j=j+Macros.SIZE_X;
						while(tabuleiro.get(i*10+j).getImage() != Macros.IMAGEM_AGUA && j<Macros.SIZE_X){
							barco.addUnidade(tabuleiro.get(i*10+j));
							j = j+Macros.SIZE_X;
						}							
						barcos.add(barco);						
					}
				}
			}			
		}
		
		updateShips();
	}
	
	
	
}
