package scratch.ev3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

	@SuppressWarnings("unused")
	private static final Logger L = LoggerFactory.getLogger(CoreConfig.class);

	@Value("${ev3.ip.address:}")
	private String ev3IpAddress;
	
	@Bean
	public String ev3IpAddress(){
		return ev3IpAddress;
	}
	
	@Bean
	public RemoteEV3Inf ev3() {		
		return new RemoteEV3Proxy();
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
