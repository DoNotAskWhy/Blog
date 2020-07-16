package cn.xhxc.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.xhxc.blog.entity.Role;
import cn.xhxc.blog.service.RoleService;
import cn.xhxc.blog.service.impl.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role create() {
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		Role role2 = roleRepository.save(role);
		Role role1 = new Role();
		role1.setName("ROLE_USER");
		roleRepository.save(role1);
		return role2;
	}

	@Override
	public Role changeRole(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> getAllRoles() {
		
		return roleRepository.findAll();
	}

	@Override
	public Role getById(Integer id) {
		return roleRepository.findById(id).get();
	}

}
