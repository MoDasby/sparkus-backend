package com.modasby.sparkusbackend.controller;

import com.modasby.sparkusbackend.dto.Post.PostDto;
import com.modasby.sparkusbackend.dto.Post.PostResponseDto;
import com.modasby.sparkusbackend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> findPosts(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable
    ) {
        return ResponseEntity.ok(postService.findPosts(userDetails.getUsername(), pageable));
    }

    @GetMapping("/{username}")
    public ResponseEntity<Page<PostResponseDto>> findPostByUsername(
            @PathVariable String username,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                postService.findPostByUser(username, pageable)
        );
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> addPost(
            @RequestBody PostDto post,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(postService.addPost(post, userDetails.getUsername()));
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<?> addLike(
            @PathVariable("postId") String postId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        postService.addLike(postId, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/like/{postId}")
    public ResponseEntity<?> removeLike(
            @PathVariable("postId") String postId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        postService.removeLike(postId, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
