package cn.xhxc.blog.service.impl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.xhxc.blog.entity.Article;
import cn.xhxc.blog.entity.Sort;
import cn.xhxc.blog.entity.User;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String>{
	
	List<Article> findByForm(Integer form);
	
	
	List<Article> findByUser(User user);
	
	@Query("select date from Article a where a.user = ?1")
	List<Date> findByDates(User user);
	
	@Query("FROM Article a WHERE YEAR(a.date)= ?1 AND a.user=?2")
	List<Article> findByYear(Integer year,User user);
	
	List<Article> findBytextContentLikeAndSortsAndUser(String like,Sort sort,User user);
	
	List<Article> findBySortsAndUser(Sort sort,User user);
	
	@Query("FROM Article a WHERE YEAR(a.date)= ?1 AND MONTH(a.date)= ?2 AND a.user= ?3")
	List<Article> findByYearAndMonthAndUser(Integer year,Integer month,User user);
	
	@Query("FROM Article a WHERE YEAR(a.date)= ?1 AND a.textContent like ?2 AND a.user= ?3")
	List<Article> findByYearAndtextContentLikeAndUser(Integer year,String like ,User user);
	
	List<Article> findBytextContentLikeAndUser(String like,User user);
	
	List<Article> findByFormAndUser(Integer form,User user);
	
	Integer countByFormAndUser(Integer form ,User user);
	
//	@Query("SELECT a FROM Article a WHERE fabulousSize = (SELECT MAX(a.fabulousSize) FROM Article a)")
//	Article maxByfabulousSize();
	
	@Query("SELECT a from Article a where a.form = ?1 ORDER BY a.fabulousSize DESC ")
	List<Article> findByFormOredrByfabulousSizeDesc(Integer form);
	
	
}
