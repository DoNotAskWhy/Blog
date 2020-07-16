package cn.xhxc.blog.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cn.xhxc.blog.entity.Role;
import cn.xhxc.blog.entity.User;
//import cn.xhxc.blog.entity.User;
import cn.xhxc.blog.entity.vo.UserRole;
import cn.xhxc.blog.service.RoleService;
import cn.xhxc.blog.service.UserRoleService;
import cn.xhxc.blog.service.UserService;
import cn.xhxc.blog.service.ex.UserNotFoundException;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 从数据库中取出用户信息
		User user = userService.getByName(username);
		System.out.println(user.getNickName());
        // 判断用户是否存在
        if(user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        // 添加权限
//        List<UserRole> userRoles = userRoleService.getByUserId(user.getId());
//        List<Role> roles = new ArrayList<>();
//        userRoles.forEach(e -> System.out.println(e));
//        for (UserRole userRole : userRoles) {
//            Role role = roleService.getById(userRole.getRoleId());
//            roles.add(role);
//            System.out.println(role);
////            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        System.out.println("判断用户权限" + authorities);
        // 返回UserDetails实现类
        System.out.println("执行到这里");
        return user;
//        return new User(user.getUsername(), user.getPassword(), authorities);
	}

}
