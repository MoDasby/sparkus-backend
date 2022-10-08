package com.modasby.sparkusbackend.service;

import com.modasby.sparkusbackend.dto.User.UserDto;
import com.modasby.sparkusbackend.dto.User.UserResponseDto;
import com.modasby.sparkusbackend.dto.UserDetails.UserDetailsResponseDto;
import com.modasby.sparkusbackend.exception.UserNotFoundException;
import com.modasby.sparkusbackend.model.Post;
import com.modasby.sparkusbackend.model.User;
import com.modasby.sparkusbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserResponseDto addUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) throw new RuntimeException("usuario ja existe");
        else if (userRepository.existsByEmail(userDto.getEmail())) throw new RuntimeException("email ja existe");

        User user = new User(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreationDate(new Date());

        return new UserResponseDto(userRepository.save(user));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public UserDetailsResponseDto getUserDetails(String username) {
        User user = this.findByUsername(username);

        return new UserDetailsResponseDto(user);
    }

    public User findByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            return userRepository.findByUsername(username);
        } else {
            throw new UserNotFoundException("usuario não encontrado");
        }
    }

    public User findByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            return userRepository.findByEmail(email);
        } else {
            throw new UserNotFoundException("email nao encontrado");
        }
    }

    public List<User> findNewUsers() {

        return userRepository.findByOrderByCreationDateDesc(Pageable.ofSize(3));
    }

    public List<UserResponseDto> findUsersByTerm(String term) {
        List<User> users = userRepository.findAllByUsernameContaining(term);

        return users.stream().map(UserResponseDto::new).collect(Collectors.toList());
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
