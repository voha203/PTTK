package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		String sql = "INSERT INTO users(username,email,password,provider,provider_id,full_name,avatar,active)"
				+ " VALUES(?,?,?,?,?,?,?,?)";
		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPasswordHash());
			ps.setString(4, user.getProvider());
			ps.setString(5, user.getProviderId());
			ps.setString(6, null);
			ps.setString(7, null);
			ps.setBoolean(8, user.getStatus());
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
}