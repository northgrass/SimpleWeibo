package entity;

public class Comment {
	private Long userId;
	private Long commentId;
	private String username;
	private Long weiboId;
	private String content;
	private String commentDate;
	private Long commentCount;
	
	public Long getUserId(){
		return userId;
	}
	
	public Long getCommentId(){
		return commentId;
	}
	
	public Long getCommentCount(){
		return commentCount;
	}
	
	public Long getWeiboId(){
		return weiboId;
	}
	
	
	public String getContent(){
		return content;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getCommentDate(){
		return commentDate;
	}
	
	
	public void setCommentCount(Long commentCount){
		this.commentCount = commentCount;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	public void setCommentId(Long commentId){
		this.commentId = commentId;
	}
	
	public void setWeiboId(Long weiboId){
		this.weiboId = weiboId;
	}
	
	
	public void setContent(String content){
		this.content = content;
	}
	

	public void setUsername(String username){
		this.username = username;
	}
	
	public void setCommentDate(String commentDate){
		this.commentDate = commentDate;
	}
}
