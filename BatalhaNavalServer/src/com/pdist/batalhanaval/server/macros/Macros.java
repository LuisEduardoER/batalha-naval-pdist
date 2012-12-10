package com.pdist.batalhanaval.server.macros;


public class Macros {

	public static final int SIZE_X = 10;
	public static final int SIZE_Y = 10;
	public static final int TAM_X = 10;
	public static final int TAM_Y = 10;
	public static int MAX_SIZE = 1000;
	public static final String askIP = "GIVE_IP";
	public final static int TIMEOUT = 1500;
	
	//MACROS MENSAGENS
	public static final int MSG_LOGIN_REQUEST = 1; //pedido para o jogador se registar
	public static final int MSG_LOGIN_FAIL = 2; // aviso de falha
	public static final int MSG_LOGIN_VALIDATED = 3; // aviso de validação
	public static final int MSG_LOGIN_CANCEL = 4; //Aviso de que o utilizador já não se quer registar
	public static final int MSG_LOGIN_LOGGED = 5; //Ja estava logado
	
	public static final int MSG_LISTA_ONLINE = 6;
	public static final int MSG_ONLINE_RESPONSE = 7;
	public static final int MSG_LISTA_JOGOS = 8;
	public static final int MSG_JOGOS_RESPONSE = 9;
	
	
	private Macros(){} //Singleton
	

}
