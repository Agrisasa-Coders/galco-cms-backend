package com.gapco.backend.service.usermanagement;

import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.dto.usermanagement.UserCreateDTO;
import com.gapco.backend.dto.usermanagement.UserUpdateRoleDTO;
import com.zff.backend.dto.usermanagement.*;
import com.gapco.backend.model.UpdatePasswordRequest;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {
    CustomApiResponse<Object> createUser(UserCreateDTO userCreateDTO);
    CustomApiResponse<Object> getUser(Long id);
    CustomApiResponse<Object> getUsers(int page, int size, String sort, String direction);
    CustomApiResponse<Object> updateUserInfo(Long id, UserCreateDTO userCreateDTO);
    CustomApiResponse<Object> updatePassword(UpdatePasswordRequest updatePasswordRequest);
    CustomApiResponse<Object> updateUserRoles(Long id, UserUpdateRoleDTO userUpdateRoleDTO);
    CustomApiResponse<Object> deleteUser(Long id);
    CustomApiResponse<Object> deleteAll();
    CustomApiResponse<Object> updateUserProfilePhoto(Long id, MultipartFile image);
}
