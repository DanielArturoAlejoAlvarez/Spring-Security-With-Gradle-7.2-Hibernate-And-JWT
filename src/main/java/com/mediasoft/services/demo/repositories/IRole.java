package com.mediasoft.services.demo.repositories;

import com.mediasoft.services.demo.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRole extends CrudRepository<Role,Long> {
    
}
