package Dao;

import Model.Account;
import java.util.HashMap;
import java.util.Map;

public class AccountRepository {
    // Giả lập bảng lưu trữ tài khoản trong Database
    private final Map<String, Account> accountDb = new HashMap<>();

    public void save(Account account) {
        accountDb.put(account.getUsername(), account);
        System.out.println("[DB Account] Đã lưu tài khoản: " + account.getUsername() + " [" + account.getRole() + "]");
    }

    public Account findByUsername(String username) {
        return accountDb.get(username);
    }
}