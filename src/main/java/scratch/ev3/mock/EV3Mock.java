package scratch.ev3.mock;

import scratch.ev3.RemoteEV3Inf;
import lejos.hardware.Audio;
import lejos.hardware.Key;
import lejos.hardware.Keys;
import lejos.hardware.LED;
import lejos.hardware.LocalBTDevice;
import lejos.hardware.LocalWifiDevice;
import lejos.hardware.Power;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.Port;
import lejos.remote.ev3.RMIBluetooth;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RMIWifi;

public class EV3Mock implements RemoteEV3Inf {

	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RMISampleProvider createSampleProvider(String portName,
			String sensorName, String modeName) {
		return new RMISampleProviderMock(portName, sensorName, modeName);
	}

	@Override
	public RMIRegulatedMotor createRegulatedMotor(String portName,
			char motorType) {
		return new RMIRegulatedMotorMock(portName, motorType);
	}

	@Override
	public RMIWifi getWifi() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RMIBluetooth getBluetooth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Port getPort(String portName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Power getPower() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Audio getAudio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TextLCD getTextLCD() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TextLCD getTextLCD(Font f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GraphicsLCD getGraphicsLCD() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLocal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalBTDevice getBluetoothDevice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalWifiDevice getWifiDevice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDefault() {
		// TODO Auto-generated method stub

	}

	@Override
	public Keys getKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Key getKey(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LED getLED() {
		// TODO Auto-generated method stub
		return null;
	}

}
