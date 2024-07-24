package com.cupk.controller;

import com.cupk.pojo.User;
import com.cupk.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginAndRegisterController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "loginAndRegister";
    }

    @GetMapping("/register")
    public String addUser(){//跳转至添加图书页面
        return "loginAndRegister";
    }

    @GetMapping("/loginAndRegister")
    public String loginAndRegister(){
        return "loginAndRegister";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.login(username, password);
//        若为管理员账户则跳转至管理员页面
        if (user != null && user.getRole().equals("admin")) {
            session.setAttribute("loggedInUser", user); // 将用户信息添加到Session
            model.addAttribute("user", user);
            user.setPassword("");
            return "redirect:/admin"; // 登录成功后跳转到用户列表页面
        } else if (user != null) {
            session.setAttribute("loggedInUser", user); // 将用户信息添加到Session
            model.addAttribute("user", user);
            user.setPassword("");
            return "redirect:/index"; // 登录成功后跳转到首页
        } else {
            model.addAttribute("loginError", "用户名或密码错误");
            return "loginAndRegister";
        }
    }

    @PostMapping("/register") //用户自己注册
    public String register(String username, String password, String email, Model model){//执行添加用户，修改数据库
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setRole("user");
        User user1 = userService.findUserByUsername(username);
        User user2 = userService.findUserByEmail(email);
        if (user1 != null || user2 != null) {
            model.addAttribute("registerError", "用户名或邮箱已存在");
            return "loginAndRegister";
        } else {
            userService.addUser(newUser); // 注册成功后跳转到登录页面
            return "redirect:/loginAndRegister";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 销毁session，清除所有信息
        return "redirect:/index"; // 重定向到首页
    }

}
