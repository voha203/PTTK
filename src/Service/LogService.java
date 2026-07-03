package Service;

import java.util.List;

import Dao.LogDao;
import Model.Log;

public class LogService {

	private LogDao ld = new LogDao();
	
	public List<Log> getLogNotRead() {
		return ld.getLogNotRead();
	}

	public void logDone(Log log) {
		ld.logDone(log);
	}
}
