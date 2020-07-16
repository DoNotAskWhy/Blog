package cn.xhxc.blog.service;

import java.util.List;

import cn.xhxc.blog.entity.Article;
import cn.xhxc.blog.entity.Comment;

public interface CommentService {
	
	void removeComment(Long id);
	
	Integer countCommentSizeByArticle(Article article);
	
	Comment getCommentById(Long id);
	
	Comment createComment(String articleId, String commentContent);

	Comment replyComment(String articleId, Long commentId, Long replyId, String commentContent);
	
	void updataCommentSize(String articleId);
	
	void deleteComment(Long id);

	List<Comment> getAllCommentByArticle(String aid);

	List<Comment> getByArticleIsNotNull();

	List<Comment> getByArticleIsNull();

}
