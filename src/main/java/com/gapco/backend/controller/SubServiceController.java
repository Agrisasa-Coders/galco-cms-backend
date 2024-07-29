package com.gapco.backend.controller;

import com.gapco.backend.dto.KnowledgeBaseCreateDTO;
import com.gapco.backend.dto.KnowledgeBaseUpdateDTO;
import com.gapco.backend.dto.SubServiceCreateDTO;
import com.gapco.backend.dto.SubServiceUpdateDTO;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.service.SubServiceImpl;
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
@RequestMapping(AppConstants.BASE_URI+"/sub-service")
@Tag(name = "SubServiceController", description = "Operations for sub-service management")
public class SubServiceController {
    private final SubServiceImpl subService;

    @Operation(
            summary = "Adding a SubService"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping(consumes = "*/*")
    public ResponseEntity<CustomApiResponse<Object>> AddSubService(@Valid @ModelAttribute SubServiceCreateDTO subServiceCreateDTO){
        log.info("SubServiceController::AddSubService Execution started");
        return new ResponseEntity<>(subService.addSubService(subServiceCreateDTO), HttpStatus.OK);
    }

    @Operation(
            summary = "Get all sub-services"
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
        log.info("SubServiceController::getAll Execution started");
        return new ResponseEntity<>(subService.getAll(page,size,sort,dir,lan), HttpStatus.OK);
    }


    @Operation(
            summary = "get blog details"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> getDetails(
            @Parameter(description = "Id of the sub-service") @PathVariable Long id
    ){
        log.info("SubServiceController::getDetails Execution started");
        return new ResponseEntity<>(subService.view(id), HttpStatus.OK);
    }

    @Operation(
            summary = "delete a knowledge base"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> delete(
            @Parameter(description = "Id of the sub-service") @PathVariable Long id
    ){
        log.info("SubServiceController::delete Execution started");
        return new ResponseEntity<>(subService.delete(id), HttpStatus.OK);
    }


    @Operation(
            summary = "delete all sub-service"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @DeleteMapping
    public ResponseEntity<CustomApiResponse<Object>> deleteAll(){
        log.info("SubServiceController::deleteAll Execution started");
        return new ResponseEntity<>(subService.deleteAll(), HttpStatus.OK);
    }


    @Operation(
            summary = "update knowledge base",
            description = "update knowledge base"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PutMapping(value="/{id}",consumes = "*/*")
    public ResponseEntity<CustomApiResponse<Object>> update(
            @Parameter(description = "Id of the blog post") @PathVariable Long id, @ModelAttribute SubServiceUpdateDTO subServiceUpdateDTO
    ){
        log.info("KnowledgeBaseController::update Execution started");
        return new ResponseEntity<>(subService.updateSubService(id,subServiceUpdateDTO), HttpStatus.OK);
    }

}
