package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Log;
import Model.User;

public class UserDao {

	public User findUserByEmail(String email) {
		String sql = "SELECT * FROM users WHERE email = ?";
		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPasswordHash(rs.getString("password"));
				user.setProvider(rs.getString("provider"));
				user.setProviderId(rs.getString("provider_id"));
				user.setStatus(rs.getBoolean("active"));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean addUser(User user) {
		String sql = "INSERT INTO users(username,email,password,provider,provider_id,full_name,avatar,active,role)"
				+ " VALUES(?,?,?,?,?,?,?,?,?)";
		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPasswordHash());
			ps.setString(4, user.getProvider());
			ps.setString(5, user.getProviderId());
			ps.setString(6, user.getFullName());
			ps.setString(7, null);
			ps.setBoolean(8, user.getStatus());
			ps.setString(9, "Member");

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public void changePassword(User user, String pass) {
		String sql = "UPDATE users SET password = ? WHERE id = ?";
		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, pass);
			ps.setInt(2, user.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<User> getUsersToSendNotificationsBooking(Log log) {
		List<User> res = new ArrayList<User>();
		User user;
		String sql = """
				SELECT u.*
				FROM booking b
				JOIN member m ON b.member_id = m.member_id
				JOIN users u ON m.user_id = u.id
				WHERE b.booking_id = ?
				  AND u.active = 1
				  AND u.want_mail = 1
				""";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, log.getRecordId());
	        ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setStatus(rs.getBoolean("active"));
				user.setWantMail(rs.getBoolean("want_mail"));
				res.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public List<User> getUsersToSendNotificationsPromotion() {
		List<User> res = new ArrayList<User>();
		User user;
		String sql = "SELECT * FROM users WHERE active = 1 and want_mail = 1";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setStatus(rs.getBoolean("active"));
				user.setWantMail(rs.getBoolean("want_mail"));
				res.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
}