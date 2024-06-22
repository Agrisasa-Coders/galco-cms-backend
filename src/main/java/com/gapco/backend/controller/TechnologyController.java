package com.gapco.backend.controller;


import com.gapco.backend.dto.TeamMemberCreateDTO;
import com.gapco.backend.entity.Technology;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.service.TeamService;
import com.gapco.backend.service.TechnologyService;
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
@RequestMapping(AppConstants.BASE_URI+"/techs")
@Tag(name = "TechnologyController", description = "Operations for managing technologies used")
public class TechnologyController {
    private final TechnologyService technologyService;

    @Operation(
            summary = "Add a new technology"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping
    public ResponseEntity<CustomApiResponse<Object>> AddTechnology(@Valid @RequestBody Technology technology){
        log.info("TechnologyController::AddTechnology Execution started");
        return new ResponseEntity<>(technologyService.addTechnology(technology), HttpStatus.OK);
    }
}
