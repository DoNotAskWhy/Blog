package cn.xhxc.blog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import cn.xhxc.blog.entity.vo.UserRole;

@Mapper
public interface UserRoleMapper {
	@Select("SELECT * FROM user_role WHERE user_id = #{userId}")
    List<UserRole> listByUserId(Long userId);
}
