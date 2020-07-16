package cn.xhxc.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cn.xhxc.blog.controller.ex.*;
import cn.xhxc.blog.entity.User;
import cn.xhxc.blog.service.UserService;
import cn.xhxc.blog.service.ex.*;
import cn.xhxc.blog.util.JsonResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseController {
	
	@Autowired
	private UserService userService;
	
	public static final int SUSESS = 2000;

	@ExceptionHandler({ ServiceException.class, FileUploadEmpException.class })
	public JsonResult<Void> handleException(Throwable e) {
		JsonResult<Void> jr = new JsonResult<>(e);

		if (e instanceof UsernameConflictException) {
			// 4000-用户名冲突异常，例如：注册时用户名已经被占用
			jr.setState(4000);
		} else if (e instanceof UserNotFoundException) {
			// 4001-用户数据不存在
			jr.setState(4001);
		} else if (e instanceof PasswordNotMatchException) {
			// 4002-验证密码失败
			jr.setState(4002);
		} else if (e instanceof CodeTimeoutException) {
			// 4003-验证码超时
			jr.setState(4003);
		} else if (e instanceof CodeErrorException) {
			// 4004-验证码错误
			jr.setState(4004);
		} else if (e instanceof CodeNullException) {
			// 4005-验证码未获取
			jr.setState(4005);
		} else if (e instanceof NickNameTooLong) {
			jr.setState(4006);
		}
		return jr;
	}
//	@RequestMapping("/")
//    public String showHome() {
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        log.info("当前登陆用户：" + name);
//
//        return "index.html";
//    }
//	
//	@RequestMapping("/login")
//	public String a() {
//		return "login.html";
//	}

	public Long getUserId() {
//      //获得session对象
//      HttpSession session = request.getSession();
//      //取出session域中所有属性名
//      Enumeration attributeNames = session.getAttributeNames();
//      while (attributeNames.hasMoreElements()) {
//          System.out.println("属性名" + attributeNames.nextElement());
//      }
//      //SPRING_SECURITY_CONTEXT
//      Object spring_security_context = session.getAttribute("SPRING_SECURITY_CONTEXT");
//      System.out.println("账号信息" + spring_security_context);
//      SecurityContext securityContext = (SecurityContext) spring_security_context;
//      //获得认证信息
//      Authentication authentication = securityContext.getAuthentication();
//      //获得用户详情
//      Object principal = authentication.getPrincipal();
//      org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) principal;
//      String username = user.getUsername();
//      System.out.println("用户1" + username);

		// 第二种方法
		// 获取上下栈
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication1 = context.getAuthentication();
//		System.out.println("权限" + authentication1);
//		System.out.println(authentication1.getAuthorities());
//		System.err.println(authentication1.isAuthenticated());
		if (authentication1.getName().
				  equals("anonymousUser")) {
				  System.out.println("NOT LOGGED IN");
				  return null;
		} 
//		User user = (User) authentication1.getPrincipal();
//		System.out.println(user1);
//		String username1 = user.getUsername();
//		System.out.println(user.getId());
//		System.out.println("用户2" + username1);
		System.out.println(((User) authentication1.getPrincipal()).getId());
		return ((User) authentication1.getPrincipal()).getId();
	}
	
	public User getUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication1 = context.getAuthentication();
		if (authentication1.getName().
				  equals("anonymousUser")) {
				  System.out.println("NOT LOGGED IN");
				  return null;
		} 
		return (User) authentication1.getPrincipal();
	}
	
}
