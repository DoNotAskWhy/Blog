package cn.xhxc.blog.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;

import cn.xhxc.blog.entity.Article;
import cn.xhxc.blog.entity.Sort;
import cn.xhxc.blog.entity.Tag;
import cn.xhxc.blog.entity.User;
import cn.xhxc.blog.entity.vo.LikeArt;

public interface ArticleService {

	Article create(Long id ,Article article);
	
	List<Article> getAllArticles();
	
	List<Article> getUserArticles(User user);
	
	List<Article> search(LikeArt likeArt,User user);
	
	List<Article> getAllOpen(User user);
	
	List<Article> getAllPrivate(User user);

	List<Article> getAllDraft(User user);
	
	Set<String> getAllDate(User user);
	
	Set<Sort> getAllSorts(Long id);
	
	Article getArticle(String id);
	
	void delete(String id);

	void addFabulous(String aid,Long uid);

	Article getMaxByfabulousSize();

	List<Article> getByArticles(Integer page,Integer size);
	
	void changeArticle(Article article);
	

	

	
}
