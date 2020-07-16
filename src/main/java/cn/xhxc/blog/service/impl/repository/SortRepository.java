package cn.xhxc.blog.service.impl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.xhxc.blog.entity.Sort;

@Repository
public interface SortRepository extends JpaRepository<Sort,Integer>{
	
	
}
