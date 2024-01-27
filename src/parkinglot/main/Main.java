package parkinglot.main;

import java.util.Scanner;

import parkinglot.service.ParkingLotService;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String command = scanner.next();
		ParkingLotService service = new ParkingLotService();
		while(!command.equals("exit")) {
			switch(command) {
				case "create_parking_lot": 
					service.createParkingLot(scanner.next(), scanner.nextInt(), scanner.nextInt());
					break;
				case "display":
					service.display(scanner.next(), scanner.next());
					break;
				case "park_vehicle": 
					service.parkVehicle(scanner.next(), scanner.next(), scanner.next());
					break;
				case "unpark_vehicle":
					service.unparkVehicle(scanner.next());
					break;
				default:
					System.out.println("Invalid command");
					break;
			}
			command = scanner.next();
		}
	}
}
