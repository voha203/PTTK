package Model;

public class Booking {
	private	int bookingId;
	private int memberId;
	private int trainerId;
	private String bookingDate;
	private String bookingTime;
	private String status;
	public Booking() {

    }
	public Booking(int bookingId, int memberId, int trainerId, String bookingDate, String bookingTime, String status) {
		super();
		this.bookingId = bookingId;
		this.memberId = memberId;
		this.trainerId = trainerId;
		this.bookingDate = bookingDate;
		this.bookingTime = bookingTime;
		this.status = status;
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getTrainerId() {
		return trainerId;
	}
	public void setTrainerId(int trainerId) {
		this.trainerId = trainerId;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
