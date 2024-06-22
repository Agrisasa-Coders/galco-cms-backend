package com.gapco.backend.service.usermanagement;

import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.dto.usermanagement.RoleCreateDTO;
import com.gapco.backend.entity.Permission;

import java.util.List;

public interface RoleService {

    List<Permission> setPermissionList(Long permissions[]);
    CustomApiResponse<Object> updateRole(Long id, RoleCreateDTO roleCreateDTO);
    CustomApiResponse<Object> createRole(RoleCreateDTO roleCreateDTO);
    CustomApiResponse<Object> deleteRole(Long id);
    CustomApiResponse<Object> getRole(Long id);
    CustomApiResponse<Object> getRoles(int page, int size, String sortBy, String sortDir);
    CustomApiResponse<Object> deleteAll();

}
