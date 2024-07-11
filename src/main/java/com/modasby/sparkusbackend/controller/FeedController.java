package com.modasby.sparkusbackend.controller;

import com.modasby.sparkusbackend.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feedService;

    @Autowired
    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    public ResponseEntity<?> getFeed(@AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();

        System.out.println(username);

        return ResponseEntity.ok(feedService.getFeed(userDetails.getUsername()));
    }
}
