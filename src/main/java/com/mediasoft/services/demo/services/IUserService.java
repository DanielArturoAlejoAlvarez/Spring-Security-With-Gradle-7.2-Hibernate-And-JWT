package com.mediasoft.services.demo.services;

import com.mediasoft.services.demo.entities.User;

public interface IUserService {
    Iterable<User> getAllUsers();

    User createUser(User user) throws Exception;

    User findById(Long id) throws Exception;

    User updateUser(User user) throws Exception;
}
