package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import Model.OTP;

public class OTPDao {

	private Connection conn;

	public OTPDao() {
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveOTP(int uid, String otp) {
		String sql = """
				INSERT INTO otp (uid, otp, expired_time) VALUES (?, ?, ?)
				ON DUPLICATE KEY UPDATE
				    otp = VALUES(otp),
				    expired_time = VALUES(expired_time)
				""";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, uid);
			ps.setString(2, otp);
			ps.setObject(3, LocalDateTime.now().plusMinutes(6));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public OTP getOTPByUid(int uid) {
		String sql = "SELECT * FROM otp WHERE uid = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				OTP otp = new OTP();
				otp.setOtpId(rs.getInt("otp_id"));
				otp.setUid(rs.getInt("uid"));
				otp.setOtp(rs.getString("otp"));
				otp.setExpiredTime(rs.getObject("expired_time", LocalDateTime.class));
				return otp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteOTP(int uid) {
	    String sql = "DELETE FROM otp WHERE uid = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, uid);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
