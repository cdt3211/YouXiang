package com.cupk.controller;

import com.cupk.pojo.Category;
import com.cupk.pojo.Post;
import com.cupk.pojo.Product;
import com.cupk.pojo.User;
import com.cupk.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CosService cosService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/posts/{productId}")//根据产品id查询所有帖子
    public ModelAndView findPostsByProductId(@PathVariable Integer productId) {
        List<Post> posts = postService.findPostsByProductId(productId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("posts", posts);
        mv.addObject("product_id", productId);
        mv.setViewName("userProductDetail");
        return mv;
    }

    @GetMapping("/index/posts/all")
    @ResponseBody
    public List<Post> findAllPosts() {
        return postService.findAllPosts();
    }


    @GetMapping("/admin/posts")//查询所有贴文
    public String findAllPosts(Model model,@RequestParam(value = "start",defaultValue = "1") int start,
                               @RequestParam(value = "size",defaultValue = "6") int size) {
        PageHelper.startPage(start,size,"post_id desc");//分页查询
        List<Post> posts = postService.findAllPosts();
        PageInfo<Post> page = new PageInfo<>(posts);
        model.addAttribute("page", page);
        return "posts";
    }

    @GetMapping("/deletePost/{post_id}") //管理员删除帖子
    public ModelAndView adminDeletePost(@PathVariable Integer post_id) {//管理员删除帖子
        postService.deletePost(post_id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/admin/posts");
        return mv;
    }

    @GetMapping("user/editPost/{post_id}")
    public String editPost(@PathVariable Integer post_id, Model model) {
        List<Product> products = productService.findAllProducts();
        Post post = postService.findPostById(post_id);
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("post", post);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "userEditPost";
    }
    @PostMapping("/editPost")
    public String editPost(@RequestParam("post_id") Integer post_id,
                           @RequestParam("cover_image") MultipartFile cover_image,
                           @RequestParam("title") String title,
                           @RequestParam("description") String description,
                           @RequestParam("product_id") Integer product_id,
                           Model model) {
        try {
            Product product = productService.findProductById(product_id);
            Post post = postService.findPostById(post_id);
            post.setTitle(title);
            post.setDescription(description);
            post.setProduct(product);
            if(!cover_image.isEmpty()){
                String imageUrl = cosService.uploadFile(cover_image);
                post.setCover_image(imageUrl);
            }
            postService.updatePost(post);
            postService.updateProductPost(post);
            return "redirect:/index/post/"+post.getPost_id();
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "上传头像失败，请重试。");
            return "userEditPost";
        }
    }


    @GetMapping("/post/{postId}")
    public String getPost(@PathVariable Integer post_id, Model model, HttpSession session) {
        Post post = postService.findPostById(post_id);
        model.addAttribute("post", post);

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            Integer user_id = loggedInUser.getUser_id();
            boolean userLiked = postService.hasUserLikedPost(post_id, user_id);
            model.addAttribute("userLiked", userLiked);
            model.addAttribute("loggedInUser", loggedInUser);
        } else {
            model.addAttribute("userLiked", false);
            model.addAttribute("loggedInUser", null);
        }
        return "postDetail";
    }

    @GetMapping("/toUpdatePost/{post_id}")
    public ModelAndView toUpdatePost(@PathVariable("post_id") int post_id) {
        Post post = postService.findPostById(post_id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("post", post);
        mv.setViewName("updatePost");
        return mv;
    }

    @GetMapping("/searchPosts")//多条件查询帖子
    public String searchPosts(Post post, Model model) {
        List<Post> posts = postService.searchPosts(post);
        model.addAttribute("posts", posts);
        return "searchPosts";
    }

    @RequestMapping("/searchPostsByStr")//模糊查询帖子
    public String searchPostsByStr(@RequestParam(value = "searchStr") String searchStr, Model model) {
        List<Post> posts = postService.searchPostsByStr(searchStr);
        model.addAttribute("posts", posts);
        return "searchPosts";
    }

    @GetMapping("/searchPostsIndex")
    @ResponseBody
    public List<Post> searchPosts(@RequestParam("searchStr") String searchStr) {
        return postService.searchPostsByStr(searchStr);
    }

    @GetMapping("/deletePosts")//批量删除帖子
    public ModelAndView deletePosts(int[] post_id) {
        postService.deletePosts(post_id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/admin/posts");//删除成功后回到帖子列表
        return mv;
    }

    @GetMapping("/user/deletePost/{post_id}") //用户删除帖子
    public String userDeletePost(@PathVariable Integer post_id,HttpSession session) {//用户删除帖子
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        postService.deletePost(post_id);
        return "redirect:/home/"+loggedInUser.getUser_id();
    }


    @GetMapping("/release")
    public String showRelease(Model model) {
        List<Product> products = productService.findAllProducts();
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "release";
    }

    @PostMapping("/release")
    public String addPost(@RequestParam("cover_image") MultipartFile cover_image,
                          @RequestParam("title") String title,
                          @RequestParam("description") String description,
                          @RequestParam("product_id") Integer product_id,
                          HttpSession session,
                          Model model) {
        try {
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                model.addAttribute("error", "用户未找到");
                return "release";
            }
            // 检查 product_id 是否存在
            Product product = productService.findProductById(product_id);
            if (product == null) {
                model.addAttribute("error", "产品未找到");
                return "release";
            }
            Post post = new Post();
            post.setTitle(title);
            post.setDescription(description);
            post.setUser(loggedInUser);
            if(!cover_image.isEmpty()){
                String imageUrl = cosService.uploadFile(cover_image);
                post.setCover_image(imageUrl);
            }
            postService.addPost(post);
            post.setProduct(product);
            postService.addProductPost(post);
            return "redirect:/index/post/"+post.getPost_id();
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "上传头像失败，请重试。");
            return "redirect:/release";
        }
    }
}
