package com.modasby.sparkusbackend.controller;

import com.modasby.sparkusbackend.dto.Post.PostDto;
import com.modasby.sparkusbackend.dto.Post.PostResponseDto;
import com.modasby.sparkusbackend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> addPost(@RequestBody PostDto post, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(postService.addPost(post, token));
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<?> addLike(@PathVariable("postId") String postId, @RequestHeader("Authorization") String token) {
        postService.addLike(postId, token);

        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/like/{postId}")
    public ResponseEntity<?> removeLike(@PathVariable("postId") String postId, @RequestHeader("Authorization") String token) {
        postService.removeLike(postId, token);

        return ResponseEntity.status(204).build();
    }
}
