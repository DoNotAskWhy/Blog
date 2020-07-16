package cn.xhxc.blog.core;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import com.alibaba.druid.support.logging.Log;

public class CustomIDGenerator extends IdentityGenerator{
	

	@Override
	public Serializable generate(SharedSessionContractImplementor s, Object obj) {
		return getId();
	}
	
	private Long getId() {
		synchronized (CustomIDGenerator.class) {
			SimpleDateFormat simpleDateFormat;
	        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	        Date date = new Date();
	        String str = simpleDateFormat.format(date);
	        Random random = new Random();
	        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
	        return  Long.valueOf(str+ rannum);// 当前时间+随机数
		}
	}
}
