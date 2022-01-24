package com.mediasoft.services.demo.services;

import com.mediasoft.services.demo.entities.Role;
import com.mediasoft.services.demo.repositories.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUser repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.mediasoft.services.demo.entities.User appUser = repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Login user invalid!"));
        Set<GrantedAuthority> grantList = new HashSet<GrantedAuthority>();
        for (Role role: appUser.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getDescription());
            grantList.add(grantedAuthority);
        }
        UserDetails user = new User(username, appUser.getPassword(), grantList);
        return user;
    }
}
