package Service;

import java.util.List;
import Dao.MemberDAO;
import Model.Member;

public class MemberService {
	private MemberDAO memberDAO;

	public MemberService() {
		memberDAO = new MemberDAO();
	}

	// Thêm
	public boolean addMember(Member member) {

		if (member.getUserId() <= 0) {
			return false;
		}

		return memberDAO.addMember(member);
	}

	// sauwr
	public boolean updateMember(Member member) {

		return memberDAO.updateMember(member);

	}

	// Xóa
	public boolean deleteMember(int memberId) {

		return memberDAO.deleteMember(memberId);

	}

	// Tìm theo ID
	public Member getMemberById(int memberId) {

		return memberDAO.getMemberById(memberId);

	}

	// ds Member
	public List<Member> getAllMember() {

		return memberDAO.getAllMember();

	}

}