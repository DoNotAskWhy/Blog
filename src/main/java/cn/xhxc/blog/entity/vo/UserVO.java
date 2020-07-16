package cn.xhxc.blog.entity.vo;

import lombok.Data;

@Data
public class UserVO {
	
	private Long userId;

	private String avatar;
	
	private String username;

	public UserVO(Long userId, String avatar, String username) {
		super();
		this.userId = userId;
		this.avatar = avatar;
		this.username = username;
	}
	
	
}
