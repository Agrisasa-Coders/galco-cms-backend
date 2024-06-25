package com.gapco.backend.service;

import com.gapco.backend.dto.InstitutionCreateDTO;
import com.gapco.backend.exception.EntityExistException;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.InstitutionRepository;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.entity.Institution;
import com.gapco.backend.util.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService{

    private final InstitutionRepository institutionRepository;

    @Override
    public CustomApiResponse<Object> createInstitution(InstitutionCreateDTO institution) {
        log.info("InstitutionServiceImpl::createInstitution Execution started");
        log.debug("InstitutionServiceImpl::createInstitution the request coming is {}",institution.toString());

        Institution newInstitution = new Institution();

        if(institution.getInstitutionId() != null && institution.getInstitutionId() == 0 ){    // institution is a super institution

            Optional<Institution> optionalInstitution = institutionRepository.findByInstitutionId(institution.getInstitutionId());

            if(optionalInstitution.isPresent()){

                log.error("InstitutionServiceImpl::createInstitution Super institution exists");
                throw new EntityExistException("Super Institution is already exists");

            } else {

                newInstitution.setInstitutionId(0);
            }

        }  else { // Institution is not a super institution

            int institutionId = institutionRepository.getMaxInstitutionId();

            newInstitution.setInstitutionId(institutionId + 100);

        }

        Optional<Institution> institutionByName = institutionRepository.findByName(institution.getName());

        if(institutionByName.isEmpty()){

            newInstitution.setName(institution.getName());
            newInstitution.setCountry(institution.getCountry());
            newInstitution.setState(1);
            newInstitution.setCity(institution.getCity());
            newInstitution.setAddress(institution.getAddress());
            newInstitution.setEmail(institution.getEmail());
            newInstitution.setContactPhone(institution.getContactPhone());
            newInstitution.setContactPhoneTwo(institution.getContactPhoneTwo());
            newInstitution.setFoundedYear(institution.getFoundedYear());
            newInstitution.setBriefHistory(institution.getBriefHistory());
            newInstitution.setVision(institution.getVision());
            newInstitution.setMission(institution.getMission());

            if(!(institution.getLanguage() == null || institution.getLanguage() == "")){
                newInstitution.setLanguage(institution.getLanguage());
            }

            institutionRepository.save(newInstitution);

        } else {
             log.error("");
             throw new EntityExistException("Institution with the same name already exists");
        }


        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Institution has been successfully created");
        customApiResponse.setData(newInstitution);

        log.info("InstitutionServiceImpl::createInstitution Execution ended");
        return customApiResponse;
    }

    @Override
    public CustomApiResponse<Object> updateInstitution(Long id, InstitutionCreateDTO updatedInstitution) {
        log.info("InstitutionServiceImpl::updateInstitution Execution started");
        log.debug("InstitutionServiceImpl::updateInstitution the request coming is {}",updatedInstitution);

        Optional<Institution> institutionById = institutionRepository.findById(id);

        if(institutionById.isPresent()){
            Institution institution = institutionById.get();

            institution.setName(updatedInstitution.getName());
            institution.setCountry(updatedInstitution.getCountry());
            institution.setCity(updatedInstitution.getCity());
            institution.setAddress(updatedInstitution.getAddress());
            institution.setEmail(updatedInstitution.getEmail());
            institution.setContactPhone(updatedInstitution.getContactPhone());
            institution.setContactPhoneTwo(updatedInstitution.getContactPhoneTwo());
            institution.setBriefHistory(updatedInstitution.getBriefHistory());
            institution.setFoundedYear(updatedInstitution.getFoundedYear());
            institution.setMission(updatedInstitution.getMission());
            institution.setVision(updatedInstitution.getVision());


            if(!(updatedInstitution.getLanguage() == null || updatedInstitution.getLanguage() == "")){
                institution.setLanguage(updatedInstitution.getLanguage());
            }

            institutionRepository.save(institution);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Institution has been successfully updated");
            customApiResponse.setData(institution);

            log.info("InstitutionServiceImpl::updateInstitution Execution ended");
            return customApiResponse;

        } else{
            log.error("InstitutionServiceImpl::updateInstitution No matching institution");
            throw new EntityNotFoundException("No matching institution found");
        }

    }

    @Override
    public CustomApiResponse<Object> getInstitution(Long institutionId) {
        log.info("InstitutionServiceImpl::getInstitution Execution started");

        Optional<Institution> institutionById = institutionRepository.findById(institutionId);

        if(institutionById.isPresent()){
            Institution institution = institutionById.get();

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse(AppConstants.OPERATION_SUCCESSFULLY_MESSAGE);
            customApiResponse.setData(institution);

            log.info("InstitutionServiceImpl::getInstitution Execution ended");
            return customApiResponse;

        }else{

            log.error("InstitutionServiceImpl::updateInstitution No matching institution found");
            throw new EntityNotFoundException("No matching institution found");
        }
    }

    @Override
    public CustomApiResponse<Object> getAllInstitutions(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Institution> pageableInstitutions = institutionRepository.findAll(pageable);

        List<Institution> institutions = pageableInstitutions.getContent();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                pageableInstitutions.getTotalElements(),
                pageableInstitutions.getTotalPages(),
                pageableInstitutions.getNumber()

        );

        customApiResponse.setData(institutions);
        return customApiResponse;
    }


    @Override
    public CustomApiResponse<Object> deleteInstitution(Long institutionId) {
        log.info("InstitutionServiceImpl::deleteInstitution Execution started");

        Optional<Institution> institutionById = institutionRepository.findById(institutionId);

        if(institutionById.isPresent()){

            Institution institution = institutionById.get();

            institutionRepository.delete(institution);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Institution has been successfully deleted");
            customApiResponse.setData(institution);

            log.info("InstitutionServiceImpl::deleteInstitution Execution ended");
            return customApiResponse;

        }else{

            log.error("InstitutionServiceImpl::updateInstitution No matching institution found");
            throw new EntityNotFoundException("No matching institution found");
        }
    }

    @Override
    public CustomApiResponse<Object> deleteAll() {
        institutionRepository.deleteAll();
        CustomApiResponse<Object> customApiResponse = new CustomApiResponse("All permissions have been successfully deleted");
        return customApiResponse;
    }
}
