package parkinglot.factory;

import parkinglot.entity.Bike;
import parkinglot.entity.Car;
import parkinglot.entity.Truck;
import parkinglot.entity.Vehicle;

public class VehicleFactory {
	private static String TRUCK = "TRUCK";
	private static String BIKE = "BIKE";
	private static String CAR = "CAR";
	public static VehicleFactory getFactory() {
		return new VehicleFactory();
	}
	
	public Vehicle getInstance(String vehicleType) {
		if(vehicleType.equals(TRUCK)) {
			return new Truck();
		}
		else if(vehicleType.equals(BIKE)) {
			return new Bike();
		}
		else {
			return new Car();
		}
	}
}
