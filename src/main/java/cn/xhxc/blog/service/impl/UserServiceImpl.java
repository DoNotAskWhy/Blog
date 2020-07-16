package cn.xhxc.blog.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import cn.xhxc.blog.entity.Role;
import cn.xhxc.blog.entity.User;
import cn.xhxc.blog.entity.vo.UserRole;
import cn.xhxc.blog.mail.IMailService;
import cn.xhxc.blog.service.UserService;
import cn.xhxc.blog.service.ex.CodeErrorException;
import cn.xhxc.blog.service.ex.CodeNullException;
import cn.xhxc.blog.service.ex.CodeTimeoutException;
import cn.xhxc.blog.service.ex.NickNameTooLong;
import cn.xhxc.blog.service.ex.UserNotFoundException;
import cn.xhxc.blog.service.ex.UsernameConflictException;
import cn.xhxc.blog.service.impl.repository.RoleRepository;
import cn.xhxc.blog.service.impl.repository.UserRepository;
import cn.xhxc.blog.util.NickName;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private IMailService mailService;
	
	@Bean("bCryptPasswordEncoder")
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	验证码
	private String checkCode;
	
	private Date date;
	
	@Override
	public User create(User user,String code) {
		System.out.println("账号注册"+ user);
		if (getByName(user.getUsername()) != null) {
			throw new UsernameConflictException("该账号已注册");
		}
		if (checkCode == null) {
			throw new CodeNullException("验证码未获取,请获取验证码");
		}
		Date nowDate = new Date();
		System.err.println(nowDate.getTime() - date.getTime());
		if (nowDate.getTime() - date.getTime() >= 60000 ) {
			throw new CodeTimeoutException("验证码超时,请重新获取验证码");
		}
		
		System.err.println("本地 " + checkCode + "外部 " + code);
		if (!checkCode.equals(code)) {
			throw new CodeErrorException("验证码错误");
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setDate(date);
		user.setNickName(NickName.generateName());
		user.setAvatar("/img/favicon.png");
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setEnabled(true);
		user.setCredentialsNonExpired(true);
		Role role = roleRepository.findById(2).get();
		System.err.println(role);
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		user.setRoles(roles);
		User users = userRepository.saveAndFlush(user);
		System.out.println("注册成功" + users);
		return user;
	}

	
	@Override
	public User update(Long id) {
		User user = getById(id);
//		System.out.println(user);
		
		List<Role> roles = user.getRoles();
		Iterator<Role> iterator = roles.iterator();
		while (iterator.hasNext()) {
			if ("ROLE_USER".equals(iterator.next().getName())) {
				System.out.println("删除方法");
				iterator.remove();
			}
		}
//		System.err.println(user);
		
		return userRepository.saveAndFlush(user);
	}

	@Override
	public void deleteId(Long id) {
		userRepository.deleteById(id);
		System.out.println("删除成功");
	}

	
	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		users = userRepository.findAll();
		return users;
	}

	@Override
	public User getByName(String name) {
		User user = null;
//		System.out.println(name);
		List<User> users = userRepository.findByUsername(name);
//		System.out.println(users);
		if (!users.isEmpty()) {
			user = users.get(0);
		}
		
		return user;
	}

	@Override
	public User getById(Long id) {
		return userRepository.findById(id).get();
	}


	@Override
	public String getCheckCode(String email) {
		checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的注册验证码为："+checkCode;
        try {
            mailService.sendSimpleMail(email, "注册验证码", message);
        }catch (Exception e){
            return "";
        }
        date = new Date();
        System.out.println("验证码发送成功");
		return checkCode;
	}


	@Override
	public void updatePassword(String username,String password,String code) {
		System.out.println("密码修改");
		User user = getByName(username);
		if (user == null) {
			throw new UserNotFoundException("该账号不存在");
		}
		if (checkCode == null) {
			throw new CodeNullException("验证码未获取,请获取验证码");
		}
		Date nowDate = new Date();
		System.err.println(nowDate.getTime() - date.getTime());
		if (nowDate.getTime() - date.getTime() >= 60000 ) {
			throw new CodeTimeoutException("验证码超时,请重新获取验证码");
		}
		
		System.err.println("本地 " + checkCode + "外部 " + code);
		if (!checkCode.equals(code)) {
			throw new CodeErrorException("验证码错误");
		}
		user.setPassword(bCryptPasswordEncoder.encode(password));
		user.setDate(date);
		User users = userRepository.saveAndFlush(user);
		System.out.println("密码修改成功" + users);
	}


	@Override
	public void updatePassword(String password) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateAvatar(String avatar,Long id) {
		System.out.println(id);
		System.out.println(avatar);
		User user = userRepository.findById(id).get();
		user.setAvatar(avatar);
		userRepository.saveAndFlush(user);
		System.out.println("修改成功");
		
	}


	@Override
	public void updataInformation(User user, String nickName, String synopsis) {
		if (nickName.length() > 5) {
			throw new NickNameTooLong("昵称过长");
		}
		user.setNickName(nickName);
		
		user.setSynopsis(synopsis);
		userRepository.saveAndFlush(user);
	}


	@Override
	public void deleteU(Long id) {
		User user = userRepository.findById(id).get();
		user.setEnabled(false);
		userRepository.save(user);
		log.info("删除成功");
	}


	@Override
	public List<User> getAdminUser() {
		List<Role> roles  = roleRepository.findByName("ROLE_ADMIN");
		List<User> users = userRepository.findAllUsersByRolesIn(roles); 
		return users;
	}


	
	
}
