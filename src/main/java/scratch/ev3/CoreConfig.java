package scratch.ev3;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

	@Bean
	public RemoteEV3 ev3(){
		RemoteEV3 ev3 = null;
		try {
			ev3 = new RemoteEV3("192.168.1.144");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ev3;
	}

	@Bean
	public RMISampleProvider distanceSampleProvider(RemoteEV3 ev3) {

		RMISampleProvider sp = ev3.createSampleProvider("S2",
				"lejos.hardware.sensor.EV3IRSensor", "Distance");
		return sp;

	}
}
