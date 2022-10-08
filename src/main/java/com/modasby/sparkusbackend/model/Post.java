package com.modasby.sparkusbackend.model;

import com.modasby.sparkusbackend.dto.Post.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity @NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "post_id", columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(nullable = false)
    private String text;

    private String imageUrl;

    private int likes = 0;

    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "username")
    private User author;

    public Post(PostDto postDto) {
        this.text = postDto.getText();
    }
}
