package cn.xhxc.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import cn.xhxc.blog.entity.Comment;
import cn.xhxc.blog.entity.User;
import cn.xhxc.blog.service.CommentService;
import cn.xhxc.blog.util.JsonResult;


@RestController
@RequestMapping("/comments")
public class CommentController extends BaseController{

	
	@Autowired
    private CommentService commentService;
    /**
     * 发表评论
     *
     * @param postId
     * @param commentId
     * @param commentContent
     * @return
     */
	@RequestMapping("/release")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")  // 指定角色权限才能操作方法
	public JsonResult<Comment> createComment(String articleId, Long replyId, Long commentId, String commentContent) {
		Comment comment;
		// 1、评论
		if (commentId == null) {
			comment = commentService.createComment(articleId, commentContent);
		} else {
			// 回复
			comment = commentService.replyComment(articleId, commentId, replyId, commentContent);
		}
		// 2、修改文章的评论数目
		if (articleId != null) {
			System.out.println();
			commentService.updataCommentSize(articleId);
		}
		System.out.println(comment);
		return new JsonResult<>(SUSESS,comment);
	}
	
	@RequestMapping("/getAllComments")
	public JsonResult<List<Comment>> getAllCommentByArticle(String aid){
		List<Comment> comments = commentService.getAllCommentByArticle(aid);
		System.out.println("执行到这里");
		comments.forEach(e -> System.err.println(e.getId()));
		return new JsonResult<>(SUSESS,comments);
	}
	
	
    /**
     * 删除评论
     *
     * @return
     */
    @RequestMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")  // 指定角色权限才能操作方法
    public JsonResult<Void> delete(@PathVariable("id") Long id) {
    	
        boolean isOwner = false;
        User user = commentService.getCommentById(id).getUser();
        User principal = null;
        if (user == null) {
			return new JsonResult<>(100);
		}
        System.out.println("留言拥有者" + user.getNickName());
        // 判断操作用户是否是评论的所有者
        if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null && user.getUsername().equals(principal.getUsername())) {
                isOwner = true;
            }
        }
        System.out.println(principal.getRoles().get(0).getName());
        if ("ROLE_ADMIN".equals(principal.getRoles().get(0).getName())) {
        	commentService.deleteComment(id);
            return new JsonResult<>(SUSESS);
		}
        if (!isOwner) {
//            return ResponseEntity.ok().body(new Response(false, "没有操作权限！"));
        	return new JsonResult<>(200);
        }
        commentService.deleteComment(id);
            
       
        return new JsonResult<>(SUSESS);
    }
    
    
    @RequestMapping("/getAllCommentsNotNull")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public JsonResult<List<Comment>> getAllCommentsNotNull(){
		List<Comment> comments = commentService.getByArticleIsNotNull();
		System.out.println("这里");
		return new JsonResult<>(SUSESS,comments);
	}
    
    @RequestMapping("/getByArticleIsNull")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public JsonResult<List<Comment>> getByArticleIsNull(){
		List<Comment> comments = commentService.getByArticleIsNull();
		System.out.println("这里");
		return new JsonResult<>(SUSESS,comments);
	}
    
    @RequestMapping("/admin/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")  // 指定角色权限才能操作方法
    public JsonResult<Void> deleteA(@PathVariable("id") Long id) {
      commentService.deleteComment(id);
      return new JsonResult<>(SUSESS);
  }
    
}