package com.cupk.pojo;

import lombok.Data;

@Data
public class UserLikes {
    private Integer id;
    private Integer user_id;
    private Integer post_id;
    private Post post;
    private User user;
}
