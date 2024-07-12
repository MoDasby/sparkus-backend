package com.modasby.sparkusbackend.model;

import com.modasby.sparkusbackend.dto.Post.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity @NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 500)
    private String text;

    private int likes = 0;

    @CreationTimestamp
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    public Post(String text, int likes, User author) {
        this.text = text;
        this.likes = likes;
        this.author = author;
    }

    public Post(PostDto postDto) {
        this.text = postDto.getText();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return likes == post.likes && Objects.equals(text, post.text) && Objects.equals(creationDate, post.creationDate) && Objects.equals(author, post.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, likes, creationDate, author);
    }
}
