package com.gapco.backend.controller;


import com.gapco.backend.dto.GalleryCreateDTO;
import com.gapco.backend.dto.InstitutionCreateDTO;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.service.GalleryService;
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
@RequestMapping(AppConstants.BASE_URI+"/gallery")
@Tag(name = "GalleryController", description = "Operations for gallery management")
public class GalleryController {

    private final GalleryService galleryService;

    @Operation(
            summary = "Add Gallery/Galleries"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping
    public ResponseEntity<CustomApiResponse<Object>> addGalleries(@Valid @ModelAttribute GalleryCreateDTO galleryCreateDTO){
        log.info("GalleryController::addGalleries Execution started");
        return new ResponseEntity<>(galleryService.addGalleries(galleryCreateDTO), HttpStatus.OK);
    }



    @Operation(
            summary = "Get all galleries"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping
    public ResponseEntity<CustomApiResponse<Object>> getAllGalleries(
            @Parameter(description = AppConstants.PAGE_NUMBER_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @Parameter(description = AppConstants.PAGE_SIZE_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @Parameter(description = AppConstants.SORT_BY_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sort,
            @Parameter(description = AppConstants.SORT_DIRECTION_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String dir,
            @Parameter(description = AppConstants.LANGUAGE_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_LANGUAGE) String lan
    ){
        log.info("GalleryController::getAllGalleries Execution started");
        return new ResponseEntity<>(galleryService.getAll(page,size,sort,dir,lan), HttpStatus.OK);
    }


    @Operation(
            summary = "delete all galleries"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @DeleteMapping
    public ResponseEntity<CustomApiResponse<Object>> deleteAll(){
        log.info("GalleryController::deleteAll Execution started");
        return new ResponseEntity<>(galleryService.deleteAll(), HttpStatus.OK);
    }
}
