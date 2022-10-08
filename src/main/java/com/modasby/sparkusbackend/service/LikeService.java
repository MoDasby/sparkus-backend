package com.modasby.sparkusbackend.service;

import com.modasby.sparkusbackend.config.JwtTokenUtil;
import com.modasby.sparkusbackend.model.Post;
import com.modasby.sparkusbackend.model.User;
import com.modasby.sparkusbackend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    private final PostRepository postRepository;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    @Autowired
    public LikeService(PostRepository postRepository, JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.postRepository = postRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    public int addLike(String postId, String tokenHeader) {
        Post post = postRepository.findById(postId).get();

        String token = getTokenString(tokenHeader);
        String authorCredential = jwtTokenUtil.getCredentialFromToken(token);

        User user = userService.findByUsername(authorCredential);

        List<Post> likedPosts = user.getLikedPosts();
        likedPosts.add(post);

        user.setLikedPosts(likedPosts);

        post.setLikes(post.getLikes() +1);

        postRepository.save(post);
        userService.save(user);

        return post.getLikes();
    }

    public int removeLike(String postId, String tokenHeader) {
        Post post = postRepository.findById(postId).get();

        String token = getTokenString(tokenHeader);
        User user = getUserByToken(token);

        List<Post> likedPosts = user.getLikedPosts();
         if (likedPosts.remove(post)) {

             post.setLikes(post.getLikes() -1);

             postRepository.save(post);
         };

        return post.getLikes();
    }

    private String getTokenString(String token) {
        return token.substring(7);
    }

    private User getUserByToken(String token) {
        String authorCredential = jwtTokenUtil.getCredentialFromToken(token);

        return userService.findByUsername(authorCredential);
    }
}
