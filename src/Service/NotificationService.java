package Service;

import java.util.List;

import Dao.NotificationDao;
import Dao.UserDao;
import Model.Log;
import Model.Notification;
import Model.User;

public class NotificationService {

	private NotificationDao nd = new NotificationDao();
	private UserDao ud = new UserDao();
	
	private EmailService em = new EmailService();

	public void sendNotification(Log log) {
		Notification noti = new Notification(0, 0, log.getTableName(), log.getAction(), null);
		List<User> listUsers = null;
		
		switch (log.getTableName()) {
		case "booking":
			listUsers = ud.getUsersToSendNotificationsBooking(log);
			break;
		case "promotion":
			listUsers = ud.getUsersToSendNotificationsPromotion();
			break;
		default:
			break;
		}
		if (listUsers != null && !listUsers.isEmpty()) {
			for (User u : listUsers) {
				if (em.sendNotification(noti, u)) {
					nd.saveNotification(noti, u.getId());
				}
			}
		}
	}
}
