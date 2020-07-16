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
@Table(name = "sort")
@Data
public class Sort {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name = "s_id")
	private Integer sId;
	
	@Column(name = "s_name")
	private String name;
	
	@ManyToMany(mappedBy = "sorts",fetch = FetchType.LAZY)
	@JsonBackReference()
	private List<Article> articles;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;

	@Override
	public String toString() {
		return "Sort [sId=" + sId + ", name=" + name + ", date=" + date + "]";
	}

	
}
	