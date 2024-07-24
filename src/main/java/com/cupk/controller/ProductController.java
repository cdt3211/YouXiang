package com.cupk.controller;

import com.cupk.pojo.Category;
import com.cupk.pojo.Product;
import com.cupk.pojo.User;
import com.cupk.service.CategoryService;
import com.cupk.service.CosService;
import com.cupk.service.ProductService;
import com.cupk.service.PostService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CosService cosService;
    @Autowired
    private PostService postService;

    @GetMapping("/index/products/{category_id}")
    @ResponseBody
    public List<Product> findProductsByCategoryId(@PathVariable("category_id") Integer category_id) {
        return productService.findProductsByCategoryId(category_id);
    }

    @GetMapping("/admin/products")//查询所有产品
    public String findAllProducts(Model model,@RequestParam(value = "start",defaultValue = "1") int start,
                               @RequestParam(value = "size",defaultValue = "6") int size) {
        PageHelper.startPage(start,size,"product_id desc");//分页查询
        List<Product> products = productService.findAllProducts();
        PageInfo<Product> page = new PageInfo<>(products);
        model.addAttribute("page", page);
        return "products";
    }
    @GetMapping("/searchProducts")//多条件查询产品
    public String searchProducts(Product product, Model model) {
        List<Product> products = productService.searchProducts(product);
        model.addAttribute("products", products);
        return "searchProducts";
    }

    @RequestMapping("/searchProductsByStr")//模糊搜索产品
    public String searchProductsByStr(@RequestParam(value = "searchStr") String searchStr, Model model){
        List<Product> products =  productService.searchProductsByStr(searchStr);
        model.addAttribute("products",products);
        return "searchProducts";
    }

    @GetMapping("/searchProductsIndex")
    @ResponseBody
    public List<Product> searchProducts(@RequestParam("searchStr") String searchStr) {
        return productService.searchProductsByStr(searchStr);
    }

    @GetMapping("/product/{product_id}")//根据指定product_id查询产品信息
    public ModelAndView findProductById(@PathVariable("product_id") int product_id) {
        Product product = productService.findProductById(product_id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("product", product);
        mv.setViewName("productdetail");
        return mv;
    }

    @GetMapping("/addProduct")
    public String adminAddProduct(Model model){//跳转至添加图书页面
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        return "addProduct";
    }
    @PostMapping("/addProduct")
    public String addProduct(@RequestParam("coverImageFile") MultipartFile coverImageFile,
                          @RequestParam("name") String name,
                          @RequestParam("description") String description,
                          @RequestParam("brand") String brand,
                          @RequestParam("category_id") Integer category_id,
                          Model model) {
        try {
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setBrand(brand);
            product.setCategory_id(category_id);
            if(!coverImageFile.isEmpty()){
                String imageUrl = cosService.uploadFile(coverImageFile);
                product.setImage(imageUrl);
            }
            productService.addProduct(product);
            return "redirect:/admin/products";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "上传图片失败，请重试。");
            return "addproduct";
        }
    }
//    @PostMapping("/addProduct") //管理员添加产品
//    public ModelAndView adminAddProduct(Product product){//执行添加产品，修改数据库
//        List<Category> categories = categoryService.findAllCategories();
//        productService.addProduct(product);
//        ModelAndView mv = new ModelAndView();
//        mv.addObject("categories", categories);
//        mv.setViewName("redirect:/admin/products");//添加产品成功后跳转至产品列表
//        return mv;
//    }

    @GetMapping("/toUpdateProduct/{product_id}")
    public String toUpdateProduct(@PathVariable Integer product_id, Model model){//跳转至修改产品页面
        Product product = productService.findProductById(product_id);
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "updateproduct";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@RequestParam("product_id") Integer product_id,
                                @RequestParam("coverImageFile") MultipartFile coverImageFile,
                                @RequestParam("name") String name,
                                @RequestParam("description") String description,
                                @RequestParam("brand") String brand,
                                @RequestParam("category_id") Integer category_id,
                                Model model){//执行修改产品，修改数据库
        try {
            Product product = productService.findProductById(product_id);
            product.setName(name);
            product.setDescription(description);
            product.setBrand(brand);
            product.setCategory_id(category_id);

            if (!coverImageFile.isEmpty()) {
                String imageUrl = cosService.uploadFile(coverImageFile);
                product.setImage(imageUrl);
            }
            productService.updateProduct(product);
            return "redirect:/admin/products";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "上传图片失败，请重试。");
            return "updateproduct";
        }
    }

    @GetMapping("/deleteProduct/{product_id}")//根据指定product_id删除产品
    public ModelAndView deleteProduct(@PathVariable("product_id") int product_id){//执行删除产品，修改数据库
        List<Integer> post_ids = postService.findPostIdsByProductId(product_id);
        int[] postIdsArray = post_ids.stream().mapToInt(Integer::intValue).toArray();
        if (postIdsArray != null && postIdsArray.length > 0){
            postService.deletePosts(postIdsArray);
        }
        productService.deleteProduct(product_id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/admin/products");//删除产品成功后跳转至产品列表
        return mv;
    }

    @GetMapping("/deleteProducts")//批量删除产品
    public ModelAndView deleteProducts(int[] product_id){
        for (int pid : product_id) {
            List<Integer> post_ids = postService.findPostIdsByProductId(pid);
            int[] postIdsArray = post_ids.stream().mapToInt(Integer::intValue).toArray();
            if (postIdsArray != null && postIdsArray.length > 0){
                postService.deletePosts(postIdsArray);
            }
        }
        productService.deleteProducts(product_id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/admin/products");//删除成功后回到产品列表
        return mv;
    }
}

