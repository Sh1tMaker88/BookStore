package com.security.userDao;

import com.security.userModel.User;

import java.util.List;
import java.util.Optional;

public interface IUserDao {

    public User findByUserName(String username);

    public List<User> findAllUsers();

    public void createUser(User user);

    public User findById(Long id);

    boolean deleteUserById(Long userId);
}
