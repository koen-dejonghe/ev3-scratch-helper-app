package scratch.ev3;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.Audio;
import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;
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
import lejos.remote.ev3.RemoteEV3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RemoteEV3Proxy implements RemoteEV3Inf {

	private static final Logger L = LoggerFactory
			.getLogger(RemoteEV3Proxy.class);

	@Autowired
	private String ev3IpAddress;

	private RemoteEV3 remoteEV3 = null;

	public RemoteEV3Proxy() {
	}

	private synchronized RemoteEV3 implementation() {

		if (remoteEV3 != null) {
			return remoteEV3;
		}

		try {
			if (ev3IpAddress != null && ev3IpAddress.length() > 0) {
				L.info("using ev3 ip address from application.properties: {}",
						ev3IpAddress);
				remoteEV3 = new RemoteEV3(ev3IpAddress);
				return remoteEV3;
			}
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			L.error("unable to connect to ev3 at address {}: {}", ev3IpAddress,
					e.getMessage());
		}

		while (true) {
			L.info("searching for ev3...");
			try {

				for (BrickInfo brick : BrickFinder.discover()) {

					String ipAddress = brick.getIPAddress();
					String type = brick.getType();

					L.info("brick of type {} found at address {}", type,
							ipAddress);

					if (!"EV3".equals(type)) {
						L.info("brick at {} is not an ev3");
						continue;
					}

					try {
						remoteEV3 = new RemoteEV3(ipAddress);
						remoteEV3.setDefault();
						return remoteEV3;
					} catch (RemoteException | MalformedURLException
							| NotBoundException e) {
						L.error("unable to connect to ev3 at address {}: {}",
								ipAddress, e.getMessage());
					}
				}
			} catch (IOException e) {
				// ignore this error ?
			}

			try {
				Thread.sleep(3000);
			} catch (InterruptedException ie) {
				L.error("error while trying to sleep", ie);
			}
		}

	}

	@Override
	public Port getPort(String portName) {
		return implementation().getPort(portName);
	}

	@Override
	public Power getPower() {
		return implementation().getPower();
	}

	@Override
	public Audio getAudio() {
		return implementation().getAudio();
	}

	@Override
	public TextLCD getTextLCD() {
		return implementation().getTextLCD();
	}

	@Override
	public TextLCD getTextLCD(Font f) {
		return implementation().getTextLCD(f);
	}

	@Override
	public GraphicsLCD getGraphicsLCD() {
		return implementation().getGraphicsLCD();
	}

	@Override
	public boolean isLocal() {
		return implementation().isLocal();
	}

	@Override
	public String getType() {
		return implementation().getType();
	}

	@Override
	public String getName() {
		return implementation().getName();
	}

	@Override
	public LocalBTDevice getBluetoothDevice() {
		return implementation().getBluetoothDevice();
	}

	@Override
	public LocalWifiDevice getWifiDevice() {
		return implementation().getWifiDevice();
	}

	@Override
	public void setDefault() {
		implementation().setDefault();
	}

	@Override
	public Keys getKeys() {
		return implementation().getKeys();
	}

	@Override
	public Key getKey(String name) {
		return implementation().getKey(name);
	}

	@Override
	public LED getLED() {
		return implementation().getLED();
	}

	@Override
	public String getHost() {
		return implementation().getHost();
	}

	@Override
	public RMISampleProvider createSampleProvider(String portName,
			String sensorName, String modeName) {
		return implementation().createSampleProvider(portName, sensorName,
				modeName);
	}

	@Override
	public RMIRegulatedMotor createRegulatedMotor(String portName,
			char motorType) {
		return implementation().createRegulatedMotor(portName, motorType);
	}

	@Override
	public RMIWifi getWifi() {
		return implementation().getWifi();
	}

	@Override
	public RMIBluetooth getBluetooth() {
		return implementation().getBluetooth();
	}

}
