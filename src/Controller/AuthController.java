package Controller;

import Dto.LoginRequest;
import Dto.RegisterRequest;
import Model.Account;
import Model.Member;
import Dao.AccountRepository;
import java.util.UUID;

public class AuthController {
    private final AccountRepository accountRepository = new AccountRepository();

    // API Đăng ký tài khoản mới cho Hội viên
    public String register(RegisterRequest request) {
        if (accountRepository.findByUsername(request.getUsername()) != null) {
            return "{ \"status\": \"FAIL\", \"message\": \"Tài khoản đã tồn tại!\" }";
        }

        String memberId = "MB-" + UUID.randomUUID().toString().substring(0, 5);
        Member newMember = new Member(memberId, request.getUsername(), request.getPassword(), request.getFullName());
        
        accountRepository.save(newMember);
        return "{ \"status\": \"SUCCESS\", \"message\": \"Đăng ký hội viên thành công!\" }";
    }

    // API Đăng nhập hệ thống
    public String login(LoginRequest request) {
        Account account = accountRepository.findByUsername(request.getUsername());
        
        if (account == null || !account.getPassword().equals(request.getPassword())) {
            return "{ \"status\": \"FAIL\", \"message\": \"Sai tài khoản hoặc mật khẩu!\" }";
        }

        return "{ \"status\": \"SUCCESS\", \"message\": \"Đăng nhập thành công!\", \"role\": \"" + account.getRole() + "\" }";
    }
}