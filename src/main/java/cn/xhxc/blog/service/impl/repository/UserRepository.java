package cn.xhxc.blog.service.impl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.xhxc.blog.entity.Role;
import cn.xhxc.blog.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	
	List<User> findByUsername(String username);
	
	List<User> findAllUsersByRolesIn(List<Role> roles);
	
	
}
