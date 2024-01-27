package parkinglot.entity;

import java.util.List;

public class ParkingLot {
	private String parkingLotId;
	private List<ParkingFloor> floors;
	private int maxFloorNumber;
	private int slotsPerFloor;
	public ParkingLot(String parkingLotId, List<ParkingFloor> floors, int numberOfFloors, int slotsPerFloor) {
		super();
		this.parkingLotId = parkingLotId;
		this.floors = floors;
		this.maxFloorNumber = numberOfFloors;
		this.slotsPerFloor = slotsPerFloor;
	}
	public String getParkingLotId() {
		return parkingLotId;
	}
	public void setParkingLotId(String parkingLotId) {
		this.parkingLotId = parkingLotId;
	}
	public List<ParkingFloor> getFloors() {
		return floors;
	}
	public void setFloors(List<ParkingFloor> floors) {
		this.floors = floors;
	}
	public int getMaxFloorNumber() {
		return maxFloorNumber;
	}
	public int getSlotsPerFloor() {
		return slotsPerFloor;
	}
}
