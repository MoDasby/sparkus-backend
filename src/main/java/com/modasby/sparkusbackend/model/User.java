package com.modasby.sparkusbackend.model;

import com.modasby.sparkusbackend.dto.User.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.util.*;

@Entity @NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Table(name = "users")
public class User{

    public User(UserDto userDto) {
        this.username = userDto.getUsername();
        this.name = userDto.getName();
        this.iconPath = userDto.getIconPath();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    private String iconPath;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private Date creationDate;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Post> posts = new ArrayList<>();

    @OneToMany
    private List<Post> likedPosts = new ArrayList<>();

    @OneToMany
    private List<User> following = new ArrayList<>();

    @OneToMany
    private List<User> followers = new ArrayList<>();

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id) || username.equals(user.username);
    }
}
