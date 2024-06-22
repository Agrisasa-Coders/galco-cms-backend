package com.gapco.backend.service;


import com.gapco.backend.model.AuthenticationRequest;
import com.gapco.backend.model.SendOTPRequest;
import com.gapco.backend.model.UpdatePasswordRequest;
import com.gapco.backend.model.ValidateOTPRequest;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.dto.usermanagement.UserRegisterDTO;

public interface AuthenticationService {
    CustomApiResponse<Object> register(UserRegisterDTO userRegisterDTO);
    CustomApiResponse<Object> login(AuthenticationRequest authenticationRequest);
    CustomApiResponse<Object> changePassword(UpdatePasswordRequest updatePasswordRequest);
    CustomApiResponse<Object> sendOTPToUser(SendOTPRequest sendOTPRequest);
    void sendDefaultPassword(SendOTPRequest sendOTPRequest,String password);
    void saveOTP(SendOTPRequest sendOTPRequest,String Otp);
    CustomApiResponse<Object> validateOTPRequest(ValidateOTPRequest validateOTPRequest);
}


