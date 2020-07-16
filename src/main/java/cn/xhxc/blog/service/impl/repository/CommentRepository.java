package cn.xhxc.blog.service.impl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.xhxc.blog.entity.Article;
import cn.xhxc.blog.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{
	
	
    Integer countByArticle(Article Article);
    
    List<Comment> findByPidAndArticle(Long pid,Article article);
    
    List<Comment> findByPid(Long pid);
    
    List<Comment> findByPidAndArticleIsNull(Long pid);
    
    List<Comment> findByArticleIsNull();
    
    List<Comment> findByArticleIsNotNull();
}
