package Controller;

import java.util.List;

import Model.Booking;
import Service.BookingService;

public class BookingController {

    private BookingService bookingService = new BookingService();

    public boolean addBooking(Booking booking) {
        return bookingService.addBooking(booking);
    }

    public boolean updateBooking(Booking booking) {
        return bookingService.updateBooking(booking);
    }

    public boolean deleteBooking(int bookingId) {
        return bookingService.deleteBooking(bookingId);
    }

    public Booking getBookingById(int bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    public List<Booking> getAllBooking() {
        return bookingService.getAllBooking();
    }

}