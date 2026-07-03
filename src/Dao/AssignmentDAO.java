package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Model.Assignment;

public class AssignmentDAO {

	private Connection conn;

	public AssignmentDAO() {
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean addAssignment(Assignment a) {
		String sql = "INSERT INTO assignment(trainer_id,member_id,assigned_date,status) VALUES(?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getTrainerId());
			ps.setInt(2, a.getMemberId());
			ps.setString(3, a.getAssignedDate());
			ps.setString(4, a.getStatus());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateAssignment(Assignment a) {
		String sql = "UPDATE assignment SET trainer_id=?,member_id=?,assigned_date=?,status=? WHERE assignment_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getTrainerId());
			ps.setInt(2, a.getMemberId());
			ps.setString(3, a.getAssignedDate());
			ps.setString(4, a.getStatus());
			ps.setInt(5, a.getAssignmentId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteAssignment(int assignmentId) {
		String sql = "DELETE FROM assignment WHERE assignment_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, assignmentId);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Assignment getAssignmentById(int assignmentId) {
		String sql = "SELECT a.*,u.full_name,t.trainer_name " +
				"FROM assignment a " +
				"JOIN member m ON a.member_id=m.member_id " +
				"JOIN users u ON m.user_id=u.id " +
				"JOIN trainer t ON a.trainer_id=t.trainer_id " +
				"WHERE assignment_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, assignmentId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Assignment a = new Assignment();
				a.setAssignmentId(rs.getInt("assignment_id"));
				a.setTrainerId(rs.getInt("trainer_id"));
				a.setMemberId(rs.getInt("member_id"));
				a.setTrainerName(rs.getString("trainer_name"));
				a.setMemberName(rs.getString("full_name"));
				a.setAssignedDate(rs.getString("assigned_date"));
				a.setStatus(rs.getString("status"));
				return a;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Assignment> getAllAssignment() {
		List<Assignment> list = new ArrayList<>();
		String sql = "SELECT a.*,u.full_name,t.trainer_name " +
				"FROM assignment a " +
				"JOIN member m ON a.member_id=m.member_id " +
				"JOIN users u ON m.user_id=u.id " +
				"JOIN trainer t ON a.trainer_id=t.trainer_id";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Assignment a = new Assignment();
				a.setAssignmentId(rs.getInt("assignment_id"));
				a.setTrainerId(rs.getInt("trainer_id"));
				a.setMemberId(rs.getInt("member_id"));
				a.setTrainerName(rs.getString("trainer_name"));
				a.setMemberName(rs.getString("full_name"));
				a.setAssignedDate(rs.getString("assigned_date"));
				a.setStatus(rs.getString("status"));
				list.add(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// Kiểm tra Member đã có PT chưa
	public boolean hasTrainer(int memberId) {
		String sql = "SELECT * FROM assignment WHERE member_id=? AND status='Active'";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, memberId);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// Trainer (Phải Active mới cho gán lịch)
	public boolean trainerActive(int trainerId) {
		String sql = "SELECT * FROM trainer WHERE trainer_id=? AND status='Active'";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, trainerId);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// Active của Member
	public boolean memberActive(int memberId) {
		String sql = "SELECT * FROM member WHERE member_id=? AND status='Active'";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, memberId);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}