package cn.xhxc.blog.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

	@RequestMapping("/login")
	public String login() {
		return "/web/login.html";
	}
	
	@RequestMapping("/")
	public String index() {
		return "/index.html";
	}
	
	@RequestMapping("/login/error")
	public String error() {
		return "/web/error.html";
	}
	
	@GetMapping("/login/timeout")
	public String timeOut() {
		System.out.println("调用方法");
		return "/web/test.html";
	}
}
