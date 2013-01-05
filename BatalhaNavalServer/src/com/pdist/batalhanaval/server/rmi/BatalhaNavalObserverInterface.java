package com.pdist.batalhanaval.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;



public interface BatalhaNavalObserverInterface extends Remote{
	
	public void actualizarInfo() throws RemoteException;
	
}
