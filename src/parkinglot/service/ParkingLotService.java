package parkinglot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import parkinglot.entity.DisplayType;
import parkinglot.entity.ParkingFloor;
import parkinglot.entity.ParkingLot;
import parkinglot.entity.ParkingSlot;
import parkinglot.entity.Ticket;
import parkinglot.entity.Vehicle;
import parkinglot.factory.VehicleFactory;

public class ParkingLotService {
	private ParkingLot parkingLot;
	Set<String> ticketSet;
	private VehicleFactory vehicleFactory;
	private Map<String, int[]> freeSlotsMap;
	private static String TRUCK = "TRUCK";
	private static String BIKE = "BIKE";
	private static String CAR = "CAR";
	public void createParkingLot(String parkingLotId, int numberOfFloors, int slotsPerFloor) {
		List<ParkingFloor> floors = generateFloors(numberOfFloors, slotsPerFloor);
		parkingLot = new ParkingLot(parkingLotId, floors, numberOfFloors, slotsPerFloor);
		freeSlotsMap = new HashMap<>();
		//{floor, slot}
		freeSlotsMap.put(TRUCK, new int[] {1,1});
		freeSlotsMap.put(BIKE, new int[] {1,2});
		freeSlotsMap.put(CAR, new int[] {1,4});
		ticketSet = new HashSet<>();
		vehicleFactory = VehicleFactory.getFactory();
		System.out.println("Created parking lot with " + numberOfFloors + " floors and " + slotsPerFloor + " slots per floor");
	}
	
	public void parkVehicle(String vehicleType, String registrationNumber, String color) {
		if(isParkingLotFull(vehicleType)) {
			System.out.println("Parking Lot Full");
			return;
		}
		int[] freeSlot = freeSlotsMap.get(vehicleType);
		int floorId = freeSlot[0];
		int slotId = freeSlot[1];
		
		ParkingFloor floor = parkingLot.getFloors().get(floorId-1);
		ParkingSlot slot = floor.getParkingSlots().get(slotId-1);
		slot.setFree(false);
		
		Vehicle vehicle = createNewVehicle(vehicleType, registrationNumber, color);
		
		Ticket ticket = new Ticket(parkingLot, floor, slot, vehicle);
		slot.setTicket(ticket);
		slot.setVehicle(vehicle);
		ticketSet.add(ticket.getTicketId());
		
		incrementToNextFreeSlot(vehicleType, freeSlot);
		
		System.out.println("Parked vehicle. Ticket ID: " + ticket.getTicketId());
	}
	
	public void unparkVehicle(String ticketId) {
		if(!ticketSet.contains(ticketId)) {
			System.out.println("Invalid Ticket");
		}
		else { 
			ticketSet.remove(ticketId);
			int[] slotDetails = getSlotDetails(ticketId);
			int floor = slotDetails[0];
			int slotNum = slotDetails[1];
			ParkingSlot slot = parkingLot.getFloors().get(floor-1).getParkingSlots().get(slotNum-1);
			slot.setFree(true);
			slot.setTicket(null);
			Vehicle vehicle = slot.getVehicle();
			slot.setVehicle(null);
			
			freeSlotsMap.put(vehicle.getClass().getName(), slotDetails);
			
			System.out.println("Unparked vehicle with Registration Number: " + vehicle.getRegistrationNumber() + " and Color: " + vehicle.getColor());
		}
	}
	
	public void display(String displayType, String vehicleType) {
		if(displayType.equals(DisplayType.free_count.toString())) {
			displayFreeCount(vehicleType);
		}
		else if(displayType.equals(DisplayType.free_slots.toString())) {
			displayFreeSlots(vehicleType);
		}
		else {
			displayOccupiedSlots(vehicleType);
		}
	}
	
	public void displayFreeCount(String vehicleType) {
		for(int floorNum = 1; floorNum <= parkingLot.getMaxFloorNumber(); floorNum++) {
			int slotCount = 0;
			ParkingFloor floor = parkingLot.getFloors().get(floorNum - 1);
			for(int slotNum = 1; slotNum <= parkingLot.getSlotsPerFloor(); slotNum++) {
				ParkingSlot slot = floor.getParkingSlots().get(slotNum-1);
				if(slot.isFree() && slot.getVehicleType().equals(vehicleType))
					slotCount++;
			}
			System.out.println("No. of free slots for " + vehicleType + " on Floor " + floorNum + ": " + slotCount);
		}
	}
	
