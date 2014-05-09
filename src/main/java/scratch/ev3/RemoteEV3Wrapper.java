package scratch.ev3;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lejos.hardware.port.Port;
import lejos.remote.ev3.RemoteEV3;

public class RemoteEV3Wrapper extends RemoteEV3 implements RemoteEV3Inf {

	private static final Logger L = LoggerFactory
			.getLogger(RemoteEV3Wrapper.class);

	public RemoteEV3Wrapper(String host) throws RemoteException,
			MalformedURLException, NotBoundException {
		super(host);

		if (L.isDebugEnabled()) {
			for (String port : new String[] { "A", "B", "C", "D", "S1", "S2",
					"S3", "S4" }) {
				Port p = getPort(port);
				L.debug("port {}: port type {}; sensor type {}", port,
						p.getPortType(), p.getSensorType());
			}
		}

	}

}
