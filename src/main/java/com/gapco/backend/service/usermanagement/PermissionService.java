package com.gapco.backend.service.usermanagement;

import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.dto.usermanagement.PermissionsCreateDTO;
import com.gapco.backend.entity.Permission;

public interface PermissionService {

    CustomApiResponse<Object> createPermission(Permission permission);
    CustomApiResponse<Object> updatePermission(Long id, Permission permission);
    CustomApiResponse<Object> createPermissions(PermissionsCreateDTO permissionsCreateDTO);
    CustomApiResponse<Object> deletePermission(Long id);
    CustomApiResponse<Object> getPermission(Long id);
    CustomApiResponse<Object> getPermissions(int page, int size, String sortBy, String sortDir);
    CustomApiResponse<Object> deleteAll();

}
