package Dao;

import Model.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "INSERT INTO member(member_name, phone, email, gender, membership_type, status) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, member.getMemberName());
            ps.setString(2, member.getPhone());
            ps.setString(3, member.getEmail());
            ps.setString(4, member.getGender());
            ps.setString(5, member.getMembershipType());
            ps.setString(6, member.getStatus());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateMember(Member member) {
        String sql = "UPDATE member SET member_name=?, phone=?, email=?, gender=?, membership_type=?, status=? WHERE member_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, member.getMemberName());
            ps.setString(2, member.getPhone());
            ps.setString(3, member.getEmail());
            ps.setString(4, member.getGender());
            ps.setString(5, member.getMembershipType());
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
        String sql = "SELECT * FROM member WHERE member_id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, memberId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getInt("member_id"));
                member.setMemberName(rs.getString("member_name"));
                member.setPhone(rs.getString("phone"));
                member.setEmail(rs.getString("email"));
                member.setGender(rs.getString("gender"));
                member.setMembershipType(rs.getString("membership_type"));
                member.setStatus(rs.getString("status"));

                return member;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Member> getAllMember() {
        List<Member> list = new ArrayList<>();
        String sql = "SELECT * FROM member";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getInt("member_id"));
                member.setMemberName(rs.getString("member_name"));
                member.setPhone(rs.getString("phone"));
                member.setEmail(rs.getString("email"));
                member.setGender(rs.getString("gender"));
                member.setMembershipType(rs.getString("membership_type"));
                member.setStatus(rs.getString("status"));

                list.add(member);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}