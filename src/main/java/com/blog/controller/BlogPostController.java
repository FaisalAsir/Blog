package com.blog.controller;

import com.blog.entity.BlogPost;
import com.blog.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("/api/blogpost")
public class BlogPostController {
    @Autowired
    private BlogPostService blogPostService;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public BlogPost createBlogPost(@Valid @RequestBody BlogPost blogPost) {
        return blogPostService.save(blogPost);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BlogPost updateBlogPost(@PathVariable String id, @Valid @RequestBody BlogPost blogPost) {
        return blogPostService.update(id,blogPost);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBlogPost(@PathVariable String id) {
        blogPostService.delete(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BlogPost getBlogPostById(@PathVariable String id) {
        return blogPostService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BlogPost> getAllBlogPost(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id,title") String[] sort){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(parseSortExpressions(sort)));
        return blogPostService.findAll(pageRequest);
    }

    private Sort.Order[] parseSortExpressions(String[] sort) {
        return Arrays.stream(sort)
                .map(expression -> {
                    String[] parts = expression.split(",");
                    String property = parts[0];
                    Sort.Direction direction = parts.length > 1 ? Sort.Direction.fromString(parts[1]) : Sort.Direction.ASC;
                    return new Sort.Order(direction, property);
                })
                .toArray(Sort.Order[]::new);
    }

}
