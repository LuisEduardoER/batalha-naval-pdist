package com.pdist.batalhanaval.server.controlo;

import java.util.ArrayList;

import com.pdist.batalhanaval.server.macros.Macros;

public class Ships {
	private ArrayList<UnidadeTabuleiro> barco;
	private int xStart, xEnd;
	private int yStart, yEnd;
	
	
	//construtores
	public Ships(){
		barco = new ArrayList<UnidadeTabuleiro>();
		xStart = xEnd = yStart = yEnd = -1;
	}
	public Ships(ArrayList<UnidadeTabuleiro> barco){
		this.barco = barco;
		
		xStart = barco.get(0).getX();
		yStart = barco.get(0).getY();
		
		xEnd = barco.get(barco.size()-1).getX()+Macros.SIZE_X;
		yEnd = barco.get(barco.size()-1).getY()+Macros.SIZE_Y;
	}
	
	//Getters
	public ArrayList<UnidadeTabuleiro> getBarco() {return barco;}
	public int getxStart() {return xStart;}
	public int getxEnd() {return xEnd;}
	public int getyStart() {return yStart;}
	public int getyEnd() {return yEnd;}
	
	//Setters
	public void setBarco(ArrayList<UnidadeTabuleiro> barco) {
		this.barco = barco;
		
		xStart = barco.get(0).getX();
		yStart = barco.get(0).getY();
		
		xEnd = barco.get(barco.size()-1).getX()+Macros.SIZE_X;
		yEnd = barco.get(barco.size()-1).getY()+Macros.SIZE_Y;
	}
	public void setxStart(int xStart) {this.xStart = xStart;}
	public void setxEnd(int xEnd) {this.xEnd = xEnd;}
	public void setyStart(int yStart) {this.yStart = yStart;}
	public void setyEnd(int yEnd) {this.yEnd = yEnd;}
	
	
	//Metodo actualizar x's e y's (caso se adicionem ou retirem unidade de tabuleiro ao barco)
	public boolean updateSize(){
		if(barco.size()<=0)
			return false;
		
		xStart = barco.get(0).getX();
		yStart = barco.get(0).getY();
		
		xEnd = barco.get(barco.size()-1).getX()+Macros.SIZE_X;
		yEnd = barco.get(barco.size()-1).getY()+Macros.SIZE_Y;
		
		return true;
	}
	//Metodo para saber se o toque foi dento do barco
	public boolean isOn(int pX, int pY){
		if(xStart<0 || yStart<0 || xEnd <= xStart || yEnd <= yStart) 
			return false;
	
		if(xStart <= pX && xEnd >= pX)
			if(yStart <= pY && yEnd >= pY)
				return true;
		return false;
	}
	
	
	

}
