package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Model.Booking;

public class BookingDAO {
	private Connection conn;

	public BookingDAO() {
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean addBooking(Booking b) {
		String sql = "INSERT INTO booking(member_id,trainer_id,booking_date,booking_time,status) VALUES(?,?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, b.getMemberId());
			ps.setInt(2, b.getTrainerId());
			ps.setString(3, b.getBookingDate());
			ps.setString(4, b.getBookingTime());
			ps.setString(5, b.getStatus());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateBooking(Booking b) {
		String sql = "UPDATE booking SET member_id=?,trainer_id=?,booking_date=?,booking_time=?,status=? WHERE booking_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, b.getMemberId());
			ps.setInt(2, b.getTrainerId());
			ps.setString(3, b.getBookingDate());
			ps.setString(4, b.getBookingTime());
			ps.setString(5, b.getStatus());
			ps.setInt(6, b.getBookingId());
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
		String sql = "SELECT b.*,u.full_name,t.trainer_name " + "FROM booking b "
				+ "JOIN member m ON b.member_id=m.member_id " + "JOIN users u ON m.user_id=u.id "
				+ "JOIN trainer t ON b.trainer_id=t.trainer_id " + "WHERE booking_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, bookingId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Booking b = new Booking();
				b.setBookingId(rs.getInt("booking_id"));
				b.setMemberId(rs.getInt("member_id"));
				b.setTrainerId(rs.getInt("trainer_id"));
				b.setMemberName(rs.getString("full_name"));
				b.setTrainerName(rs.getString("trainer_name"));
				b.setBookingDate(rs.getString("booking_date"));
				b.setBookingTime(rs.getString("booking_time"));
				b.setStatus(rs.getString("status"));
				return b;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Booking> getAllBooking() {
		List<Booking> list = new ArrayList<>();
		String sql = "SELECT b.*,u.full_name,t.trainer_name " + "FROM booking b "
				+ "JOIN member m ON b.member_id=m.member_id " + "JOIN users u ON m.user_id=u.id "
				+ "JOIN trainer t ON b.trainer_id=t.trainer_id";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Booking b = new Booking();
				b.setBookingId(rs.getInt("booking_id"));
				b.setMemberId(rs.getInt("member_id"));
				b.setTrainerId(rs.getInt("trainer_id"));
				b.setMemberName(rs.getString("full_name"));
				b.setTrainerName(rs.getString("trainer_name"));
				b.setBookingDate(rs.getString("booking_date"));
				b.setBookingTime(rs.getString("booking_time"));
				b.setStatus(rs.getString("status"));
				list.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// Kiểm tra trùng
	public boolean isTrainerBusy(int trainerId, String bookingDate, String bookingTime) {
		String sql = "SELECT * FROM booking WHERE trainer_id=? AND booking_date=? AND booking_time=? AND status<>'Cancelled'";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, trainerId);
			ps.setString(2, bookingDate);
			ps.setString(3, bookingTime);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// Kiểm tra hạn
	public boolean isPackageValid(int memberId) {
		String sql = "SELECT package_expiry FROM member WHERE member_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, memberId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String expiryStr = rs.getString("package_expiry");
				if (expiryStr != null && !expiryStr.trim().isEmpty()) {
					// Chuyển chuỗi Date từ database sang đối tượng java.sql.Date để so sánh chính
					// xác
					java.sql.Date expiry = java.sql.Date.valueOf(expiryStr);
					return !expiry.before(new java.sql.Date(System.currentTimeMillis()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}