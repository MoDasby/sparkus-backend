package com.modasby.sparkusbackend.controller;

import com.modasby.sparkusbackend.dto.User.UserResponseDto;
import com.modasby.sparkusbackend.dto.UserDetails.UserDetailsResponseDto;
import com.modasby.sparkusbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDetailsResponseDto> getUserDetails(@PathVariable String username, @RequestHeader("Authorization") String tokenHeader) {
        return ResponseEntity.ok(userService.getUserDetails(username, tokenHeader));
    }

    @GetMapping("/{username}/likes")
    public ResponseEntity<List<?>> getUserLikes(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserLikedPosts(username));
    }

    @GetMapping("/search/{term}")
    public ResponseEntity<?> getUserByWord(@PathVariable String term) {
        return ResponseEntity.ok(userService.findUsersByTerm(term));
    }

    @GetMapping("/{username}/follow")
    public ResponseEntity<Integer> follow(@PathVariable("username") String username, @RequestHeader("Authorization") String tokenHeader) {
        userService.follow(username, tokenHeader);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{username}/followers")
    public ResponseEntity<List<UserResponseDto>> getFollowers(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getFollowers(username));
    }

    @GetMapping("/{username}/following")
    public ResponseEntity<List<UserResponseDto>> getFollowing(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getFollowing(username));
    }

    @GetMapping("/{username}/unfollow")
    public ResponseEntity<Integer> stopFollow(@PathVariable("username") String username, @RequestHeader("Authorization") String tokenHeader) {
        userService.stopFollow(username, tokenHeader);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
