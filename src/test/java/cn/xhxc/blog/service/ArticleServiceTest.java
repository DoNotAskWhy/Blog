package cn.xhxc.blog.service;

import static org.assertj.core.api.Assertions.in;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;

import cn.xhxc.blog.entity.Article;
import cn.xhxc.blog.entity.Sort;
import cn.xhxc.blog.entity.User;
import cn.xhxc.blog.service.impl.repository.ArticleRepository;
import cn.xhxc.blog.service.impl.repository.SortRepository;
import sun.net.www.content.text.plain;

@SpringBootTest
public class ArticleServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private SortRepository sortRepository;
	
	@Autowired
	private EntityManager em;
	
	@Test
	public void test() {
		User user = userService.getById(2020041510112196592L);
		Integer year = 2020;
		Integer month = 5;
		List<Article> articles = articleRepository.findByYearAndMonthAndUser(year, month,user);
//		List<Article> articles = articleRepository.findByYear(year);
		System.out.println(articles.size());
		articles.forEach(e -> System.out.println(e));
	}
	
	@Test
	public void name() {
		User user = userService.getById(2020041510112196592L);
		String a = "%d%";
		List<Article> articles = articleRepository.findBytextContentLikeAndUser(a, user);
		System.out.println(articles.size());
		articles.forEach(e -> System.out.println(e));
	}
	
	@Test
	public void test1() {
		User user = userService.getById(2020041510112196592L);
		Sort sort = sortRepository.findById(Integer.valueOf(1)).get();
		String like = "%测%";
//		List<Article> articles = articleRepository.findBySortsAndUser(sort, user);
		List<Article> articles = articleRepository.findBytextContentLikeAndSortsAndUser(like,sort,user);
		articles.forEach(e -> System.out.println(e));
	}
	
	@Test
	public void test2() {
		User user = userService.getById(2020041510112196592L);
		String like = "%测试%";
//		List<Article> articles = articleRepository.findBySortsAndUser(sort, user);
		List<Article> articles = articleRepository.findByYearAndtextContentLikeAndUser(2020, like, user);
		articles.forEach(e -> System.out.println(e));
	}
	
	@Test
	public void test3() {
		System.out.println("sdhfoi");
	}
}
