package scratch.ev3;

import java.rmi.RemoteException;

import lejos.hardware.DeviceException;
import lejos.remote.ev3.RMISampleProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PollController {
	
	private static final Logger L = LoggerFactory.getLogger(PollController.class);
	
	@Autowired
	RMISampleProvider distanceSampleProvider;
	
	@Autowired
	RMISampleProvider touchSampleProvider;

	@RequestMapping("/poll")
	public String poll(Model model) {

		float distance = getSample(distanceSampleProvider);
		float touch = getSample(touchSampleProvider);

		model.addAttribute("distance", distance);
		model.addAttribute("touch", touch);

		return "poll";
	}
	
	private float getSample(RMISampleProvider provider){
		try {
			float[] fetchSample = provider.fetchSample();
			return fetchSample[0];
		} catch (RemoteException | NullPointerException | DeviceException e) {
			L.error(e.getMessage());
			return 0;
		}		
	}
	
	private boolean getBooleanSample(RMISampleProvider provider){
		float s = getSample(provider);
		return s == 1.0;
	}

}
