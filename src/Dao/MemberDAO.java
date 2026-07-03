package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Model.Member;

public class MemberDAO {

	private Connection conn;

	public MemberDAO() {
		try {
			conn = DBConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean addMember(Member member) {
		String sql = "INSERT INTO member(user_id, phone, gender, membership_type, package_expiry, status) VALUES(?,?,?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, member.getUserId());
			ps.setString(2, member.getPhone());
			ps.setString(3, member.getGender());
			ps.setString(4, member.getMembershipType());
			ps.setString(5, member.getPackageExpiry());
			ps.setString(6, member.getStatus());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateMember(Member member) {
		String sql = "UPDATE member SET user_id=?, phone=?, gender=?, membership_type=?, package_expiry=?, status=? WHERE member_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, member.getUserId());
			ps.setString(2, member.getPhone());
			ps.setString(3, member.getGender());
			ps.setString(4, member.getMembershipType());
			ps.setString(5, member.getPackageExpiry());
			ps.setString(6, member.getStatus());
			ps.setInt(7, member.getMemberId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteMember(int memberId) {
		String sql = "DELETE FROM member WHERE member_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, memberId);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Member getMemberById(int memberId) {
		// Thay đổi SQL sử dụng JOIN để lấy full_name của bảng users liên kết qua trường u.id
		String sql = "SELECT m.*, u.full_name FROM member m JOIN users u ON m.user_id = u.id WHERE m.member_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, memberId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Member member = new Member();
				member.setMemberId(rs.getInt("member_id"));
				member.setUserId(rs.getInt("user_id"));
				member.setPhone(rs.getString("phone"));
				member.setGender(rs.getString("gender"));
				member.setMembershipType(rs.getString("membership_type"));
				member.setPackageExpiry(rs.getString("package_expiry"));
				member.setStatus(rs.getString("status"));
				// Đổ dữ liệu tên đầy đủ vào Model
				member.setFullName(rs.getString("full_name"));
				
				return member;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Member> getAllMember() {
		List<Member> list = new ArrayList<>();
		String sql = "SELECT m.*, u.full_name FROM member m JOIN users u ON m.user_id = u.id";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Member member = new Member();
				member.setMemberId(rs.getInt("member_id"));
				member.setUserId(rs.getInt("user_id"));
				member.setPhone(rs.getString("phone"));
				member.setGender(rs.getString("gender"));
				member.setMembershipType(rs.getString("membership_type"));
				member.setPackageExpiry(rs.getString("package_expiry"));
				member.setStatus(rs.getString("status"));
				// Đổ dữ liệu tên đầy đủ vào Model
				member.setFullName(rs.getString("full_name"));
				
				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}