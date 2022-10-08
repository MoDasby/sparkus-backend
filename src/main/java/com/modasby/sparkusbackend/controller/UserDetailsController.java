package com.modasby.sparkusbackend.controller;

import com.modasby.sparkusbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userDetails")
public class UserDetailsController {

    private final UserService userService;

    @Autowired
    public UserDetailsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserDetails(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserDetails(username));
    }
}
