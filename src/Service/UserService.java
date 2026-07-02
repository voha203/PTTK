package Service;

import Dao.UserDao;
import Model.User;

public class UserService {
	private UserDao ud = new UserDao();

	public User verifyAccount(String email) {
		return ud.findUserByEmail(email);
	}

}
