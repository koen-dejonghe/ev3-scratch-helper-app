package scratch.ev3;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	/**
	 * Scratch is sometimes sending reset_all requests at random.
	 * For now, ignore them, and implement this as an explicit command
	 * @param model
	 * @return
	 */
	//@RequestMapping("/reset_all")
	@RequestMapping("/reset")
	public String resetAll(Model model) {
		L.info("closing ports");
		motors.closeAll();
		sensors.closeAll();
		return "ignored";
	}

	@RequestMapping("/crossdomain.xml")
	public String flashXDomain(Model model) {
		L.info("got cross domain request");
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String catchAll(HttpServletRequest req) {
		L.error(req.getRequestURI());
		return null;
	}

}
