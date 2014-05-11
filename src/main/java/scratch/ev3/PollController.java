package scratch.ev3;

import java.rmi.RemoteException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PollController {

	@Autowired
	private SensorComposite sensors;

	@Autowired
	private MotorComposite motors;

	@Value("#{T(java.lang.Integer).parseInt('${poll.throttle:10}')}")
	private Integer pollThrottle;

	private HashMap<String, Object> modelMap = new HashMap<>();

	private int throttlingCounter = 0;

	@RequestMapping("/poll")
	public String poll(Model model) throws RemoteException {

		// wait commands are currently either buggy or not supported by scratch
		// model.addAttribute("_busy", makeBusyLine());

		pollSensors(model);

		pollMotors(model);

		if (++throttlingCounter >= pollThrottle) {
			throttlingCounter = 0;
		}
		return "poll";
	}

	private void pollSensors(Model model) {
		for (String port : sensors.getPorts()) {
			String attr = "sensor" + port;

			if (throttlingCounter == 0) {
				float sample = sensors.getSample(port);
				modelMap.put(attr, sample);
			}
			model.addAttribute(attr, modelMap.get(attr));
		}
	}

	private void pollMotors(Model model) {
		for (String port : motors.getPorts()) {
			String speedAttr = "speedMotor" + port;
			String maxSpeedAttr = "maxSpeedMotor" + port;
			String tachoCount = "tachoCountMotor" + port;
			String limitAngle = "limitAngleMotor" + port;

			if (throttlingCounter == 0) {
				modelMap.put(speedAttr, motors.getSpeed(port));
				modelMap.put(maxSpeedAttr, motors.getMaxSpeed(port));
				modelMap.put(tachoCount, motors.getTachoCount(port));
				modelMap.put(limitAngle, motors.getLimitAngle(port));
			}

			model.addAttribute(speedAttr, modelMap.get(speedAttr));
			model.addAttribute(maxSpeedAttr, modelMap.get(maxSpeedAttr));
			model.addAttribute(tachoCount, modelMap.get(tachoCount));
			model.addAttribute(limitAngle, modelMap.get(limitAngle));
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
