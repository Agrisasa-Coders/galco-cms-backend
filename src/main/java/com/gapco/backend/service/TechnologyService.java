package com.gapco.backend.service;


import com.gapco.backend.entity.Technology;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.TechnologyRepository;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.util.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TechnologyService {
    private final TechnologyRepository technologyRepository;


    public CustomApiResponse<Object> addTechnology(Technology technology){
        log.info("TechnologyService::addTechnology Execution started");

        Technology newTechnology = new Technology();
        newTechnology.setDescription(technology.getDescription());
        newTechnology.setName(technology.getName());

        if(!(technology.getLanguage() == null || Objects.equals(technology.getLanguage(), ""))){
            newTechnology.setLanguage(technology.getLanguage());
        }

        technologyRepository.save(newTechnology);

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("New Technology has been successfully created");
        customApiResponse.setData(newTechnology);

        log.info("TechnologyService::addTechnology Execution ended");
        return customApiResponse;

    }


    public CustomApiResponse<Object> getAll(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Technology> pageableTechnology = technologyRepository.findAll(pageable);

        List<Technology> technologies = pageableTechnology.getContent();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                pageableTechnology.getTotalElements(),
                pageableTechnology.getTotalPages(),
                pageableTechnology.getNumber()

        );
        customApiResponse.setData(technologies);
        return customApiResponse;
    }


    public CustomApiResponse<Object> view(Long id) {

        Optional<Technology> checkTechnology = technologyRepository.findById(id);

        if(checkTechnology.isPresent()){

            Technology technologyDetails = checkTechnology.get();
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Record Founds");
            customApiResponse.setData(technologyDetails);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Record not found");
        }
    }


    public CustomApiResponse<Object> update(Long id, Technology technology) {

        Optional<Technology> findTechnology = technologyRepository.findById(id);

        if(findTechnology.isPresent()){

            Technology updatedTechnology = findTechnology.get();
            updatedTechnology.setDescription(technology.getDescription());
            updatedTechnology.setName(technology.getName());


            if(!(technology.getLanguage() == null || Objects.equals(technology.getLanguage(), ""))){
                updatedTechnology.setLanguage(technology.getLanguage());
            }

            technologyRepository.save(updatedTechnology);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Technology has been successfully updated");
            customApiResponse.setData(updatedTechnology);

            log.info("TechnologyService::update Execution ended");
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Record not found");
        }

    }
}
