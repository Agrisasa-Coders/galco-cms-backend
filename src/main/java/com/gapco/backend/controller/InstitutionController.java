package com.gapco.backend.controller;

import com.gapco.backend.dto.InstitutionCreateDTO;
import com.gapco.backend.service.InstitutionService;
import com.gapco.backend.response.CustomApiResponse;
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
@RequestMapping(AppConstants.BASE_URI+"/institution")
@Tag(name = "InstitutionController", description = "Operations for institution management")
public class InstitutionController {

    private final InstitutionService institutionService;

    @Operation(
            summary = "Create institution"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<CustomApiResponse<Object>> createInstitution(@Valid @ModelAttribute InstitutionCreateDTO institution){
        log.info("InstitutionController::createInstitution Execution started");
        return new ResponseEntity<>(institutionService.createInstitution(institution), HttpStatus.OK);
    }

    @Operation(
            summary = "update institution",
            description = "update institution"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PutMapping(value="/{id}",consumes = "multipart/form-data")
    public ResponseEntity<CustomApiResponse<Object>> updateInstitution(
            @Parameter(description = "Id of the institution") @PathVariable Long id, @ModelAttribute InstitutionCreateDTO institution
    ){
        log.info("InstitutionController::updateInstitution Execution started");
        return new ResponseEntity<>(institutionService.updateInstitution(id,institution), HttpStatus.OK);
    }

    @Operation(
            summary = "get institution details"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> getInstitution(
            @Parameter(description = "Id of the institution") @PathVariable Long id
    ){
        log.info("InstitutionController::getInstitution Execution started");
        return new ResponseEntity<>(institutionService.getInstitution(id), HttpStatus.OK);
    }


    @Operation(
            summary = "Get all institutions"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping("/all")
    public ResponseEntity<CustomApiResponse<Object>> getAllInstitutions(
            @Parameter(description = AppConstants.PAGE_NUMBER_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @Parameter(description = AppConstants.PAGE_SIZE_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @Parameter(description = AppConstants.SORT_BY_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sort,
            @Parameter(description = AppConstants.SORT_DIRECTION_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String dir,
            @Parameter(description = AppConstants.LANGUAGE_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_LANGUAGE) String lan
    ){
        log.info("InstitutionController::getAllInstitutions Execution started");
        return new ResponseEntity<>(institutionService.getAllInstitutions(page,size,sort,dir,lan), HttpStatus.OK);
    }


    @Operation(
            summary = "Delete all institutions"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @DeleteMapping
    public ResponseEntity<CustomApiResponse<Object>> deleteAllInstitutions(){
        log.info("InstitutionController::deleteAllInstitutions Execution started");
        return new ResponseEntity<>(institutionService.deleteAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete an institution"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> deleteInstitution(
            @Parameter(description = "Id of the institution") @PathVariable Long id
    ){
        log.info("InstitutionController::deleteInstitution Execution started");
        return new ResponseEntity<>(institutionService.deleteInstitution(id), HttpStatus.OK);
    }

}
