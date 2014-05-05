package scratch.ev3;

import lejos.hardware.ev3.EV3;
import lejos.remote.ev3.RMIBluetooth;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RMIWifi;

public interface RemoteEV3Inf extends EV3 {
	
	public String getHost();
	
	public RMISampleProvider createSampleProvider(String portName, String sensorName, String modeName);
	
	public RMIRegulatedMotor createRegulatedMotor(String portName, char motorType);
	
	public RMIWifi getWifi();
	
	public RMIBluetooth getBluetooth();

}
