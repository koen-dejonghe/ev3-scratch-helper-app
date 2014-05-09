package scratch.ev3.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import scratch.ev3.MotorComposite;
import scratch.ev3.RemoteEV3Inf;
import scratch.ev3.SensorComposite;

@Profile("mockProfile")
@Configuration
public class MockConfig {

	private static final Logger L = LoggerFactory.getLogger(MockConfig.class);

	@Bean
	public RemoteEV3Inf ev3() {
		L.info("providing mock ev3");
			return new MockEV3();
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
