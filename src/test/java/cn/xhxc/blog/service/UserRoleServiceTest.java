package cn.xhxc.blog.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.xhxc.blog.entity.Role;
import cn.xhxc.blog.entity.User;
import cn.xhxc.blog.entity.vo.UserRole;
import cn.xhxc.blog.service.impl.repository.UserRepository;;

@SpringBootTest
public class UserRoleServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Test
	public void a() {
		List<UserRole> userRoles =  userRoleService.getByUserId(1L);
		userRoles.forEach(e -> System.out.println(e));
	}

}
