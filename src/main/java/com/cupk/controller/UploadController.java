package com.cupk.controller;

import com.cupk.service.CosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UploadController {
    @Autowired
    private CosService cosService;

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("imageFile") MultipartFile file, Model model) {
        try {
            String imageUrl = cosService.uploadFile(file);
            model.addAttribute("imageUrl", imageUrl);
            // 可以在这里将 imageUrl 保存到数据库中
            return "uploadSuccess";
        } catch (IOException e) {
            e.printStackTrace();
            return "uploadFailure";
        }
    }
}
