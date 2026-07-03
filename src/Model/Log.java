package Model;

import java.time.LocalDateTime;

public class Log {
	private int lid;
	private String tableName;
	private int recordId;
	private String action;
	private LocalDateTime createdAt;
	private boolean proccessed;

	public Log() {
		super();
	}

	public Log(int lid, String tableName, int recordId, String action, LocalDateTime createdAt, boolean proccessed) {
		super();
		this.lid = lid;
		this.tableName = tableName;
		this.recordId = recordId;
		this.action = action;
		this.createdAt = createdAt;
		this.proccessed = proccessed;
	}

	public int getLid() {
		return lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isProccessed() {
		return proccessed;
	}

	public void setProccessed(boolean proccessed) {
		this.proccessed = proccessed;
	}

}
