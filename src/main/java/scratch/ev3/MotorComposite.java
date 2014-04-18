package scratch.ev3;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MotorComposite {

	@Autowired
	private RemoteEV3 ev3;

	private HashMap<String, RMIRegulatedMotor> motorMap = new HashMap<>();

	private ConcurrentHashMap<String, Boolean> runningCommandIds = new ConcurrentHashMap<>();

	private static final Logger L = LoggerFactory
			.getLogger(MotorComposite.class);

	public MotorComposite() {
	}

	@PostConstruct
	public void postConstruct() {
	}

	// TODO find out why this is not working
	@PreDestroy
	public void closeAll() {
		for (String port : motorMap.keySet()) {
			try {
				L.info("closing port {}", port);
				motorMap.get(port).close();
			} catch (RemoteException e) {
				L.error("error closing port {}", port, e);
			}
		}
	}

	public void createMotor(String port, String type, String commandId)
			throws RemoteException {

		if (motorMap.get(port) != null) {
			L.info("closing motor port {}", port);
			motorMap.get(port).close();
		}

		L.info("adding commandId {} to running commands", commandId);
		runningCommandIds.put(commandId, true);

		char t = type.charAt(0);
		RMIRegulatedMotor motor = ev3.createRegulatedMotor(port, t);
		motorMap.put(port, motor);

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
	public void createMotor(String port, String type) throws RemoteException {
		if (motorMap.get(port) != null) {
			L.info("closing motor port {}", port);
			motorMap.get(port).close();
		}

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
		return motorMap.get(port);
	}

	public Set<String> getPorts() {
		return motorMap.keySet();
	}

}
