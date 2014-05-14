package scratch.ev3;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private ArrayList<HashMap<String, Object>> history = new ArrayList<>();

	private int throttlingCounter = 0;

	private boolean recording = false;

	private static final Logger L = LoggerFactory
			.getLogger(PollController.class);

	@RequestMapping("/poll")
	public String poll(Model model) throws RemoteException {

		// wait commands are currently either buggy or not supported by scratch
		// model.addAttribute("_busy", makeBusyLine());

		pollSensors(model);

		pollMotors(model);

		if (++throttlingCounter >= pollThrottle) {
			modelMap.clear();
			throttlingCounter = 0;
		}

		if (L.isTraceEnabled()) {
			L.trace("throttlingCounter={}", throttlingCounter);
		}

		if (recording) {
			record();
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
			String direction = "directionMotor" + port;

			if (throttlingCounter == 0) {
				modelMap.put(speedAttr, motors.getSpeed(port));
				modelMap.put(maxSpeedAttr, motors.getMaxSpeed(port));
				modelMap.put(tachoCount, motors.getTachoCount(port));
				modelMap.put(limitAngle, motors.getLimitAngle(port));
				modelMap.put(direction, motors.getDirection(port));
			}

			model.addAttribute(speedAttr, modelMap.get(speedAttr));
			model.addAttribute(maxSpeedAttr, modelMap.get(maxSpeedAttr));
			model.addAttribute(tachoCount, modelMap.get(tachoCount));
			model.addAttribute(limitAngle, modelMap.get(limitAngle));
			model.addAttribute(direction, modelMap.get(direction));
		}
	}

	@RequestMapping("/record/start")
	public String startRecording(Model model) {
		recording = true;
		history.clear();
		return "ignored";
	}

	@RequestMapping("/record/stop")
	public String stopRecording(Model model) {
		recording = false;
		if (L.isDebugEnabled()) {
			L.debug("history dump: {}", history);
		}
		return "ignored";
	}

	@RequestMapping("/record/play")
	public String playRecording(Model model) {
		motors.play(history);
		return "ignored";
	}

	private void record() {
		if (modelMap.size() > 0)
			history.add(modelMap);
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

	public class State {

	}

}
