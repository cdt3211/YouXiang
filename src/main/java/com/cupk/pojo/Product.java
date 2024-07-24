package com.cupk.pojo;

import lombok.Data;

@Data
public class Product {
    private Integer product_id;
    private String name;
    private String description;
    private Integer collected; //收藏量
    private Integer postnumbers; //发帖量
    private String brand;//品牌
    private String image="https://img0.baidu.com/it/u=3845639002,4040944931&fm=253&fmt=auto&app=138&f=JPG?w=300&h=300";
    private Integer category_id;
    private Category category;//产品所属分类
}
