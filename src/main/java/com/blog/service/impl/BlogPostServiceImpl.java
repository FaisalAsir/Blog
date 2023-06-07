package com.blog.service.impl;

import com.blog.entity.BlogPost;
import com.blog.exception.ResourceNotFound;
import com.blog.repository.BlogPostRepository;
import com.blog.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class BlogPostServiceImpl implements BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Override
    public BlogPost findById(String id) {
        return blogPostRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ResourceNotFound("Data Tidak Ditemukan!"));
    }

    @Override
    public Page<BlogPost> findAll(PageRequest pageRequest) {
        return blogPostRepository.findAll(pageRequest);
    }

    @Override
    public BlogPost save(BlogPost blogPost) {
        return blogPostRepository.save(blogPost);
    }

    @Override
    public BlogPost update(String id, BlogPost blogPost) {
        return blogPostRepository.findById(UUID.fromString(id))
                .map(blogPostUpdate -> {
                    blogPostUpdate.setTitle(blogPost.getTitle());
                    blogPostUpdate.setAuthor(blogPost.getAuthor());
                    blogPostUpdate.setBody(blogPost.getBody());
                    return blogPostRepository.save(blogPostUpdate);
                })
                .orElseThrow(() -> new ResourceNotFound("Data Tidak Ditemukan!"));
    }

    @Override
    public void delete(String id) {
        BlogPost blogPost = blogPostRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFound("Data Tidak Ditemukan!"));
        blogPostRepository.delete(blogPost);
    }
}
