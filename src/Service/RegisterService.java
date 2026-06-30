package Service;

import Dao.UserDao;
import Model.User;

public class RegisterService {
	private UserDao userDao;

	public RegisterService() {
		userDao = new UserDao();
	}

	public String register(User user) {
		User userFind = userDao.findUserByEmail(user.getEmail());
		if (userFind != null) {
			if ("GOOGLE".equals(userFind.getProvider()))
				return "This Google account has already been registered.";
			return "Email already exists.";
		}
		boolean result = userDao.addUser(user);
		if (result) {
			return "Registration successful.";
		} else {
			return "Registration failed.";
		}
	}
}