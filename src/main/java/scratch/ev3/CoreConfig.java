package scratch.ev3;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {
	
	private static final Logger L = LoggerFactory.getLogger(CoreConfig.class);

	@Value("${ev3.ip.address}")
    private String ev3IpAddress;
	
	@Value("${ev3.sensor.distance.port}")
    private String ev3SensorDistancePort;
		
	@Value("${ev3.sensor.touch.port}")
    private String ev3SensorTouchPort;
		
	@Bean
	public RemoteEV3 ev3(){
		RemoteEV3 ev3 = null;
		try {
			ev3 = new RemoteEV3(ev3IpAddress);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			L.error("Unable to reach EV3", e);
		}
		return ev3;
	}

	/*
	 * TODO: the sample providers are now created at start up.
	 * That means if the sensor is not connected at start up, 
	 * the program will never recover, even when the sensor is
	 * connected later on.
	 * This should be fixed.
	 *
	 */
	
	@Bean
	public RMISampleProvider distanceSampleProvider(RemoteEV3 ev3) {
		RMISampleProvider sp = ev3.createSampleProvider(ev3SensorDistancePort,
				"lejos.hardware.sensor.EV3IRSensor", "Distance");
		return sp;
	}
	
	@Bean
	public RMISampleProvider touchSampleProvider(RemoteEV3 ev3) {
		RMISampleProvider sp = ev3.createSampleProvider(ev3SensorTouchPort,
				"lejos.hardware.sensor.EV3TouchSensor", "Touch");
		return sp;
	}
	
	
}
