package scratch.ev3;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MotorController {

	private static final Logger L = LoggerFactory
			.getLogger(MotorController.class);

	@Autowired
	private MotorComposite motors;

	@RequestMapping("/connectMotor/{commandId}/{type}/{port}")
	public String connectMotor(@PathVariable("commandId") String commandId,
			@PathVariable("type") String type,
			@PathVariable("port") String port, Model model)
			throws RemoteException {
		L.info("connecting a {} motor on port {}", type, port);
		motors.createMotor(port, type, commandId);
		return "motor";
	}

	/**
	 * Normally Scratch should add a command id to the request, but for some
	 * reason when wait-command blocks are executed, Scratch just hangs.
	 * See http://scratch.mit.edu/discuss/topic/35231/?page=1#post-302258
	 * @param type
	 * @param port
	 * @param model
	 * @return
	 * @throws RemoteException
	 */
	@RequestMapping("/connectMotor/{type}/{port}")
	public String connectMotorNoWait(@PathVariable("type") String type,
			@PathVariable("port") String port, Model model)
			throws RemoteException {
		L.info("connecting a {} motor on port {}", type, port);
		motors.createMotor(port, type);
		return "motor";
	}

	@RequestMapping("/speed/{port}/{speed}")
	public String setSpeed(@PathVariable("port") String port,
			@PathVariable("speed") int speed, Model model)
			throws RemoteException {
		motors.getMotor(port).setSpeed(speed);
		return "motor";
	}

	@RequestMapping("/forward/{port}")
	public String forward(@PathVariable("port") String port, Model model)
			throws RemoteException {

		if (motors.getMotor(port) == null) {

		}

		motors.getMotor(port).forward();
		return "motor";
	}

	@RequestMapping("/backward/{port}")
	public String backward(@PathVariable("port") String port, Model model)
			throws RemoteException {
		motors.getMotor(port).backward();
		return "motor";
	}

	@RequestMapping("/stop/{port}")
	public String stopImmediate(@PathVariable("port") String port)
			throws RemoteException {
		motors.getMotor(port).stop(false);
		return "motor";
	}

	@RequestMapping("/stopImmediate/{port}")
	public String stop(@PathVariable("port") String port, Model model)
			throws RemoteException {
		motors.getMotor(port).stop(true);
		return "motor";
	}


}
