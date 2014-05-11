package scratch.ev3;

public class State {

	private MotorState stateOfMotorA;
	private MotorState stateOfMotorB;
	private MotorState stateOfMotorC;
	private MotorState stateOfMotorD;

	private SensorState stateOfSensor1;
	private SensorState stateOfSensor2;
	private SensorState stateOfSensor3;
	private SensorState stateOfSensor4;

	public void setStateOfMotor(String motor, MotorState state) {
		switch (motor) {
		case "A":
			stateOfMotorA = state;
			break;
		case "B":
			stateOfMotorB = state;
			break;
		case "C":
			stateOfMotorC = state;
			break;
		case "D":
			stateOfMotorD = state;
			break;
		}
	}

	public MotorState getStateOfMotor(String motor) {
		switch (motor) {
		case "A":
			return stateOfMotorA;
		case "B":
			return stateOfMotorB;
		case "C":
			return stateOfMotorC;
		case "D":
			return stateOfMotorD;
		}
		return null;
	}

	public void setStateOfSensor(String sensor, SensorState state) {
		switch (sensor) {
		case "S1":
			stateOfSensor1 = state;
			break;
		case "S2":
			stateOfSensor2 = state;
			break;
		case "S3":
			stateOfSensor3 = state;
			break;
		case "S4":
			stateOfSensor4 = state;
			break;
		}
	}

	public SensorState getStateOfSensor(String sensor) {
		switch (sensor) {
		case "S1":
			return stateOfSensor1;
		case "S2":
			return stateOfSensor2;
		case "S3":
			return stateOfSensor3;
		case "S4":
			return stateOfSensor4;
		}
		return null;
	}

}
