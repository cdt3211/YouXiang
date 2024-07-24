package com.cupk.service;

import com.cupk.pojo.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAllComments();//查询所有
    List<Comment> findCommentsByPostId(Integer postId);
    public Comment findCommentById(Integer comment_id);//根据id查询
    public void addComment(Comment comment);//添加
    public void updateComment(Comment comment);//更新
    public void deleteComment(Integer comment_id);//删除
    public List<Comment> searchComments(Comment comment);//多条件查询
    public List<Comment> searchCommentsByStr(String searchStr);//全局模糊查询
    public void deleteComments(int[] comment_ids);//批量删除
}
