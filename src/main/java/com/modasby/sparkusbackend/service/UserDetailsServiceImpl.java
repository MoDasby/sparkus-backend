package com.modasby.sparkusbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String credential) throws UsernameNotFoundException {
        if (userService.existsByUsername(credential)) {
            com.modasby.sparkusbackend.model.User user = userService.findByUsername(credential);

            return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
        } else if (userService.existsByEmail(credential)) {
            com.modasby.sparkusbackend.model.User user = userService.findByEmail(credential);

            return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Usuario ou senha incorreto");
        }
    }
}
