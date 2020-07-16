package cn.xhxc.blog.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.xhxc.blog.entity.Role;
import cn.xhxc.blog.entity.User;
import cn.xhxc.blog.entity.vo.UserRole;
import cn.xhxc.blog.service.UserRoleService;
import cn.xhxc.blog.service.UserService;
import cn.xhxc.blog.util.JsonResult;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;

	
	@RequestMapping("/login")
	public JsonResult<User> login(String username,String password){
		System.out.println(username+ " " + password);
		return new JsonResult<>(SUSESS,null);
	}
	
	@GetMapping("/hello")
    public String hello(Principal principal) {
		 Object principal1 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	     User user = (User)principal1;
	     return "当前登录用户信息：" + user.toString();
    }
	
//	@RequestMapping("/reg")
//	public JsonResult<User> create(
//			User user,
//			@RequestParam("c_password") String cPassword,
//			@RequestParam("v_code") String code) {
//		System.out.println(user);
//		userService.create(user,code);
//		return new JsonResult<>(SUSESS);
//	}

	@RequestMapping("/updataRole")
	public JsonResult<User> updateRole(Long id){
		User user = userService.update(id);
		System.out.println(user);
		return new JsonResult<>(SUSESS,user);
	}
	
	@RequestMapping("/updataInformation")
	public JsonResult<Void> updataInformation(String nickName,String synopsis){
		userService.updataInformation(getUser(),nickName,synopsis);
		return new JsonResult<>(SUSESS);
	}
	
	@RequestMapping("/select")
	@PreAuthorize("hasRole('ROLE_USER')")
	public JsonResult<List<User>> select(){
		List<User> users = userService.getAllUsers();
		return new JsonResult<>(SUSESS,users);
	}
	
	
	@RequestMapping("/getName")
	public JsonResult<User> selectByName(String name){
		User user = userService.getByName(name);
		return new JsonResult<>(SUSESS,user);
	}
	
	
	@RequestMapping("/getId")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public JsonResult<User> selectById(Long id){
		User user = userService.getById(id);
		System.out.println(user);
		return new JsonResult<>(SUSESS,user);
	}
	
	@RequestMapping("/getUserRole")
	public JsonResult<List<UserRole>> selectUserRole(Long userId){
		
		return new JsonResult<>(SUSESS,userRoleService.getByUserId(userId));
	}
	
	@RequestMapping("/getAdminUser")
	public JsonResult<List<User>> getAdminUser(){
		List<User> users = userService.getAdminUser();
		return new JsonResult<>(SUSESS,users);
	}
	
	@RequestMapping("/findByPassword")
	public JsonResult<Void> findByPassword(String username, String password,
			@RequestParam("v_code") String code){
		userService.updatePassword(username, password, code);
		return new JsonResult<>(SUSESS);
	}
	
	@RequestMapping("/getCheckCode")
    @ResponseBody
    public JsonResult<Map<String, String>> getCheckCode(String email){
        String checkCode = userService.getCheckCode(email);
        Map<String,String> map = new HashMap<>();
        map.put("code", checkCode);
        return new JsonResult<>(SUSESS,map);
    }
	
	@RequestMapping("/showUsername")
    public JsonResult<User> showUsername() {
		Long id = getUserId();
		if (id == null) {
			return new JsonResult<>(SUSESS,null);
		}
		User user = userService.getById(id);
		return new JsonResult<>(SUSESS,user);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/deleteId")
	public JsonResult<Void> deleteId(Long id){
		userService.deleteId(id);
		return new JsonResult<>(SUSESS);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/deleteU")
	public JsonResult<Void> deleteU(Long id){
		userService.deleteId(id);
		return new JsonResult<>(SUSESS);
	}
	
}
