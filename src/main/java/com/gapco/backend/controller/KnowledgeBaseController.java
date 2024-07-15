package com.gapco.backend.controller;


import com.gapco.backend.dto.KnowledgeBaseCreateDTO;
import com.gapco.backend.dto.KnowledgeBaseUpdateDTO;
import com.gapco.backend.response.CustomApiResponse;
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
@RequestMapping(AppConstants.BASE_URI+"/knowledge")
@Tag(name = "KnowledgeBaseController", description = "Operations for knowledgeBase management")
public class KnowledgeBaseController {
    private final KnowledgeBaseService knowledgeBaseService;

    @Operation(
            summary = "Adding a knowledge base"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<CustomApiResponse<Object>> AddKnowledgeBase(@Valid @ModelAttribute KnowledgeBaseCreateDTO knowledgeBaseCreateDTO){
        log.info("KnowledgeBaseController::AddKnowledgeBase Execution started");
        return new ResponseEntity<>(knowledgeBaseService.addNewKnowledgeBase(knowledgeBaseCreateDTO), HttpStatus.OK);
    }


    @Operation(
            summary = "Get all knowledge bases"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping()
    public ResponseEntity<CustomApiResponse<Object>> getAll(
            @Parameter(description = AppConstants.PAGE_NUMBER_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @Parameter(description = AppConstants.PAGE_SIZE_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @Parameter(description = AppConstants.SORT_BY_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sort,
            @Parameter(description = AppConstants.SORT_DIRECTION_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String dir,
            @Parameter(description = AppConstants.LANGUAGE_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_LANGUAGE) String lan
    ){
        log.info("KnowledgeBaseController::getAll Execution started");
        return new ResponseEntity<>(knowledgeBaseService.getAll(page,size,sort,dir,lan), HttpStatus.OK);
    }

    @Operation(
            summary = "get blog details"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> getDetails(
            @Parameter(description = "Id of the knowledge") @PathVariable Long id
    ){
        log.info("KnowledgeBaseController::getDetails Execution started");
        return new ResponseEntity<>(knowledgeBaseService.view(id), HttpStatus.OK);
    }


    @Operation(
            summary = "update knowledge base",
            description = "update knowledge base"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PutMapping(value="/{id}",consumes = "multipart/form-data")
    public ResponseEntity<CustomApiResponse<Object>> update(
            @Parameter(description = "Id of the blog post") @PathVariable Long id, @ModelAttribute KnowledgeBaseUpdateDTO knowledgeBaseCreateDTO
    ){
        log.info("KnowledgeBaseController::update Execution started");
        return new ResponseEntity<>(knowledgeBaseService.update(id,knowledgeBaseCreateDTO), HttpStatus.OK);
    }
}
