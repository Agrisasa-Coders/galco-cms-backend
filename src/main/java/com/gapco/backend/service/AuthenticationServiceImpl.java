package com.gapco.backend.service;

import com.gapco.backend.entity.*;
import com.gapco.backend.exception.EntityExistException;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.exception.InternalServerErrorException;
import com.gapco.backend.model.*;
import com.gapco.backend.repository.*;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.dto.usermanagement.UserRegisterDTO;
import com.gapco.backend.util.Helper;
import com.gapco.backend.util.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ConfigurationRepository configurationRepository;
    private final UserOTPRepository userOTPRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final LoginAuthorizationService logAuthService;
    private final InstitutionRepository institutionRepository;
    private final NotificationService notificationService;
    private final LogService logService;


    @Override
    public CustomApiResponse<Object> register(UserRegisterDTO userRegisterDTO) {

        log.info("AuthenticationServiceImpl:: register class createUser Execution started");
        log.debug("AuthenticationServiceImpl::register the request coming is {}",userRegisterDTO.toString());

        Optional<User> userByEmailOrPhone = userRepository.
                findByEmailOrPhoneNumber(
                        userRegisterDTO.getEmail(),
                        userRegisterDTO.getPhoneNumber()
                );

        if(userByEmailOrPhone.isEmpty()){

            if(userRegisterDTO.getPassword().equals(userRegisterDTO.getPasswordConfirm())){


                String[] roles= {"ROLE_DEFAULT"};

                User newUser = new User();
                newUser.setFirstName(userRegisterDTO.getFirstName());
                newUser.setLastName(userRegisterDTO.getLastName());
                newUser.setEmail(userRegisterDTO.getEmail());
                newUser.setPhoneNumber(userRegisterDTO.getPhoneNumber());
                newUser.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
                newUser.setPasswordLastUpdate(Helper.getCurrentTime());

                if(userRegisterDTO.getInstitutionId() == null){
                    Optional<Institution> optionalInstitution = institutionRepository.findByInstitutionId(0);
                    if(optionalInstitution.isPresent()){
                        newUser.setInstitution(optionalInstitution.get());
                    }
                }

                newUser.setRoles(this.setRolesList(roles));

                if (userRegisterDTO.getUserType() == null) {
                    newUser.setUserType("member");
                } else {
                    newUser.setUserType(userRegisterDTO.getUserType());
                }

                userRepository.save(newUser);

                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .user(newUser)
                        .build();

                //sending otp through email
                SendOTPRequest sendOTPRequest = new SendOTPRequest();
                sendOTPRequest.setChannel("email");
                sendOTPRequest.setUsername(newUser.getUsername());
                sendOTPToUser(sendOTPRequest);

                //Returning ApiResponse
                CustomApiResponse<Object> apiResponse = new CustomApiResponse<>("You have been successfully registered, enter OTP sent to your email to continue using application");
                apiResponse.setData(authResponse);

                log.info("AuthenticationServiceImpl::register Execution ended");

                return apiResponse;
            } else {
                throw new InternalServerErrorException("Passwords does not match");
            }
        } else {
            throw new EntityExistException("email or phone number used already used for registration");
        }
    }

    @Override
    public CustomApiResponse<Object> login(AuthenticationRequest authenticationRequest) {

        log.info("AuthenticationServiceImpl:: login Execution started");
        log.debug("AuthenticationServiceImpl::login the request coming is {}",authenticationRequest.toString());

        logService.logToFile(AppConstants.LOGS_PATH,"LoginRequest", authenticationRequest.getEmail());

        Optional<User> userByEmail = userRepository.findByEmail(authenticationRequest.getEmail());

        Optional<Institution> optionalInstitution = institutionRepository.findByInstitutionId(0);

        Optional<UserRequestOTP> optionalUserOpt = userOTPRepository.findByUserId(authenticationRequest.getEmail());

        if(userByEmail.isPresent()){ //Check if user present

//            if(optionalUserOpt.isPresent()){
//
//                boolean isValidated = optionalUserOpt.get().isValidated();
//
//                if(isValidated){ //Check if the user
//
//                    if(optionalInstitution.isPresent()){ //Check if there is any institution for this user
//
//                        User user = userByEmail.get();
//
//                        if(logAuthService.isNoOfPasswordAttemptsAllowed(
//                                optionalInstitution.get().getId(),
//                                user.getNoOfPasswordAttempts()))
//                        {
//
//                            if(logAuthService.isPasswordNotExpired(
//                                    optionalInstitution.get().getId(),
//                                    user.getPasswordLastUpdate())){
//
//                                try{
//                                    authenticationManager.authenticate(
//                                            new UsernamePasswordAuthenticationToken(
//                                                    authenticationRequest.getEmail(),
//                                                    authenticationRequest.getPassword()
//                                            )
//                                    );
//
//                                } catch(Exception e){
//
//                                    logAuthService.incrementPasswordAttempt(user);
//                                    log.debug("AuthenticationServiceImpl::login No of Password Attempts {}",user.getNoOfPasswordAttempts());
//
//                                    logService.logToFile(AppConstants.LOGS_PATH,"LoginException",AppConstants.INCORRECT_PASSWORD_MESSAGE);
//
//                                    throw new InternalServerErrorException(AppConstants.INCORRECT_PASSWORD_MESSAGE);
//                                }
//
//                                logAuthService.resetPasswordAttempts(user);
//
//                                //logAuthService.recordUserLogin(user);  //
//
//                                log.debug("AuthenticationServiceImpl::login  No of Password Attempts {}",user.getNoOfPasswordAttempts());
//
//                                String token = jwtService.generateToken(user);
//
//                                AuthenticationResponse authResponse = AuthenticationResponse.builder()
//                                        .user(user)
//                                        .token(token)
//                                        .expiresAt(jwtService.extractExpirationDate(token).toString())
//                                        .build();
//
//                                //Returning ApiResponse
//                                CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("User has been successfully logged in");
//                                customApiResponse.setData(authResponse);
//
//                                return customApiResponse;
//
//                            } else{
//
//                                logService.logToFile(AppConstants.LOGS_PATH,"LoginException",AppConstants.PASSWORD_EXPIRES_MESSAGE);
//
//                                throw new InternalServerErrorException(AppConstants.PASSWORD_EXPIRES_MESSAGE);
//                            }
//                        }else{
//                            logService.logToFile(AppConstants.LOGS_PATH,"LoginException",AppConstants.MAXIMUM_PASSWORD_ATTEMPT_MESSAGE);
//                            throw new InternalServerErrorException(AppConstants.MAXIMUM_PASSWORD_ATTEMPT_MESSAGE);
//                        }
//
//                    } else {
//
//                        throw new InternalServerErrorException("No institution is set for this user");
//                    }
//                }else{
//                    throw new InternalServerErrorException("Validate the OTP before login");
//                }
//
//            }else{
//                throw new InternalServerErrorException("There is no OTP validated for this user");
//            }

            if(optionalInstitution.isPresent()){ //Check if there is any institution for this user

                User user = userByEmail.get();

                if(logAuthService.isNoOfPasswordAttemptsAllowed(
                        optionalInstitution.get().getId(),
                        user.getNoOfPasswordAttempts()))
                {

                    if(logAuthService.isPasswordNotExpired(
                            optionalInstitution.get().getId(),
                            user.getPasswordLastUpdate())){

                        try{
                            authenticationManager.authenticate(
                                    new UsernamePasswordAuthenticationToken(
                                            authenticationRequest.getEmail(),
                                            authenticationRequest.getPassword()
                                    )
                            );

                        } catch(Exception e){

                            logAuthService.incrementPasswordAttempt(user);
                            log.debug("AuthenticationServiceImpl::login No of Password Attempts {}",user.getNoOfPasswordAttempts());

                            logService.logToFile(AppConstants.LOGS_PATH,"LoginException",AppConstants.INCORRECT_PASSWORD_MESSAGE);

                            throw new InternalServerErrorException(AppConstants.INCORRECT_PASSWORD_MESSAGE);
                        }

                        logAuthService.resetPasswordAttempts(user);

                        //logAuthService.recordUserLogin(user);  //

                        log.debug("AuthenticationServiceImpl::login  No of Password Attempts {}",user.getNoOfPasswordAttempts());

                        String token = jwtService.generateToken(user);

                        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                                .user(user)
                                .token(token)
                                .expiresAt(jwtService.extractExpirationDate(token).toString())
                                .build();

                        //Returning ApiResponse
                        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("User has been successfully logged in");
                        customApiResponse.setData(authResponse);

                        return customApiResponse;

                    } else{

                        logService.logToFile(AppConstants.LOGS_PATH,"LoginException",AppConstants.PASSWORD_EXPIRES_MESSAGE);

                        throw new InternalServerErrorException(AppConstants.PASSWORD_EXPIRES_MESSAGE);
                    }
                }else{
                    logService.logToFile(AppConstants.LOGS_PATH,"LoginException",AppConstants.MAXIMUM_PASSWORD_ATTEMPT_MESSAGE);
                    throw new InternalServerErrorException(AppConstants.MAXIMUM_PASSWORD_ATTEMPT_MESSAGE);
                }

            } else {

                throw new InternalServerErrorException("No institution is set for this user");
            }
        } else {

            logService.logToFile(AppConstants.LOGS_PATH,"LoginException","Email does not exists");
            throw new InternalServerErrorException("Email does not exists");
        }
    }

    @Override
    public CustomApiResponse<Object> changePassword(UpdatePasswordRequest updatePasswordRequest) {

        log.info("AuthenticationServiceImpl:: updatePassword Execution started");
        log.debug("AuthenticationServiceImpl::updatePassword the request coming is {}",updatePasswordRequest.toString());

        logService.logToFile(AppConstants.LOGS_PATH,"ChangePassword", updatePasswordRequest.getUserId());

        User user = userRepository.findByEmail(updatePasswordRequest.getUserId())
                        .orElseThrow(()->new EntityNotFoundException("User with that id is not found"));

        if(updatePasswordRequest.getPassword().equals(updatePasswordRequest.getPasswordConfirm()))
        {
            user.setPassword(passwordEncoder.encode(updatePasswordRequest.getPassword()));
            user.setPasswordLastUpdate(Helper.getCurrentTime());

            userRepository.save(user);

            String token = jwtService.generateToken(user);

            AuthenticationResponse authResponse = AuthenticationResponse.builder()
                    .user(user)
                    .token(token)
                    .expiresAt(jwtService.extractExpirationDate(token).toString())
                    .build();

            //Returning ApiResponse
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("User has been successfully changed the password");
            customApiResponse.setData(authResponse);

            log.info("AuthenticationServiceImpl::register Execution ended");

            logService.logToFile(AppConstants.LOGS_PATH,"ChangePassword","User has been successfully changed the password");
            return customApiResponse;

        } else {
            logService.logToFile(AppConstants.LOGS_PATH,"ChangePassword", "Passwords do not match");
            throw new InternalServerErrorException("Passwords do not match");
        }
    }

    @Override
    public CustomApiResponse<Object> sendOTPToUser(SendOTPRequest sendOTPRequest) {

        logService.logToFile(AppConstants.LOGS_PATH,"SendOTPToUser",sendOTPRequest.getUsername());

        String otp = Helper.generateOTP(4);

        if(sendOTPRequest.getChannel().equals("email")){

            Notification notification = Notification.builder()
                    .to(sendOTPRequest.getUsername())
                    .from(AppConstants.FROM_EMAIL)
                    .subject(AppConstants.OTP_EMAIL_SUBJECT)
                    .message(AppConstants.OTP_MESSAGE+otp)
                    .build();

            notificationService.sendEmailNotification(notification);

        }

        saveOTP(sendOTPRequest,otp);

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("OTP has been successfully sent");
        customApiResponse.setData(sendOTPRequest);

        return customApiResponse;
    }


    @Override
    public void sendDefaultPassword(SendOTPRequest sendOTPRequest,String password) {

        logService.logToFile(AppConstants.LOGS_PATH,"sendDefaultPassword",sendOTPRequest.getUsername());

        if(sendOTPRequest.getChannel().equals("email")){

            Notification notification = Notification.builder()
                    .to(sendOTPRequest.getUsername())
                    .from(AppConstants.FROM_EMAIL)
                    .subject(AppConstants.PASSWORD_EMAIL_SUBJECT)
                    .message(AppConstants.PASSWORD_MESSAGE+password)
                    .build();

            notificationService.sendEmailNotification(notification);

        }

    }

    @Override
    public void saveOTP(SendOTPRequest sendOTPRequest,String otp) {
        Optional<UserRequestOTP> optionalUserOTP = userOTPRepository.findByUserId(sendOTPRequest.getUsername());

        Configuration configuration = configurationRepository.findAll().get(0);

        UserRequestOTP userRequestOTP = null;

        if(optionalUserOTP.isPresent()){

            userRequestOTP = optionalUserOTP.get();
        } else {
            userRequestOTP = new UserRequestOTP();
            userRequestOTP.setUserId(sendOTPRequest.getUsername());
        }

        userRequestOTP.setOtp(otp);
        userRequestOTP.setValidated(false);
        userRequestOTP.setExpiredAt(Helper.getCurrentTime().plusMinutes(configuration.getOtpExpiryTimeInMinutes()));

        userOTPRepository.save(userRequestOTP);
    }

    @Override
    public CustomApiResponse<Object> validateOTPRequest(ValidateOTPRequest validateOTPRequest) {

        logService.logToFile(AppConstants.LOGS_PATH,"ValidateOTPRequest",validateOTPRequest.getUsername());

        log.info("AuthenticationServiceImpl::validateOTPRequest execution started");

        UserRequestOTP userRequestOTP = userOTPRepository.findByUserId(validateOTPRequest.getUsername())
                .orElseThrow(()->new EntityNotFoundException("user is not found"));

        if(userRequestOTP.getOtp().equals(validateOTPRequest.getOtp()) &&
            Helper.getCurrentTime().isBefore(userRequestOTP.getExpiredAt())
        ){

            userRequestOTP.setValidated(true);
            userOTPRepository.save(userRequestOTP);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("OTP is successfully validated");
            customApiResponse.setData(validateOTPRequest);

            log.info("AuthenticationServiceImpl::validateOTPRequest execution ended");
            return customApiResponse;
        } else {

            log.info("AuthenticationServiceImpl::validateOTPRequest execution ended");

            logService.logToFile(AppConstants.LOGS_PATH,"ValidateOTPRequest","OTP not match or has been expired");

            throw new InternalServerErrorException("OTP not match or has been expired");
        }
    }




    public List<Role> setRolesList(String[] roles) {

        List<Role> rolesList = new ArrayList<>();

        int i;

        if(roles.length > 0){
            for(i=0; i< roles.length; i++){
                Optional<Role> optionalRole = roleRepository.findByRoleName(roles[i]);

                if(optionalRole.isPresent())
                    rolesList.add(optionalRole.get());
            }
        }

        return rolesList;
    }
}
