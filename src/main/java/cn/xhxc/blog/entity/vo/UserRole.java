package cn.xhxc.blog.entity.vo;

import java.io.Serializable;


import lombok.Data;

@Data
public class UserRole implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long userId;
	
	private Integer roleId;
}
