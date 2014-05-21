package scratch.ev3;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("mockProfile")
@WebAppConfiguration
public class PollControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	MotorComposite motors;

	@Autowired
	SensorComposite sensors;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void loadRootAndReceivePollResults() throws Exception {

		motors.createMotor("A", "L");
		motors.setSpeed("A", 400);

		sensors.createSensor("S1", "Distance");

		ResultActions perform = mockMvc.perform(get("/poll"));

		perform.andExpect(status().isOk());
		perform.andExpect(model().attribute("speedMotorA", 400));
		perform.andExpect(model().attribute("sensorS1",
				not(isEmptyOrNullString())));

	}
}
