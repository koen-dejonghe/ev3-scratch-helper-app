package scratch.ev3;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ConfigController {

	private static final Logger L = LoggerFactory
			.getLogger(ConfigController.class);

	@Autowired
	private MotorComposite motors;

	@Autowired
	private SensorComposite sensors;

	@Value(value = "${server.port}")
	private String serverPort;

	@RequestMapping("/closeAll")
	public String reset(Model model) {
		L.info("closing ports");
		motors.closeAll();
		sensors.closeAll();
		return "ignored";
	}

	/**
	 * Scratch is sometimes sending reset_all requests at random. For now,
	 * ignore them, and implement this as an explicit command
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/reset_all")
	public String resetAll(Model model) {
		L.info("reset_all received");
		return "ignored";
	}

	@RequestMapping("/crossdomain.xml")
	public String flashXDomain(Model model) {
		if (L.isDebugEnabled())
			L.debug("setting serverPort={} in xdomain request", serverPort);
		model.addAttribute("serverPort", serverPort);
		return "crossdomain";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String catchAll(HttpServletRequest req) {
		L.error("received unknown request: {}", req.getRequestURI());
		return null;
	}

}
