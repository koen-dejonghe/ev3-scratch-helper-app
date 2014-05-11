package scratch.ev3;

import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MotorController {

	private static final Logger L = LoggerFactory
			.getLogger(MotorController.class);

	@Autowired
	private MotorComposite motors;

	@RequestMapping("/connectMotor/{commandId}/{type}/{port}")
	public String connectMotor(@PathVariable("commandId") String commandId,
			@PathVariable("type") String type,
			@PathVariable("port") String port, Model model) {
		
		L.info("connecting a {} motor on port {}: start", type, port);
		motors.createMotor(port, type, commandId);
		L.info("connecting a {} motor on port {}: done", type, port);
		return "ignored";
	
	}

	/**
	 * Normally Scratch should add a command id to the request, but for some
	 * reason when wait-command blocks are executed, Scratch just hangs. See
	 * http://scratch.mit.edu/discuss/topic/35231/?page=1#post-302258
	 * 
	 * @param type
	 * @param port
	 * @param model
	 * @return
	 * @throws RemoteException
	 */
	@RequestMapping("/connectMotor/{type}/{port}")
	public String connectMotorNoWait(@PathVariable("type") String type,
			@PathVariable("port") String port, Model model) {
	
		L.info("connecting a {} motor on port {}: start", type, port);
		motors.createMotor(port, type);
		L.info("connecting a {} motor on port {}: done", type, port);
		return "ignored";
	
	}

	@RequestMapping("/speed/{port}/{speed}")
	public String setSpeed(@PathVariable("port") String port,
			@PathVariable("speed") int speed, Model model) {
		motors.setSpeed(port, speed);
		return "ignored";
	}

	@RequestMapping("/run/{port}/{direction}")
	public String forward(@PathVariable("port") String port,
			@PathVariable("direction") String direction, Model model) {
		if (L.isDebugEnabled())
			L.debug("motor {}: moving {}", port, direction);
		motors.move(port, direction);
		return "ignored";
	}

	@RequestMapping("/stop/{port}")
	public String stopImmediate(@PathVariable("port") String port) {
		motors.stop(port, false);
		return "ignored";
	}

	@RequestMapping("/stopImmediate/{port}")
	public String stop(@PathVariable("port") String port, Model model)
			throws RemoteException {
		motors.stop(port, true);
		return "ignored";
	}

	@RequestMapping("/resetTachoCount/{port}")
	public String resetTachoCount(@PathVariable("port") String port, Model model) {
		motors.resetTachoCount(port);
		return "ignored";
	}

	@RequestMapping("/rotate/{port}/{angle}")
	public String rotate(@PathVariable("port") String port,
			@PathVariable("angle") int angle, Model model) {
		motors.rotate(port, angle);
		return "ignored";
	}

	@RequestMapping("/rotateTo/{port}/{limitAngle}")
	public String rotateTo(@PathVariable("port") String port,
			@PathVariable("limitAngle") int limitAngle, Model model) {
		motors.rotate(port, limitAngle);
		return "ignored";
	}

}
