package com.mediasoft.services.demo.services;

import com.mediasoft.services.demo.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{
    @Override
    public Iterable<User> getAllUsers() {
        return null;
    }
}
