package com.gapco.backend.controller;



import com.gapco.backend.dto.ServiceCreateDTO;
import com.gapco.backend.dto.ServiceUpdateDTO;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.service.ServiceImpl;
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
@RequestMapping(AppConstants.BASE_URI+"/services")
@Tag(name = "ServiceController", description = "Operations for managing services")
public class ServiceController {

    private final ServiceImpl service;

    @Operation(
            summary = "Create a service"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping(consumes = "*/*")
    public ResponseEntity<CustomApiResponse<Object>> createService(@Valid @ModelAttribute ServiceCreateDTO serviceCreateDTO){
        log.info("ServiceController::createService Execution started");
        return new ResponseEntity<>(service.addService(serviceCreateDTO), HttpStatus.OK);
    }


    @Operation(
            summary = "Get all services"
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
        log.info("ServiceController::getAll Execution started");
        return new ResponseEntity<>(service.getAll(page,size,sort,dir,lan), HttpStatus.OK);
    }


    @Operation(
            summary = "get service details"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> getDetails(
            @Parameter(description = "Id of the service") @PathVariable Long id
    ){
        log.info("ServiceController::getDetails Execution started");
        return new ResponseEntity<>(service.view(id), HttpStatus.OK);
    }

    @Operation(
            summary = "delete service"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> deleteService(
            @Parameter(description = "Id of the service") @PathVariable Long id
    ){
        log.info("ServiceController::deleteService Execution started");
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }


    @Operation(
            summary = "delete all services"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @DeleteMapping
    public ResponseEntity<CustomApiResponse<Object>> deleteAll(){
        log.info("ServiceController::deleteAll Execution started");
        return new ResponseEntity<>(service.deleteAll(), HttpStatus.OK);
    }


    @Operation(
            summary = "update a service",
            description = "update a service"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PutMapping(value = "/{id}",consumes = "*/*")
    public ResponseEntity<CustomApiResponse<Object>> update(
            @Parameter(description = "Id of the service") @PathVariable Long id, @ModelAttribute ServiceUpdateDTO serviceUpdateDTO
    ){
        log.info("ServiceController::update Execution started");
        return new ResponseEntity<>(service.update(id,serviceUpdateDTO), HttpStatus.OK);
    }
}
