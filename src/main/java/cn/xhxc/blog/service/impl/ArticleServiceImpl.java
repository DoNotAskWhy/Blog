package cn.xhxc.blog.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.xhxc.blog.dao.ArUserVOMapper;
import cn.xhxc.blog.entity.Article;
import cn.xhxc.blog.entity.Sort;
import cn.xhxc.blog.entity.Tag;
import cn.xhxc.blog.entity.User;
import cn.xhxc.blog.entity.vo.ArUserVO;
import cn.xhxc.blog.entity.vo.LikeArt;
import cn.xhxc.blog.service.ArticleService;
import cn.xhxc.blog.service.impl.repository.ArticleRepository;
import cn.xhxc.blog.service.impl.repository.SortRepository;
import cn.xhxc.blog.service.impl.repository.TagRepository;
import cn.xhxc.blog.service.impl.repository.UserRepository;
import cn.xhxc.blog.util.Html2PlainText;
import cn.xhxc.blog.util.Markdown2Html;
import lombok.Data;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private EntityManager em;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private SortRepository sortRepository;
	
	@Autowired
	private ArUserVOMapper arUserVOMapper;

	@Override
	public Article create(Long id, Article article) {
		User user = userRepository.findById(id).get();
		Date date = new Date();
		List<Tag> tags = article.getTags();
		List<Sort> sorts = article.getSorts();
		if (tags != null) {
			for (Tag tag : tags) {
				tag.setDate(date);
				tagRepository.save(tag);
			}
		}
		if (sorts != null) {
			for (Sort sort : article.getSorts()) {
				sort.setDate(date);
				sortRepository.save(sort);
			}
		}
		article.setAuthor(user.getNickName());
		article.setDate(date);
		article.setUser(user);
		Article a = articleRepository.saveAndFlush(article);
		user.getArticles().add(a);
		return a;
	}

	@Override
	public List<Article> getAllArticles() {
		List<Article> articles = articleRepository.findByFormOredrByfabulousSizeDesc(0);
		for (Article article : articles) {
			article.setTextContent(Html2PlainText.convert(Markdown2Html.convert(article.getTextContent())));
		}
		return articles;
	}

	@Override
	public Article getArticle(String id) {
		Article article = articleRepository.findById(id).get();
		return article;
	}

	@Override
	public List<Article> getUserArticles(User user) {
		List<Article> articles = articleRepository.findByUser(user);
		return articles;
	}

	@Override
	public void delete(String id) {
		articleRepository.deleteById(id);
		System.out.println("删除成功");
	}

	@Override
	public Set<String> getAllDate(User user) {
		Set<String> years = new LinkedHashSet<>();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		List<Date> dates = articleRepository.findByDates(user);
		for (Date date : dates) {
			cal.setTime(date);
			years.add(String.valueOf(cal.get(Calendar.YEAR)));
		}
		return years;
	}

	@Override
	public Set<Sort> getAllSorts(Long id) {
		User user = userRepository.findById(id).get();
		List<Article> articles = user.getArticles();
		Set<Sort> sorts = new LinkedHashSet<>();
		for (Article article : articles) {
			for (Sort sort : article.getSorts()) {
				sorts.add(sort);
			}
		}
		return sorts;
	}

	@Override
	public List<Article> search(LikeArt likeArt, User user) {
		if (!("".equals(likeArt.getLike()))) {
			likeArt.setLike("%" +likeArt.getLike()+"%");
		}
		Sort sort = null;
		List<Article> search = new ArrayList<>();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		if ("0".equals(likeArt.getYear()) && likeArt.getSort() == 0 && "".equals(likeArt.getLike())) {
			return null;
		}
//		年，月，分类，模糊
		if (likeArt.getYear() != 0 && likeArt.getMonth() != 0 && likeArt.getSort() != 0
				&& !("".equals(likeArt.getLike()))) {
			sort = sortRepository.findById(likeArt.getSort()).get();
			List<Article> articles = articleRepository.findBytextContentLikeAndSortsAndUser(likeArt.getLike(), sort,
					user);
			for (Article article : articles) {
				cal.setTime(article.getDate());
				if (cal.get(Calendar.YEAR) == likeArt.getYear() && cal.get(Calendar.MONTH)+1 == likeArt.getMonth()) {
					search.add(article);
				}
			}
			
			return search;
		}
//		年，月，分类
		if (likeArt.getYear() != 0 && likeArt.getMonth() != 0 && likeArt.getSort() != 0) {
			System.out.println("年月分类");
			sort = sortRepository.findById(likeArt.getSort()).get();
			List<Article> articles = articleRepository.findBySortsAndUser(sort, user);
			System.out.println("ch" + articles.size());
			for (Article article : articles) {
				cal.setTime(article.getDate());
				System.out.println(cal.get(Calendar.YEAR) == likeArt.getYear());
				System.out.println(cal.get(Calendar.MONTH));
				if (cal.get(Calendar.YEAR) == likeArt.getYear() && cal.get(Calendar.MONTH)+1 == likeArt.getMonth()) {
					System.out.println("nian");
					search.add(article);
				}
			}
			System.out.println(search.size());
			return search;
		}
//		年，月，模糊
		if (likeArt.getYear() != 0 && likeArt.getMonth() != 0 && !("".equals(likeArt.getLike()))) {
			sort = sortRepository.findById(likeArt.getSort()).get();
			List<Article> articles = articleRepository.findBytextContentLikeAndUser(likeArt.getLike(), user);
			for (Article article : articles) {
				cal.setTime(article.getDate());
				if (cal.get(Calendar.YEAR) == likeArt.getYear() && cal.get(Calendar.MONTH)+1 == likeArt.getMonth()) {
					search.add(article);
				}
			}
			return search;
		}
//		年，分类，模糊
		if (likeArt.getYear() != 0 && likeArt.getSort() != 0 && !("".equals(likeArt.getLike()))) {
			sort = sortRepository.findById(likeArt.getSort()).get();
			List<Article> articles = articleRepository.findBytextContentLikeAndSortsAndUser(likeArt.getLike(), sort, user);
			for (Article article : articles) {
				cal.setTime(article.getDate());
				if (cal.get(Calendar.YEAR) == likeArt.getYear()) {
					search.add(article);
				}
			}
			return search;
		}
//		年，月
		if (likeArt.getYear() != 0 && likeArt.getMonth() != 0) {
			List<Article> articles = articleRepository.findByYearAndMonthAndUser(likeArt.getYear(), likeArt.getMonth(), user);
			return articles;
		}
//		年，分类
		if (likeArt.getYear() != 0 && likeArt.getSort() != 0 ) {
			System.out.println("年分类");
			sort = sortRepository.findById(likeArt.getSort()).get();
			List<Article> articles = articleRepository.findBySortsAndUser(sort, user);
			for (Article article : articles) {
				cal.setTime(article.getDate());
				if (cal.get(Calendar.YEAR) == likeArt.getYear()) {
					search.add(article);
				}
			}
			return search;
		}
//		年，模糊
		if (likeArt.getYear() !=0 && !("".equals(likeArt.getLike()))) {
			List<Article> articles = articleRepository.findByYearAndtextContentLikeAndUser(likeArt.getYear(), likeArt.getLike(), user);
			return articles;
		}
		if (likeArt.getYear() !=0) {
			List<Article> articles = articleRepository.findByYear(likeArt.getYear(), user);
			return articles;
		}
		if (likeArt.getSort() != 0) {
			sort = sortRepository.findById(likeArt.getSort()).get();
			List<Article> articles = articleRepository.findBySortsAndUser(sort, user);
			return articles;
		}
		if (!("".equals(likeArt.getLike()))) {
			List<Article> articles = articleRepository.findBytextContentLikeAndUser(likeArt.getLike(),user);
			return articles;
		}
		return search;
	}

	@Override
	public List<Article> getAllOpen(User user) {
		List<Article> articles = articleRepository.findByFormAndUser(0,user);
		return articles;
	}

	@Override
	public List<Article> getAllPrivate(User user) {
		List<Article> articles = articleRepository.findByFormAndUser(1,user);
		return articles;
	}

	@Override
	public List<Article> getAllDraft(User user) {
		List<Article> articles = articleRepository.findByFormAndUser(2,user);
		return articles;
	}

	@Override
	public void addFabulous(String aid ,Long uid) {
		Article article = articleRepository.findById(aid).get();
		List<ArUserVO> arUserVOs = arUserVOMapper.select(aid);
		if (arUserVOs.size() == 0) {
			arUserVOMapper.save(new ArUserVO(aid, uid));
			article.setFabulousSize(article.getFabulousSize() + 1);
		}else {
			for (int i = 0; i < arUserVOs.size(); i++) {
				if (arUserVOs.get(i).getUid().equals(uid)){
					arUserVOMapper.delete(aid, uid);
					article.setFabulousSize(article.getFabulousSize() - 1);
				}else {
					arUserVOMapper.save(new ArUserVO(aid, uid));
					article.setFabulousSize(article.getFabulousSize() + 1);
				}
			}
		}
		
		System.out.println("点赞数"+ article.getFabulousSize());
		articleRepository.saveAndFlush(article);
	}

	@Override
	public Article getMaxByfabulousSize() {
		Query query = em.createQuery("SELECT MAX(a.fabulousSize) FROM Article a");
		Object result = query.getSingleResult();
		Query query1 = em.createQuery("SELECT a FROM Article a WHERE fabulousSize = ?1");
		query1.setParameter(1, result);
		Object result1 = query1.getSingleResult();
		Article article = (Article) result1;
		article.setTextContent(Html2PlainText.convert(Markdown2Html.convert(article.getTextContent())));
		return article;
	}

	@Override
	public List<Article> getByArticles(Integer page, Integer size) {
		org.springframework.data.domain.Sort.Direction sort =  org.springframework.data.domain.Sort.Direction.DESC;
		///PageRequest继承于AbstractPageRequest并且实现了Pageable
		///获取PageRequest对象 index:页码 从0开始  size每页容量 sort排序方式 "id"->properties 以谁为准排序
		Pageable pageable = PageRequest.of(page, size, sort, "fabulousSize");
		///要匹配的实例对象
		Article a = new Article();
		a.setForm(0);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("form",GenericPropertyMatchers.startsWith());
		Example<Article> example = Example.of(a,exampleMatcher);
//		Page<Article> page1 = articleRepository.findAll(example,pageable);
		Page<Article> page1 = articleRepository.findAll(example,pageable);
		
		for (Article article : page1.getContent()) {
			article.setTextContent(Html2PlainText.convert(Markdown2Html.convert(article.getTextContent())));
		}
		return page1.getContent();
	}

	@Override
	public void changeArticle(Article article) {
		Article articleq = articleRepository.findById(article.getAId()).get();
		articleq.setTitle(article.getTitle());
		articleq.setTextContent(article.getTextContent());
		articleq.setForm(article.getForm());
		Date date = new Date();
		List<Tag> tags = article.getTags();
		List<Sort> sorts = article.getSorts();
		if (tags != null) {
			for (Tag tag : tags) {
				tag.setDate(date);
				tagRepository.save(tag);
			}
		}
		if (sorts != null) {
			for (Sort sort : article.getSorts()) {
				sort.setDate(date);
				sortRepository.save(sort);
			}
		}
		
		articleq.setTags(tags);
		articleq.setSorts(sorts);
		articleRepository.saveAndFlush(articleq);
		System.out.println("修改成功");
		
	}

}
;