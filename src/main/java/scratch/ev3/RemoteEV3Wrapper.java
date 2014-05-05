package scratch.ev3;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.remote.ev3.RemoteEV3;

public class RemoteEV3Wrapper extends RemoteEV3 implements RemoteEV3Inf {

	public RemoteEV3Wrapper(String host) throws RemoteException,
			MalformedURLException, NotBoundException {
		super(host);
	}

}
