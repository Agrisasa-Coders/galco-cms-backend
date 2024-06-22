package com.gapco.backend.controller;


import com.gapco.backend.dto.KnowledgeBaseCreateDTO;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.service.BlogService;
import com.gapco.backend.service.KnowledgeBaseService;
import com.gapco.backend.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public ResponseEntity<CustomApiResponse<Object>> BlogController(@Valid @RequestBody KnowledgeBaseCreateDTO blog){
        log.info("BlogController::BlogController Execution started");
        return new ResponseEntity<>(blogService.addBlogPost(blog), HttpStatus.OK);
    }
}
