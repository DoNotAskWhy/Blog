package cn.xhxc.blog.service;

import java.util.List;

import cn.xhxc.blog.entity.Role;

public interface RoleService {
	Role create();
	Role changeRole(String name);
	List<Role> getAllRoles();
	Role getById(Integer id);
}
