package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Model.Rating;

public class RatingDAO {

	private Connection conn;

	public RatingDAO() {
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean addRating(Rating r) {
		String sql = "INSERT INTO rating(booking_id,trainer_id,member_id,star,comment,rating_date) VALUES(?,?,?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, r.getBookingId());
			ps.setInt(2, r.getTrainerId());
			ps.setInt(3, r.getMemberId());
			ps.setInt(4, r.getStar());
			ps.setString(5, r.getComment());
			ps.setString(6, r.getRatingDate());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateRating(Rating r) {
		String sql = "UPDATE rating SET star=?,comment=?,rating_date=? WHERE rating_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, r.getStar());
			ps.setString(2, r.getComment());
			ps.setString(3, r.getRatingDate());
			ps.setInt(4, r.getRatingId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteRating(int ratingId) {
		String sql = "DELETE FROM rating WHERE rating_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, ratingId);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Rating getRatingById(int ratingId) {
		String sql = "SELECT r.*,u.full_name,t.trainer_name " +
				"FROM rating r " +
				"JOIN member m ON r.member_id=m.member_id " +
				"JOIN users u ON m.user_id=u.id " +
				"JOIN trainer t ON r.trainer_id=t.trainer_id " +
				"WHERE rating_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, ratingId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Rating r = new Rating();
				r.setRatingId(rs.getInt("rating_id"));
				r.setBookingId(rs.getInt("booking_id"));
				r.setTrainerId(rs.getInt("trainer_id"));
				r.setMemberId(rs.getInt("member_id"));
				r.setTrainerName(rs.getString("trainer_name"));
				r.setMemberName(rs.getString("full_name"));
				r.setStar(rs.getInt("star"));
				r.setComment(rs.getString("comment"));
				r.setRatingDate(rs.getString("rating_date"));
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Rating> getAllRating() {
		List<Rating> list = new ArrayList<>();
		String sql = "SELECT r.*,u.full_name,t.trainer_name " +
				"FROM rating r " +
				"JOIN member m ON r.member_id=m.member_id " +
				"JOIN users u ON m.user_id=u.id " +
				"JOIN trainer t ON r.trainer_id=t.trainer_id";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Rating r = new Rating();
				r.setRatingId(rs.getInt("rating_id"));
				r.setBookingId(rs.getInt("booking_id"));
				r.setTrainerId(rs.getInt("trainer_id"));
				r.setMemberId(rs.getInt("member_id"));
				r.setTrainerName(rs.getString("trainer_name"));
				r.setMemberName(rs.getString("full_name"));
				r.setStar(rs.getInt("star"));
				r.setComment(rs.getString("comment"));
				r.setRatingDate(rs.getString("rating_date"));
				list.add(r);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 1. Kiểm tra Lịch đặt (Booking) đã hoàn thành chưa mới cho phép đánh giá
	public boolean bookingCompleted(int bookingId) {
		String sql = "SELECT * FROM booking WHERE booking_id=? AND status='Completed'";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, bookingId);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// Kiểm tra Lịch đã từng đánh giá chưa
	public boolean alreadyRated(int bookingId) {
		String sql = "SELECT * FROM rating WHERE booking_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, bookingId);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// Tính toán điểm và update vào trainer
	public void updateTrainerRating(int trainerId) {
		String sql = "UPDATE trainer SET rating=(SELECT AVG(star) FROM rating WHERE trainer_id=?) WHERE trainer_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, trainerId);
			ps.setInt(2, trainerId);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}