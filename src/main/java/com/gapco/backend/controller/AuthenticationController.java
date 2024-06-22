package com.gapco.backend.controller;

import com.gapco.backend.dto.usermanagement.UserRegisterDTO;
import com.gapco.backend.model.AuthenticationRequest;
import com.gapco.backend.model.SendOTPRequest;
import com.gapco.backend.model.UpdatePasswordRequest;
import com.gapco.backend.model.ValidateOTPRequest;
import com.gapco.backend.model.*;
import com.gapco.backend.service.AuthenticationService;
import com.gapco.backend.response.CustomApiResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(AppConstants.BASE_URI+"/auth")
@Tag(name = "AuthenticationController", description = "Operations for api users' authorizations")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(
            summary = "User registration ",
            description = "Api which accepts inputs for a user registration"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping("/register")
    public ResponseEntity<CustomApiResponse<Object>> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO){
        log.info("AuthenticationController::register Execution started");
        return new ResponseEntity<>(authenticationService.register(userRegisterDTO), HttpStatus.OK);
    }

    @Operation(
            summary = "Login a user to the system",
            description = "Login a user to the system"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping("/login")
    public ResponseEntity<CustomApiResponse<Object>> login(@Valid @RequestBody AuthenticationRequest authenticationRequest){
        log.info("AuthenticationController::login Execution started");
        return new ResponseEntity<>(authenticationService.login(authenticationRequest), HttpStatus.OK);
    }


    @Operation(
            summary = "Changing a password of the user",
            description = "Changing a password of the user"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping("/change-password")
    public ResponseEntity<CustomApiResponse<Object>> changePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest){
        log.info("AuthenticationController::changePassword Execution started");
        return new ResponseEntity<>(authenticationService.changePassword(updatePasswordRequest), HttpStatus.OK);
    }

//    @PostMapping("/mobile/register")
//    public ResponseEntity<ApiResponse<AuthenticationResponse>> registerMobileUser(@Valid @RequestBody MobileUserCreateDTO mobileUserCreateDTO){
//        log.info("AuthenticationController::registerMobileUser Execution started");
//        return new ResponseEntity<>(authenticationService.registerMobileUser(mobileUserCreateDTO), HttpStatus.OK);
//    }

    @Operation(
            summary = "Sending OTP the user",
            description = "Sending OTP the user,via either email or phone"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping("/send-otp")
    public ResponseEntity<CustomApiResponse<Object>> sendOtpToUser(@Valid @RequestBody SendOTPRequest sendOTPRequest){
        log.info("AuthenticationController::sendOtpToUser Execution started");
        return new ResponseEntity<>(authenticationService.sendOTPToUser(sendOTPRequest), HttpStatus.OK);
    }

    @Operation(
            summary = "Validate OTP",
            description = "Validate otp sent to the user"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping("/validate-otp")
    public ResponseEntity<CustomApiResponse<Object>> validateOTP(@Valid @RequestBody ValidateOTPRequest validateOTPRequest){
        log.info("AuthenticationController::validateOTP Execution started");
        return new ResponseEntity<>(authenticationService.validateOTPRequest(validateOTPRequest), HttpStatus.OK);
    }

}
