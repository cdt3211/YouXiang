package com.cupk.mapper;

import com.cupk.pojo.Post;
import com.cupk.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> findPostsByProductId(Integer product_id); //根据产品id查询对应帖子
    public List<Integer> findPostIdsByProductId(Integer product_id);
    List<Post> findAllPosts();//查询全部帖子
    List<Post> findPostsByUserId(Integer user_id);//根据用户id查询帖子
    public void addPost(Post post); //添加帖子
    public void deletePost(Integer post_id); //删除帖子
    void deleteCommentOfPost(Integer post_id);//删除评论
    void deleteProductPost(Integer post_id);//删除产品对应帖子相关表
    void deleteUserLikePost(Integer post_id);//删除用户对应帖子相关表
    void updatePost(Post post); //更新帖子
    Post findPostById(Integer post_id); //根据帖子id查询帖子
    List<Post> searchPosts(Post post);//多条件查询帖子
    List<Post> searchPostsByStr(String searchStr);//全局模糊查询帖子
    void deletePosts(int[] post_ids);//管理员批量删除帖子
    void deleteCommentOfPosts(int[] post_ids);//删除评论
    void deleteProductPosts(int[] post_ids);//删除产品对应帖子相关表
    void deleteUserLikePosts(int[] post_ids);//删除用户对应帖子相关表
    void likePost(@Param("post_id") Integer post_id,@Param("user_id") Integer user_id);//点赞
    void unlikePost(@Param("post_id") Integer post_id,@Param("user_id") Integer user_id);//取消点赞
    void addPostLike(Integer post_id);//增加点赞数
    void reducePostLike(Integer post_id);//减少点赞数
    List<Post> findUserLikes(Integer user_id);//查询用户点赞的帖子
    Boolean hasUserLikedPost(@Param("post_id") Integer post_id, @Param("user_id") Integer user_id);
    void addProductPost(Post post);
    void updateProductPost(Post post);
    List<Integer> findPostIdsByUserId(Integer userId);
}
