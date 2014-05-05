package scratch.ev3;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import scratch.ev3.mock.EV3Mock;

@Configuration
public class TestConfig {

	private static final Logger L = LoggerFactory.getLogger(TestConfig.class);

	@Bean
	public RemoteEV3Inf ev3() {
			return new EV3Mock();
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
