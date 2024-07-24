package com.cupk.service;

import com.cupk.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories(); //查询全部分类
    public Category findCategoryById(Integer category_id);//根据Cid查询分类
    public void addCategory(Category category);//添加分类
    public void updateCategory(Category category);//修改分类
    public void deleteCategory(Integer category_id);//删除分类
    public List<Category> searchCategories(Category category);//多条件查询分类
    public List<Category> searchCategoriesByStr(String searchStr);//全局模糊查询分类
    public void deleteCategories(int[] category_ids);//批量删除分类
}
