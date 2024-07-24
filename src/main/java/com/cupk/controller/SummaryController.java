package com.cupk.controller;

import com.cupk.service.BigModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


@Controller
public class SummaryController {
    @Autowired
    private BigModelService bigModelService;

    @GetMapping("/summary/{productId}")
    @ResponseBody
    public ResponseEntity<String> getSummaryForPostsByProductId(@PathVariable Integer productId){
        String summary = bigModelService.generateSummaryForPostsByProductId(productId);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/abstract/{postId}")
    @ResponseBody
    public ResponseEntity<String> getAbstractByPostId(@PathVariable Integer postId){
        String summary = bigModelService.generateAbstractByPostId(postId);
        return ResponseEntity.ok(summary);
    }
}
