package scratch.ev3;

import java.rmi.RemoteException;

import lejos.hardware.DeviceException;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PollController {

	private static final Logger L = LoggerFactory
			.getLogger(PollController.class);

	@Autowired
	SensorComposite sensors;

	@Autowired
	MotorComposite motors;

	@RequestMapping("/poll")
	public String poll(Model model) throws RemoteException {

		model.addAttribute("_busy", makeBusyLine());

		// sensors
		for (String port : sensors.getPorts()) {
			RMISampleProvider sensor = sensors.getSensor(port);
			float sample = getSample(sensor);
			model.addAttribute("sensor" + port, sample);
		}

		// motors
		for (String port : motors.getPorts()) {
			RMIRegulatedMotor motor = motors.getMotor(port);
			model.addAttribute("speedMotor" + port, motor.getSpeed());
			model.addAttribute("maxSpeedMotor" + port, motor.getMaxSpeed());

			/*
			 * model.addAttribute("tachoCountMotor" + port,
			 * motor.getTachoCount()); model.addAttribute("limitAngleMotor" +
			 * port, motor.getLimitAngle());
			 */
		}

		return "poll";
	}

	//TODO move this to SensorComposite
	private float getSample(RMISampleProvider provider) {
		try {
			float[] fetchSample = provider.fetchSample();
			return fetchSample[0];
		} catch (RemoteException | NullPointerException | DeviceException e) {
			L.error(e.getMessage());
			return 0;
		}
	}

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
