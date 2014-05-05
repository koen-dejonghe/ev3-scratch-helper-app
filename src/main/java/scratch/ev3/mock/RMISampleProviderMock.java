package scratch.ev3.mock;

import java.rmi.RemoteException;
import java.util.Random;

import lejos.remote.ev3.RMISampleProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RMISampleProviderMock implements RMISampleProvider {

	private String portName;
	private String sensorName;
	private String modeName;

	private Random r;

	private static final Logger L = LoggerFactory
			.getLogger(RMISampleProviderMock.class);

	public RMISampleProviderMock(String portName, String sensorName,
			String modeName) {

		this.portName = portName;
		this.sensorName = sensorName;
		this.modeName = modeName;

		r = new Random();

		L.info("instantiating {} on port {} with mode {} ",
				new Object[] { sensorName, portName, modeName });
	}

	@Override
	public float[] fetchSample() throws RemoteException {
		
		int max = 50;
		if (modeName.equals("Distance")){
			max = 100;
		}
		float f = r.nextInt(max);
		
		float[] fa = new float[] { f };
		return fa;
	}

	@Override
	public void close() throws RemoteException {
		// TODO Auto-generated method stub

	}

}
