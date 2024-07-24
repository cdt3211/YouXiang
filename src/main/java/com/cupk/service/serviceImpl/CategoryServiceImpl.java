package com.cupk.service.serviceImpl;

import com.cupk.mapper.CategoryMapper;
import com.cupk.pojo.Category;
import com.cupk.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findAllCategories() {
        return categoryMapper.findAllCategories(); //调用CategoryMapper中的方法
    }

    @Override
    public Category findCategoryById(Integer category_id) {
        return categoryMapper.findCategoryById(category_id);
    }

    @Override
    public void addCategory(Category category) {
        categoryMapper.addCategory(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryMapper.updateCategory(category);
    }

    @Override
    public void deleteCategory(Integer category_id) {
        categoryMapper.deleteCategory(category_id);
    }

    @Override
    public List<Category> searchCategories(Category category) {
        return categoryMapper.searchCategories(category);
    }

    @Override
    public List<Category> searchCategoriesByStr(String searchStr) {
        return categoryMapper.searchCategoriesByStr(searchStr);
    }

    @Override
    public void deleteCategories(int[] category_ids) {
        categoryMapper.deleteCategories(category_ids);
    }
}
