package com.cupk.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Post {
    private Integer post_id;
    private String title;//标题
    private String description; //描述
    private String cover_image= "https://youxiang-1317606226.cos.ap-beijing.myqcloud.com/d8600a84-e0e4-4a40-9b7d-8bccd5086621_Abner (2).png"; //封面图
    private String detail_images; //详情图
    private Integer user_id;
    private User user; //关联的user
    private Integer likes; //点赞量
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_at; //创建时间
    private Product product; //关联的product
}
