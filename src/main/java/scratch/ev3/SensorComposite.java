package scratch.ev3;

import java.rmi.RemoteException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import lejos.hardware.DeviceException;
import lejos.remote.ev3.RMISampleProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class SensorComposite {

	@Autowired
	private RemoteEV3Inf ev3;

	private ConcurrentHashMap<String, RMISampleProvider> sensorMap = new ConcurrentHashMap<>();

	private static final Logger L = LoggerFactory
			.getLogger(SensorComposite.class);

	public SensorComposite() {
	}

	public void createSensor(String port, String type) {
		RMISampleProvider sp = ev3.createSampleProvider(port,
				typeToClassName(type), type);
		sensorMap.put(port, sp);
	}

	private String typeToClassName(String type) {
		switch (type) {
		case "Distance":
			return "lejos.hardware.sensor.EV3IRSensor";
		case "Touch":
			return "lejos.hardware.sensor.EV3TouchSensor";
		case "Color":
			return "lejos.hardware.sensor.EV3ColorSensor";
		}
		throw new IllegalArgumentException("Unknown sensor type " + type);
	}

	public RMISampleProvider getSensor(String port) {
		return sensorMap.get(port);
	}

	public Set<String> getPorts() {
		return sensorMap.keySet();
	}

	public void closeAll() {
		for (String port : sensorMap.keySet()) {
			try {
				L.info("closing port {}", port);
				sensorMap.get(port).close();
				sensorMap.remove(port);
			} catch (RemoteException e) {
				L.error("error closing port {}", port, e);
			}
		}
	}

	public void close(String port) {
		RMISampleProvider sensor = sensorMap.get(port);
		if (sensor == null) {
			L.error("unable to close port {}: port is not initialized", port);
			return;
		}

		try {
			sensor.close();
		} catch (RemoteException e) {
			L.error("unable to close port {}: {}", port, e.getMessage());
		} finally {
			sensorMap.remove(port);
		}

	}

	public void createSensor(String port, String type, String commandId) {
		throw new NotImplementedException();
	}

	public float getSample(String port) {

		RMISampleProvider sensor = sensorMap.get(port);
		if (sensor == null) {
			L.error("unable to read from port {}: port is not initialized",
					port);
			return 0;
		}

		try {
			float[] fetchSample = sensor.fetchSample();
			return fetchSample[0];
		} catch (RemoteException | NullPointerException | DeviceException e) {
			L.error("unable to read from port {}: {}", port, e.getMessage());
			return 0;
		}
	}

}
