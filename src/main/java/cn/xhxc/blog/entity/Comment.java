package cn.xhxc.blog.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cn.xhxc.blog.entity.vo.CommentVO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class Comment implements Serializable {
    private static final long serialVersionUID = -4502134548520740266L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long id;
    
    @Column(name = "pid",columnDefinition="bigint default 0")
    private Long pid = 0L;//父评论，如果不设置，默认为0
    
    @NotEmpty(message = "评论内容不能为空")
    @Size(max = 500, message = "评论内容不能多于500个字符")
    @Column(nullable = false)
    private String content;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({ "articles","roles"})
    private User user;
    
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @org.hibernate.annotations.CreationTimestamp  // 由数据库自动创建时间
    private Timestamp createTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @JsonIgnore
    private Article article;//所属文章
    
//    @OneToMany(fetch = FetchType.LAZY)//不设置级联，使用懒加载
//    @JoinColumn(name = "pid", referencedColumnName = "id")

    @Transient
    private List<CommentVO> commentVOs;
    
    @JoinColumn(name = "reply_user_id")
    @OneToOne(fetch = FetchType.LAZY)//不设置级联，使用懒加载
    @JsonIgnoreProperties
    private User replyUser;
    
    public Comment() {
    }
    public Comment(User user,Article article,  String content) {
        this.article = article;
        this.content = content;
        this.user = user;
    }
    public Comment(User user, Article article, Long pid, String content) {
        this.user = user;
        this.article = article;
        this.pid = pid;
        this.content = content;
    }
	@Override
	public String toString() {
		return "Comment [id=" + id + ", pid=" + pid + ", content=" + content + ", createTime=" + createTime
				+ ", article=" + article + ", commentVOs=" + commentVOs + "]";
	}
	
    
    
}