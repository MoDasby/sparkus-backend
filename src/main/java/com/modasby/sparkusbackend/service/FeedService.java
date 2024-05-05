package com.modasby.sparkusbackend.service;

import com.modasby.sparkusbackend.config.JwtTokenUtil;
import com.modasby.sparkusbackend.dto.Feed.FeedResponseDto;
import com.modasby.sparkusbackend.dto.Post.PostResponseDto;
import com.modasby.sparkusbackend.dto.User.UserResponseDto;
import com.modasby.sparkusbackend.model.Post;
import com.modasby.sparkusbackend.model.User;
import com.modasby.sparkusbackend.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final PostRepository postRepository;

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public FeedService(PostRepository postRepository,
                       UserService userService,
                       JwtTokenUtil jwtTokenUtil) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public FeedResponseDto getFeed(String tokenHeader) {
        List<Post> feedPosts = postRepository.findByOrderByCreationDateDesc();

        String token = tokenHeader.substring(7);
        String authorCredential = jwtTokenUtil.getCredentialFromToken(token);

        User user = userService.findByUsername(authorCredential);
        Function<Post, Boolean> isLiked = (p) -> user.getLikedPosts().contains(p);

        List<User> newUsers = userService.findNewUsers();

        return new FeedResponseDto(
                newUsers.stream().map(UserResponseDto::new).collect(Collectors.toList()),
                feedPosts.stream().map(p -> new PostResponseDto(p, isLiked.apply(p))).collect(Collectors.toList())
        );
    }
}
