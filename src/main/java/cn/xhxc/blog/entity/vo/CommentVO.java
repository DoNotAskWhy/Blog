package cn.xhxc.blog.entity.vo;

import java.util.Date;
import lombok.Data;

@Data
public class CommentVO {
	
    private Long id;
    
    private Long pid;//父评论，如果不设置，默认为0
    
    private String content;
    
    private UserVO user;
    
    private Date createTime;
    
    private String articleId;//所属文章
    
//    @OneToMany(fetch = FetchType.LAZY)//不设置级联，使用懒加载
//    @JoinColumn(name = "pid", referencedColumnName = "id")
    
    private UserVO replyUser;

	public CommentVO(Long id, Long pid, String content, UserVO user, Date createTime, String articleId,
			UserVO replyUser) {
		super();
		this.id = id;
		this.pid = pid;
		this.content = content;
		this.user = user;
		this.createTime = createTime;
		this.articleId = articleId;
		this.replyUser = replyUser;
	}
    
    
}
