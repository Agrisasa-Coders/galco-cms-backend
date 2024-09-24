package com.gapco.backend.service;

import com.gapco.backend.dto.TermsAndConditionDTO;
import com.gapco.backend.entity.TermsAndCondition;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.TermsAndConditionRepository;
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
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TermsAndConditionService {
    private final TermsAndConditionRepository termsAndConditionRepository;
    private final StorageService storageService;


    public CustomApiResponse<Object> addTermsAndCondition(TermsAndConditionDTO termsAndCondition) {
        log.info("TermsAndConditionService::addTermsAndCondition Execution started");

        TermsAndCondition newTermsAndCondition = new TermsAndCondition();
        newTermsAndCondition.setTerms(termsAndCondition.getTerms());


        if (!(termsAndCondition.getLanguage() == null || Objects.equals(termsAndCondition.getLanguage(), ""))) {
            newTermsAndCondition.setLanguage(termsAndCondition.getLanguage());
        }

        termsAndConditionRepository.save(newTermsAndCondition);

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("New TermsAndCondition has been successfully created");
        customApiResponse.setData(newTermsAndCondition);

        log.info("TermsAndConditionService::addTermsAndCondition Execution ended");
        return customApiResponse;

    }


    public CustomApiResponse<Object> getAll(int page, int size, String sortBy, String sortDir, String language) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

//        Page<TermsAndCondition> pageableTermsAndCondition = termsAndConditionRepository.findAll(pageable);

        Page<TermsAndCondition> pageableTermsAndCondition = termsAndConditionRepository.getAll(language, pageable);

        List<TermsAndCondition> technologies = pageableTermsAndCondition.getContent();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                pageableTermsAndCondition.getTotalElements(),
                pageableTermsAndCondition.getTotalPages(),
                pageableTermsAndCondition.getNumber()

        );
        customApiResponse.setData(technologies);
        return customApiResponse;
    }


    public CustomApiResponse<Object> view(Long id) {

        Optional<TermsAndCondition> checkTermsAndCondition = termsAndConditionRepository.findById(id);

        if (checkTermsAndCondition.isPresent()) {

            TermsAndCondition termsAndConditionDetails = checkTermsAndCondition.get();
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Record Founds");
            customApiResponse.setData(termsAndConditionDetails);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Record not found");
        }
    }


    public CustomApiResponse<Object> delete(Long id) {

        Optional<TermsAndCondition> checkTermsAndCondition = termsAndConditionRepository.findById(id);

        if (checkTermsAndCondition.isPresent()) {

            TermsAndCondition termsAndConditionDetails = checkTermsAndCondition.get();

            termsAndConditionRepository.delete(termsAndConditionDetails);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("TermsAndCondition has been successfully deleted");
            customApiResponse.setData(termsAndConditionDetails);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Record not found");
        }
    }

    public CustomApiResponse<Object> deleteAll() {
        termsAndConditionRepository.deleteAll();
        CustomApiResponse<Object> customApiResponse = new CustomApiResponse("TermsAndCondition has been successfully deleted");
        customApiResponse.setData(new ArrayList<>());
        return customApiResponse;
    }


    public CustomApiResponse<Object> update(Long id, TermsAndConditionDTO termsAndCondition) {

        Optional<TermsAndCondition> findTermsAndCondition = termsAndConditionRepository.findById(id);

        if (findTermsAndCondition.isPresent()) {

            TermsAndCondition updatedTermsAndCondition = findTermsAndCondition.get();
            updatedTermsAndCondition.setTerms(termsAndCondition.getTerms());


            if (!(termsAndCondition.getLanguage() == null || Objects.equals(termsAndCondition.getLanguage(), ""))) {
                updatedTermsAndCondition.setLanguage(termsAndCondition.getLanguage());
            }

            termsAndConditionRepository.save(updatedTermsAndCondition);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("TermsAndCondition has been successfully updated");
            customApiResponse.setData(updatedTermsAndCondition);

            log.info("TermsAndConditionService::update Execution ended");
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Record not found");
        }

    }
}

