package com.gapco.backend.controller;


import com.gapco.backend.dto.CompanyCreateDTO;
import com.gapco.backend.dto.InstitutionCreateDTO;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.service.CompanyService;
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
@RequestMapping(AppConstants.BASE_URI+"/company")
@Tag(name = "CompanyController", description = "Operations for company management")
public class CompanyController {


    private final CompanyService companyService;

    @Operation(
            summary = "Create company"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping
    public ResponseEntity<CustomApiResponse<Object>> createCompany(@ModelAttribute CompanyCreateDTO companyCreateDTO){
        log.info("CompanyController::createCompany Execution started");
        return new ResponseEntity<>(companyService.createCompany(companyCreateDTO), HttpStatus.OK);
    }

    @Operation(
            summary = "update company",
            description = "update company"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> updateCompany(
            @Parameter(description = "Id of the company") @PathVariable Long id, @ModelAttribute CompanyCreateDTO companyCreateDTO
    ){
        log.info("CompanyController::updateCompany Execution started");
        return new ResponseEntity<>(companyService.updateCompany(id,companyCreateDTO), HttpStatus.OK);
    }


    @Operation(
            summary = "get company details"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> getCompanyDetails(
            @Parameter(description = "Id of the company") @PathVariable Long id
    ){
        log.info("CompanyController::company Execution started");
        return new ResponseEntity<>(companyService.getCompany(id), HttpStatus.OK);
    }


    @Operation(
            summary = "Get all companies"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping("/all")
    public ResponseEntity<CustomApiResponse<Object>> getAllCompanies(
            @Parameter(description = AppConstants.PAGE_NUMBER_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @Parameter(description = AppConstants.PAGE_SIZE_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @Parameter(description = AppConstants.SORT_BY_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sort,
            @Parameter(description = AppConstants.SORT_DIRECTION_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String dir,
            @Parameter(description = AppConstants.LANGUAGE_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_LANGUAGE) String lan
    ){
        log.info("CompanyController::getAllCompanies Execution started");
        return new ResponseEntity<>(companyService.getAllCompanies(page,size,sort,dir,lan), HttpStatus.OK);
    }
}
