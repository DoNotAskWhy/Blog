package cn.xhxc.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xhxc.blog.dao.UserRoleMapper;
import cn.xhxc.blog.entity.vo.UserRole;
import cn.xhxc.blog.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public List<UserRole> getByUserId(Long id) {
		List<UserRole> userRoles = userRoleMapper.listByUserId(id);
		return userRoles;
	}

}
