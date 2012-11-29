package com.pdist.batalhanaval.server.controlo;


//Letras no Tabuleiro
public class Letra {
	private char letra;
	private int posY;
	
	
	//construtores
	public Letra(){
		posY = -1;
		letra = ' ';
	}	
	public Letra(char letra){
		posY = -1;
		this.letra = Character.toUpperCase(letra);
	}
	public Letra(char letra, int posY){
		this.posY = posY;
		this.letra = Character.toUpperCase(letra);
	}
	
	//Getters
	public char getLetra() {return letra;}
	public int getPosY() {return posY;}
	
	//Setters
	public void setLetra(char letra) {this.letra = Character.toUpperCase(letra);}
	public void setPosY(int posY) {this.posY = posY;}
	
	
	
	

}
