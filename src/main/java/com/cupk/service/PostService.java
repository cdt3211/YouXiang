package com.cupk.service;

import com.cupk.pojo.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostService {
    List<Post> findPostsByProductId(Integer product_id); //根据分类查询全部商品
    public List<Integer> findPostIdsByProductId(Integer product_id);

    List<Post> findAllPosts();//查询全部帖子
    List<Post> findPostsByUserId(Integer user_id);//根据用户id查询帖子
    public void addPost(Post post); //添加帖子
    public void deletePost(Integer post_id); //删除帖子
    public void updatePost(Post post); //更新帖子
    public Post findPostById(Integer post_id); //根据帖子id查询帖子
    public List<Post> searchPosts(Post post);//多条件查询帖子
    public List<Post> searchPostsByStr(String searchStr);//全局模糊查询帖子
    public void deletePosts(int[] post_ids);//管理员批量删除帖子
    void likePost(Integer post_id,Integer user_id);//点赞
    void unlikePost(Integer post_id,Integer user_id);//取消点赞
    List<Post> findUserLikes(Integer user_id);//查询用户点赞的帖子
    Boolean hasUserLikedPost(@Param("post_id") Integer postId, @Param("user_id") Integer userId);
    public void addProductPost(Post post);
    void updateProductPost(Post post);
    public List<Integer> findPostIdsByUserId(Integer user_id);
}
