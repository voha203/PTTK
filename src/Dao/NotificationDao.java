package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import Model.Notification;

public class NotificationDao {

	private Connection conn;

	public NotificationDao() {
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveNotification(Notification noti, int uid) {
		String sql = "INSERT INTO notification (uid, title, content, created_time) VALUES (?, ?, ?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, uid);
			ps.setString(2, noti.getTitle());
			ps.setString(3, noti.getContent());
			ps.setObject(4, LocalDateTime.now());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
