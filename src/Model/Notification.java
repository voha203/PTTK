package Model;

import java.time.LocalDateTime;

public class Notification {

	private int nid;
	private int uid;
	private String title;
	private String content;
	private LocalDateTime createdTime;

	public Notification() {
		super();
	}

	public Notification(int nid, int uid, String title, String content, LocalDateTime createdTime) {
		super();
		this.nid = nid;
		this.uid = uid;
		this.title = title;
		this.content = content;
		this.createdTime = createdTime;
	}

	public int getNid() {
		return nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

}
