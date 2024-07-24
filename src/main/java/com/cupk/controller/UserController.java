package com.cupk.controller;

import com.cupk.pojo.User;
import com.cupk.service.CosService;
import com.cupk.service.PostService;
import com.cupk.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CosService cosService;

    @Autowired
    private PostService postService;

    @GetMapping("/admin/users")//查询所有用户
    public String findAllUsers(Model model,@RequestParam(value = "start",defaultValue = "1") int start,
                               @RequestParam(value = "size",defaultValue = "10") int size) {
        PageHelper.startPage(start,size,"user_id desc");//分页查询
        List<User> users = userService.findAllUsers();
        PageInfo<User> page = new PageInfo<>(users);
        model.addAttribute("page", page);
        return "users";
    }


    @GetMapping("/searchUsers")//多条件查询用户
    public String searchUsers(User user,Model model) {
        List<User> users = userService.searchUsers(user);
        model.addAttribute("users", users);
        return "searchUsers";
    }

    @RequestMapping("/searchUsersByStr")//模糊搜索用户
    public String searchUsersByStr(@RequestParam(value = "searchStr") String searchStr, Model model){
        List<User> users =  userService.searchUsersByStr(searchStr);
        model.addAttribute("users",users);
        return "searchUsers";
    }

    @GetMapping("/user/{user_id}")//根据指定uid查询用户信息
    public ModelAndView findUserById(@PathVariable("user_id") int user_id) {
        User user = userService.findUserById(user_id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", user);
        mv.setViewName("userdetail");
        return mv;
    }

    @GetMapping("/addUser")
    public String adminAddUser(){//跳转至添加图书页面
        return "addUser";
    }


    @PostMapping("/addUser")
    public String addUser(@RequestParam("avatarFile") MultipartFile avatarFile,
                          @RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam("email") String email,
                          @RequestParam("bio") String bio,
                          @RequestParam("role") String role,
                          Model model) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setBio(bio);
            user.setRole(role);
            if(!avatarFile.isEmpty()){
                String avatar = cosService.uploadFile(avatarFile);
                user.setAvatar(avatar);
            }
            userService.addUser(user);
            return "redirect:/admin/users";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "上传头像失败，请重试。");
            return "adduser";
        }
    }

    @GetMapping("/toUpdateUser/{user_id}")//根据指定uid查询用户信息
    public String toUpdateUser(@PathVariable Integer user_id,Model model){//跳转至修改用户页面
        User user = userService.findUserById(user_id);
        model.addAttribute("user",user);
        return "updateuser";
    }
    @GetMapping("/toUserUpdateUser/{user_id}")//根据指定uid查询用户信息
    public String userUpdateUser(@PathVariable Integer user_id,Model model){//跳转至修改用户页面
        User user = userService.findUserById(user_id);
        model.addAttribute("user",user);
        return "userUpdateUser";
    }

    @PostMapping("/admin/updateUser")
    public String adminUpdateUser(@RequestParam("user_id") Integer user_id,
                             @RequestParam("avatarFile") MultipartFile avatarFile,
                             @RequestParam("username") String username,
                             @RequestParam("email") String email,
                             @RequestParam("bio") String bio,
                             @RequestParam("role") String role,
                             Model model) {
        try {
            User user = userService.findUserById(user_id);
            user.setUsername(username);
            user.setEmail(email);
            user.setBio(bio);
            user.setRole(role);

            if (!avatarFile.isEmpty()) {
                String avatarUrl = cosService.uploadFile(avatarFile);
                user.setAvatar(avatarUrl);
            }
            userService.updateUser(user);
            return "redirect:/admin/users";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "上传头像失败，请重试。");
            return "updateuser";
        }
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestParam("user_id") Integer user_id,
                             @RequestParam("avatarFile") MultipartFile avatarFile,
                             @RequestParam("username") String username,
                             @RequestParam("email") String email,
                             @RequestParam("bio") String bio,
                             Model model) {
        try {
            User user = userService.findUserById(user_id);
            user.setUsername(username);
            user.setEmail(email);
            user.setBio(bio);

            if (!avatarFile.isEmpty()) {
                String avatarUrl = cosService.uploadFile(avatarFile);
                user.setAvatar(avatarUrl);
            }
            userService.updateUser(user);
            return "redirect:/home/" + user_id;
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "上传头像失败，请重试。");
            return "userUpdateUser";
        }
    }


    @GetMapping("/deleteUser/{user_id}")//根据指定uid删除用户
    public ModelAndView deleteUser(@PathVariable("user_id") int user_id){//执行删除用户，修改数据库
        List<Integer> post_ids = postService.findPostIdsByUserId(user_id);
        int[] postIdsArray = post_ids.stream().mapToInt(Integer::intValue).toArray();
//        List<Integer> comment_ids = commentService.findCommentIdsByUserId(user_id);
//        int[] commentIdsArray = comment_ids.stream().mapToInt(Integer::intValue).toArray();
        if (postIdsArray != null && postIdsArray.length > 0){
            postService.deletePosts(postIdsArray);
        }
        else {
            System.out.println("该用户没有发表过文章");
        }
//        if (commentIdsArray != null && commentIdsArray.length > 0)
//            commentService.deleteComments(commentIdsArray);
//        else {
//            System.out.println("该用户没有发表过评论");
//        }

        userService.deleteUser(user_id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/admin/users");//删除用户成功后跳转至用户列表
        return mv;
    }
    @GetMapping("/deleteUsers")//批量删除用户
    public ModelAndView deleteUsers(int[] user_id){
        // 遍历每个用户ID
        for (int uid : user_id) {
            // 查询该用户的帖子ID列表
            List<Integer> post_ids = postService.findPostIdsByUserId(uid);
            int[] postIdsArray = post_ids.stream().mapToInt(Integer::intValue).toArray();

            // 如果该用户有发表过帖子，则批量删除这些帖子
            if (postIdsArray != null && postIdsArray.length > 0) {
                postService.deletePosts(postIdsArray);
            } else {
                System.out.println("用户 " + uid + " 没有发表过文章");
            }
        }

        // 批量删除用户
        userService.deleteUsers(user_id);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/admin/users");//删除成功后回到用户列表
        return mv;
    }
}
