package Dao;

import java.util.List;

import Model.Log;
import Service.LogService;
import Service.NotificationService;

public class DBWatcher extends Thread {

	private LogService ls = new LogService();
	private NotificationService ns = new NotificationService();

	@Override
	public void run() {
		while (true) {
			List<Log> list = ls.getLogNotRead();
			if (!list.isEmpty()) {
				for (Log log : list) {
					ns.sendNotification(log);
					ls.logDone(log);
				}
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
