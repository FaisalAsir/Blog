package com.blog.service.impl;

import com.blog.entity.BlogPost;
import com.blog.exception.ResourceNotFound;
import com.blog.repository.BlogPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class BlogPostServiceImplTest {
    @Mock
    private BlogPostRepository blogPostRepository;

    @InjectMocks
    private BlogPostServiceImpl blogPostService;

    private BlogPost testBlogPost;
    private UUID testBlogPostId;

    @BeforeEach
    public void setup() {
        testBlogPostId = UUID.randomUUID();
        testBlogPost = new BlogPost();
        testBlogPost.setId(testBlogPostId);
        testBlogPost.setTitle("Test Blog Post");
        testBlogPost.setAuthor("John Doe");
        testBlogPost.setBody("This is a test blog post.");
    }
    @Test
    public void findById_ValidId_ReturnsBlogPost() {
        given(blogPostRepository.findById(any(UUID.class))).willReturn(Optional.of(testBlogPost));

        BlogPost result = blogPostService.findById(testBlogPostId.toString());

        assertThat(result).isNotNull();
        assertThat(testBlogPostId).isEqualTo(result.getId());
        assertThat(testBlogPost.getTitle()).isEqualTo(result.getTitle());
        assertThat(testBlogPost.getAuthor()).isEqualTo(result.getAuthor());
        assertThat(testBlogPost.getBody()).isEqualTo(result.getBody());

        verify(blogPostRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void findById_InvalidId_ThrowsResourceNotFound() {
        given(blogPostRepository.findById(any(UUID.class))).willReturn(Optional.empty());

        assertThatThrownBy(() -> blogPostService.findById(testBlogPostId.toString()))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessage("Data Tidak Ditemukan!");

        verify(blogPostRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void findAll_ReturnsPageOfBlogPosts() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<BlogPost> mockPage = Mockito.mock(Page.class);
        given(blogPostRepository.findAll(pageRequest)).willReturn(mockPage);

        Page<BlogPost> result = blogPostService.findAll(pageRequest);

        assertThat(result).isNotNull().isEqualTo(mockPage);

        verify(blogPostRepository, times(1)).findAll(pageRequest);
    }

    @Test
    public void save_ReturnsSavedBlogPost() {
        given(blogPostRepository.save(any(BlogPost.class))).willReturn(testBlogPost);

        BlogPost result = blogPostService.save(testBlogPost);

        assertThat(result).isNotNull();
        assertThat(testBlogPostId).isEqualTo(result.getId());
        assertThat(testBlogPost.getTitle()).isEqualTo(result.getTitle());
        assertThat(testBlogPost.getAuthor()).isEqualTo(result.getAuthor());
        assertThat(testBlogPost.getBody()).isEqualTo(result.getBody());

        verify(blogPostRepository, times(1)).save(any(BlogPost.class));
    }
    @Test
    public void update_ValidId_ReturnsUpdatedBlogPost() {
        String newTitle = "Updated Blog Post";
        String newAuthor = "Jane Smith";
        String newBody = "This blog post has been updated.";

        BlogPost updatedBlogPost = new BlogPost();
        updatedBlogPost.setId(testBlogPostId);
        updatedBlogPost.setTitle(newTitle);
        updatedBlogPost.setAuthor(newAuthor);
        updatedBlogPost.setBody(newBody);
        given(blogPostRepository.findById(any(UUID.class))).willReturn(Optional.of(testBlogPost));
        given(blogPostRepository.save(any(BlogPost.class))).willReturn(updatedBlogPost);

        BlogPost result = blogPostService.update(testBlogPostId.toString(), updatedBlogPost);

        assertThat(result).isNotNull();
        assertThat(testBlogPostId).isEqualTo(result.getId());
        assertThat(testBlogPost.getTitle()).isEqualTo(result.getTitle());
        assertThat(testBlogPost.getAuthor()).isEqualTo(result.getAuthor());
        assertThat(testBlogPost.getBody()).isEqualTo(result.getBody());

        verify(blogPostRepository, times(1)).findById(any(UUID.class));
        verify(blogPostRepository, times(1)).save(any(BlogPost.class));
    }
    @Test
    public void update_InvalidId_ThrowsResourceNotFound() {
        given(blogPostRepository.findById(any(UUID.class))).willReturn(Optional.empty());

        assertThatThrownBy(() -> blogPostService.update(testBlogPostId.toString(), testBlogPost))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessage("Data Tidak Ditemukan!");

        verify(blogPostRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void delete_ValidId_DeletesBlogPost() {
        given(blogPostRepository.findById(any(UUID.class))).willReturn(Optional.of(testBlogPost));

        blogPostService.delete(testBlogPostId.toString());

        verify(blogPostRepository, times(1)).findById(any(UUID.class));
        verify(blogPostRepository, times(1)).delete(any(BlogPost.class));
    }

    @Test
    public void delete_InvalidId_ThrowsResourceNotFound() {
        given(blogPostRepository.findById(any(UUID.class))).willReturn(Optional.empty());

        assertThatThrownBy(() -> blogPostService.delete(testBlogPostId.toString()))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessage("Data Tidak Ditemukan!");

        verify(blogPostRepository, times(1)).findById(any(UUID.class));
    }
}