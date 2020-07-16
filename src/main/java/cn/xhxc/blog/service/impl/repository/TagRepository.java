package cn.xhxc.blog.service.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.xhxc.blog.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer>{

}
