package com.blog.service;

import com.blog.entity.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface BlogPostService {
    BlogPost findById(String id);
    Page<BlogPost> findAll(PageRequest pageRequest);
    BlogPost save(BlogPost blogPost);
    BlogPost update(String id, BlogPost blogPost);
    void delete(String id);
}
