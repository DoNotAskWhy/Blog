package cn.xhxc.blog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.xhxc.blog.core.CustomIDGenerator;
import cn.xhxc.blog.dao.ArUserVOMapper;
import cn.xhxc.blog.entity.vo.ArUserVO;

@SpringBootTest
class BlogApplicationTests {
	
	@Autowired
	private ArUserVOMapper arUserVOMapper;

	@Test
	void contextLoads() {
		List<ArUserVO> arUserVOs = arUserVOMapper.select("1");
		System.out.println(arUserVOs.size());
	}

}
