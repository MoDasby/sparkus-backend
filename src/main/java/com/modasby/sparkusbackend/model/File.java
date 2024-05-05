package com.modasby.sparkusbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity@AllArgsConstructor @NoArgsConstructor @Getter @Setter
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
