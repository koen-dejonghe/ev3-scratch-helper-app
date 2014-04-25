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

	@Value("${ev3.ip.address:}")
	private String ev3IpAddress;

	@Bean
	public RemoteEV3 ev3() throws IOException, NotBoundException {

		if (ev3IpAddress != null && ev3IpAddress.length() > 0) {
			L.info("using ev3 ip address from application.properties: {}", ev3IpAddress);
			RemoteEV3 ev3 = new RemoteEV3(ev3IpAddress);
			return ev3;
		}

		L.info("start searching for ev3");
		BrickInfo[] bricks = BrickFinder.discover();

		if (bricks != null && bricks.length > 0) {
			BrickInfo brick = bricks[0];
			
			// TODO check if the brick is an ev3

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
