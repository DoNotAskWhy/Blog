package cn.xhxc.blog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import cn.xhxc.blog.entity.vo.ArUserVO;;

@Mapper
public interface ArUserVOMapper {
	
	@Insert("insert into great(id,aid, uid) values(#{id},#{aid}, #{uid})")
    Integer save(ArUserVO arUserVO);
	
	@Select("select * from great where aid=#{aid}")
	List<ArUserVO> select(String aid);
	
	@Delete("delete from great where aid = #{aid} and uid = #{uid}")
	Integer delete(String aid , Long uid);
	
	@Select("select count(*) from great where aid = #{aid} and uid = #{uid}")
	Integer count(String aid ,Long uid);
}
