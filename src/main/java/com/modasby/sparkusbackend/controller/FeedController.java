package com.modasby.sparkusbackend.controller;

import com.modasby.sparkusbackend.dto.Feed.FeedResponseDto;
import com.modasby.sparkusbackend.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<FeedResponseDto> getFeed(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable
    ) {

        return ResponseEntity.ok(feedService.getFeed(userDetails.getUsername(), pageable));
    }
}
