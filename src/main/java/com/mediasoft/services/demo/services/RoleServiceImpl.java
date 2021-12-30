package com.mediasoft.services.demo.services;

import com.mediasoft.services.demo.entities.Role;
import com.mediasoft.services.demo.repositories.IRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService{
    @Autowired
    private IRole repo;

    @Override
    public Iterable<Role> getAllRoles() {
        return repo.findAll();
    }
}
