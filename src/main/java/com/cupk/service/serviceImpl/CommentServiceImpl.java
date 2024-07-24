package com.cupk.service.serviceImpl;

import com.cupk.mapper.CommentMapper;
import com.cupk.pojo.Category;
import com.cupk.pojo.Comment;
import com.cupk.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findAllComments() {
        return commentMapper.findAllComments();
    }

    @Override
    public List<Comment> findCommentsByPostId(Integer postId) {
        return commentMapper.findCommentsByPostId(postId);
    }

    @Override
    public Comment findCommentById(Integer comment_id) {
        return commentMapper.findCommentById(comment_id);
    }

    @Override
    public void addComment(Comment comment) {
        commentMapper.addComment(comment);
    }

    @Override
    public void updateComment(Comment comment) {
        commentMapper.updateComment(comment);
    }

    @Override
    public void deleteComment(Integer comment_id) {
        commentMapper.deleteComment(comment_id);
    }

    @Override
    public List<Comment> searchComments(Comment comment) {
        return commentMapper.searchComments(comment);
    }

    @Override
    public List<Comment> searchCommentsByStr(String searchStr) {
        return commentMapper.searchCommentsByStr(searchStr);
    }

    @Override
    public void deleteComments(int[] comment_ids) {
        commentMapper.deleteComments(comment_ids);
    }
}
