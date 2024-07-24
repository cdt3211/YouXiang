package com.cupk.service.serviceImpl;

import com.cupk.mapper.UserMapper;
import com.cupk.pojo.User;
import com.cupk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> findAllUsers() {
        return userMapper.findAllUsers();
    }

    @Override
    public User findUserById(Integer user_id) {
        return userMapper.findUserById(user_id);
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUser(Integer user_id) {
        userMapper.deleteCommentsByUserId(user_id);
        userMapper.deleteCollectsByUserId(user_id);
        userMapper.deleteLikesByUserId(user_id);
        userMapper.deleteUser(user_id);
    }

//    @Override
//    public List<Integer> findCollectIdsByUserId(Integer user_id) {
//        return userMapper.findCollectIdsByUserId(user_id);
//    }
//    @Override
//    public List<Integer> findLikeIdsByUserId(Integer user_id) {
//        return userMapper.findLikeIdsByUserId(user_id);
//    }
//    @Override
//    public void deleteCommentsByUserId(Integer user_id) {
//        userMapper.deleteCommentsByUserId(user_id);
//    }
//    @Override
//    public void deleteCollectsByUserId(Integer user_id) {
//        userMapper.deleteCollectsByUserId(user_id);
//    }
//    @Override
//    public void deleteLikesByUserId(Integer user_id) {
//        userMapper.deletelikesByUserId(user_id);
//    }

    @Override
    public List<User> searchUsers(User user) {
        return userMapper.searchUsers(user);
    }

    @Override
    public List<User> searchUsersByStr(String searchStr) {
        return userMapper.searchUsersByStr(searchStr);
    }

    @Override
    public void deleteUsers(int[] user_ids) {
        userMapper.deleteCommentsByUserIds(user_ids);
        userMapper.deleteCollectsByUserIds(user_ids);
        userMapper.deleteLikesByUserIds(user_ids);
        userMapper.deleteUsers(user_ids);
    }

    @Override
    public User login(String username, String password) {
        return userMapper.login(username, password);
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }
}

