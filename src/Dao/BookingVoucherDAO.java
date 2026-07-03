package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Model.BookingVoucher;

public class BookingVoucherDAO {
    private Connection conn;
    public BookingVoucherDAO() {
        try {
            conn = DBConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addBookingVoucher(BookingVoucher bookingVoucher) {
        String sql = "INSERT INTO booking_voucher(booking_id,voucher_id,discount_amount) VALUES(?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bookingVoucher.getBookingId());
            ps.setInt(2, bookingVoucher.getVoucherId());
            ps.setDouble(3, bookingVoucher.getDiscountAmount());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean deleteByBookingId(int bookingId) {

        String sql = "DELETE FROM booking_voucher WHERE booking_id=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, bookingId);

            return ps.executeUpdate() >= 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public List<BookingVoucher> getAllBookingVoucher() {
        List<BookingVoucher> list = new ArrayList<>();
        String sql = "SELECT * FROM booking_voucher";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BookingVoucher bookingVoucher = new BookingVoucher();
                bookingVoucher.setId(rs.getInt("id"));
                bookingVoucher.setBookingId(rs.getInt("booking_id"));
                bookingVoucher.setVoucherId(rs.getInt("voucher_id"));
                bookingVoucher.setDiscountAmount(rs.getDouble("discount_amount"));
                list.add(bookingVoucher);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public BookingVoucher getBookingVoucherByBookingId(int bookingId) {

        String sql = "SELECT * FROM booking_voucher WHERE booking_id=?";

        try {

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, bookingId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                BookingVoucher bookingVoucher = new BookingVoucher();

                bookingVoucher.setId(rs.getInt("id"));
                bookingVoucher.setBookingId(rs.getInt("booking_id"));
                bookingVoucher.setVoucherId(rs.getInt("voucher_id"));
                bookingVoucher.setDiscountAmount(rs.getDouble("discount_amount"));

                return bookingVoucher;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}