package scratch.ev3;

public class Translator {
	
	public static String translateMotorType(String type){
		if ("Grote".equals(type)){
			return "Large";
		}
		return type;		
	}

	public static String translateDirection(String direction) {
		if ("Vooruit".equals(direction)){
			return "Forward";
		}
		if ("Achteruit".equals(direction)){
			return "Backward";
		}
		return direction;
	}

	public static String translateSensorType(String type) {
		if ("Kleuren".equals(type)){
			return "Color";
		}
		if ("Afstand".equals(type)){
			return "Distance";
		}
		if ("Toets".equals(type)){
			return "Touch";
		}
		return type;
	}

}
