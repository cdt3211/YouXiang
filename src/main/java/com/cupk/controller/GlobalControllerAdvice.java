package com.cupk.controller;

import com.cupk.pojo.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ModelAttribute("loggedInUser")
    public User globalUserAttribute(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }

}


