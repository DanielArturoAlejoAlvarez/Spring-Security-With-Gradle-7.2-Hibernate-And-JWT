package com.mediasoft.services.demo.repositories;

import com.mediasoft.services.demo.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUser extends CrudRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
