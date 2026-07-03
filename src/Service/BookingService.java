package Service;

import java.util.List;
import Dao.BookingDAO;
import Model.Booking;

public class BookingService {

	private BookingDAO bookingDAO = new BookingDAO();

	public String addBooking(Booking booking) {

	    if (!bookingDAO.isPackageValid(booking.getMemberId()))
	        return "PACKAGE_EXPIRED";

	    if (bookingDAO.isTrainerBusy(
	            booking.getTrainerId(),
	            booking.getBookingDate(),
	            booking.getBookingTime()))
	        return "TRAINER_BUSY";

	    if (bookingDAO.addBooking(booking))
	        return "SUCCESS";

	    return "ERROR";
	}

	public boolean updateBooking(Booking booking) {
		return bookingDAO.updateBooking(booking);
	}

	public boolean deleteBooking(int bookingId) {
		return bookingDAO.deleteBooking(bookingId);
	}

	public Booking getBookingById(int bookingId) {
		return bookingDAO.getBookingById(bookingId);
	}

	public List<Booking> getAllBooking() {
		return bookingDAO.getAllBooking();
	}
}