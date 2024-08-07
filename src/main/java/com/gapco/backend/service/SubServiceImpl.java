package com.gapco.backend.service;


import com.gapco.backend.dto.SubServiceCreateDTO;
import com.gapco.backend.dto.SubServiceUpdateDTO;
import com.gapco.backend.entity.SubService;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.ServiceRepository;
import com.gapco.backend.repository.SubServiceRepository;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.util.AppConstants;
import com.gapco.backend.util.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubServiceImpl {
    private final ServiceRepository serviceRepository;
    private final StorageService storageService;
    private final SubServiceRepository subServiceRepository;

    public CustomApiResponse<Object> addSubService(SubServiceCreateDTO subServiceCreateDTO){
        log.info("SubService::addSubService Execution started");

        Optional<com.gapco.backend.entity.Service> serviceOptional = serviceRepository.findById(subServiceCreateDTO.getServiceId());

        if(serviceOptional.isPresent()){
            SubService newSubService = new SubService();
            newSubService.setDescription(subServiceCreateDTO.getDescription());
            newSubService.setName(subServiceCreateDTO.getName());

            if(subServiceCreateDTO.getPhoto() != null){

                String filePath = storageService.storeFileToFileSystem(
                        subServiceCreateDTO.getPhoto(),
                        subServiceCreateDTO.getPhoto().getOriginalFilename()
                );

                newSubService.setPhotoUrl(filePath);
            }

            if(!(subServiceCreateDTO.getLanguage() == null || Objects.equals(subServiceCreateDTO.getLanguage(), ""))){
                newSubService.setLanguage(subServiceCreateDTO.getLanguage());
            }

            newSubService.setService(serviceOptional.get());

            subServiceRepository.save(newSubService);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("New SubService has been successfully created");
            customApiResponse.setData(newSubService);

            log.info("SubService::addSubService Execution ended");
            return customApiResponse;
        } else {

            throw new EntityNotFoundException("Service with specified id is not found");
        }
    }


    public CustomApiResponse<Object> updateSubService(Long id, SubServiceUpdateDTO subServiceUpdateDTO){
        log.info("SubService::updateSubService Execution started");

        Optional<SubService> optionalSubService = subServiceRepository.findById(id);

        if(optionalSubService.isPresent()){

            Optional<com.gapco.backend.entity.Service> serviceOptional = serviceRepository.findById(subServiceUpdateDTO.getServiceId());

            if(serviceOptional.isPresent()){
                SubService existingSubService = optionalSubService.get();
                existingSubService.setDescription(subServiceUpdateDTO.getDescription());
                existingSubService.setName(subServiceUpdateDTO.getName());

                if(subServiceUpdateDTO.getPhoto() != null){

                    String filePath = storageService.storeFileToFileSystem(
                            subServiceUpdateDTO.getPhoto(),
                            subServiceUpdateDTO.getPhoto().getOriginalFilename()
                    );

                    existingSubService.setPhotoUrl(filePath);
                }

                if(!(subServiceUpdateDTO.getLanguage() == null || Objects.equals(subServiceUpdateDTO.getLanguage(), ""))){
                    existingSubService.setLanguage(subServiceUpdateDTO.getLanguage());
                }

                existingSubService.setService(serviceOptional.get());

                subServiceRepository.save(existingSubService);

                CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("SubService has been successfully updated");
                customApiResponse.setData(existingSubService);

                log.info("SubService::updateSubService Execution ended");
                return customApiResponse;
            } else {

                throw new EntityNotFoundException("Service with specified id is not found");
            }
        } else {
            throw new EntityNotFoundException("SubService with specified id is not found");
        }
    }


    public CustomApiResponse<Object> getAll(int page, int size, String sortBy, String sortDir,String language) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<SubService> pageableSubServices = subServiceRepository.getAll(language,pageable);

        List<SubService> subServices = pageableSubServices.getContent();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                pageableSubServices.getTotalElements(),
                pageableSubServices.getTotalPages(),
                pageableSubServices.getNumber()

        );
        customApiResponse.setData(subServices);
        return customApiResponse;
    }


    public CustomApiResponse<Object> view(Long id) {

        Optional<SubService> checkSubService = subServiceRepository.findById(id);

        if(checkSubService.isPresent()){

            SubService subService = checkSubService.get();
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Record Founds");
            customApiResponse.setData(subService);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Record not found");
        }
    }


    public CustomApiResponse<Object> delete(Long id) {

        Optional<SubService> checkSubService = subServiceRepository.findById(id);

        if(checkSubService.isPresent()){

            SubService subServiceDetails = checkSubService.get();

            subServiceRepository.delete(subServiceDetails);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("SubService has been successfully deleted");
            customApiResponse.setData(subServiceDetails);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Record not found");
        }
    }


    public CustomApiResponse<Object> deleteAll() {
        subServiceRepository.deleteAll();
        CustomApiResponse<Object> customApiResponse = new CustomApiResponse("SubServices has been successfully deleted");
        customApiResponse.setData(new ArrayList<>());
        return customApiResponse;
    }

}
