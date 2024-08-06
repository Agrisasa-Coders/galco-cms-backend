package com.gapco.backend.service;


import com.gapco.backend.dto.CareerCreateDTO;
import com.gapco.backend.dto.CareerUpdateDTO;
import com.gapco.backend.entity.Career;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.exception.InternalServerErrorException;
import com.gapco.backend.repository.CareerRepository;
import com.gapco.backend.response.CustomApiResponse;
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
public class CareerService {
    private final CareerRepository careerRepository;
    private final StorageService storageService;

    public CustomApiResponse<Object> addCareerPost(CareerCreateDTO career){
        log.info("CareerService::addCareerPost Execution started");

        Career newCareer = new Career();
        newCareer.setTitle(career.getTitle());
        newCareer.setShortDescription(career.getShortDescription());
        newCareer.setRequirements(career.getRequirements());
        newCareer.setJobPostUrl(career.getJobPostUrl());

        try{
            String photoPath = storageService.storeFileToFileSystem(
                    career.getPhoto(),
                    career.getPhoto().getOriginalFilename()
            );

            String docPath = storageService.storeFileToFileSystem(
                    career.getDocument(),
                    career.getDocument().getOriginalFilename()
            );

            newCareer.setPhotoUrl(photoPath);
            newCareer.setDocumentUrl(docPath);

        }catch(Exception e){
            throw new InternalServerErrorException("There is an error when uploading documents/photo");
        }


        if(!(career.getLanguage() == null || career.getLanguage() == "")){
            newCareer.setLanguage(career.getLanguage());
        }

        careerRepository.save(newCareer);

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("New Career post has been successfully created");
        customApiResponse.setData(newCareer);

        log.info("CareerService::addCareerPost  Execution ended");
        return customApiResponse;
    }


    public CustomApiResponse<Object> updateCareerPost(Long id, CareerUpdateDTO career){
        log.info("CareerService::updateCareerPost Execution started");

        Optional<Career> optionalCareer = careerRepository.findById(id);

        if(optionalCareer.isPresent()){

            Career updatedCareer = optionalCareer.get();
            updatedCareer.setTitle(career.getTitle());
            updatedCareer.setShortDescription(career.getShortDescription());
            updatedCareer.setRequirements(career.getRequirements());
            updatedCareer.setJobPostUrl(career.getJobPostUrl());

            try{

                if(career.getPhoto() != null){

                    String photoPath = storageService.storeFileToFileSystem(
                            career.getPhoto(),
                            career.getPhoto().getOriginalFilename()
                    );

                    updatedCareer.setPhotoUrl(photoPath);
                }

                if(career.getDocument() != null){

                    String docPath = storageService.storeFileToFileSystem(
                            career.getDocument(),
                            career.getDocument().getOriginalFilename()
                    );

                    updatedCareer.setDocumentUrl(docPath);
                }

            }catch(Exception e){
                throw new InternalServerErrorException("There is an error when uploading documents/photo");
            }


            if(!(career.getLanguage() == null || career.getLanguage() == "")){
                updatedCareer.setLanguage(career.getLanguage());
            }

            careerRepository.save(updatedCareer);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Career post has been successfully updated");
            customApiResponse.setData(updatedCareer);

            log.info("CareerService::updateCareerPost  Execution ended");
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("No career post with the specified found");
        }
    }


    public CustomApiResponse<Object> getAll(int page, int size, String sortBy, String sortDir,String language) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Career> pageableCareers = careerRepository.getAll(language,pageable);

        List<Career> careers = pageableCareers.getContent();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                pageableCareers.getTotalElements(),
                pageableCareers.getTotalPages(),
                pageableCareers.getNumber()

        );
        customApiResponse.setData(careers);
        return customApiResponse;
    }

    public CustomApiResponse<Object> view(Long id) {

        Optional<Career> checkCareer = careerRepository.findById(id);

        if(checkCareer.isPresent()){

            Career careerDetails = checkCareer.get();
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Record Founds");
            customApiResponse.setData(careerDetails);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Career Post not found");
        }
    }

    public CustomApiResponse<Object> delete(Long id) {

        Optional<Career> checkCareer = careerRepository.findById(id);

        if(checkCareer.isPresent()){

            Career careerDetails = checkCareer.get();

            careerRepository.delete(careerDetails);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("career has been successfully deleted");
            customApiResponse.setData(careerDetails);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Career Post not found");
        }
    }

    public CustomApiResponse<Object> deleteAll() {
        careerRepository.deleteAll();
        CustomApiResponse<Object> customApiResponse = new CustomApiResponse("All careers have been successfully deleted");
        customApiResponse.setData(new ArrayList<>());
        return customApiResponse;
    }
}
