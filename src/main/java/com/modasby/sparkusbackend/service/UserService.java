package com.modasby.sparkusbackend.service;

import com.modasby.sparkusbackend.dto.Post.PostResponseDto;
import com.modasby.sparkusbackend.dto.User.UserDto;
import com.modasby.sparkusbackend.dto.User.UserResponseDto;
import com.modasby.sparkusbackend.dto.UserDetails.UserDetailsResponseDto;
import com.modasby.sparkusbackend.exception.BadRequestException;
import com.modasby.sparkusbackend.exception.EntityNotFoundException;
import com.modasby.sparkusbackend.exception.UserOrEmailAlreadyExistsException;
import com.modasby.sparkusbackend.model.Post;
import com.modasby.sparkusbackend.model.User;
import com.modasby.sparkusbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto addUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername()) || userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserOrEmailAlreadyExistsException("Usuário ou email já existe");
        }

        User user = new User(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return new UserResponseDto(userRepository.save(user));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public UserDetailsResponseDto getUserDetails(String username, String searcherUsername) {
        User user = this.findByUsername(username);

        User user1 = this.findByUsername(searcherUsername);

        return new UserDetailsResponseDto(user, user.getFollowers().contains(user1));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public List<UserResponseDto> findNewUsers(Pageable pageable) {

        return userRepository.findByOrderByCreationDateDesc(pageable)
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<UserResponseDto> findUsersByTerm(String term) {
        List<User> users = userRepository.findAllByUsernameContaining(term);

        return users.stream().map(UserResponseDto::new).collect(Collectors.toList());
    }

    public void follow(String username, String followerUsername) {
        User user = findByUsername(followerUsername);
        User userToFollow = findByUsername(username);

        if (Objects.equals(user.getId(), userToFollow.getId())) {
            throw new BadRequestException("não pode seguir você mesmo");
        }

        if (user.getFollowing().contains(userToFollow) && userToFollow.getFollowers().contains(user)) return;

        user.getFollowing().add(userToFollow);
        userToFollow.getFollowers().add(user);

        save(user);
        save(userToFollow);
    }

    public void stopFollow(String username, String followerUsername) {
        User user = findByUsername(followerUsername);
        User userToUnfollow = findByUsername(username);

        if (Objects.equals(user.getId(), userToUnfollow.getId())) {
            throw new BadRequestException("não pode deixar de seguir você mesmo");
        }

        if (!user.getFollowing().contains(userToUnfollow) && !userToUnfollow.getFollowers().contains(user)) return;

        user.getFollowing().remove(userToUnfollow);
        userToUnfollow.getFollowers().remove(user);

        save(user);
        save(userToUnfollow);
    }

    public List<UserResponseDto> getFollowers(String username) {
        User user = findByUsername(username);

        return user.getFollowers().stream().map(UserResponseDto::new).collect(Collectors.toList());
    }

    public List<UserResponseDto> getFollowing(String username) {
        User user = findByUsername(username);

        return user.getFollowing().stream().map(UserResponseDto::new).collect(Collectors.toList());
    }

    public List<PostResponseDto> getUserLikedPosts(String username) {

        User user = findByUsername(username);

        List<Post> likedPosts = user.getLikedPosts();

        return likedPosts
                .stream()
                .map(p -> new PostResponseDto(p, true))
                .collect(Collectors.toList());
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
