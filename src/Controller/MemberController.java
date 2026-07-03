package Controller;

import java.util.List;

import Model.Member;
import Service.MemberService;

public class MemberController {

    private MemberService memberService;

    public MemberController() {
        memberService = new MemberService();
    }

    // Thêm 
    public boolean addMember(Member member) {
        return memberService.addMember(member);
    }

    // Cập nhật
    public boolean updateMember(Member member) {
        return memberService.updateMember(member);
    }

    // Xóa
    public boolean deleteMember(int memberId) {
        return memberService.deleteMember(memberId);
    }

    // Tìm theo ID
    public Member getMemberById(int memberId) {
        return memberService.getMemberById(memberId);
    }

    // Lấy tất cả Member
    public List<Member> getAllMember() {
        return memberService.getAllMember();
    }

}