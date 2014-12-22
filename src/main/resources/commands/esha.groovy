
import org.crsh.cli.Command
import org.crsh.cli.Usage
import org.crsh.command.InvocationContext
import org.springframework.beans.factory.support.DefaultListableBeanFactory

import scratch.ev3.*

@Usage("EV3 Scratch Helper App")
class esha {

	@Usage("shutdown")
	@Command
	def shutdown(InvocationContext context) {
		DefaultListableBeanFactory bf = context.attributes."spring.beanfactory"

		SensorComposite sensors = bf.getBean("sensors")
		sensors.closeAll()

		MotorComposite motors = bf.getBean("motors")
		motors.closeAll()

		System.exit(0)
	}
}


