package com.cupk.controller;

import com.cupk.pojo.*;
import com.cupk.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PostService postService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;




    @GetMapping("/index")
    public String index(Model model) {
        List<Category> categories = categoryService.findAllCategories();
        System.out.println("Categories: " + categories); // 调试日志
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("index/product/{productId}")//根据产品id查询所有帖子
    public String findProductById(@PathVariable("productId") int productId,HttpSession session,Model model) {
        Product product = productService.findProductById(productId);
        List<Post> posts = postService.findPostsByProductId(productId);
        model.addAttribute("product", product);
        model.addAttribute("posts", posts);
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null){
            Integer user_id = loggedInUser.getUser_id();
            boolean userCollected = productService.hasUserCollectedProduct(productId, user_id);
            model.addAttribute("userCollected", userCollected);
            model.addAttribute("loggedInUser",loggedInUser);
        } else {
            model.addAttribute("userCollected", false);
            model.addAttribute("loggedInUser",null);
        }
        return "userProductDetail";
    }

    @GetMapping("index/post/{postId}")//根据帖子id查询帖子详情
    public String findPostById(@PathVariable Integer postId, Model model, HttpSession session) {
        Post post = postService.findPostById(postId);
        List<Comment> comments = commentService.findCommentsByPostId(postId);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            Integer user_id = loggedInUser.getUser_id();
            boolean userLiked = postService.hasUserLikedPost(postId, user_id);
            model.addAttribute("userLiked", userLiked);
            model.addAttribute("loggedInUser", loggedInUser);
        } else {
            model.addAttribute("userLiked", false);
            model.addAttribute("loggedInUser", null);
        }
        return "userpostDetail";
    }

    @PostMapping("/user/deleteComment/{comment_id}")//根据指定cid删除评论
    public ResponseEntity<?> deleteComment(@PathVariable("comment_id") int comment_id){//执行删除评论，修改数据库
        commentService.deleteComment(comment_id);
        return ResponseEntity.ok(Collections.singletonMap("success", true));
    }


    @GetMapping("/home/{userId}")
    public String home(Model model,@PathVariable Integer userId) {
        List<Post> posts = postService.findPostsByUserId(userId);
        List<Post> likePosts = postService.findUserLikes(userId);
        List<Product> products = productService.findUserCollects(userId);
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("products", products);
        model.addAttribute("likePosts", likePosts);
        return "home";
    }

    @GetMapping("/pleaseLogin")
    public String pleaseLogin() {
        return "pleaseLogin";
    }

    //点赞
    @PostMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Integer postId, @RequestBody Map<String, Integer> payload) {
        Integer userId = payload.get("userId");
        postService.likePost(postId, userId);
        return ResponseEntity.ok(Collections.singletonMap("success", true));
    }

    @PostMapping("/{postId}/unlike")
    public ResponseEntity<?> unlikePost(@PathVariable Integer postId, @RequestBody Map<String, Integer> payload) {
        Integer userId = payload.get("userId");
        postService.unlikePost(postId, userId);
        return ResponseEntity.ok(Collections.singletonMap("success", true));
    }

    //收藏
    @PostMapping("/{productId}/collect")
    public ResponseEntity<?> collectProduct(@PathVariable Integer productId, @RequestBody Map<String, Integer> payload) {
        Integer userId = payload.get("userId");
        productService.collectProduct(productId, userId);
        return ResponseEntity.ok(Collections.singletonMap("success", true));
    }

    @PostMapping("/{productId}/cancelCollect")
    public ResponseEntity<?> cancelCollect(@PathVariable Integer productId, @RequestBody Map<String, Integer> payload) {
        Integer userId = payload.get("userId");
        productService.cancelCollect(productId, userId);
        return ResponseEntity.ok(Collections.singletonMap("success", true));
    }

}
