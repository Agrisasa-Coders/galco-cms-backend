package com.gapco.backend.service;

import com.gapco.backend.dto.KnowledgeBaseCreateDTO;
import com.gapco.backend.entity.Blog;
import com.gapco.backend.entity.KnowledgeBase;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.BlogRepository;
import com.gapco.backend.repository.ServiceRepository;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final ServiceRepository serviceRepository;

    public CustomApiResponse<Object> addBlogPost(KnowledgeBaseCreateDTO blog){
        log.info("BlogService::addBlogPost Execution started");

        Optional<com.gapco.backend.entity.Service> serviceOptional = serviceRepository.findById(blog.getServiceId());

        if(serviceOptional.isPresent()){
            Blog newBlog = new Blog();
            newBlog.setDescription(blog.getDescription());
            newBlog.setTitle(blog.getTitle());

            com.gapco.backend.entity.Service service = serviceOptional.get();

            newBlog.setService(service);

            blogRepository.save(newBlog);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("New Blog post has been successfully created");
            customApiResponse.setData(newBlog);

            log.info("BlogService::addBlogPost Execution ended");
            return customApiResponse;
        } else {

            throw new EntityNotFoundException("Service with specified id is not found");
        }
    }


    public CustomApiResponse<Object> getAll(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Blog> pageableBlogs = blogRepository.findAll(pageable);

        List<Blog> blogs = pageableBlogs.getContent();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                pageableBlogs.getTotalElements(),
                pageableBlogs.getTotalPages(),
                pageableBlogs.getNumber()

        );
        customApiResponse.setData(blogs);
        return customApiResponse;
    }

    public CustomApiResponse<Object> view(Long id) {

        Optional<Blog> checkBlog = blogRepository.findById(id);

        if(checkBlog.isPresent()){


            Blog blogDetails = checkBlog.get();
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Record Founds");
            customApiResponse.setData(blogDetails);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Blog Post not found");
        }
    }

    public CustomApiResponse<Object> update(Long id,KnowledgeBaseCreateDTO blog) {

        Optional<Blog> findBlog = blogRepository.findById(id);

        if(findBlog.isPresent()){

            Optional<com.gapco.backend.entity.Service> serviceOptional = serviceRepository.findById(blog.getServiceId());

            if(serviceOptional.isPresent()){
                Blog updatedBlogPost = findBlog.get();
                updatedBlogPost.setDescription(blog.getDescription());
                updatedBlogPost.setTitle(blog.getTitle());

                com.gapco.backend.entity.Service service = serviceOptional.get();

                updatedBlogPost.setService(service);

                blogRepository.save(updatedBlogPost);

                CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Blog  post has been successfully updated");
                customApiResponse.setData(updatedBlogPost);

                log.info("BlogService::update Execution ended");
                return customApiResponse;
            } else {

                throw new EntityNotFoundException("Service with specified id is not found");
            }

        } else {
            throw new EntityNotFoundException("Blog Post not found");
        }

    }

}
