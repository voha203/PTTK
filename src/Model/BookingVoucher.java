package Model;

public class BookingVoucher {

	private int id;
	private int bookingId;
	private int voucherId;
	private double discountAmount;

	public BookingVoucher() {
	}

	public BookingVoucher(int id, int bookingId, int voucherId, double discountAmount) {
		super();
		this.id = id;
		this.bookingId = bookingId;
		this.voucherId = voucherId;
		this.discountAmount = discountAmount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(int voucherId) {
		this.voucherId = voucherId;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

}