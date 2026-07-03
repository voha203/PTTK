package Service;

import java.util.List;

import Dao.BookingVoucherDAO;
import Model.BookingVoucher;

public class BookingVoucherService {

    private BookingVoucherDAO bookingVoucherDAO;

    public BookingVoucherService() {
        bookingVoucherDAO = new BookingVoucherDAO();
    }

    public boolean addBookingVoucher(BookingVoucher bookingVoucher) {
        return bookingVoucherDAO.addBookingVoucher(bookingVoucher);
    }
    public boolean deleteByBookingId(int bookingId) {
        return bookingVoucherDAO.deleteByBookingId(bookingId);
    }
    public List<BookingVoucher> getAllBookingVoucher() {
        return bookingVoucherDAO.getAllBookingVoucher();
    }

    public BookingVoucher getBookingVoucherByBookingId(int bookingId) {
        return bookingVoucherDAO.getBookingVoucherByBookingId(bookingId);
    }

}