package com.cupk.service;

import com.cupk.pojo.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {

    List<Product> findAllProducts();

    List<Product> findProductsByCategoryId(Integer category_id); //根据分类id查询产品

    List<Product> searchProducts(Product product);//多条件查询

    List<Product> searchProductsByStr(String searchStr);//全局模糊查询

    Product findProductById(Integer product_id);//根据产品id查询产品

    void deleteProducts(int[] product_ids);//批量删除

    void deleteProduct(Integer product_id);

    void updateProduct(Product product);

    void addProduct(Product product);

    void collectProduct(@Param("product_id") Integer post_id, @Param("user_id") Integer user_id);//收藏
    void cancelCollect(@Param("product_id") Integer post_id,@Param("user_id") Integer user_id);//取消收藏
    List<Product> findUserCollects(Integer user_id);//查询用户收藏的产品
    Boolean hasUserCollectedProduct(@Param("product_id") Integer product_id, @Param("user_id") Integer user_id);
}
