package Service;

import Dao.UserDao;
import Model.User;

public class LoginService {

	private UserDao userDao;

	public LoginService() {
		userDao = new UserDao();
	}

	public String checkLogin(String username, String password, String provider, String providerId) {
		User user = userDao.findUserByEmail(username);

		if (user == null) {
			return "Account not found.";
		}

		String storedPassword = user.getPasswordHash();
		String storedProvider = user.getProvider();
		String storedProviderId = user.getProviderId();
		boolean status = user.getStatus();
		
		if (status == false) {
			return "Account is locked. Please contact support.";
		}
		
		if (!"LOCAL".equals(provider)) {
			if (!storedProvider.equals(provider) || !storedProviderId.equals(providerId)) {
				return "This account is not registered with provider: " + provider;
			}
		} else {
			if (!storedPassword.equals(password)) {
				return "Incorrect password.";
			}
		}

		return "Login successful.";
	}

	public User getUserLogin(String email) {
		return userDao.findUserByEmail(email);
	}
}