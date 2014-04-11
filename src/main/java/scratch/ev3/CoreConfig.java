package scratch.ev3;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

	@Value("${ev3.ip.address}")
    private String ev3IpAddress;
	
	@Value("${ev3.sensor.distance.port}")
    private String ev3SensorDistancePort;
		
	@Bean
	public RemoteEV3 ev3(){
		RemoteEV3 ev3 = null;
		try {
			ev3 = new RemoteEV3(ev3IpAddress);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ev3;
	}

	@Bean
	public RMISampleProvider distanceSampleProvider(RemoteEV3 ev3) {

		RMISampleProvider sp = ev3.createSampleProvider(ev3SensorDistancePort,
				"lejos.hardware.sensor.EV3IRSensor", "Distance");
		return sp;

	}
}
