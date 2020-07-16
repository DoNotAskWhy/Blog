package cn.xhxc.blog.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import cn.xhxc.blog.entity.Role;
import cn.xhxc.blog.entity.User;
import cn.xhxc.blog.service.impl.repository.RoleRepository;
import cn.xhxc.blog.service.impl.repository.UserRepository;;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private RoleRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	@Qualifier("bCryptPasswordEncoder")
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Test
	public void a() {
		String a = "http://localhost:8080/file/change_avatar";
		System.out.println(a.substring(0,a.indexOf("/", 7))+"/upload/"+"1.jpg");
		System.out.println(a.indexOf("/", 7));
		System.out.println(a.lastIndexOf("/"));
	}

	@Test
	public void b() {
		List<Role> roles  = repository.findAll();
		List<User> users = userRepository.findAllUsersByRolesIn(roles); 
		users.forEach(e -> System.out.println(e.getUsername()));
	}
}
