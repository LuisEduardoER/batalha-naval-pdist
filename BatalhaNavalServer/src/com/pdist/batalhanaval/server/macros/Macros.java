package com.pdist.batalhanaval.server.macros;


public class Macros {

	public static final int SIZE_X = 10;
	public static final int SIZE_Y = 10;
	public static final int TAM_X = 32;
	public static final int TAM_Y = 32;
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
	
	public static final int MSG_INICIAR_JOGO = 10;//um jogador pede para o jogo começar
	public static final int MSG_PEDIDO_JOGO = 11;//o servidor reencaminha o pedido para o 2º jogador
	public static final int MSG_INICIAR_RESPONSE = 12;//Resposta dada ao 1º utilizador
	public static final int MSG_PEDIDO_RESPONSE = 13;//Resposta dada pelo 2º utilizador ao servidor
	
	public static final int MSG_JOGAR = 14;
	public static final int MSG_JOGAR_RESPONSE = 15;
	public static final int MSG_GET_TABULEIRO = 16;
	public static final int MSG_SET_TABULEIRO = 17;
	
	public static final int MSG_ATACAR = 18;
	public static final int MSG_ACTUALIZAR_TABS = 19;
	
	public static final String ACEITAR_PEDIDO = "aceitar";
	public static final String REJEITAR_PEDIDO = "rejeitar";
	public static final String IGNORAR_PEDIDO = "ignorar"; // pode ser com timeOut depois de receber a proposta
	
	public static final int IMAGEM_AGUA = 1000;
	public static final int IMAGEM_SHOTED = 1001;
	public static final int IMAGEM_BARCO_1 = 1002;//cabeça do barco
	public static final int IMAGEM_BARCO_2 = 1002;//resto do barco
	
	private Macros(){} //Singleton
	

}
