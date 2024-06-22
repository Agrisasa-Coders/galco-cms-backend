package com.gapco.backend.controller;


import com.gapco.backend.dto.ServiceCreateDTO;
import com.gapco.backend.dto.TeamMemberCreateDTO;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.service.ServiceImpl;
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
import org.springframework.web.bind.annotation.RequestMapping;

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
    @PostMapping
    public ResponseEntity<CustomApiResponse<Object>> createService(@Valid @ModelAttribute ServiceCreateDTO serviceCreateDTO){
        log.info("ServiceController::createService Execution started");
        return new ResponseEntity<>(service.addService(serviceCreateDTO), HttpStatus.OK);
    }
}
