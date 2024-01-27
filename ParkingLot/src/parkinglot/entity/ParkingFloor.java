package parkinglot.entity;

import java.util.List;

public class ParkingFloor {
	private int floorNumber;
	private List<ParkingSlot> parkingSlots;
	int maxSlotNumber;
	public ParkingFloor(int floorNumber, List<ParkingSlot> parkingSlots, int maxSlotNumber) {
		super();
		this.floorNumber = floorNumber;
		this.parkingSlots = parkingSlots;
		this.maxSlotNumber = maxSlotNumber;
	}
	public int getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	public List<ParkingSlot> getParkingSlots() {
		return parkingSlots;
	}
	public void setParkingSlots(List<ParkingSlot> parkingSlots) {
		this.parkingSlots = parkingSlots;
	}
	public int getMaxSlotNumber() {
		return maxSlotNumber;
	}
	public void setMaxSlotNumber(int maxSlotNumber) {
		this.maxSlotNumber = maxSlotNumber;
	}
}
