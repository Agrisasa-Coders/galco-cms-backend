package com.gapco.backend.controller;

import com.gapco.backend.dto.usermanagement.UserCreateDTO;
import com.gapco.backend.dto.usermanagement.UserUpdateRoleDTO;
import com.gapco.backend.service.usermanagement.UserService;
import com.gapco.backend.model.UpdatePasswordRequest;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(AppConstants.BASE_URI+"/user")
@Tag(name = "UserController", description = "Operations for users management")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Create a user"
    )
    @PostMapping
    public ResponseEntity<CustomApiResponse<Object>> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO){
        log.info("UserController::createUser Execution started");
        return new ResponseEntity<>(userService.createUser(userCreateDTO), HttpStatus.OK);
    }

    @Operation(
            summary = "Getting user details"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> getUser(@PathVariable Long id){
        log.info("UserController::getUser Execution started");
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Getting all users"
    )
    @GetMapping
    public ResponseEntity<CustomApiResponse<Object>> getUsers(
            @Parameter(description = AppConstants.PAGE_NUMBER_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @Parameter(description = AppConstants.PAGE_SIZE_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @Parameter(description = AppConstants.SORT_BY_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sort,
            @Parameter(description = AppConstants.SORT_DIRECTION_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String dir
    ){
        log.info("UserController::getUser Execution started");
        return new ResponseEntity<>(userService.getUsers(page,size,sort,dir), HttpStatus.OK);
    }


    @Operation(
            summary = "Delete all users"
    )
    @DeleteMapping
    public ResponseEntity<CustomApiResponse<Object>> deleteAllUsers(){
        log.info("UserController::deleteAllUsers Execution started");
        return new ResponseEntity<>(userService.deleteAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Update a user"
    )
    @PutMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> updateUser(@PathVariable Long id, @Valid @RequestBody UserCreateDTO userCreateDTO){
        log.info("RoleController::getRole Execution started");
        return new ResponseEntity<>(userService.updateUserInfo(id,userCreateDTO), HttpStatus.OK);
    }

    @Operation(
            summary = "Update a user profile photo"
    )
    @PutMapping(path = "/{id}/profile",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<CustomApiResponse<Object>> updateUserProfilePhoto(@PathVariable Long id,@RequestParam("image") MultipartFile image){
        log.info("UserController::updateUserProfilePhoto Execution started");
        return new ResponseEntity<>(userService.updateUserProfilePhoto(id,image), HttpStatus.OK);
    }


    @Operation(
            summary = "Update role/roles of the user"
    )
    @PutMapping("/{id}/role")
    public ResponseEntity<CustomApiResponse<Object>> updateUserRoles(@PathVariable Long id, @Valid @RequestBody UserUpdateRoleDTO userUpdateRoleDTO){
        log.info("RoleController::getRole Execution started");
        return new ResponseEntity<>(userService.updateUserRoles(id,userUpdateRoleDTO), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a user"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> deleteUser(@PathVariable Long id){
        log.info("RoleController::deleteRole Execution started");
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Update user's password",
            description = "This operation is for user to update his/her password"
    )
    @PostMapping("/update-password")
    public ResponseEntity<CustomApiResponse<Object>> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest){
        log.info("AuthenticationController::changePassword Execution started");
        return new ResponseEntity<>(userService.updatePassword(updatePasswordRequest), HttpStatus.OK);
    }

}
