package com.pdist.batalhanaval.server.main;

import java.util.ArrayList;
import com.pdist.batalhanaval.server.controlo.Cliente;

public class VarsGlobais {

	public static ArrayList<Cliente> ClientesOn = new ArrayList<Cliente>();
	public static ArrayList<Thread> threads = new ArrayList<Thread>();
	public static int nClientes = 0;
	
	private VarsGlobais(){}

}
