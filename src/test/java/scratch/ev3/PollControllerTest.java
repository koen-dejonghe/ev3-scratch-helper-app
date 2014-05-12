package scratch.ev3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriver;
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
	RemoteEV3Inf ev3;

	private WebDriver driver;

	@Before
	public void setup() {
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		driver = new MockMvcHtmlUnitDriver(mockMvc, true);
	}

	@After
	public void destroy() {
		if (driver != null) {
			driver.close();
		}
	}

	@Test
	public void test() {
		driver.get("http://localhost:4321/poll");
		String pageSource = driver.getPageSource();
		
	}
}
