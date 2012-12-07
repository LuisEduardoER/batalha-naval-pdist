package com.pdist.batalhanaval.client.controlo;

public class Numero {
	private char numero;
	private int posX;

	//construtores
	public Numero(){
		posX = -1;
		numero = ' ';
	}	
	public Numero(char numero){
		posX = -1;
		this.numero = Character.toUpperCase(numero);
	}
	public Numero(char numero, int posX){
		this.posX = posX;
		this.numero = Character.toUpperCase(numero);
	}
		
	//Getters
	public char getNumero() {return numero;}
	public int getPosX() {return posX;}
		
	//Setters
	public void setNumero(char numero) {this.numero = Character.toUpperCase(numero);}
	public void setPosX(int posX) {this.posX = posX;}	
}
