package cn.xhxc.blog.service;

import java.util.List;

import cn.xhxc.blog.entity.vo.UserRole;

public interface UserRoleService {
	
	List<UserRole> getByUserId(Long id);
}
