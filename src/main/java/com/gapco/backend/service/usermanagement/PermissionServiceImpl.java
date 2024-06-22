package com.gapco.backend.service.usermanagement;

import com.gapco.backend.exception.EntityExistException;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.UserPermissionRepository;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.dto.usermanagement.PermissionsCreateDTO;
import com.gapco.backend.entity.Permission;
import com.gapco.backend.util.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService{

    private final UserPermissionRepository userPermissionRepository;

    @Override
    public CustomApiResponse<Object> createPermission(Permission permission) {
        log.info("PermissionServiceImpl::createPermission Execution started");
        log.debug("PermissionServiceImpl::createPermission the request coming is {}",permission.toString());

        Optional<Permission> optionalPermission = userPermissionRepository.findByPermissionName(permission.getPermissionName());

        if(optionalPermission.isEmpty()){

            Permission newPermission = new Permission();
            newPermission.setPermissionName(permission.getPermissionName());
            userPermissionRepository.save(newPermission);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Permission has been successfully created");
            customApiResponse.setData(newPermission);

            log.info("PermissionServiceImpl::createPermission Execution ended");
            return customApiResponse;

        }else{

            throw new EntityExistException("Permission already exists");
        }
    }

    @Override
    public CustomApiResponse<Object> updatePermission(Long id, Permission updatedPermission) {
        log.info("PermissionServiceImpl::updatePermission Execution started");
        log.debug("PermissionServiceImpl::updatePermission the request coming is {}",updatedPermission.toString());

        Optional<Permission> permissionById = userPermissionRepository.findById(id);

        if(permissionById.isPresent()){

            Permission permission = permissionById.get();
            permission.setPermissionName(updatedPermission.getPermissionName());

            userPermissionRepository.save(permission);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Permission has been successfully updated");
            customApiResponse.setData(permission);

            log.info("PermissionServiceImpl::updatePermission Execution ended");
            return customApiResponse;

        } else{
            log.error("PermissionServiceImpl::updatePermission No matching permission");
            throw new EntityNotFoundException("No matching permission found");
        }
    }

    @Override
    public CustomApiResponse<Object> createPermissions(PermissionsCreateDTO permissionsCreateDTO) {
        log.info("PermissionServiceImpl::createNewPermissions Execution started");
        log.debug("PermissionServiceImpl::createNewPermissions the request coming is {}",permissionsCreateDTO.toString());

        List<Permission> createdPermissions = new ArrayList<>();

        String[] permissions = permissionsCreateDTO.getPermissions();

        if(permissions.length > 0){

            for(int i=0; i< permissions.length; i++){

                Optional<Permission> permissionByName = userPermissionRepository.findByPermissionName(permissions[i]);

                if(permissionByName.isEmpty()){

                    Permission newPermission = new Permission();

                    newPermission.setPermissionName(permissions[i]);

                    userPermissionRepository.save(newPermission);

                    createdPermissions.add(newPermission);

                }
            }
        }

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Permissions has been successfully created");
        customApiResponse.setData(createdPermissions);

        log.info("PermissionServiceImpl::createNewPermissions Execution ended");
        return customApiResponse;

    }

    @Override
    public CustomApiResponse<Object> deletePermission(Long id) {
        log.info("PermissionServiceImpl::deletePermission Execution started");

        Optional<Permission> permissionById = userPermissionRepository.findById(id);

        if(permissionById.isPresent()){

            Permission permission = permissionById.get();

            userPermissionRepository.delete(permission);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse("Permission has been successfully deleted");
            customApiResponse.setData(permission);

            log.info("PermissionServiceImpl::deleteInstitution Execution ended");
            return customApiResponse;

        }else{

            log.error("PermissionServiceImpl::deleteInstitution No matching permission found");
            throw new EntityNotFoundException("No matching permission found");
        }
    }

    @Override
    public CustomApiResponse<Object> getPermission(Long id) {
        log.info("PermissionServiceImpl::getPermission Execution started");

        Optional<Permission> permissionById = userPermissionRepository.findById(id);

        if(permissionById.isPresent()){

            Permission permission = permissionById.get();

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>(AppConstants.OPERATION_SUCCESSFULLY_MESSAGE);
            customApiResponse.setData(permission);

            log.info("PermissionServiceImpl::getPermission Execution ended");
            return customApiResponse;

        }else{

            log.error("PermissionServiceImpl::getPermission No matching permission found");
            throw new EntityNotFoundException("No matching permission found");
        }
    }

    @Override
    public CustomApiResponse<Object> getPermissions(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Permission> pageablePermissions = userPermissionRepository.findAll(pageable);

        List<Permission> permissions = pageablePermissions.getContent();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                pageablePermissions.getTotalElements(),
                pageablePermissions.getTotalPages(),
                pageablePermissions.getNumber()

        );

        customApiResponse.setData(permissions);
        return customApiResponse;
    }

    @Override
    public CustomApiResponse<Object> deleteAll() {
        userPermissionRepository.deleteAll();
        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("All permissions have been successfully deleted");
        return customApiResponse;
    }
}
