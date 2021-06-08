package com.security.userService;

import com.security.userModel.User;

import java.util.List;

public interface IUserService {

    public boolean userExists(String username);

    public void createUser(User user);

    public User getUser(Long id);

    public List<User> getList();
}
