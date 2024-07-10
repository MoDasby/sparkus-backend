package com.modasby.sparkusbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@Table(name = "images")
public class File {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String name;
    private String contentType;
    private Long size;

    @Lob
    private byte[] data;
}
