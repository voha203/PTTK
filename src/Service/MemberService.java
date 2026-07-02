package Service;

import java.util.List;

import Dao.MemberDAO;
import Model.Member;

public class MemberService {

    private MemberDAO memberDAO = new MemberDAO();

    public boolean addMember(Member member) {
        return memberDAO.addMember(member);
    }

    public boolean updateMember(Member member) {
        return memberDAO.updateMember(member);
    }

    public boolean deleteMember(int memberId) {
        return memberDAO.deleteMember(memberId);
    }

    public Member getMemberById(int memberId) {
        return memberDAO.getMemberById(memberId);
    }

    public List<Member> getAllMember() {
        return memberDAO.getAllMember();
    }

}