package cn.xhxc.blog.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Table(name = "tag")
@Data
public class Tag {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name = "t_id")
	private Integer tId;
	
	@Column(name = "t_name")
	private String name;
	
	@ManyToMany(mappedBy = "tags",fetch = FetchType.LAZY)
	@JsonBackReference()
	private List<Article> articles;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;

	@Override
	public String toString() {
		return "Tag [tId=" + tId + ", name=" + name + ", date=" + date + "]";
	}
	
	
}
