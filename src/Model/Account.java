package Model;

public abstract class Account {
    private String accountId;
    private String username;
    private String password; // 
    private String role;     // MEMBER, TRAINER, ADMIN

    public Account(String accountId, String username, String password, String role) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public String getUsername() {
	 return username; 
    }
    public String getPassword() {
	 return password; 
    }
    public String getRole() {
	 return role; 
    }
    public String getAccountId() {
	 return accountId; 
    }
}