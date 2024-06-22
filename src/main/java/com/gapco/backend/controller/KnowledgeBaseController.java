package com.gapco.backend.controller;


import com.gapco.backend.dto.KnowledgeBaseCreateDTO;
import com.gapco.backend.dto.TeamMemberCreateDTO;
import com.gapco.backend.repository.KnowledgeBaseRepository;
import com.gapco.backend.response.CustomApiResponse;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @PostMapping
    public ResponseEntity<CustomApiResponse<Object>> AddKnowledgeBase(@Valid @RequestBody KnowledgeBaseCreateDTO knowledgeBaseCreateDTO){
        log.info("KnowledgeBaseController::AddKnowledgeBase Execution started");
        return new ResponseEntity<>(knowledgeBaseService.addNewKnowledgeBase(knowledgeBaseCreateDTO), HttpStatus.OK);
    }
}
