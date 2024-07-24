package com.cupk.service.serviceImpl;

import com.cupk.mapper.ProductMapper;
import com.cupk.pojo.Product;
import com.cupk.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findAllProducts() {
        return productMapper.findAllProducts();
    }

    @Override
    public List<Product> findProductsByCategoryId(Integer category_id) {
        return productMapper.findProductsByCategoryId(category_id);
    }

    @Override
    public List<Product> searchProducts(Product product) {
        return productMapper.searchProducts(product);
    }

    @Override
    public List<Product> searchProductsByStr(String searchStr) {
        return productMapper.searchProductsByStr(searchStr);
    }

    @Override
    public Product findProductById(Integer product_id) {
        return productMapper.findProductById(product_id);
    }

    @Override
    public void deleteProducts(int[] product_ids) {
        productMapper.deleteProductPostsByProductIds(product_ids);
        productMapper.deleteCollectsByProductIds(product_ids);
        productMapper.deleteProducts(product_ids);
    }

    @Override
    public void deleteProduct(Integer product_id) {
        productMapper.deleteProductPost(product_id);
        productMapper.deleteCollects(product_id);
        productMapper.deleteProduct(product_id);
    }

    @Override
    public void updateProduct(Product product) {
        productMapper.updateProduct(product);
    }

    @Override
    public void addProduct(Product product) {
        productMapper.addProduct(product);
    }

    @Override
    public void collectProduct(Integer post_id, Integer user_id) {
        productMapper.collectProduct(post_id, user_id);
        productMapper.addCollect(post_id);
    }

    @Override
    public void cancelCollect(Integer post_id, Integer user_id) {
        productMapper.cancelCollect(post_id, user_id);
        productMapper.reduceCollect(post_id);
    }

    @Override
    public List<Product> findUserCollects(Integer user_id) {
        return productMapper.findUserCollects(user_id);
    }

    @Override
    public Boolean hasUserCollectedProduct(Integer product_id, Integer user_id) {
        return productMapper.hasUserCollectedProduct(product_id, user_id);
    }
}
