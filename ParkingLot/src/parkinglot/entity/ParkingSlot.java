package parkinglot.entity;

public class ParkingSlot {
	private int slotNumber;
	private Vehicle vehicle;
	private boolean isFree;
	private Ticket ticket;
	private String vehicleType;
	public ParkingSlot(int slotNumber, Vehicle vehicle, boolean isFree, Ticket ticket, String vehicleType) {
		super();
		this.slotNumber = slotNumber;
		this.vehicle = vehicle;
		this.isFree = isFree;
		this.ticket = ticket;
		this.vehicleType = vehicleType;
	}
	public int getSlotNumber() {
		return slotNumber;
	}
	public void setSlotNumber(int slotNumber) {
		this.slotNumber = slotNumber;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public boolean isFree() {
		return isFree;
	}
	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}
	public Ticket getTicket() {
		return ticket;
	}
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	public String getVehicleType() {
		return vehicleType;
	}
}
