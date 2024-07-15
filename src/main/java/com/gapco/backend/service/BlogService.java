package com.gapco.backend.service;

import com.gapco.backend.dto.BlogCreateDTO;
import com.gapco.backend.dto.BlogUpdateDTO;
import com.gapco.backend.entity.Blog;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.BlogRepository;
import com.gapco.backend.repository.ServiceRepository;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.util.AppConstants;
import com.gapco.backend.util.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final ServiceRepository serviceRepository;
    private final StorageService storageService;

    public CustomApiResponse<Object> addBlogPost(BlogCreateDTO blog){
        log.info("BlogService::addBlogPost Execution started");

        Optional<com.gapco.backend.entity.Service> serviceOptional = serviceRepository.findById(blog.getServiceId());

        if(serviceOptional.isPresent()){
            Blog newBlog = new Blog();
            newBlog.setDescription(blog.getDescription());
            newBlog.setTitle(blog.getTitle());
            newBlog.setQuote(blog.getQuote());
            newBlog.setSubTitle(blog.getSubTitle());
            newBlog.setIntroduction(blog.getIntroduction());

            com.gapco.backend.entity.Service service = serviceOptional.get();

            newBlog.setService(service);

            String filePath = storageService.storeFileToFileSystem(
                    blog.getPhoto(),
                    blog.getPhoto().getOriginalFilename()
            );

            newBlog.setPhotoUrl(Helper.getUploadedPath(filePath));

            if(!(blog.getLanguage() == null || blog.getLanguage() == "")){
                newBlog.setLanguage(blog.getLanguage());
            }

            blogRepository.save(newBlog);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("New Blog post has been successfully created");
            customApiResponse.setData(newBlog);

            log.info("BlogService::addBlogPost Execution ended");
            return customApiResponse;
        } else {

            throw new EntityNotFoundException("Service with specified id is not found");
        }
    }


    public CustomApiResponse<Object> getAll(int page, int size, String sortBy, String sortDir,String language) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        //Page<Blog> pageableBlogs = blogRepository.findAll(pageable);
        Page<Blog> pageableBlogs = blogRepository.getAll(language,pageable);

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

    public CustomApiResponse<Object> update(Long id, BlogUpdateDTO blog) {

        Optional<Blog> findBlog = blogRepository.findById(id);

        if(findBlog.isPresent()){

            Optional<com.gapco.backend.entity.Service> serviceOptional = serviceRepository.findById(blog.getServiceId());

            if(serviceOptional.isPresent()){
                Blog updatedBlogPost = findBlog.get();
                updatedBlogPost.setDescription(blog.getDescription());
                updatedBlogPost.setTitle(blog.getTitle());
                updatedBlogPost.setQuote(blog.getQuote());
                updatedBlogPost.setSubTitle(blog.getSubTitle());
                updatedBlogPost.setIntroduction(blog.getIntroduction());

                com.gapco.backend.entity.Service service = serviceOptional.get();

                updatedBlogPost.setService(service);

                if(blog.getPhoto() != null){

                    String filePath = storageService.storeFileToFileSystem(
                            blog.getPhoto(),
                            blog.getPhoto().getOriginalFilename()
                    );

                    updatedBlogPost.setPhotoUrl(Helper.getUploadedPath(filePath));
                }


                if(!(blog.getLanguage() == null || blog.getLanguage() == "")){
                    updatedBlogPost.setLanguage(blog.getLanguage());
                }

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
