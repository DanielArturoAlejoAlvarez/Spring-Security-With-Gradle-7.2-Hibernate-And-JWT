package com.mediasoft.services.demo.services;

import com.mediasoft.services.demo.entities.User;
import com.mediasoft.services.demo.repositories.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private IUser repo;

    @Override
    public Iterable<User> getAllUsers() {
        return repo.findAll();
    }
}
