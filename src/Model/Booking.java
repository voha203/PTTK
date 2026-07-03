package Model;

public class Booking {
	private int bookingId;
	private int memberId;
	private int trainerId;
	private String memberName;
	private String trainerName;
	private String bookingDate;
	private String bookingTime;
	private String status;
	private double price;
	private double finalPrice;
	public Booking() {
	}

	public Booking(int bookingId, int memberId, int trainerId, String memberName, String trainerName,
			String bookingDate, String bookingTime, String status, double price, double finalPrice) {
		super();
		this.bookingId = bookingId;
		this.memberId = memberId;
		this.trainerId = trainerId;
		this.memberName = memberName;
		this.trainerName = trainerName;
		this.bookingDate = bookingDate;
		this.bookingTime = bookingTime;
		this.status = status;
		this.price = price;
		this.finalPrice = finalPrice;
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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}

	@Override
	public String toString() {
	    return memberName;
	}
}
