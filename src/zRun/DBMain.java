package zRun;

import Dao.DBWatcher;

public class DBMain {

	public static void main(String[] args) {
	    DBWatcher watcher = new DBWatcher();
	    watcher.start();
	}
}
