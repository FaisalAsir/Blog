package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BLOG_POST")
public class BlogPost {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true, nullable = false)
    @Type(type = "uuid-char")
    private UUID id;
    @NotNull(message = "Title may not be null")
    private String title;
    @NotNull(message = "body may not be null")
    private String body;
    @NotNull(message = "author may not be null")
    private String author;
}
