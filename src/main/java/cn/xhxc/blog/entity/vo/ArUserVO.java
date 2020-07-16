package cn.xhxc.blog.entity.vo;

import lombok.Data;

@Data
public class ArUserVO {
	private Integer id;
	private String aid;
	private Long uid;
	public ArUserVO(String aid, Long uid) {
		super();
		this.aid = aid;
		this.uid = uid;
	}
	
	
}
