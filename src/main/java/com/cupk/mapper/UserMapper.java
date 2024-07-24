package com.cupk.mapper;

import com.cupk.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    public List<User> findAllUsers();//查询全部用户
    public User findUserById(Integer user_id);//根据Uid查询用户
//    public List<Integer> findCollectIdsByUserId(Integer user_id);//根据Uid查询收藏的文章id
//    public List<Integer> findLikeIdsByUserId(Integer user_id);//根据Uid查询点赞的文章id
    public void addUser(User user);//添加用户
    public void updateUser(User user);//修改用户
    public void deleteUser(Integer user_id);//删除用户
    public void deleteUsers(int[] user_ids);//批量删除用户
    public User login(String username, String password); // 添加登录方法
    public User findUserByUsername(String username); // 根据用户名查询用户
    public User findUserByEmail(String email); // 根据邮箱查询用户
    public List<User> searchUsers(User user);//多条件查询用户
    public List<User> searchUsersByStr(String searchStr);//全局模糊查询用户
    public void deleteCollectsByUserId(Integer userId);
    public void deleteCommentsByUserId(Integer userId);
    public void deleteLikesByUserId(Integer userId);
    public void deleteCommentsByUserIds(int[] user_ids);
    public void deleteCollectsByUserIds(int[] user_ids);
    public void deleteLikesByUserIds(int[] user_ids);
}
