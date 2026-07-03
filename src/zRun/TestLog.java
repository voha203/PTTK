package zRun;

import Dao.LogDao;

public class TestLog {
	public static void main(String[] args) {
		LogDao ld = new LogDao();
		ld.insertLogTest();
	}
}
