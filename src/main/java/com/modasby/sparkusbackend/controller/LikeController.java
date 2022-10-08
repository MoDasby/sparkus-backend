package com.modasby.sparkusbackend.controller;

import com.modasby.sparkusbackend.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PutMapping("/add/{id}")
    public ResponseEntity<Integer> addLike(@PathVariable String id, @RequestHeader("Authorization") String tokenHeader) {
        return ResponseEntity.ok(likeService.addLike(id, tokenHeader));
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<Integer> removeLike(@PathVariable String id, @RequestHeader("Authorization") String tokenHeader) {
        return ResponseEntity.ok(likeService.removeLike(id, tokenHeader));
    }
}
