package scratch.ev3.state;

import java.util.HashMap;

public class BrickState {
	
	private HashMap<String, MotorState> motorStates = new HashMap<>();

	private HashMap<String, SensorState> sensorStates = new HashMap<>();

	public void setStateOfMotor(String motor, MotorState state) {
		motorStates.put(motor, state);
	}

	public MotorState getStateOfMotor(String motor) {
		return motorStates.get(motor);
	}

	public void setStateOfSensor(String sensor, SensorState state) {
		sensorStates.put(sensor, state);
	}

	public SensorState getStateOfSensor(String sensor) {
		return sensorStates.get(sensor);
	}

}
