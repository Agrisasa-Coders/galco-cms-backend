package com.gapco.backend.controller;

import com.gapco.backend.dto.ConfigurationCreateDTO;
import com.gapco.backend.service.ConfigurationService;
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
@RequestMapping(AppConstants.BASE_URI+"/configuration")
@Tag(name = "ConfigurationController", description = "Operations for configuration management")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @Operation(
            summary = "Create Settings for the institution",
            description = "Creating settings(configurations) which will be used by the institution(ZFF)"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping
    public ResponseEntity<CustomApiResponse<Object>> createConfigurationPolicy(@Valid @RequestBody ConfigurationCreateDTO configuration){
        log.info("ConfigurationController::createConfigurationPolicy Execution started");
        return new ResponseEntity<>(configurationService.setConfiguration(configuration), HttpStatus.OK);
    }

    @Operation(
            summary = "Update Settings",
            description = "Update Settings for the institution"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> updateConfigurationPolicy(
            @Parameter(description = "Id of the configuration to update") @PathVariable Integer id, @Valid @RequestBody ConfigurationCreateDTO configuration){
        log.info("ConfigurationController::createConfigurationPolicy Execution started");
        return new ResponseEntity<>(configurationService.updateConfiguration(id,configuration), HttpStatus.OK);
    }
}
