package cn.xhxc.blog.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cn.xhxc.blog.entity.vo.CommentVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "article")
@Setter
@Getter
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Article implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "a_id")
	private String aId;

	private String author;

	@Column(length = 50, nullable = false)
	private String title;

	@Lob // 大对象，映射 MySQL 的 Long Text 类型
	@Basic(fetch = FetchType.LAZY) // 懒加载
	private String content;

	@Lob // 大对象，映射 MySQL 的 Long Text 类型
	@Basic(fetch = FetchType.LAZY) // 懒加载
	@Column(nullable = false) // 映射为字段，值不能为空
	private String textContent;
	
	//发布类型 0公开 1私密 2保存
//	@Column(nullable = false)
	private Integer form;
	
	@Column(name = "comment_size",columnDefinition="int default 0")
    private Integer commentSize;  // 评论数
	
	@Column(name = "fabulous_size",columnDefinition="int default 0")
    private Integer fabulousSize;  // 点赞数
	
    //当文章删除后，与之关联的评论也会被删除
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "pid=0")
    private List<Comment> commentList;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = false)
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties(value="articles")
	private User user;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "article_tag", joinColumns = @JoinColumn(name = "a_id"), inverseJoinColumns = @JoinColumn(name = "t_id"))
	private List<Tag> tags;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "article_sort", joinColumns = @JoinColumn(name = "a_id"), inverseJoinColumns = @JoinColumn(name = "s_id"))
	private List<Sort> sorts;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;

	public void updateCommentSize() {
        this.commentSize = this.commentList.size();
    }

	@Override
	public String toString() {
		return "Article [aId=" + aId + ", author=" + author + ", title=" + title + ", content=" + content
				+ ", textContent=" + textContent + ", form=" + form + ", commentSize=" + commentSize + ", fabulousSize="
				+ fabulousSize + ", date=" + date + "]";
	}
	
}
