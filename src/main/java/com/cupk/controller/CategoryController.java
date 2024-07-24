package com.cupk.controller;

import com.cupk.pojo.Category;
import com.cupk.pojo.User;
import com.cupk.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/categories")//查询所有分类
    public String findAllCategories(Model model,@RequestParam(value = "start",defaultValue = "1") int start,
                               @RequestParam(value = "size",defaultValue = "10") int size) {
        PageHelper.startPage(start,size,"category_id desc");//分页查询
        List<Category> categories = categoryService.findAllCategories();
        PageInfo<Category> page = new PageInfo<>(categories);
        model.addAttribute("page", page);
        return "categories";
    }

    @GetMapping("/searchCategories")//多条件查询分类
    public String searchCategories(Category category, Model model) {
        List<Category> categories = categoryService.searchCategories(category);
        model.addAttribute("categories", categories);
        return "searchCategories";
    }

    @RequestMapping("/searchCategoriesByStr")//模糊搜索分类
    public String searchCategoriesByStr(@RequestParam(value = "searchStr") String searchStr, Model model){
        List<Category> categories =  categoryService.searchCategoriesByStr(searchStr);
        model.addAttribute("categories",categories);
        return "searchCategories";
    }

    @GetMapping("/category/{category_id}")//根据指定cid查询分类信息
    public ModelAndView findCategoryById(@PathVariable("category_id") int category_id) {
        Category category = categoryService.findCategoryById(category_id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("category", category);
        mv.setViewName("categorydetail");
        return mv;
    }

    @GetMapping("/addCategory")
    public String adminAddCategory(){//跳转至添加图书页面
        return "addcategory";
    }

    @PostMapping("/addCategory") //管理员添加分类
    public ModelAndView adminAddCategory(Category category){//执行添加分类，修改数据库
        categoryService.addCategory(category);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/admin/categories");//添加分类成功后跳转至分类列表
        return mv;
    }

    @GetMapping("/toUpdateCategory/{category_id}")//根据指定cid查询分类信息
    public ModelAndView toUpdateCategory(@PathVariable("category_id") int category_id){//跳转至修改分类页面
        Category category = categoryService.findCategoryById(category_id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("category", category);
        mv.setViewName("updateCategory");
        return mv;
    }

    @PostMapping("/updateCategory")
    public ModelAndView updateCategory(Category category){//执行修改分类，修改数据库
        categoryService.updateCategory(category);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/admin/categories");//修改分类成功后跳转至用户列表
        return mv;
    }

    @GetMapping("/deleteCategory/{category_id}")//根据指定cid删除分类
    public ModelAndView deleteCategory(@PathVariable("category_id") int category_id){//执行删除分类，修改数据库
        categoryService.deleteCategory(category_id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/admin/categories");//删除分类成功后跳转至用户列表
        return mv;
    }

    @GetMapping("/deleteCategories")//批量删除分类
    public ModelAndView deleteCategories(int[] category_id){
        categoryService.deleteCategories(category_id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/admin/categories");//删除成功后回到分类列表
        return mv;
    }
}
