package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Model.Log;

public class LogDao {

	private Connection conn;

	public LogDao() {
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Log> getLogNotRead() {
		List<Log> logs = new ArrayList<>();

		String sql = "SELECT * FROM log WHERE processed = false ORDER BY created_at ASC";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Log log = new Log();
				log.setLid(rs.getInt("lid"));
				log.setTableName(rs.getString("table_name"));
				log.setRecordId(rs.getInt("record_id"));
				log.setAction(rs.getString("action"));
				log.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
				log.setProccessed(rs.getBoolean("processed"));
				logs.add(log);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return logs;
	}

	public void logDone(Log log) {
		String sql = "UPDATE log SET processed = 1 WHERE lid = ?";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, log.getLid());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Purely for testing
	 */
	public void insertLogTest() {
		String sql = """
				INSERT INTO log (table_name, record_id, action, created_at)
				VALUES
				('promotion', 1, 'INSERT', NOW()),
				('promotion', 2, 'UPDATE', NOW()),
				('promotion', 3, 'DELETE', NOW()),
				('booking', 1, 'INSERT', NOW()),
				('booking', 2, 'UPDATE', NOW());
								""";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}