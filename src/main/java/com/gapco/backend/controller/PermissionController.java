package com.gapco.backend.controller;

import com.gapco.backend.dto.usermanagement.PermissionsCreateDTO;
import com.gapco.backend.entity.Permission;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.service.usermanagement.PermissionService;
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
@RequestMapping(AppConstants.BASE_URI+"/permission")
@Tag(name = "PermissionController", description = "Operations for permissions management")
public class PermissionController {

    private final PermissionService permissionService;

    @Operation(
            summary = "Create permission"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping
    public ResponseEntity<CustomApiResponse<Object>> createPermission(@Valid @RequestBody Permission permission){
        log.info("PermissionController::createPermission Execution started");
        return new ResponseEntity<>(permissionService.createPermission(permission),HttpStatus.OK);
    }

    @Operation(
            summary = "Create list of permissions",
            description = "This is for creating more than one permission"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping(value = "/create-permissions")
    public ResponseEntity<CustomApiResponse<Object>> createPermissions(@Valid @RequestBody PermissionsCreateDTO permissionsCreateDTO){
        return new ResponseEntity<>(permissionService.createPermissions(permissionsCreateDTO),HttpStatus.OK);
    }

    @Operation(
            summary = "update a permission"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> updatePermission(@PathVariable Long id, @RequestBody Permission permission){
        log.info("PermissionController::updatePermission Execution started");
        return new ResponseEntity<>(permissionService.updatePermission(id,permission), HttpStatus.OK);
    }

    @Operation(
            summary = "Get a permission"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> getPermission(
            @Parameter(description = "Id of the permission") @PathVariable Long id
    ){
        log.info("PermissionController::getPermission Execution started");
        return new ResponseEntity<>(permissionService.getPermission(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Get list of permissions"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping
    public ResponseEntity<CustomApiResponse<Object>> getPermissions(
            @Parameter(description = AppConstants.PAGE_NUMBER_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @Parameter(description = AppConstants.PAGE_SIZE_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @Parameter(description = AppConstants.SORT_BY_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sort,
            @Parameter(description = AppConstants.SORT_DIRECTION_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String dir
    ){
        log.info("PermissionController::getPermissions Execution started");
        return new ResponseEntity<>(permissionService.getPermissions(page,size,sort,dir),HttpStatus.OK);
    }


    @Operation(
            summary = "Delete all permissions"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @DeleteMapping
    public ResponseEntity<CustomApiResponse<Object>> deleteAllPermissions(){
        log.info("PermissionController::deleteAllPermissions Execution started");
        return new ResponseEntity<>(permissionService.deleteAll(),HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a permission"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> deletePermission(
            @Parameter(description = "Id of the permission")@PathVariable Long id
    ){
        log.info("PermissionController::deletePermission Execution started");
        return new ResponseEntity<>(permissionService.deletePermission(id), HttpStatus.OK);
    }
}
