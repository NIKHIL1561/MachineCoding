package parkinglot.entity;

public class Ticket {
	private ParkingLot parkingLot;
	private ParkingFloor floor;
	private ParkingSlot slot;
	private String ticketId;
	private Vehicle vehicle;
	public Ticket(ParkingLot parkingLot, ParkingFloor floor, ParkingSlot slot, Vehicle vehicle) {
		super();
		this.parkingLot = parkingLot;
		this.floor = floor;
		this.slot = slot;
		ticketId = parkingLot.getParkingLotId() + "_" + floor.getFloorNumber() + "_" + slot.getSlotNumber();
		this.vehicle = vehicle;
	}
	
	public String getTicketId() {
		return ticketId;
	}

	public ParkingLot getParkingLot() {
		return parkingLot;
	}
	public void setParkingLot(ParkingLot parkingLotId) {
		this.parkingLot = parkingLotId;
	}
	public ParkingFloor getFloor() {
		return floor;
	}
	public void setFloor(ParkingFloor floor) {
		this.floor = floor;
	}
	public ParkingSlot getSlot() {
		return slot;
	}
	public void setSlot(ParkingSlot slot) {
		this.slot = slot;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}
