package com.pdist.batalhanaval.client.main;

import java.util.ArrayList;




public class VarsGlobais {
	//CLIENTE
	public static boolean NovoJogoThreadCreated = false;
	public static boolean MeuTurno = false;
	
	public static ArrayList<Integer> tabJogador1 = new ArrayList<Integer>();
	
	//para impedir que o user spamme o outro com convites enquanto não receber a resposta do ultimo que mandou
	public static boolean sentConvite = false;
	
	private VarsGlobais(){}
}
