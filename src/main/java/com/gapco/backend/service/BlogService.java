package com.gapco.backend.service;

import com.gapco.backend.dto.KnowledgeBaseCreateDTO;
import com.gapco.backend.entity.Blog;
import com.gapco.backend.entity.KnowledgeBase;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.BlogRepository;
import com.gapco.backend.repository.ServiceRepository;
import com.gapco.backend.response.CustomApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
