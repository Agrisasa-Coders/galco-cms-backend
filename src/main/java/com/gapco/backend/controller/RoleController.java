package com.gapco.backend.controller;

import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.dto.usermanagement.RoleCreateDTO;
import com.gapco.backend.service.usermanagement.RoleService;
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
@RequestMapping(AppConstants.BASE_URI+"/role")
@Tag(name = "RoleController", description = "Operations for roles management")
public class RoleController {

    private final RoleService roleService;

    @Operation(
            summary = "Create a role"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping
    public ResponseEntity<CustomApiResponse<Object>> createRole(@Valid @RequestBody RoleCreateDTO roleCreateDTO){
        log.info("RoleController::createRole Execution started");
        return new ResponseEntity<>(roleService.createRole(roleCreateDTO), HttpStatus.OK);
    }

    @Operation(
            summary = "Create a role details"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> getRole(
            @Parameter(description = "Id of the role") @PathVariable Long id
    ){
        log.info("RoleController::getRole Execution started");
        return new ResponseEntity<>(roleService.getRole(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Get all roles"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping("/all")
    public ResponseEntity<CustomApiResponse<Object>> getRoles(
            @Parameter(description = AppConstants.PAGE_NUMBER_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @Parameter(description = AppConstants.PAGE_SIZE_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @Parameter(description = AppConstants.SORT_BY_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sort,
            @Parameter(description = AppConstants.SORT_DIRECTION_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String dir
    ){
        log.info("RoleController::getRole Execution started");
        return new ResponseEntity<>(roleService.getRoles(page,size,sort,dir), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete all  a roles"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @DeleteMapping
    public ResponseEntity<CustomApiResponse<Object>> deleteAllRoles(){
        log.info("RoleController::deleteAllRoles Execution started");
        return new ResponseEntity<>(roleService.deleteAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Update a role"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> updateRole(
            @Parameter(description = "Id of the role") @PathVariable Long id, @RequestBody RoleCreateDTO roleCreateDTO
    ){
        log.info("RoleController::getRole Execution started");
        return new ResponseEntity<>(roleService.updateRole(id,roleCreateDTO), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a role"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> deleteRole(
            @Parameter(description = "Id of the role") @PathVariable Long id
    ){
        log.info("RoleController::deleteRole Execution started");
        return new ResponseEntity<>(roleService.deleteRole(id), HttpStatus.OK);
    }
}
