package com.modasby.sparkusbackend.controller;


import com.modasby.sparkusbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final UserService userService;

    @Autowired
    public SearchController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{term}")
    public ResponseEntity<?> getUserByWord(@PathVariable String term) {
        return ResponseEntity.ok(userService.findUsersByTerm(term));
    }
}
