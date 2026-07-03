package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Model.Voucher;

public class VoucherDAO {
	private Connection conn;

	public VoucherDAO() {
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean addVoucher(Voucher voucher) {
		String sql = "INSERT INTO voucher(promotion_id, voucher_code, discount_percent, minimum_amount, quantity, used_count, expire_date, status) VALUES(?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, voucher.getPromotionId());
			ps.setString(2, voucher.getVoucherCode());
			ps.setDouble(3, voucher.getDiscountPercent());
			ps.setDouble(4, voucher.getMinimumAmount());
			ps.setInt(5, voucher.getQuantity());
			ps.setInt(6, voucher.getUsedCount());
			ps.setString(7, voucher.getExpireDate());
			ps.setString(8, voucher.getStatus());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateVoucher(Voucher voucher) {
		String sql = "UPDATE voucher SET promotion_id=?, voucher_code=?, discount_percent=?, minimum_amount=?, quantity=?, used_count=?, expire_date=?, status=? WHERE voucher_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, voucher.getPromotionId());
			ps.setString(2, voucher.getVoucherCode());
			ps.setDouble(3, voucher.getDiscountPercent());
			ps.setDouble(4, voucher.getMinimumAmount());
			ps.setInt(5, voucher.getQuantity());
			ps.setInt(6, voucher.getUsedCount());
			ps.setString(7, voucher.getExpireDate());
			ps.setString(8, voucher.getStatus());
			ps.setInt(9, voucher.getVoucherId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteVoucher(int voucherId) {
		String sql = "DELETE FROM voucher WHERE voucher_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, voucherId);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Voucher getVoucherById(int voucherId) {
		String sql = "SELECT * FROM voucher WHERE voucher_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, voucherId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Voucher voucher = new Voucher();
				voucher.setVoucherId(rs.getInt("voucher_id"));
				voucher.setPromotionId(rs.getInt("promotion_id"));
				voucher.setVoucherCode(rs.getString("voucher_code"));
				voucher.setDiscountPercent(rs.getInt("discount_percent"));
				voucher.setMinimumAmount(rs.getDouble("minimum_amount"));
				voucher.setQuantity(rs.getInt("quantity"));
				voucher.setUsedCount(rs.getInt("used_count"));
				voucher.setExpireDate(rs.getString("expire_date"));
				voucher.setStatus(rs.getString("status"));
				return voucher;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Voucher> getAllVoucher() {
		List<Voucher> list = new ArrayList<>();
		String sql = "SELECT * FROM voucher";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Voucher voucher = new Voucher();
				voucher.setVoucherId(rs.getInt("voucher_id"));
				voucher.setPromotionId(rs.getInt("promotion_id"));
				voucher.setVoucherCode(rs.getString("voucher_code"));
				voucher.setDiscountPercent(rs.getInt("discount_percent"));
				voucher.setMinimumAmount(rs.getDouble("minimum_amount"));
				voucher.setQuantity(rs.getInt("quantity"));
				voucher.setUsedCount(rs.getInt("used_count"));
				voucher.setExpireDate(rs.getString("expire_date"));
				voucher.setStatus(rs.getString("status"));
				list.add(voucher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}