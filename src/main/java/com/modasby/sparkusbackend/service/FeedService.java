package com.modasby.sparkusbackend.service;

import com.modasby.sparkusbackend.dto.Feed.FeedResponseDto;
import com.modasby.sparkusbackend.dto.Post.PostResponseDto;
import com.modasby.sparkusbackend.dto.User.UserResponseDto;
import com.modasby.sparkusbackend.model.Post;
import com.modasby.sparkusbackend.model.User;
import com.modasby.sparkusbackend.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final PostRepository postRepository;

    private final UserService userService;

    @Autowired
    public FeedService(PostRepository postRepository,
                       UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public FeedResponseDto getFeed(String username, Pageable pageable) {
        Page<Post> feedPosts = postRepository.findByOrderByCreationDateDesc(pageable);

        User user = userService.findByUsername(username);
        Function<Post, Boolean> isLiked = (p) -> user.getLikedPosts().contains(p);

        List<User> newUsers = userService.findNewUsers();

        return new FeedResponseDto(
                newUsers.stream().map(UserResponseDto::new).collect(Collectors.toList()),
                feedPosts.map(p -> new PostResponseDto(p, isLiked.apply(p)))
        );
    }
}
