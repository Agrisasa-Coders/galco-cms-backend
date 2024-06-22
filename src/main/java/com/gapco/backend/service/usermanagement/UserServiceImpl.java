package com.gapco.backend.service.usermanagement;

import com.gapco.backend.exception.EntityExistException;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.exception.InternalServerErrorException;
import com.gapco.backend.repository.InstitutionRepository;
import com.gapco.backend.repository.RoleRepository;
import com.gapco.backend.repository.UserRepository;
import com.gapco.backend.entity.Institution;
import com.gapco.backend.model.SendOTPRequest;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.dto.usermanagement.UserCreateDTO;
import com.gapco.backend.dto.usermanagement.UserUpdateRoleDTO;
import com.gapco.backend.entity.Role;
import com.gapco.backend.entity.User;
import com.gapco.backend.model.UpdatePasswordRequest;
import com.gapco.backend.service.AuthenticationService;
import com.gapco.backend.service.LogService;
import com.gapco.backend.service.StorageService;
import com.gapco.backend.util.Helper;
import com.gapco.backend.util.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final InstitutionRepository institutionRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authService;
    private final LogService logService;
    private final StorageService storageService;


    @Override
    public CustomApiResponse<Object> createUser(UserCreateDTO userCreateDTO) {

        log.info("UserServiceImpl::createUser Execution started");
        log.debug("UserServiceImpl::createUser the request coming is {}",userCreateDTO.toString());

        logService.logToFile(AppConstants.LOGS_PATH,"CreateUser",userCreateDTO.toString());

        Optional<User> userByEmailOrPhone = userRepository.
                findByEmailOrPhoneNumber(
                        userCreateDTO.getEmail(),
                        userCreateDTO.getPhoneNumber()
                );

        if(userByEmailOrPhone.isEmpty()){

                User newUser = new User();

                newUser.setFirstName(userCreateDTO.getFirstName());
                newUser.setMiddleName(userCreateDTO.getMiddleName());
                newUser.setLastName(userCreateDTO.getLastName());
                newUser.setEmail(userCreateDTO.getEmail());
                newUser.setPhoneNumber(userCreateDTO.getPhoneNumber());
                newUser.setPasswordLastUpdate(Helper.getCurrentTime());
                newUser.setUserType(userCreateDTO.getUserType());
                newUser.setRegion(userCreateDTO.getRegion());
                newUser.setDistrict(userCreateDTO.getDistrict());
                newUser.setNida(userCreateDTO.getNida());

                if(userCreateDTO.getInstitutionId() == null){
                    Optional<Institution> optionalInstitution = institutionRepository.findByInstitutionId(0);
                    if(optionalInstitution.isPresent()){
                        newUser.setInstitution(optionalInstitution.get());
                    }
                } else {

                    Optional<Institution> optionalInstitution = institutionRepository.findByInstitutionId(userCreateDTO.getInstitutionId());

                    if(optionalInstitution.isPresent()){
                        newUser.setInstitution(optionalInstitution.get());
                    }
                }

                String password = Helper.getRandomString(8);

                newUser.setPassword(passwordEncoder.encode(password));

                //Setting roles
                if(userCreateDTO.getRoles() !=null && userCreateDTO.getRoles().length > 0){
                        newUser.setRoles(this.setRolesList(userCreateDTO.getRoles()));
                }

                log.info("UserServiceImpl::createUser user");
                userRepository.save(newUser);


                //sending default password through email
                SendOTPRequest sendOTPRequest = new SendOTPRequest();
                sendOTPRequest.setChannel("email");
                sendOTPRequest.setUsername(newUser.getUsername());
                authService.sendDefaultPassword(sendOTPRequest,password);

                //Returning ApiResponse
                CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("User has been successfully created");
                customApiResponse.setData(newUser);

                log.info("UserServiceImpl::createUser Execution ended");

                logService.logToFile(AppConstants.LOGS_PATH,"CreateUser","User has been successfully created");

                return customApiResponse;


        } else {

            logService.logToFile(AppConstants.LOGS_PATH,"CreateUser","email or phone number used already used for registration");

            throw new EntityExistException("email or phone number used already used for registration");
        }
    }

    @Override
    public CustomApiResponse<Object> getUser(Long id) {

        log.info("UserServiceImpl::getUser Execution started");

        Optional<User> userById = userRepository.findById(id);

        if(userById.isPresent()){

            User user = userById.get();

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>(AppConstants.OPERATION_SUCCESSFULLY_MESSAGE);
            customApiResponse.setData(user);

            log.info("UserServiceImpl::getUser Execution ended");
            return customApiResponse;

        }else{

            log.error("UserServiceImpl::getUser No matching role found");
            throw new EntityNotFoundException("No matching role found");
        }
    }

    @Override
    public CustomApiResponse<Object> getUsers(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<User> userPageableRequests = userRepository.findAll(pageable);

        List<User> users = userPageableRequests.getContent();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                userPageableRequests.getTotalElements(),
                userPageableRequests.getTotalPages(),
                userPageableRequests.getNumber()

        );

        customApiResponse.setData(users);
        return customApiResponse;
    }

    @Override
    public CustomApiResponse<Object> updateUserInfo(Long id, UserCreateDTO userCreateDTO) {
        log.info("UserServiceImpl::updateUserInfo Execution started");
        log.debug("UserServiceImpl::updateUserInfo the request coming is {}",userCreateDTO.toString());

        Optional<User> userById = userRepository.findById(id);

        if(userById.isPresent()){

            User savedUser = userById.get();

            Optional<Institution> optionalInstitution = institutionRepository.findByInstitutionId(userCreateDTO.getInstitutionId());

            savedUser.setFirstName(userCreateDTO.getFirstName());
            savedUser.setMiddleName(userCreateDTO.getMiddleName());
            savedUser.setLastName(userCreateDTO.getLastName());
            savedUser.setEmail(userCreateDTO.getEmail());
            savedUser.setPhoneNumber(userCreateDTO.getPhoneNumber());
            savedUser.setPasswordLastUpdate(Helper.getCurrentTime());
            savedUser.setUserType(userCreateDTO.getUserType());
            savedUser.setRegion(userCreateDTO.getRegion());
            savedUser.setDistrict(userCreateDTO.getDistrict());
            savedUser.setNida(userCreateDTO.getNida());

            if(optionalInstitution.isPresent()){
                savedUser.setInstitution(optionalInstitution.get());
            }

            //Setting roles
            if(userCreateDTO.getRoles() !=null && userCreateDTO.getRoles().length > 0){
                savedUser.setRoles(this.setRolesList(userCreateDTO.getRoles()));
            }

            log.info("UserServiceImpl::updateUserInfo user");
            userRepository.save(savedUser);

            //Returning ApiResponse
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("User has been successfully updated");
            customApiResponse.setData(savedUser);

            log.info("UserServiceImpl::updateUserInfo Execution ended");

            logService.logToFile(AppConstants.LOGS_PATH,"UpdateUser","User has been successfully updated");

            return customApiResponse;

        } else {
            throw new EntityNotFoundException("User is not found");
        }
    }

    @Override
    public CustomApiResponse<Object> updatePassword(UpdatePasswordRequest updatePasswordRequest) {

        log.info("UserServiceImpl:: updatePassword Execution started");
        log.debug("UserServiceImpl::updatePassword the request coming is {}",updatePasswordRequest.toString());

        User user = userRepository.findByEmail(updatePasswordRequest.getUserId())
                .orElseThrow(()->new EntityNotFoundException("User with that id is not found"));

        if(updatePasswordRequest.getPassword().equals(updatePasswordRequest.getPasswordConfirm()))
        {
            user.setPassword(passwordEncoder.encode(updatePasswordRequest.getPassword()));
            user.setPasswordLastUpdate(Helper.getCurrentTime());

            userRepository.save(user);


            //Returning ApiResponse
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("User has been successfully updated the password");
            customApiResponse.setData(user);

            log.info("AuthenticationServiceImpl::register Execution ended");

            return customApiResponse;

        } else {
            throw new InternalServerErrorException("Passwords do not match");
        }
    }

    @Override
    public CustomApiResponse<Object> updateUserRoles(Long id, UserUpdateRoleDTO userUpdateRoleDTO) {
        log.info("UserServiceImpl::updateUserRoles Execution started");
        log.debug("UserServiceImpl::updateUserRoles the request coming is {}",userUpdateRoleDTO.toString());

        Optional<User> userById = userRepository.findById(id);

        if(userById.isPresent()){

            User savedUser = userById.get();

            savedUser.setRoles(this.setRolesList(userUpdateRoleDTO.getRoles()));

            userRepository.save(savedUser);

            //Returning ApiResponse
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("User roles has been successfully updated");
            customApiResponse.setData(savedUser);

            log.info("UserServiceImpl::editUser Execution ended");

            return customApiResponse;

        } else {
            throw new EntityNotFoundException("User is not found");
        }
    }



    @Override
    public CustomApiResponse<Object> deleteUser(Long id) {
        log.info("UserServiceImpl::deleteUser Execution started");

        Optional<User> userById = userRepository.findById(id);

        if(userById.isPresent()){

            User fetchedUser = userById.get();

            userRepository.delete(fetchedUser);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("User has been successfully deleted");
            customApiResponse.setData(fetchedUser);

            log.info("UserServiceImpl::deleteUser Execution ended");
            return customApiResponse;

        }else{

            log.error("UserServiceImpl::deleteUser No matching role found");
            throw new EntityNotFoundException("No matching role found");
        }
    }

    @Override
    public CustomApiResponse<Object> deleteAll() {
        userRepository.deleteAll();
        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Users have been successfully deleted");
        return customApiResponse;
    }






    @Override
    public CustomApiResponse<Object> updateUserProfilePhoto(Long id, MultipartFile image) {
        log.info("UserServiceImpl::updateUserProfilePhoto execution started");

        String profilePhotoLocation = null;

        User user = userRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("User is not found"));
        try{

            profilePhotoLocation = storageService.storeFileToFileSystem(image,image.getOriginalFilename());

            if(profilePhotoLocation != null){

                user.setProfilePicLocation(profilePhotoLocation);
                userRepository.save(user);

                CustomApiResponse<Object> customApiResponse = new CustomApiResponse("Profile photo updated successfully");
                customApiResponse.setData(null);
                return customApiResponse;

            } else {

                logService.logToFile(AppConstants.LOGS_PATH,"updateUserProfilePhoto-Exception",AppConstants.FAILING_TO_UPLOAD_PHOTO_MESSAGE);
                throw new InternalServerErrorException(AppConstants.FAILING_TO_UPLOAD_PHOTO_MESSAGE);
            }

        } catch(Exception e){

            logService.logToFile(AppConstants.LOGS_PATH,"updateUserProfilePhoto-Exception",AppConstants.FAILING_TO_UPLOAD_PHOTO_MESSAGE);
            throw new InternalServerErrorException(AppConstants.FAILING_TO_UPLOAD_PHOTO_MESSAGE);
        }

    }

    public List<Role> setRolesList(Long[] roles) {

        List<Role> rolesList = new ArrayList<>();

        int i;

        if(roles.length > 0){

            for(i=0; i< roles.length; i++){
                Optional<Role> optionalRole = roleRepository.findById(roles[i]);

                if(optionalRole.isPresent())
                    rolesList.add(optionalRole.get());
            }
        }

        return rolesList;
    }
}
