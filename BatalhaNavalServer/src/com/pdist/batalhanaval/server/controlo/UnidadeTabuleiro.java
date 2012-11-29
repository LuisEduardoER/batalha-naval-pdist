package com.pdist.batalhanaval.server.controlo;
import java.net.URI;

import com.pdist.batalhanaval.server.macros.*;

public class UnidadeTabuleiro {
	private int x,y;
	private URI image;
	private boolean ocupied;
	private boolean shooted;

	//Constructores;
	public UnidadeTabuleiro(){
		x = y = -1;
		//image = imagem;
		ocupied = shooted = false;
		
	}
	public UnidadeTabuleiro(int x, int y){

		this.x = x;
		this.y = y;
		ocupied = shooted = false;
	}
	
	//Getters
	public int getX(){return x;}
	public int getY() {return y;}
	public URI getImage() {return image;}
	public boolean isOcupied() {return ocupied;}
	public boolean isShooted() {return shooted;}
	
	//Setters
	public void setX(int x){this.x = x;}
	public void setY(int y) {this.y = y;}
	public void setImage(URI image) {this.image = image;}
	public void setOcupied(boolean ocupied) {this.ocupied = ocupied;}
	public void setShooted(boolean shooted) {this.shooted = shooted;}

	//Verificar se um ponto está na quadricula
	public boolean isOn(int pX, int pY){
		
		if(x<0 || y<0) //falta pôr os >
			return false;
	
		if(x <= pX && (x+Macros.SIZE_X) >= pX)
			if(y <= pY && (y+Macros.SIZE_Y) >= pY)
				return true;
		return false;
	}

}
