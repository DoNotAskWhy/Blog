package cn.xhxc.blog.service;

import java.util.List;


import cn.xhxc.blog.entity.Role;
import cn.xhxc.blog.entity.User;


public interface UserService {
	User create(User user,String code);
	User update(Long id);
	void updatePassword(String username,String password,String code);
	void updatePassword(String password);
	void updateAvatar(String avatar,Long id);
	void updataInformation(User user, String nickName, String synopsis);
	void deleteId(Long id);
	void deleteU(Long id);
	List<User> getAllUsers();
	User getByName(String name);
	User getById(Long id);
	String getCheckCode(String email);
	List<User> getAdminUser();
	
}
