package com.gapco.backend.controller;

import com.gapco.backend.dto.PrivacyPolicyDTO;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.service.PrivacyPolicyService;
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
@RequestMapping(AppConstants.BASE_URI+"/privacy-policy")
@Tag(name = "PrivacyPolicyController", description = "Operations for managing technologies used")
public class PrivacyPolicyController {
    private final PrivacyPolicyService privacyPolicyService;

    @Operation(
            summary = "Add a new privacyPolicy"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping(consumes = "*/*")
    public ResponseEntity<CustomApiResponse<Object>> AddPrivacyPolicy(@Valid @ModelAttribute PrivacyPolicyDTO privacyPolicy){
        log.info("PrivacyPolicyController::AddPrivacyPolicy Execution started");
        return new ResponseEntity<>(privacyPolicyService.addPrivacyPolicy(privacyPolicy), HttpStatus.OK);
    }


    @Operation(
            summary = "Get all technologies"
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
        log.info("PrivacyPolicyController::getAll Execution started");
        return new ResponseEntity<>(privacyPolicyService.getAll(page,size,sort,dir,lan), HttpStatus.OK);
    }

    @Operation(
            summary = "get privacyPolicy details"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> getDetails(
            @Parameter(description = "Id of the PrivacyPolicy") @PathVariable Long id
    ){
        log.info("PrivacyPolicyController::getDetails Execution started");
        return new ResponseEntity<>(privacyPolicyService.view(id), HttpStatus.OK);
    }


    @Operation(
            summary = "delete privacyPolicy"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> delete(
            @Parameter(description = "Id of the PrivacyPolicy") @PathVariable Long id
    ){
        log.info("PrivacyPolicyController::delete Execution started");
        return new ResponseEntity<>(privacyPolicyService.delete(id), HttpStatus.OK);
    }

    @Operation(
            summary = "delete all technologies"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @DeleteMapping
    public ResponseEntity<CustomApiResponse<Object>> deleteAll(){
        log.info("PrivacyPolicyController::deleteAll Execution started");
        return new ResponseEntity<>(privacyPolicyService.deleteAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "update privacyPolicy",
            description = "update privacyPolicy"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PutMapping(value = "/{id}",consumes = "*/*")
    public ResponseEntity<CustomApiResponse<Object>> update(
            @Parameter(description = "Id of the privacyPolicy") @PathVariable Long id, @ModelAttribute PrivacyPolicyDTO privacyPolicy
    ){
        log.info("PrivacyPolicyController::update Execution started");
        return new ResponseEntity<>(privacyPolicyService.update(id,privacyPolicy), HttpStatus.OK);
    }
}

