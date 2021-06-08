package com.security.userDao;

import com.security.userModel.User;

import java.util.List;
import java.util.Optional;

public interface IUserDao {

    public Optional<User> findByUserName(String username);

    public List<User> findAllUsers();
}
