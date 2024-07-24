package com.cupk.pojo;

import lombok.Data;

@Data
public class User {
    private Integer user_id;
    private String username;
    private String password;
    private String email;
    private String avatar = "https://youxiang-1317606226.cos.ap-beijing.myqcloud.com/d8600a84-e0e4-4a40-9b7d-8bccd5086621_Abner (2).png"; //默认头像
    private String bio;
    private String role;
}
