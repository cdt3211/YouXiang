package com.cupk.mapper;

import com.cupk.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    public List<Comment> findAllComments();
    List<Comment> findCommentsByPostId(Integer postId);
    public Comment findCommentById(Integer comment_id);
    public void addComment(Comment comment);

    public void updateComment(Comment comment);
    public void deleteComment(Integer comment_id);
    public List<Comment> searchComments(Comment comment);
    public List<Comment> searchCommentsByStr(String searchStr);
    public void deleteComments(int[] commentIds);
}
