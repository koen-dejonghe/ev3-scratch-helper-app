package scratch.ev3;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

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
	public RemoteEV3 ev3() {

		try {
			if (ev3IpAddress != null && ev3IpAddress.length() > 0) {
				L.info("using ev3 ip address from application.properties: {}",
						ev3IpAddress);
				RemoteEV3 ev3 = new RemoteEV3(ev3IpAddress);
				return ev3;
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

					if (! "EV3".equals(type)){
						L.info("brick at {} is not an ev3");
						continue;
					}
					
					try {
						RemoteEV3 ev3 = new RemoteEV3(ipAddress);
						ev3.setDefault();
						return ev3;
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

	@Bean
	public SensorComposite sensors() {
		return new SensorComposite();
	}

	@Bean
	public MotorComposite motors() {
		return new MotorComposite();
	}

}
