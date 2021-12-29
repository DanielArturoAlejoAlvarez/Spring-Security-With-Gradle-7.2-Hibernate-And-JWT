package com.mediasoft.services.demo.services;

import com.mediasoft.services.demo.entities.User;
import com.mediasoft.services.demo.repositories.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private IUser repo;

    @Override
    public Iterable<User> getAllUsers() {
        return repo.findAll();
    }

    private boolean checkUsernameExists(User user) throws Exception {
        Optional<User> userFound = repo.findByUsername(user.getUsername());
        if (userFound.isPresent()) {
            throw new Exception("User not available!");
        }
        return false;
    }
}
