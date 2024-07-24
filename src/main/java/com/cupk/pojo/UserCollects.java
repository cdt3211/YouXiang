package com.cupk.pojo;

import lombok.Data;

@Data
public class UserCollects {
    private Integer id;
    private Integer user_id;
    private Integer product_id;
    private Product product;
    private User user;
}
