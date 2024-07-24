package com.cupk.controller;

import com.cupk.pojo.Comment;
import com.cupk.pojo.User;
import com.cupk.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/admin/comments")//查询所有评论
    public String findAllComments(Model model,@RequestParam(value = "start",defaultValue = "1") int start,
                               @RequestParam(value = "size",defaultValue = "10") int size) {
        PageHelper.startPage(start,size,"comment_id desc");//分页查询
        List<Comment> comments = commentService.findAllComments();
        PageInfo<Comment> page = new PageInfo<>(comments);
        model.addAttribute("page", page);
        return "comments";
    }

    @GetMapping("/searchComments")//多条件查询评论
    public String searchComments(Comment comment, Model model) {
        List<Comment> comments = commentService.searchComments(comment);
        model.addAttribute("comments", comments);
        return "searchComments";
    }

    @RequestMapping("/searchCommentsByStr")//模糊搜索评论
    public String searchCommentsByStr(@RequestParam(value = "searchStr") String searchStr, Model model){
        List<Comment> comments =  commentService.searchCommentsByStr(searchStr);
        model.addAttribute("comments",comments);
        return "searchComments";
    }

    @GetMapping("/deleteComment/{comment_id}")//根据指定cid删除评论
    public ModelAndView deleteComment(@PathVariable("comment_id") int comment_id){//执行删除评论，修改数据库
        commentService.deleteComment(comment_id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/admin/comments");//删除评论成功后跳转至用户列表
        return mv;
    }

    @GetMapping("/deleteComments")//批量删除评论
    public ModelAndView deleteComments(int[] comment_id){
        commentService.deleteComments(comment_id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/admin/comments");//删除成功后回到评论列表
        return mv;
    }

    @PostMapping("/addComment")
    @ResponseBody
    public Comment addComment(@RequestBody Comment comment) {
        commentService.addComment(comment);
        return comment;
    }
}
