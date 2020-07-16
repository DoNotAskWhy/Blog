package cn.xhxc.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.xhxc.blog.entity.Role;
import cn.xhxc.blog.service.RoleService;
import cn.xhxc.blog.util.JsonResult;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController{
	
	@Autowired
	private RoleService roleService;

	@RequestMapping("create")
	public JsonResult<Role> create() {
		Role role = roleService.create();
		return new JsonResult<>(null,role);
	}
	
	@RequestMapping("select")
	public JsonResult<List<Role>> select(){
		List<Role> roles = roleService.getAllRoles();
		return new JsonResult<>(SUSESS,roles);
		
	}
}
