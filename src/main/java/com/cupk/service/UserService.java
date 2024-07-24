package com.cupk.service;

import com.cupk.pojo.User;

import java.util.List;

public interface UserService {
     List<User> findAllUsers();//查询所有用户
    public User findUserById(Integer user_id);//根据Uid查询用户
    public void addUser(User user);//添加用户
    public void updateUser(User user);//更新用户
    public void deleteUser(Integer user_id);//删除用户

//    public List<Integer> findCollectIdsByUserId(Integer user_id);//根据Uid查询收藏的文章id
//    public List<Integer> findLikeIdsByUserId(Integer user_id);//根据Uid查询点赞的文章id
//    void deleteCommentsByUserId(Integer user_id);
//
//    void deleteCollectsByUserId(Integer user_id);
//
//    void deleteLikesByUserId(Integer user_id);

    public List<User> searchUsers(User user);//多条件查询用户
    public List<User> searchUsersByStr(String searchStr);//全局模糊查询用户
    public void deleteUsers(int[] user_ids);//批量删除用户
    public User login(String username, String password); // 添加登录方法
    public User findUserByUsername(String username);
    public User findUserByEmail(String email);
}
