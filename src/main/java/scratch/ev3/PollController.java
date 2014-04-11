package scratch.ev3;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMISampleProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PollController {
	
	@Autowired
	RMISampleProvider distanceSampleProvider;
	
	@RequestMapping("/greeting")
	public String greeting(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@RequestMapping("/poll")
	public String poll(Model model) {

		float distance = 0;
		
		try {
			float[] fetchSample = distanceSampleProvider.fetchSample();
			distance = fetchSample[0];
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("distance", distance);
		return "poll";
	}

}
