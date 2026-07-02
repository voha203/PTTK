package Service;

import java.util.List;
import Dao.BookingDAO;
import Model.Booking;

public class BookingService {
    private final BookingDAO bookingDAO;

    public BookingService() {
        this.bookingDAO = new BookingDAO();
    }

    // Thêm Booking (Kiểm tra dữ liệu trống)
    public boolean addBooking(Booking booking) {
        if (booking == null || booking.getMemberId() <= 0 || booking.getTrainerId() <= 0) {
            return false;
        }
        return bookingDAO.addBooking(booking);
    }

    // Cập nhật Booking
    public boolean updateBooking(Booking booking) {
        return bookingDAO.updateBooking(booking);
    }

    // Xóa Booking
    public boolean deleteBooking(int bookingId) {
        return bookingDAO.deleteBooking(bookingId);
    }

    // Lấy Booking theo ID
    public Booking getBookingById(int bookingId) {
        return bookingDAO.getBookingById(bookingId);
    }

    // Lấy danh sách toàn bộ Booking
    public List<Booking> getAllBooking() {
        return bookingDAO.getAllBooking();
    }
}