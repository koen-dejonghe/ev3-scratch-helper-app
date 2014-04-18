package scratch.ev3;

import java.io.IOException;
import java.rmi.NotBoundException;

import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;
import lejos.remote.ev3.RemoteEV3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

	private static final Logger L = LoggerFactory.getLogger(CoreConfig.class);

	@Bean
	public RemoteEV3 ev3() throws IOException, NotBoundException {
		
//		RemoteEV3 ev3 = new RemoteEV3("192.168.1.144");
//		return ev3;

		L.info("start searching for ev3");
		BrickInfo[] bricks = BrickFinder.discover();

		if (bricks != null && bricks.length > 0) {
			BrickInfo brick = bricks[0];
			String ipAddress = brick.getIPAddress();
			L.info("connecting ev3 at address {}", ipAddress);

			RemoteEV3 ev3 = new RemoteEV3(ipAddress);
			ev3.setDefault();
			
			return ev3;
		}

		// TODO maybe use a better type of exception
		throw new UnsupportedOperationException("Unable to find EV3");
	}

	@Bean
	public SensorComposite sensors() {
		return new SensorComposite();
	}

	@Bean
	public MotorComposite motors() {
		return new MotorComposite();
	}

}
