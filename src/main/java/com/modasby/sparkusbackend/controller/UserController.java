package com.modasby.sparkusbackend.controller;

import com.modasby.sparkusbackend.dto.User.UserResponseDto;
import com.modasby.sparkusbackend.dto.UserDetails.UserDetailsResponseDto;
import com.modasby.sparkusbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public ResponseEntity<List<UserResponseDto>> getNewUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.findNewUsers(pageable));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDetailsResponseDto> getUserDetails(
            @PathVariable String username,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(userService.getUserDetails(username, userDetails.getUsername()));
    }

    @GetMapping("/{username}/likes")
    public ResponseEntity<List<?>> getUserLikes(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserLikedPosts(username));
    }

    @GetMapping("/search/{term}")
    public ResponseEntity<List<UserResponseDto>> getUserByWord(@PathVariable String term) {
        return ResponseEntity.ok(userService.findUsersByTerm(term));
    }

    @GetMapping("/{username}/follow")
    public ResponseEntity<?> follow(
            @PathVariable("username") String username,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        userService.follow(username, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).build();
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
    public ResponseEntity<?> stopFollow(
            @PathVariable("username") String username,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        userService.stopFollow(username, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
