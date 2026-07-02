package Controller;

import java.util.List;

import Model.Member;
import Service.MemberService;

public class MemberController {

    private MemberService memberService = new MemberService();

    public boolean addMember(Member member) {
        return memberService.addMember(member);
    }

    public boolean updateMember(Member member) {
        return memberService.updateMember(member);
    }

    public boolean deleteMember(int memberId) {
        return memberService.deleteMember(memberId);
    }

    public Member getMemberById(int memberId) {
        return memberService.getMemberById(memberId);
    }

    public List<Member> getAllMember() {
        return memberService.getAllMember();
    }

}