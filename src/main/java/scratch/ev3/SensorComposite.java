package scratch.ev3;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.PreDestroy;

import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SensorComposite {

	@Autowired
	private RemoteEV3 ev3;

	private HashMap<String, RMISampleProvider> sensorMap = new HashMap<>();
	
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
	
	public RMISampleProvider getSensor(String port){
		return sensorMap.get(port);
	}
	
	public Set<String> getPorts(){
		return sensorMap.keySet();
	}

	@PreDestroy
	public void closeAll() {
		for (RMISampleProvider sensor : sensorMap.values()){
			try {
				sensor.close();
			} catch (RemoteException e) {
				L.error("Unable to close sensor", e);
			}
		}
	}

	public void createSensor(String port, String type, String commandId) {
		// TODO Auto-generated method stub
		
	}

}
