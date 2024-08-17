package com.gapco.backend.controller;

import com.gapco.backend.dto.CertificationDTO;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.service.CertificationService;
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
@RequestMapping(AppConstants.BASE_URI+"/certifications")
@Tag(name = "CertificationController", description = "Operations for managing certifications used")

public class CertificationController {
    private final CertificationService certificationService;

    @Operation(
            summary = "Add a new technology"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping(consumes = "*/*")
    public ResponseEntity<CustomApiResponse<Object>> addTechnology(@Valid @ModelAttribute CertificationDTO technology){
        log.info("CertificationController::AddTechnology Execution started");
        return new ResponseEntity<>(certificationService.addCertification(technology), HttpStatus.OK);
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
        log.info("CertificationController::getAll Execution started");
        return new ResponseEntity<>(certificationService.getAll(page,size,sort,dir,lan), HttpStatus.OK);
    }

    @Operation(
            summary = "get certification details"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> getDetails(
            @Parameter(description = "Id of the certification") @PathVariable Long id
    ){
        log.info("CertificationController::getDetails Execution started");
        return new ResponseEntity<>(certificationService.view(id), HttpStatus.OK);
    }


    @Operation(
            summary = "delete certification"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> delete(
            @Parameter(description = "Id of the certification") @PathVariable Long id
    ){
        log.info("CertificationController::delete Execution started");
        return new ResponseEntity<>(certificationService.delete(id), HttpStatus.OK);
    }

    @Operation(
            summary = "delete all technologies"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @DeleteMapping
    public ResponseEntity<CustomApiResponse<Object>> deleteAll(){
        log.info("CertificationController::deleteAll Execution started");
        return new ResponseEntity<>(certificationService.deleteAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "update certification",
            description = "update certification"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PutMapping(value = "/{id}",consumes = "*/*")
    public ResponseEntity<CustomApiResponse<Object>> update(
            @Parameter(description = "Id of the certification") @PathVariable Long id, @ModelAttribute CertificationDTO certification
    ){
        log.info("CertificationController::update Execution started");
        return new ResponseEntity<>(certificationService.update(id,certification), HttpStatus.OK);
    }
}
