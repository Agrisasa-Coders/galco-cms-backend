package com.gapco.backend.controller;

import com.gapco.backend.dto.CareerCreateDTO;
import com.gapco.backend.dto.CareerUpdateDTO;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.service.CareerService;
import com.gapco.backend.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(AppConstants.BASE_URI+"/career")
@Tag(name = "CareerController", description = "Operations for career management")
public class CareerController {

    private final CareerService careerService;

    @Operation(
            summary = "Create a career post"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping(consumes = "*/*")
    public ResponseEntity<CustomApiResponse<Object>> createCareerPost(@ModelAttribute CareerCreateDTO careerCreateDTO){

        log.info("CareerController::createCareerPost Execution started");
        return new ResponseEntity<>(careerService.addCareerPost(careerCreateDTO), HttpStatus.OK);
    }

    @Operation(
            summary = "update career",
            description = "update career"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PutMapping(value = "/{id}",consumes = "*/*")
    public ResponseEntity<CustomApiResponse<Object>> updateCareer(
            @Parameter(description = "Id of the career") @PathVariable Long id, @ModelAttribute CareerUpdateDTO careerUpdateDTO
    ){
        log.info("CareerController::updateCareer Execution started");
        return new ResponseEntity<>(careerService.updateCareerPost(id,careerUpdateDTO), HttpStatus.OK);
    }

    @Operation(
            summary = "get career details"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> getCareerDetails(
            @Parameter(description = "Id of the career") @PathVariable Long id
    ){
        log.info("CareerController::getCareerDetails Execution started");
        return new ResponseEntity<>(careerService.view(id), HttpStatus.OK);
    }


    @Operation(
            summary = "Get all careers posts"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping
    public ResponseEntity<CustomApiResponse<Object>> getAllCareers(
            @Parameter(description = AppConstants.PAGE_NUMBER_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @Parameter(description = AppConstants.PAGE_SIZE_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @Parameter(description = AppConstants.SORT_BY_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sort,
            @Parameter(description = AppConstants.SORT_DIRECTION_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String dir,
            @Parameter(description = AppConstants.LANGUAGE_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_LANGUAGE) String lan
    ){
        log.info("CareerController::getAllCareers Execution started");
        return new ResponseEntity<>(careerService.getAll(page,size,sort,dir,lan), HttpStatus.OK);
    }

}
