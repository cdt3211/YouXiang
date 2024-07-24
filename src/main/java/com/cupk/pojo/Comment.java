package com.cupk.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Comment {
    private Integer comment_id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Integer comment_user_id;
    private Integer comment_post_id;
    private Date create_at; //创建时间
    private String content;//评论内容
    private Integer likes;//点赞数
    private User user; //评论者
    private Post post; //评论的帖子
}
