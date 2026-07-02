package Dao;

import Model.Booking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    private Connection conn;

    public BookingDAO() {
        try {
            conn = DBConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean addBooking(Booking booking) {
        String sql = "INSERT INTO booking(member_id, trainer_id, booking_date, booking_time, status) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, booking.getMemberId());
            ps.setInt(2, booking.getTrainerId());
            ps.setString(3, booking.getBookingDate());
            ps.setString(4, booking.getBookingTime());
            ps.setString(5, booking.getStatus());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBooking(Booking booking) {
        String sql = "UPDATE booking SET member_id=?, trainer_id=?, booking_date=?, booking_time=?, status=? WHERE booking_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, booking.getMemberId());
            ps.setInt(2, booking.getTrainerId());
            ps.setString(3, booking.getBookingDate());
            ps.setString(4, booking.getBookingTime());
            ps.setString(5, booking.getStatus());
            ps.setInt(6, booking.getBookingId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBooking(int bookingId) {
        String sql = "DELETE FROM booking WHERE booking_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bookingId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Booking getBookingById(int bookingId) {
        String sql = "SELECT * FROM booking WHERE booking_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, bookingId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setMemberId(rs.getInt("member_id"));
                booking.setTrainerId(rs.getInt("trainer_id"));
                booking.setBookingDate(rs.getString("booking_date"));
                booking.setBookingTime(rs.getString("booking_time"));
                booking.setStatus(rs.getString("status"));

                return booking;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Booking> getAllBooking() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM booking";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setMemberId(rs.getInt("member_id"));
                booking.setTrainerId(rs.getInt("trainer_id"));
                booking.setBookingDate(rs.getString("booking_date"));
                booking.setBookingTime(rs.getString("booking_time"));
                booking.setStatus(rs.getString("status"));

                list.add(booking);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}