package scratch.ev3;

import java.rmi.RemoteException;
import java.util.HashMap;

import lejos.hardware.DeviceException;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PollController {

	private static final Logger L = LoggerFactory
			.getLogger(PollController.class);

	@Autowired
	private SensorComposite sensors;

	@Autowired
	private MotorComposite motors;

	@Value("#{T(java.lang.Integer).parseInt('${poll.throttle}')}")
	private Integer pollThrottle;

	private HashMap<String, Object> modelMap = new HashMap<>();

	private int throttlingCounter = 0;

	@RequestMapping("/poll")
	public String poll(Model model) throws RemoteException {

		// wait commands are currently either buggy or not supported by scratch
		// model.addAttribute("_busy", makeBusyLine());

		// sensors
		for (String port : sensors.getPorts()) {
			String attr = "sensor" + port;
			if (throttlingCounter == 0) {
				RMISampleProvider sensor = sensors.getSensor(port);
				float sample = getSample(sensor);
				modelMap.put(attr, sample);
			}
			model.addAttribute(attr, modelMap.get(attr));
		}

		// motors
		for (String port : motors.getPorts()) {
			String speedAttr = "speedMotor" + port;
			String maxSpeedAttr = "maxSpeedMotor" + port;
			if (throttlingCounter == 0) {
				RMIRegulatedMotor motor = motors.getMotor(port);
				modelMap.put(speedAttr, motor.getSpeed());
				modelMap.put(maxSpeedAttr, motor.getMaxSpeed());
			}
			model.addAttribute(speedAttr, modelMap.get(speedAttr));
			model.addAttribute(maxSpeedAttr, modelMap.get(maxSpeedAttr));

			/*
			 * model.addAttribute("tachoCountMotor" + port,
			 * motor.getTachoCount()); model.addAttribute("limitAngleMotor" +
			 * port, motor.getLimitAngle());
			 */
		}

		if (++throttlingCounter >= pollThrottle) {
			throttlingCounter = 0;
		}
		return "poll";
	}

	// TODO move this to SensorComposite
	private float getSample(RMISampleProvider provider) {
		try {
			float[] fetchSample = provider.fetchSample();
			return fetchSample[0];
		} catch (RemoteException | NullPointerException | DeviceException e) {
			L.error(e.getMessage());
			return 0;
		}
	}

	@SuppressWarnings("unused")
	private String makeBusyLine() {
		// busy
		StringBuffer runningCommands = new StringBuffer();
		for (String commandId : motors.getRunningCommands()) {
			runningCommands.append(commandId);
			runningCommands.append(" ");
		}
		return runningCommands.toString();
	}

}
