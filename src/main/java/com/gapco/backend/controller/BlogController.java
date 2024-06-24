package com.gapco.backend.controller;


import com.gapco.backend.dto.BlogCreateDTO;
import com.gapco.backend.dto.BlogUpdateDTO;
import com.gapco.backend.dto.KnowledgeBaseCreateDTO;
import com.gapco.backend.entity.Blog;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.service.BlogService;
import com.gapco.backend.service.KnowledgeBaseService;
import com.gapco.backend.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(AppConstants.BASE_URI+"/blog")
@Tag(name = "BlogController", description = "Operations for blog management")
public class BlogController {

    private final BlogService blogService;

    @Operation(
            summary = "Adding a knowledge base"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping
    public ResponseEntity<CustomApiResponse<Object>> AddBlogPost(@Valid @ModelAttribute BlogCreateDTO blog){
        log.info("BlogController::AddBlogPost Execution started");
        return new ResponseEntity<>(blogService.addBlogPost(blog), HttpStatus.OK);
    }


    @Operation(
            summary = "Get all blog posts"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping()
    public ResponseEntity<CustomApiResponse<Object>> getAll(
            @Parameter(description = AppConstants.PAGE_NUMBER_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @Parameter(description = AppConstants.PAGE_SIZE_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @Parameter(description = AppConstants.SORT_BY_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sort,
            @Parameter(description = AppConstants.SORT_DIRECTION_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String dir
    ){
        log.info("BlogController::getAll Execution started");
        return new ResponseEntity<>(blogService.getAll(page,size,sort,dir), HttpStatus.OK);
    }

    @Operation(
            summary = "get blog details"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> getDetails(
            @Parameter(description = "Id of the blog") @PathVariable Long id
    ){
        log.info("BlogController::CompetitionController Execution started");
        return new ResponseEntity<>(blogService.view(id), HttpStatus.OK);
    }

    @Operation(
            summary = "update blog post",
            description = "update blog post"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> update(
            @Parameter(description = "Id of the blog post") @PathVariable Long id, @ModelAttribute BlogUpdateDTO blog
    ){
        log.info("BlogController::update Execution started");
        return new ResponseEntity<>(blogService.update(id,blog), HttpStatus.OK);
    }
}
