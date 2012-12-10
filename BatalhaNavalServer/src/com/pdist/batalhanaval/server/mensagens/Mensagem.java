package com.pdist.batalhanaval.server.mensagens;

import java.io.Serializable;
import java.util.ArrayList;

import com.pdist.batalhanaval.server.controlo.Cliente;
import com.pdist.batalhanaval.server.controlo.Letra;
import com.pdist.batalhanaval.server.controlo.Numero;

public class Mensagem implements Serializable {

		/**
	 * 
	 */
	private static final long serialVersionUID = -3510385339259903686L;
		private int type;
		private String msg_text;
		private String responseText;
		private Letra letra;
		private Numero numero;
		private Cliente cliente;
		private ArrayList<String> nomesClientes;
		private ArrayList<String> nomesJogadores1;
		private ArrayList<String> nomesJogadores2;
		private ArrayList<String> nomesJogos;
		private ArrayList<Integer> tabuleiro;
		
		
		//construtores
		public Mensagem(int type){
			this.type = type;
			msg_text = "";
			letra = null;
			numero = null;
			cliente = null;
			nomesClientes = null;
			nomesJogadores1 = null;
			nomesJogadores2 = null;
			nomesJogos = null;
			responseText="";
			tabuleiro = null;
		}		
		public Mensagem(int type, String msg_text){
			this.type = type;
			this.msg_text = msg_text;
			letra = null;
			numero = null;
			cliente = null;
			nomesClientes = null;
			nomesJogadores1 = null;
			nomesJogadores2 = null;
			nomesJogos = null;
			responseText="";
			tabuleiro = null;
		}
		public Mensagem(int type, Letra letra, Numero numero){
			this.type = type;
			msg_text = "";
			this.letra = letra;
			this.numero = numero;
			cliente = null;
			nomesClientes = null;
			nomesJogadores1 = null;
			nomesJogadores2 = null;
			nomesJogos = null;
			responseText="";
			tabuleiro = null;
		}
		public Mensagem(int type, Cliente cliente1, Cliente cliente2){
			this.type = type;
			msg_text = "";
			letra = null;
			numero = null;
			this.cliente = cliente1;
			nomesClientes = null;
			nomesJogadores1 = null;
			nomesJogadores2 = null;
			nomesJogos = null;
			responseText="";
			tabuleiro = null;
		}
		
		
		//getters
		public int getType(){return type;}		
		public String getMsgText(){return msg_text;}
		public Letra getLetra(){return letra;}
		public Numero getNumero(){return numero;}
		public Cliente getCliente(){return cliente;}
		public ArrayList<String> getNomesClientes(){return nomesClientes;}
		public ArrayList<String> getNomesJogadores1(){return nomesJogadores1;}
		public ArrayList<String> getNomesJogadores2(){return nomesJogadores2;}
		public ArrayList<String> getNomesJogos(){return nomesJogos;}
		public String getResponseText(){return responseText;}
		public ArrayList<Integer> getTabuleiro(){return tabuleiro;}
		
		//Setters
		public void setType(int type){this.type = type;}
		public void setMsgText(String msg_text){this.msg_text = msg_text;}
		public void setLetra(Letra letra){this.letra = letra;}
		public void setNumero(Numero numero){this.numero = numero;}
		public void setCliente(Cliente cliente){this.cliente = cliente;}
		public void setNomesClientes(ArrayList<String> nomesClientes){this.nomesClientes = nomesClientes;}
		public void setNomesJogadores1(ArrayList<String> nomesJogadores1){this.nomesJogadores1 = nomesJogadores1;}
		public void setNomesJogadores2(ArrayList<String> nomesJogadores2){this.nomesJogadores2 = nomesJogadores2;}
		public void setNomesJogos(ArrayList<String> nomesJogos){this.nomesJogos = nomesJogos;}
		public void setResponseText(String responseText){this.responseText = responseText;}
		public void setTabuleiro(ArrayList<Integer> tabuleiro){this.tabuleiro = tabuleiro;}
		
		//usar os nomes de clientes
		public void addNomesClientes(String nome){nomesClientes.add(nome);}
		public void removeNomesCliente(String nome){nomesClientes.remove(nome);}
		public String getNomesCliente(int i){
			if(i<0 || i>nomesClientes.size()-1)
				return null;
			
			return nomesClientes.get(i);}
		public void clearNomesClientes(){nomesClientes.clear();}
		
		//usar nomes de jogadores 1
		public void addNomesJogadores1(String nome){nomesJogadores1.add(nome);}
		public void removeNomesJogadores1(String nome){nomesJogadores1.remove(nome);}
		public String getNomesJogadores1(int i){
			if(i<0 || i>nomesJogadores1.size()-1)
				return null;
			
			return nomesJogadores1.get(i);}
		public void clearNomesJogadores1(){nomesJogadores1.clear();}
		
		
		//usar nomes de jogadores 2
		public void addNomesJogadores2(String nome){nomesJogadores2.add(nome);}
		public void removeNomesJogadores2(String nome){nomesJogadores2.remove(nome);}
		public String getNomesJogadores2(int i){
			if(i<0 || i>nomesJogadores2.size()-1)
				return null;
					
			return nomesJogadores2.get(i);}
		public void clearNomesJogadores2(){nomesJogadores2.clear();}

		//usar nomes de jogos
		public void addNomesJogos(String nome){nomesJogos.add(nome);}
		public void removeNomesJogos(String nome){nomesJogos.remove(nome);}
		public String getNomesJogos(int i){
			if(i<0 || i>nomesJogos.size()-1)
				return null;
							
			return nomesJogos.get(i);}
		public void clearNomesJogos(){nomesJogos.clear();}		
		
		//usar tabuleiro
		public void addParteTabuleiro(int uni){tabuleiro.add(uni);}
		public void removeParteTabuleiro(int pos){tabuleiro.remove(pos);}
		public Integer getParteTabuleiro(int i){
			if(i<0 || i>tabuleiro.size()-1)
				return null;
									
			return tabuleiro.get(i);}
		public void clearTabuleiro(){tabuleiro.clear();}
}
