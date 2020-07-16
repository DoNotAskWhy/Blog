package cn.xhxc.blog.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.xhxc.blog.entity.Article;
import cn.xhxc.blog.entity.Sort;
import cn.xhxc.blog.entity.Tag;
import cn.xhxc.blog.entity.vo.LikeArt;
import cn.xhxc.blog.service.ArticleService;
import cn.xhxc.blog.service.UserService;
import cn.xhxc.blog.util.JsonResult;

@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController{
	
	@Autowired
	private ArticleService articleService;
	
	
	@RequestMapping("editorWeb")
	public JsonResult<Article> get(@RequestBody Article article) {
		System.out.println("编辑器");
//		System.out.println(tags.fo);
		Long id = getUserId();
		System.out.println(article);
		Article a = articleService.create(id,article);
		return new JsonResult<>(SUSESS,a);
	}
	
	@RequestMapping("/getAllsArticle")
	public JsonResult<List<Article>> getAllsArticle(){
		List<Article> articles = articleService.getAllArticles();
		return new JsonResult<>(SUSESS,articles);
	}
	
	@RequestMapping("/getAllOpen")
	public JsonResult<List<Article>> getAllOpen() {
		List<Article> articles = articleService.getAllOpen(getUser());
		return new JsonResult<>(SUSESS,articles);
	}
	
	@RequestMapping("/getAllPrivate")
	public JsonResult<List<Article>> getAllPrivate() {
		List<Article> articles = articleService.getAllPrivate(getUser());
		return new JsonResult<>(SUSESS,articles);
	}
	
	@RequestMapping("/getAllDraft")
	public JsonResult<List<Article>> getAllDraft() {
		List<Article> articles = articleService.getAllDraft(getUser());
		return new JsonResult<>(SUSESS,articles);
	}
	
	@RequestMapping("/getAllYears")
	public JsonResult<Set<String>> getAllYears(){
		Set<String> years = articleService.getAllDate(getUser());
		years.forEach(e -> System.err.println(e));
		return new JsonResult<>(SUSESS,years);
	}
	
	@RequestMapping("/getAllSorts")
	public JsonResult<Set<Sort>> getAllSorts(){
		Set<Sort> sorts = articleService.getAllSorts(getUserId());
		return new JsonResult<>(SUSESS,sorts);
	}
	
	@RequestMapping("/getUserAeticle")
	public JsonResult<List<Article>> getUserAeticle(){
		List<Article> articles = articleService.getUserArticles(getUser());
		return new JsonResult<>(SUSESS,articles);
	}
	
	@RequestMapping("/getMaxByfabulousSize")
	public JsonResult<Article> getMaxByfabulousSize(){
		Article article = articleService.getMaxByfabulousSize();
		return new JsonResult<>(SUSESS,article);
	}
	
	@RequestMapping("/getAeticleLimit")
	public JsonResult<List<Article>> getAeticleLimit(){
		List<Article> article = articleService.getByArticles(0,5);
		return new JsonResult<>(SUSESS,article);
	}
	
	@RequestMapping("/addFabulous")
	public JsonResult<Void> addFabulous(String aid){
		articleService.addFabulous(aid,getUserId());
		return new JsonResult<>(SUSESS);
	}
	
	@RequestMapping("/search")
	public JsonResult<List<Article>> search(LikeArt likeArt){
		System.out.println(likeArt);
		System.out.println("".equals(likeArt.getLike()));
		List<Article> articles = articleService.search(likeArt,getUser());
		return new JsonResult<>(SUSESS,articles);
	}
	
	@RequestMapping("/details/{aid}")
	public JsonResult<Article> getArticle(
			@PathVariable("aid") String id){
		Article article = articleService.getArticle(id);
		return new JsonResult<>(SUSESS,article);
	}
	
	@RequestMapping("changeArticle")
	public JsonResult<Void> changeArticle(
//			String aid,String title,String content,String textContent,
//			Integer form,List<Tag> tags,List<Sort> sorts
			@RequestBody Article article) {
//		System.out.println(article.getAId());
//		article.getTags().forEach(e -> System.out.println(e));
		articleService.changeArticle(article);
		return new JsonResult<>(SUSESS);
	}
	
	@RequestMapping("/delete")
	public JsonResult<Void> deleteArticle(
			@RequestParam("aid") String id){
		articleService.delete(id);
		return new JsonResult<>(SUSESS);
	}
			
}
