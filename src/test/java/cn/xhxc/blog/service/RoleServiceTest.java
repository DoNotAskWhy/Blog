package cn.xhxc.blog.service;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.xhxc.blog.entity.Role;
import cn.xhxc.blog.service.impl.repository.RoleRepository;

@SpringBootTest
public class RoleServiceTest {

	@Autowired
	private RoleRepository rolerepository;
	
	@Test
	public void a() {
		Optional<Role> role = rolerepository.findById(1);
		if (role !=null && role.isPresent()) {
			System.out.println(role.get());
		}
		System.out.println(role);
	}
}
