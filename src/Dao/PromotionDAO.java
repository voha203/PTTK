package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Model.Promotion;

public class PromotionDAO {
	private Connection conn;

	public PromotionDAO() {
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean addPromotion(Promotion promotion) {
		String sql = "INSERT INTO promotion(promotion_name, description, start_date, end_date, status) VALUES(?,?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, promotion.getPromotionName());
			ps.setString(2, promotion.getDescription());
			ps.setString(3, promotion.getStartDate());
			ps.setString(4, promotion.getEndDate());
			ps.setString(5, promotion.getStatus());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updatePromotion(Promotion promotion) {
		String sql = "UPDATE promotion SET promotion_name=?, description=?, start_date=?, end_date=?, status=? WHERE promotion_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, promotion.getPromotionName());
			ps.setString(2, promotion.getDescription());
			ps.setString(3, promotion.getStartDate());
			ps.setString(4, promotion.getEndDate());
			ps.setString(5, promotion.getStatus());
			ps.setInt(6, promotion.getPromotionId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deletePromotion(int promotionId) {

		String sql = "DELETE FROM promotion WHERE promotion_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, promotionId);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Promotion getPromotionById(int promotionId) {
		String sql = "SELECT * FROM promotion WHERE promotion_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, promotionId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Promotion promotion = new Promotion();
				promotion.setPromotionId(rs.getInt("promotion_id"));
				promotion.setPromotionName(rs.getString("promotion_name"));
				promotion.setDescription(rs.getString("description"));
				promotion.setStartDate(rs.getString("start_date"));
				promotion.setEndDate(rs.getString("end_date"));
				promotion.setStatus(rs.getString("status"));
				return promotion;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Promotion> getAllPromotion() {
		List<Promotion> list = new ArrayList<>();
		String sql = "SELECT * FROM promotion";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Promotion promotion = new Promotion();
				promotion.setPromotionId(rs.getInt("promotion_id"));
				promotion.setPromotionName(rs.getString("promotion_name"));
				promotion.setDescription(rs.getString("description"));
				promotion.setStartDate(rs.getString("start_date"));
				promotion.setEndDate(rs.getString("end_date"));
				promotion.setStatus(rs.getString("status"));

				list.add(promotion);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}