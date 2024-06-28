package com.gapco.backend.controller;


import com.gapco.backend.dto.CustomerCreateDTO;
import com.gapco.backend.dto.TeamMemberCreateDTO;
import com.gapco.backend.dto.TeamMemberUpdateDTO;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.service.CustomerService;
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
@RequestMapping(AppConstants.BASE_URI+"/customers")
@Tag(name = "CustomerController", description = "Operations for managing customers")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(
            summary = "Create a new customer"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping
    public ResponseEntity<CustomApiResponse<Object>> addCustomer(@Valid @ModelAttribute CustomerCreateDTO customerCreateDTO){
        log.info("CustomerController::addCustomer Execution started");
        return new ResponseEntity<>(customerService.addCustomer(customerCreateDTO), HttpStatus.OK);
    }



    @Operation(
            summary = "Get all customers"
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
        log.info("CustomerController::getAll Execution started");
        return new ResponseEntity<>(customerService.getAll(page,size,sort,dir,lan), HttpStatus.OK);
    }


    @Operation(
            summary = "get customer details"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> getDetails(
            @Parameter(description = "Id of the customer") @PathVariable Long id
    ){
        log.info("CustomerController::getDetails Execution started");
        return new ResponseEntity<>(customerService.view(id), HttpStatus.OK);
    }


    @Operation(
            summary = "update a customer",
            description = "update a customer"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> update(
            @Parameter(description = "Id of the customer") @PathVariable Long id, @ModelAttribute CustomerCreateDTO customerCreateDTO
    ){
        log.info("CustomerController::update Execution started");
        return new ResponseEntity<>(customerService.updateCustomer(id,customerCreateDTO), HttpStatus.OK);
    }
}
