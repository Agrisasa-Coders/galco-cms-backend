package com.gapco.backend.service;

import com.gapco.backend.dto.InstitutionCreateDTO;
import com.gapco.backend.response.CustomApiResponse;


public interface InstitutionService {
    CustomApiResponse<Object> createInstitution(InstitutionCreateDTO institution);
    CustomApiResponse<Object> updateInstitution(Long institutionId, InstitutionCreateDTO institution);
    CustomApiResponse<Object> getInstitution(Long institutionId);
    CustomApiResponse<Object> getAllInstitutions(int page, int size, String sortBy, String sortDir,String language);
    CustomApiResponse<Object> deleteInstitution(Long institutionId);
    CustomApiResponse<Object> deleteAll();
}
