package com.cupk.mapper;

import com.cupk.pojo.Post;
import com.cupk.pojo.Product;
import com.cupk.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findAllProducts();//查询全部产品
    Product findProductById(Integer product_id);//根据产品id查询产品
    void addProduct(Product product);//添加产品
    void updateProduct(Product product);//修改产品
    void deleteProduct(Integer product_id);//删除产品
    List<Product> searchProducts(Product product);//多条件查询产品
    List<Product> searchProductsByStr(String searchStr);//全局模糊查询产品
    void deleteProducts(int[] product_ids);//批量删除产品
    List<Product> findProductsByCategoryId(Integer categoryId); //根据分类id查询对应产品

    void collectProduct(@Param("product_id") Integer post_id, @Param("user_id") Integer user_id);//收藏
    void cancelCollect(@Param("product_id") Integer post_id,@Param("user_id") Integer user_id);//取消收藏
    void addCollect(Integer product_id);//增加收藏数
    void reduceCollect(Integer product_id);//减少收藏数
    List<Product> findUserCollects(Integer user_id);//查询用户收藏的产品
    Boolean hasUserCollectedProduct(@Param("product_id") Integer product_id, @Param("user_id") Integer user_id);
    void deleteProductPost(Integer product_id);
    void deleteCollects(Integer product_id);
    void deleteProductPostsByProductIds(int[] product_ids);
    void deleteCollectsByProductIds(int[] product_ids);
}
