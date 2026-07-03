package Controller;

import java.util.List;

import Model.BookingVoucher;
import Service.BookingVoucherService;

public class BookingVoucherController {

    private BookingVoucherService bookingVoucherService;

    public BookingVoucherController() {
        bookingVoucherService = new BookingVoucherService();
    }

    public boolean addBookingVoucher(BookingVoucher bookingVoucher) {
        return bookingVoucherService.addBookingVoucher(bookingVoucher);
    }
    public boolean deleteByBookingId(int bookingId) {
        return bookingVoucherService.deleteByBookingId(bookingId);
    }
    public List<BookingVoucher> getAllBookingVoucher() {
        return bookingVoucherService.getAllBookingVoucher();
    }

    public BookingVoucher getBookingVoucherByBookingId(int bookingId) {
        return bookingVoucherService.getBookingVoucherByBookingId(bookingId);
    }

}