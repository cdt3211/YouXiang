package com.cupk.service.serviceImpl;

import com.cupk.mapper.PostMapper;
import com.cupk.pojo.Post;
import com.cupk.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;

    @Override
    public List<Post> findPostsByProductId(Integer product_id) {
        return postMapper.findPostsByProductId(product_id);
    }
    @Override
    public List<Integer> findPostIdsByProductId(Integer product_id) {
        return postMapper.findPostIdsByProductId(product_id);
    }
    @Override
    public List<Post> findAllPosts() {
        return postMapper.findAllPosts();
    }

    @Override
    public List<Post> findPostsByUserId(Integer user_id) {
        return postMapper.findPostsByUserId(user_id);
    }

    @Override
    public List<Integer> findPostIdsByUserId(Integer user_id) {
        return postMapper.findPostIdsByUserId(user_id);
    }
    @Override
    public void addPost(Post post) {
        postMapper.addPost(post);
    }

    @Override
    public void deletePost(Integer post_id) {
        postMapper.deleteCommentOfPost(post_id);
        postMapper.deleteUserLikePost(post_id);
        postMapper.deleteProductPost(post_id);
        postMapper.deletePost(post_id);
    }

    @Override
    public void updatePost(Post post) {
        postMapper.updatePost(post);
    }

    @Override
    public Post findPostById(Integer post_id) {
        return postMapper.findPostById(post_id);
    }

    @Override
    public List<Post> searchPosts(Post post) {
        return postMapper.searchPosts(post);
    }

    @Override
    public List<Post> searchPostsByStr(String searchStr) {
        return postMapper.searchPostsByStr(searchStr);
    }

    @Override
    public void deletePosts(int[] post_ids) {
        postMapper.deleteCommentOfPosts(post_ids);
        postMapper.deleteUserLikePosts(post_ids);
        postMapper.deleteProductPosts(post_ids);
        postMapper.deletePosts(post_ids);
    }

    @Override
    public void likePost(Integer post_id,Integer user_id) {
        postMapper.likePost(post_id, user_id);
        postMapper.addPostLike(post_id);
    }

    @Override
    public void unlikePost(Integer post_id,Integer user_id) {
        postMapper.unlikePost(post_id, user_id);
        postMapper.reducePostLike(post_id);
    }

    @Override
    public List<Post> findUserLikes(Integer user_id) {
        return postMapper.findUserLikes(user_id);
    }

    @Override
    public Boolean hasUserLikedPost(Integer postId, Integer userId) {
        return postMapper.hasUserLikedPost(postId, userId);
    }

    @Override
    public void addProductPost(Post post) {
        postMapper.addProductPost(post);
    }

    @Override
    public void updateProductPost(Post post) {
        postMapper.updateProductPost(post);
    }
}
