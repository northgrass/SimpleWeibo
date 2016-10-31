package entity;

import java.sql.Timestamp;

public class Weibo {
	private String content = "";
	private String username = "";
	private long commentCount = 0;
	private long repostCount = 0;
	private long userid = 0;
	private long weiboid = 0;
	private long referenceid = 0;
	private String createDate = "";
	private int flag = 0;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(long commentCount) {
		this.commentCount = commentCount;
	}
	
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}
	
	public long getRepostCount() {
		return repostCount;
	}

	public void setRepostCount(long repostCount) {
		this.repostCount = repostCount;
	}
	public long getWeiboid() {
		return weiboid;
	}

	public void setWeiboid(long weiboid) {
		this.weiboid = weiboid;
	}
	public long getReferenceid() {
		return referenceid;
	}

	public void setReferenceid(long referenceid) {
		this.referenceid = referenceid;
	}
}