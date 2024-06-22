package com.gapco.backend.service.usermanagement;

import com.gapco.backend.exception.EntityExistException;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.exception.InternalServerErrorException;
import com.gapco.backend.repository.RoleRepository;
import com.gapco.backend.repository.UserPermissionRepository;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.dto.usermanagement.RoleCreateDTO;
import com.gapco.backend.entity.Permission;
import com.gapco.backend.entity.Role;
import com.gapco.backend.util.AppConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class RoleServiceImpl implements RoleService{

    private UserPermissionRepository userPermissionRepository;
    private RoleRepository roleRepository;

    @Override
    public List<Permission> setPermissionList(Long[] permissions) {
        List<Permission> permissionList = new ArrayList<>();

        int i;

        if(permissions.length > 0){

            for(i=0; i< permissions.length; i++){
                Optional<Permission> permissionOptional = userPermissionRepository.findById(permissions[i]);

                if(permissionOptional.isPresent())
                    permissionList.add(permissionOptional.get());
            }
        }

        return permissionList;

    }


    @Override
    public CustomApiResponse<Object> updateRole(Long id, RoleCreateDTO roleCreateDTO) {
        log.info("RoleServiceImpl::updateRole Execution started");
        log.debug("RoleServiceImpl::updateRole the request coming is {}",roleCreateDTO.toString());

        Optional<Role> roleById = roleRepository.findById(id);

        if(roleById.isPresent()){

            Role currentRole = roleById.get();

            currentRole.setRoleName(roleCreateDTO.getRoleName());

            if(roleCreateDTO.getPermissions().length > 0)
                currentRole.setPermissions(this.setPermissionList(roleCreateDTO.getPermissions()));

            Role updatedRole = roleRepository.save(currentRole);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Role has been successfully updated");
            customApiResponse.setData(updatedRole);

            log.info("RoleServiceImpl::updateRole Execution ended");
            return customApiResponse;

        } else{
            log.error("RoleServiceImpl::updateRole No matching permission");
            throw new EntityNotFoundException("No matching permission found");
        }
    }

    @Override
    public CustomApiResponse<Object> createRole(RoleCreateDTO roleCreateDTO) {

        log.info("RoleServiceImpl::createRole Execution started");

        Optional<Role> roleByName = roleRepository.findByRoleName(roleCreateDTO.getRoleName());

        if(roleByName.isEmpty()){

            try{

                Role newRole = new Role();
                newRole.setRoleName(roleCreateDTO.getRoleName());
                newRole.setPermissions(this.setPermissionList(roleCreateDTO.getPermissions()));

                roleRepository.save(newRole);

                CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Role has been successfully created");
                customApiResponse.setData(newRole);

                log.info("RoleServiceImpl::createRole Execution ended");
                return customApiResponse;

            } catch (Exception ex){
                throw new InternalServerErrorException("Role creating failed. internal server error");
            }

        } else {
            throw new EntityExistException("Role already exists");
        }
    }

    @Override
    public CustomApiResponse<Object> deleteRole(Long id) {
        log.info("RoleServiceImpl::deleteRole Execution started");

        Optional<Role> roleById = roleRepository.findById(id);

        if(roleById.isPresent()){

            Role role = roleById.get();

            roleRepository.delete(role);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Role has been successfully deleted");
            customApiResponse.setData(role);

            log.info("RoleServiceImpl::deleteRole Execution ended");
            return customApiResponse;

        }else{

            log.error("RoleServiceImpl::deleteRole No matching permission found");
            throw new EntityNotFoundException("No matching permission found");
        }
    }

    @Override
    public CustomApiResponse<Object> getRole(Long id) {
        log.info("RoleServiceImpl::getRole Execution started");

        Optional<Role> roleById = roleRepository.findById(id);

        if(roleById.isPresent()){

            Role role = roleById.get();

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>(AppConstants.OPERATION_SUCCESSFULLY_MESSAGE);
            customApiResponse.setData(role);

            log.info("RoleServiceImpl::getRole Execution ended");
            return customApiResponse;

        }else{

            log.error("RoleServiceImpl::getRole No matching role found");
            throw new EntityNotFoundException("No matching role found");
        }
    }

    @Override
    public CustomApiResponse<Object> getRoles(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Role> pageableRoles = roleRepository.findAll(pageable);

        List<Role> roles = pageableRoles.getContent();

        List<Role> modifiedRoles = roles.stream().map((role)->{
            role.setPermissions(null);
            return role;
        }).collect(Collectors.toList());

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                pageableRoles.getTotalElements(),
                pageableRoles.getTotalPages(),
                pageableRoles.getNumber()

        );

        customApiResponse.setData(modifiedRoles);
        return customApiResponse;
    }

    @Override
    public CustomApiResponse<Object> deleteAll() {
        roleRepository.deleteAll();
        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Roles have been successfully deleted");
        return customApiResponse;
    }
}
