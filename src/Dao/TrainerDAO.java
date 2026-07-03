package Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Trainer;

public class TrainerDAO {
	private Connection conn;

	public TrainerDAO() {
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Add
	public boolean addTrainer(Trainer trainer) {
		String sql = "INSERT INTO trainer(trainer_name,phone,email,specialization,experience,rating,status) VALUES(?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, trainer.getTrainerName());
			ps.setString(2, trainer.getPhone());
			ps.setString(3, trainer.getEmail());
			ps.setString(4, trainer.getSpecialization());
			ps.setInt(5, trainer.getExperience());
			ps.setFloat(6, trainer.getRating());
			ps.setString(7, trainer.getStatus());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// cạp nhật
	public boolean updateTrainer(Trainer trainer) {
		String sql = "UPDATE trainer SET trainer_name=?,phone=?,email=?,specialization=?,experience=?,rating=?,status=? WHERE trainer_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, trainer.getTrainerName());
			ps.setString(2, trainer.getPhone());
			ps.setString(3, trainer.getEmail());
			ps.setString(4, trainer.getSpecialization());
			ps.setInt(5, trainer.getExperience());
			ps.setFloat(6, trainer.getRating());
			ps.setString(7, trainer.getStatus());
			ps.setInt(8, trainer.getTrainerId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// xóa
	public boolean deleteTrainer(int trainerId) {
		String sql = "DELETE FROM trainer WHERE trainer_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, trainerId);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// tìm
	public Trainer getTrainerById(int trainerId) {
		String sql = "SELECT * FROM trainer WHERE trainer_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, trainerId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Trainer trainer = new Trainer();
				trainer.setTrainerId(rs.getInt("trainer_id"));
				trainer.setTrainerName(rs.getString("trainer_name"));
				trainer.setPhone(rs.getString("phone"));
				trainer.setEmail(rs.getString("email"));
				trainer.setSpecialization(rs.getString("specialization"));
				trainer.setExperience(rs.getInt("experience"));
				trainer.setRating(rs.getFloat("rating"));
				trainer.setStatus(rs.getString("status"));
				return trainer;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Get All
	public List<Trainer> getAllTrainer() {
		List<Trainer> list = new ArrayList<>();
		String sql = "SELECT * FROM trainer";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Trainer trainer = new Trainer();
				trainer.setTrainerId(rs.getInt("trainer_id"));
				trainer.setTrainerName(rs.getString("trainer_name"));
				trainer.setPhone(rs.getString("phone"));
				trainer.setEmail(rs.getString("email"));
				trainer.setSpecialization(rs.getString("specialization"));
				trainer.setExperience(rs.getInt("experience"));
				trainer.setRating(rs.getFloat("rating"));
				trainer.setStatus(rs.getString("status"));
				list.add(trainer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}