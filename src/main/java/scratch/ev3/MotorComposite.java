package scratch.ev3;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PreDestroy;

import lejos.remote.ev3.RMIRegulatedMotor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MotorComposite {

	@Autowired
	private RemoteEV3Inf ev3;

	private ConcurrentHashMap<String, RMIRegulatedMotor> motorMap = new ConcurrentHashMap<>();

	private ConcurrentHashMap<String, String> directionMap = new ConcurrentHashMap<>();

	private ConcurrentHashMap<String, Boolean> runningCommandIds = new ConcurrentHashMap<>();

	private static final Logger L = LoggerFactory
			.getLogger(MotorComposite.class);

	@PreDestroy
	// TODO @PreDestroy annotation is not working
	public void closeAll() {
		for (String port : motorMap.keySet()) {
			L.info("closing port {}", port);
			close(port);
		}
	}

	public void close(String port) {
		RMIRegulatedMotor motor = motorMap.get(port);
		if (motor == null) {
			L.error("unable to close port {}: port is not initialized", port);
			return;
		}

		try {
			motor.close();
		} catch (RemoteException e) {
			L.error("unable to close port {}: {}", port, e.getMessage());
		} finally {
			motorMap.remove(port);
			directionMap.remove(port);
		}

	}

	public void createMotor(String port, String type, String commandId) {
		L.info("adding commandId {} to running commands", commandId);
		runningCommandIds.put(commandId, true);

		createMotor(port, type);

		L.info("removing commandId {} from running commands", commandId);
		runningCommandIds.remove(commandId);
	}

	/**
	 * Normally Scratch should add a command id to the request, but for some
	 * reason when wait-command blocks are executed, Scratch just hangs. See
	 * http://scratch.mit.edu/discuss/topic/35231/?page=1#post-302258
	 * 
	 * @param port
	 * @param type
	 * @throws RemoteException
	 */
	public void createMotor(String port, String type) {

		close(port);

		char t = type.charAt(0);
		RMIRegulatedMotor motor = ev3.createRegulatedMotor(port, t);
		motorMap.put(port, motor);
	}

	public Set<String> getRunningCommands() {
		return runningCommandIds.keySet();
	}

	public boolean isCommandRunning(String id) {
		Boolean running = runningCommandIds.get(id);
		if (running == null) {
			return false;
		}
		return running;
	}

	public RMIRegulatedMotor getMotor(String port) {
		RMIRegulatedMotor motor = motorMap.get(port);
		return motor;
	}

	public Set<String> getPorts() {
		return motorMap.keySet();
	}

	public int getSpeed(String port) {
		RMIRegulatedMotor motor = motorMap.get(port);
		if (motor == null) {
			L.error("unable to retrieve speed from port {}: port is not initialized",
					port);
			return 0;
		}

		try {
			int speed = motor.getSpeed();
			return speed;
		} catch (RemoteException e) {
			L.error("unable to retrieve speed from port {}: {}", port,
					e.getMessage());
			return 0;
		}
	}

	public float getMaxSpeed(String port) {
		RMIRegulatedMotor motor = motorMap.get(port);
		if (motor == null) {
			L.error("unable to retrieve maximum speed from port {}: port is not initialized",
					port);
			return 0;
		}

		try {
			float speed = motor.getMaxSpeed();
			return speed;
		} catch (RemoteException e) {
			L.error("unable to retrieve speed from port {}: {}", port,
					e.getMessage());
			return 0;
		}
	}

	public int getTachoCount(String port) {
		RMIRegulatedMotor motor = motorMap.get(port);
		if (motor == null) {
			L.error("unable to retrieve tacho count from port {}: port is not initialized",
					port);
			return 0;
		}

		try {
			int tachoCount = motor.getTachoCount();
			return tachoCount;
		} catch (RemoteException e) {
			L.error("unable to retrieve tacho count from port {}: {}", port,
					e.getMessage());
			return 0;
		}
	}

	public int getLimitAngle(String port) {
		RMIRegulatedMotor motor = motorMap.get(port);
		if (motor == null) {
			L.error("unable to retrieve limit angle from port {}: port is not initialized",
					port);
			return 0;
		}

		try {
			int limitAngle = motor.getLimitAngle();
			return limitAngle;
		} catch (RemoteException e) {
			L.error("unable to retrieve limit angle from port {}: {}", port,
					e.getMessage());
			return 0;
		}
	}

	public void setSpeed(String port, int speed) {
		RMIRegulatedMotor motor = motorMap.get(port);
		if (motor == null) {
			L.error("unable to set speed on port {}: port is not initialized",
					port);
			return;
		}

		try {
			motor.setSpeed(speed);
		} catch (RemoteException e) {
			L.error("unable to set speed on port {}: {}", port, e.getMessage());
		}
	}

	public void move(String port, String direction) {
		RMIRegulatedMotor motor = motorMap.get(port);
		if (motor == null) {
			L.error("unable to move motor on port {}: port is not initialized",
					port);
			return;
		}

		directionMap.put(port, direction);

		try {
			if ("Forward".equals(direction)) {
				motor.forward();
			} else if ("Backward".equals(direction)) {
				motor.backward();
			} else {
				L.error("Unknown direction {}", direction);
			}
		} catch (RemoteException e) {
			L.error("unable to move motor on port {}: {}", port, e.getMessage());
		}
	}

	public void stop(String port, boolean immediate) {
		RMIRegulatedMotor motor = motorMap.get(port);
		if (motor == null) {
			L.error("unable to stop motor on port {}: port is not initialized",
					port);
			return;
		}

		try {
			motor.stop(immediate);
		} catch (RemoteException e) {
			L.error("unable to stop motor on port {}: {}", port, e.getMessage());
		}
	}

	public void resetTachoCount(String port) {
		RMIRegulatedMotor motor = motorMap.get(port);
		if (motor == null) {
			L.error("unable to reset tacho count on port {}: port is not initialized",
					port);
			return;
		}

		try {
			motor.resetTachoCount();
		} catch (RemoteException e) {
			L.error("unable to reset tacho count on port {}: {}", port,
					e.getMessage());
		}
	}

	public void rotate(String port, int angle) {
		RMIRegulatedMotor motor = motorMap.get(port);
		if (motor == null) {
			L.error("unable to rotate motor on port {}: port is not initialized",
					port);
			return;
		}

		try {
			motor.rotate(angle);
		} catch (RemoteException e) {
			L.error("unable to rotate motor on port {}: {}", port,
					e.getMessage());
		}
	}

	public void rotateTo(String port, int limitAngle) {
		RMIRegulatedMotor motor = motorMap.get(port);
		if (motor == null) {
			L.error("unable to rotateTo motor on port {}: port is not initialized",
					port);
			return;
		}

		try {
			motor.rotateTo(limitAngle);
		} catch (RemoteException e) {
			L.error("unable to rotateTo motor on port {}: {}", port,
					e.getMessage());
		}
	}

	public String getDirection(String port) {
		return directionMap.get(port);
	}

	public void play(ArrayList<HashMap<String, Object>> history) {

		Integer oldSpeedA = 0;
		Integer oldSpeedB = 0;
		String oldDirectionA = "";
		String oldDirectionB = "";
		
		long startPlaying = System.currentTimeMillis();

		for (HashMap<String, Object> state : history) {
			String directionMotorA = (String) state.get("directionMotorA");
			String directionMotorB = (String) state.get("directionMotorB");
			Integer speedMotorA = (Integer) state.get("speedMotorA");
			Integer speedMotorB = (Integer) state.get("speedMotorB");

			L.debug("speedMotorA={}", speedMotorA);
			L.debug("speedMotorB={}", speedMotorB);
			L.debug("directionMotorA={}", directionMotorA);
			L.debug("directionMotorB={}", directionMotorB);

			if (speedMotorA != oldSpeedA) {
				setSpeed("A", speedMotorA);
				oldSpeedA = speedMotorA;
			}

			if (speedMotorB != oldSpeedB) {
				setSpeed("B", speedMotorB);
				oldSpeedB = speedMotorB;
			}

			if (directionMotorA != null
					&& !directionMotorA.equals(oldDirectionA)) {
				move("A", directionMotorA);
				oldDirectionA = directionMotorA;
			}

			if (directionMotorB != null
					&& !directionMotorB.equals(oldDirectionB)) {
				move("B", directionMotorB);
				oldDirectionB = directionMotorB;
			}

			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				L.error(e.getMessage());
			}
		}

		stop("A", true);
		stop("B", true);

		long endPlaying = System.currentTimeMillis();
		
		L.debug("playing recording took {} ms", endPlaying - startPlaying);

	}

}
