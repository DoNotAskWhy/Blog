package cn.xhxc.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xhxc.blog.entity.Article;
import cn.xhxc.blog.entity.Comment;
import cn.xhxc.blog.entity.User;
import cn.xhxc.blog.entity.vo.CommentVO;
import cn.xhxc.blog.entity.vo.UserVO;
import cn.xhxc.blog.service.CommentService;
import cn.xhxc.blog.service.impl.repository.ArticleRepository;
import cn.xhxc.blog.service.impl.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private ArticleRepository articleRepository;

	
	@Override
	@Transactional
	public void removeComment(Long id) {
		commentRepository.deleteById(id);
	}

	@Override
	public Integer countCommentSizeByArticle(Article article) {
		return commentRepository.countByArticle(article);
	}

	@Override
	public Comment getCommentById(Long id) {
		return commentRepository.findById(id).get();
	}

	@Override
	public Comment createComment(String articleId, String commentContent) {
		Article article =  null;
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(articleId != null) {
			article = articleRepository.findById(articleId).get();
			Comment comment = new Comment(user, article, commentContent);
			article.getCommentList().add(comment);
			return commentRepository.saveAndFlush(comment);
		}
		Comment comment = new Comment(user, article, commentContent);
		return commentRepository.saveAndFlush(comment);
	}

	@Override
	public Comment replyComment(String articleId, Long commentId, Long replyId, String commentContent) {
		Article article =  null;
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(articleId != null) {
			article = articleRepository.findById(articleId).get();
		}
		
		Comment comment = new Comment(user, article, commentId, commentContent);
		// 评论回复，需要加上@用户
		if (replyId != null) {
			Comment replyComment = commentRepository.findById(replyId).get();
			comment.setReplyUser(replyComment.getUser());
		}
		// 添加评论
		return commentRepository.saveAndFlush(comment);
	}

	@Override
	public void updataCommentSize(String articleId) {
		Article originalArticle = articleRepository.findById(articleId).get();
        originalArticle.setCommentSize(countCommentSizeByArticle(originalArticle));
        articleRepository.saveAndFlush(originalArticle);
	}

	@Override
	public void deleteComment(Long id) {
		Comment originalComment = getCommentById(id);
        //1、先删除子评论
        List<Comment> commentList = commentRepository.findByPid(id);
        if (commentList != null && commentList.size() != 0) {
            for (int i = 0; i < commentList.size(); i++) {
                
                removeComment(commentList.get(i).getId());
            }
        }
        //2、删除该评论
        removeComment(id);
        //3、修改文章的评论数目
        Article originalArticle = originalComment.getArticle();
        if (originalArticle != null) {
        	originalArticle.setCommentSize(countCommentSizeByArticle(originalArticle));
        	articleRepository.saveAndFlush(originalArticle);
		}
		
	}

	@Override
	public List<Comment> getAllCommentByArticle(String aid) {
		Article article = null;
		List<Comment> comments = null;
//		System.out.println(aid);
//		System.out.println(article);
//		System.out.println(aid == "null");
//		System.out.println(aid.equals("null"));
//		System.out.println("这里的" + aid);
		if(!aid.equals("null")) {
			System.out.println("评论");
			article = articleRepository.findById(aid).get();
			comments = commentRepository.findByPidAndArticle(0L, article);
			for (Comment comment : comments) {
				List<CommentVO> commentVOs = new ArrayList<>();
				
				for (Comment comment2 : commentRepository.findByPid(comment.getId())) {
					
					
					CommentVO commentVO = new CommentVO(comment2.getId(), comment2.getPid(),
							comment2.getContent(), new UserVO(comment2.getUser().getId(), comment2.getUser().getAvatar()
									, comment2.getUser().getNickName()), 
							comment2.getCreateTime(), comment2.getArticle().getAId(), 
							new UserVO(comment2.getReplyUser().getId(), comment2.getReplyUser().getAvatar(), 
									comment2.getReplyUser().getNickName()));
					commentVOs.add(commentVO);
				}
				comment.setCommentVOs(commentVOs);
			}
			return comments;
		}else {
			System.out.println("留言");
			comments = commentRepository.findByPidAndArticleIsNull(0L);
			for (Comment comment : comments) {
				List<CommentVO> commentVOs = new ArrayList<>();
				
				for (Comment comment2 : commentRepository.findByPid(comment.getId())) {
					
					
					CommentVO commentVO = new CommentVO(comment2.getId(), comment2.getPid(),
							comment2.getContent(), new UserVO(comment2.getUser().getId(), comment2.getUser().getAvatar()
									, comment2.getUser().getNickName()), 
							comment2.getCreateTime(), null, 
							new UserVO(comment2.getReplyUser().getId(), comment2.getReplyUser().getAvatar(), 
									comment2.getReplyUser().getNickName()));
					commentVOs.add(commentVO);
				}
				comment.setCommentVOs(commentVOs);
			}
			return comments;
		}
		
		
	}

	@Override
	public List<Comment> getByArticleIsNotNull() {
		List<Comment> comments = commentRepository.findByArticleIsNotNull();
		return comments;
	}

	@Override
	public List<Comment> getByArticleIsNull() {
		List<Comment> comments = commentRepository.findByArticleIsNull();
		return comments;
	}

}
