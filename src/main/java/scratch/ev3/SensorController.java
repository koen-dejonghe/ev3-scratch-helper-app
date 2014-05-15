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
public class SensorController {

	private static final Logger L = LoggerFactory
			.getLogger(SensorController.class);

	@Autowired
	private SensorComposite sensors;

	@RequestMapping("/connectSensor/{commandId}/{type}/{port}")
	public String connectSensor(@PathVariable("commandId") String commandId,
			@PathVariable("type") String type,
			@PathVariable("port") String port, Model model)
			throws RemoteException {
		
		type = Translator.translateSensorType(type);
		
		L.info("connecting a {} sensor on port {}: start", type, port);
		sensors.createSensor(port, type, commandId);
		L.info("connecting a {} sensor on port {}: done", type, port);
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
	@RequestMapping("/connectSensor/{type}/{port}")
	public String connectSensorNoWait(@PathVariable("type") String type,
			@PathVariable("port") String port, Model model)
			throws RemoteException {
		
		type = Translator.translateSensorType(type);

		L.info("connecting a {} sensor on port {}: start", type, port);
		sensors.createSensor(port, type);
		L.info("connecting a {} sensor on port {}: done", type, port);
		return "ignored";
	}


}
