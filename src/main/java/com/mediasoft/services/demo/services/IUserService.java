package com.mediasoft.services.demo.services;

import com.mediasoft.services.demo.entities.User;

public interface IUserService {
    public Iterable<User> getAllUsers();
}
