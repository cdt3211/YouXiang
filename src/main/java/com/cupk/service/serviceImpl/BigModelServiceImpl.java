package com.cupk.service.serviceImpl;

import com.cupk.bigmodel.BigModelNew;
import com.cupk.pojo.Post;
import com.cupk.pojo.Product;
import com.cupk.service.BigModelService;
import com.cupk.service.PostService;
import com.cupk.service.ProductService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@Service
public class BigModelServiceImpl implements BigModelService {
    @Autowired
    private PostService postService; // 假设有一个 PostRepository 用于获取帖子数据
    @Autowired
    private ProductService productService;
    @Autowired
    private OkHttpClient okHttpClient;

    @Override //生成摘要
    public String generateSummary(String content) {
        try {
            CountDownLatch latch = new CountDownLatch(1); // 创建 CountDownLatch 实例
            BigModelNew bigModelNew = new BigModelNew("userId", latch);
            String summary = bigModelNew.getSummary(content);
            latch.await(30, TimeUnit.SECONDS); // 等待任务完成，最多等待30秒
            return summary;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating summary.";
        }
    }


    @Override //根据产品的所有帖子生成摘要
    public String generateSummaryForPostsByProductId(Integer productId) {
        List<Post> posts = postService.findPostsByProductId(productId); //根据产品id获取帖子
        Product product = productService.findProductById(productId); //根据产品id获取产品
        StringBuilder allPostsContent = new StringBuilder();
        for (Post post : posts) { //ai获取内容
            allPostsContent.append("产品名称为：").append(product.getName()).append("\n");
            allPostsContent.append("用户发帖标题为：").append(post.getTitle()).append("\n");
            allPostsContent.append("帖子内容为：").append(post.getDescription()).append("\n");
            allPostsContent.append("帖子点赞量为：").append(post.getLikes()).append("\n");
        }
        return generateSummary(allPostsContent.toString());
    }

    public String generateAbstract(String content) {
        try {
            CountDownLatch latch = new CountDownLatch(1); // 创建 CountDownLatch 实例
            BigModelNew bigModelNew = new BigModelNew("userId", latch);
            String summary = bigModelNew.getAbstract(content);
            latch.await(30, TimeUnit.SECONDS); // 等待任务完成，最多等待30秒
            return summary;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating summary.";
        }
    }

    @Override
    public String generateAbstractByPostId(Integer postId) {
        Post post = postService.findPostById(postId);
        StringBuilder postContent = new StringBuilder();
        postContent.append("用户发帖标题为：").append(post.getTitle()).append("\n");
        postContent.append("帖子内容为：").append(post.getDescription()).append("\n");
        postContent.append("相关产品为：").append(post.getProduct().getName()).append("\n");
        postContent.append("帖子点赞量为：").append(post.getLikes()).append("\n");
        return generateAbstract(postContent.toString());
    }
}