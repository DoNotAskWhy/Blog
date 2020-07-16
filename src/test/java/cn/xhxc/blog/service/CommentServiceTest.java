package cn.xhxc.blog.service;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.xhxc.blog.entity.Article;
import cn.xhxc.blog.entity.Comment;
import cn.xhxc.blog.entity.User;
import cn.xhxc.blog.service.impl.repository.ArticleRepository;
import cn.xhxc.blog.service.impl.repository.CommentRepository;

@SpringBootTest
public class CommentServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Test
	public void test() {
		User user = userService.getById(2020041510112196592L);
		
		Article article = articleService.getArticle("4028d78171fcbdad0171fcbfbd090002");
		
		String commentContent = "sflkdjglisdfjlk";
		
		Comment comment = new Comment(user, article, commentContent);
		commentRepository.save(comment);
	}
	
	@Test
	public void test1() {
		
		
		Article article = articleService.getArticle("4028d78171fcbdad0171fcbfbd090002");
		
		Integer i = commentRepository.countByArticle(article);
		System.out.println(i);
	}
	
	@Test
	public void test2() {
		List<Comment> comments = commentRepository.findByArticleIsNotNull();
		comments.forEach(e -> System.out.println(e));
	}
	
}
