package com.modasby.sparkusbackend.model;

import com.modasby.sparkusbackend.dto.User.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "VARCHAR(255)")
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

    @Column(nullable = false)
    private Date creationDate;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Post> posts;

    @OneToMany
    private List<Post> likedPosts;
}
