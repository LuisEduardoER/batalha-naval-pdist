package com.pdist.batalhanaval.client.mensagens;

import com.pdist.batalhanaval.client.controlo.Cliente;
import com.pdist.batalhanaval.client.controlo.Letra;
import com.pdist.batalhanaval.client.controlo.Numero;

public class Mensagem {

		private int type;
		private String msg_text;
		private Letra letra;
		private Numero numero;
		private Cliente cliente;
		
		//construtores
		public Mensagem(int type){
			this.type = type;
			msg_text = "";
			letra = null;
			numero = null;
			cliente = null;
		}		
		public Mensagem(int type, String msg_text){
			this.type = type;
			this.msg_text = msg_text;
			letra = null;
			numero = null;
			cliente = null;
		}
		public Mensagem(int type, Letra letra, Numero numero){
			this.type = type;
			msg_text = "";
			this.letra = letra;
			this.numero = numero;
			cliente = null;
		}
		public Mensagem(int type, Cliente cliente){
			this.type = type;
			msg_text = "";
			letra = null;
			numero = null;
			this.cliente = cliente;
		}
		
		
		//getters
		public int getType(){return type;}		
		public String getMsgText(){return msg_text;}
		public Letra getLetra(){return letra;}
		public Numero getNumero(){return numero;}
		public Cliente getCliente(){return cliente;}
		
		
		//Setters
		public void setType(int type){this.type = type;}
		public void setMsgText(String msg_text){this.msg_text = msg_text;}
		public void setLetra(Letra letra){this.letra = letra;}
		public void setNumero(Numero numero){this.numero = numero;}
		public void setCliente(Cliente cliente){this.cliente = cliente;}
}