	public void displayFreeSlots(String vehicleType) {
		for(int floorNum = 1; floorNum <= parkingLot.getMaxFloorNumber(); floorNum++) {
			ParkingFloor floor = parkingLot.getFloors().get(floorNum - 1);
			StringBuilder sb = new StringBuilder();
			for(int slotNum = 1; slotNum <= parkingLot.getSlotsPerFloor(); slotNum++) {
				ParkingSlot slot = floor.getParkingSlots().get(slotNum-1);
				if(slot.isFree() && slot.getVehicleType().equals(vehicleType))
					sb.append(slotNum + ",");
			}
			String res = sb.length() > 1 ? sb.substring(0, sb.length()-1) : sb.toString();
			System.out.println("Free slots for " + vehicleType + " on Floor " + floorNum + ": " + res);
		}
	}
	
	public void displayOccupiedSlots(String vehicleType) {
		for(int floorNum = 1; floorNum <= parkingLot.getMaxFloorNumber(); floorNum++) {
			ParkingFloor floor = parkingLot.getFloors().get(floorNum - 1);
			StringBuilder sb = new StringBuilder();
			for(int slotNum = 1; slotNum <= parkingLot.getSlotsPerFloor(); slotNum++) {
				ParkingSlot slot = floor.getParkingSlots().get(slotNum-1);
				if(!slot.isFree() && slot.getVehicleType().equals(vehicleType))
					sb.append(slotNum + ",");
			}
			String res = sb.length() > 1 ? sb.substring(0, sb.length()-1) : sb.toString();
			System.out.println("Occupied slots for " + vehicleType + " on Floor " + floorNum + ": " + res);
		}
	}
	
	private int[] getSlotDetails(String ticketId) {
		String[] details = ticketId.split("_");
		return new int[] {Integer.parseInt(details[1]), Integer.parseInt(details[2])};
	}
	
	private Vehicle createNewVehicle(String vehicleType, String registrationNumber, String color) {
		Vehicle vehicle = vehicleFactory.getInstance(vehicleType);
		vehicle.setColor(color);
		vehicle.setRegistrationNumber(registrationNumber);
		return vehicle;
	}
	
	public void incrementToNextFreeSlot(String vehicleType, int[] freeSlot) {
		ParkingSlot slot = fetchSlot(freeSlot[0], freeSlot[1]);
		if(vehicleType.equals(TRUCK)) {
			while(slot != null && !slot.isFree()) {
				freeSlot[0]++;
				slot = fetchSlot(freeSlot[0], freeSlot[1]);
			}
		}
		
		else if(vehicleType.equals(BIKE)) {
			while(slot != null && !slot.isFree()) {
				if(freeSlot[1] == 2) {
					freeSlot[1]++;
				}
				else {
					freeSlot[0]++;
					freeSlot[1] = 2;
				}
				slot = fetchSlot(freeSlot[0], freeSlot[1]);
			}
			
		}
		else {
			while(slot != null && !slot.isFree()) {
				if(freeSlot[1] == parkingLot.getFloors().get(0).getMaxSlotNumber()) {
					freeSlot[0]++;
					freeSlot[1] = 4;
				}
				else {
					freeSlot[1]++;
				}
				slot = fetchSlot(freeSlot[0], freeSlot[1]);
			}
		}
	}
	
	private ParkingSlot fetchSlot(int floor, int slotNum) {
		ParkingSlot slot = null;
		try {
			slot = parkingLot.getFloors().get(floor - 1).getParkingSlots().get(slotNum - 1);
		}
		catch(Exception e) {
			return null;
		}
		return slot;
	}
	
	
	private boolean isParkingLotFull(String vehicleType) {
		int[] freeSlots = freeSlotsMap.get(vehicleType);
		int floor = freeSlots[0];
		
		if(floor == 1 + parkingLot.getMaxFloorNumber()) {
			return true;
		}
		
		return false;
	}
	
	private List<ParkingFloor> generateFloors(int numberOfFloors, int slotsPerFloor) {
		List<ParkingFloor> floors = new ArrayList<>(numberOfFloors);
		for(int i=0; i<numberOfFloors; i++) {
			int floorNumber = i+1;
			List<ParkingSlot> slots = generateSlots(slotsPerFloor);
			floors.add(new ParkingFloor(floorNumber, slots, slotsPerFloor));
		}
		return floors;
	}
	
	private List<ParkingSlot> generateSlots(int slotsPerFloor) {
		List<ParkingSlot> slots = new ArrayList<>(slotsPerFloor);
		//truck
		slots.add(new ParkingSlot(1, null, true, null, TRUCK));
		
		//bikes
		slots.add(new ParkingSlot(2, null, true, null, BIKE));
		slots.add(new ParkingSlot(3, null, true, null, BIKE));
		
		//cars
		for(int i=4; i<=slotsPerFloor; i++) {
			slots.add(new ParkingSlot(i, null, true, null, CAR));
		}
		
		return slots;
	}
}
