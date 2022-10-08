package com.modasby.sparkusbackend.controller;

import com.modasby.sparkusbackend.dto.Feed.FeedResponseDto;
import com.modasby.sparkusbackend.dto.Post.PostDto;
import com.modasby.sparkusbackend.dto.Post.PostResponseDto;
import com.modasby.sparkusbackend.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:,3000")
@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feedService;

    @Autowired
    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    public ResponseEntity<FeedResponseDto> getFeed(@RequestHeader("Authorization") String tokenHeader) {
        return ResponseEntity.ok(feedService.getFeed(tokenHeader));
    }

    @PostMapping("/post")
    public ResponseEntity<PostResponseDto> addPost(@RequestBody PostDto post, @RequestHeader("Authorization") String tokenHeader) {
        return ResponseEntity.ok(feedService.addPost(post, tokenHeader));
    }
}
