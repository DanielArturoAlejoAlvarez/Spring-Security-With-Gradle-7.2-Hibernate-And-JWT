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

    @Override
    public User createUser(User user) throws Exception {
        if (checkPasswordValid(user) && checkUsernameAvailable(user)) {
            user = repo.save(user);
        }
        return user;
    }

    @Override
    public User findById(Long id) throws Exception {
        return repo.findById(id).orElseThrow(() -> new Exception("User to not exist!"));
    }

    @Override
    public User updateUser(User fromUser) throws Exception {
        User toUser = findById(fromUser.getId());
        mapUser(fromUser,toUser);
        return repo.save(toUser);
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        User user = findById(id);
        repo.delete(user);
    }

    private boolean checkUsernameAvailable(User user) throws Exception {
        Optional<User> userFound = repo.findByUsername(user.getUsername());
        if (userFound.isPresent()) {
            throw new Exception("User not available!");
        }
        return true;
    }

    private boolean checkPasswordValid(User user) throws Exception {
        if (user.getPasswordConfirmation() == null || user.getPasswordConfirmation().isEmpty()) {
            throw new Exception("Confirm Password is required!");
        }

        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new Exception("Password and confirmation password are not the same");
        }
        return true;
    }

    protected void mapUser(User from, User to) {
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setEmail(from.getEmail());
        to.setUsername(from.getUsername());
        to.setRoles(from.getRoles());
        to.setImgURL(from.getImgURL());
    }
}
