package com.gapco.backend.service;

import com.gapco.backend.dto.PrivacyPolicyDTO;
import com.gapco.backend.entity.PrivacyPolicy;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.PrivacyPolicyRepository;
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
public class PrivacyPolicyService {
    private final PrivacyPolicyRepository privacyPolicyRepository;
    private final StorageService storageService;


    public CustomApiResponse<Object> addPrivacyPolicy(PrivacyPolicyDTO privacyPolicy) {
        log.info("PrivacyPolicyService::addPrivacyPolicy Execution started");

        PrivacyPolicy newPrivacyPolicy = new PrivacyPolicy();
        newPrivacyPolicy.setPolicy(privacyPolicy.getPolicy());


        if (!(privacyPolicy.getLanguage() == null || Objects.equals(privacyPolicy.getLanguage(), ""))) {
            newPrivacyPolicy.setLanguage(privacyPolicy.getLanguage());
        }

        privacyPolicyRepository.save(newPrivacyPolicy);

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("New PrivacyPolicy has been successfully created");
        customApiResponse.setData(newPrivacyPolicy);

        log.info("PrivacyPolicyService::addPrivacyPolicy Execution ended");
        return customApiResponse;

    }


    public CustomApiResponse<Object> getAll(int page, int size, String sortBy, String sortDir, String language) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

//        Page<PrivacyPolicy> pageablePrivacyPolicy = privacyPolicyRepository.findAll(pageable);

        Page<PrivacyPolicy> pageablePrivacyPolicy = privacyPolicyRepository.getAll(language, pageable);

        List<PrivacyPolicy> technologies = pageablePrivacyPolicy.getContent();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                pageablePrivacyPolicy.getTotalElements(),
                pageablePrivacyPolicy.getTotalPages(),
                pageablePrivacyPolicy.getNumber()

        );
        customApiResponse.setData(technologies);
        return customApiResponse;
    }


    public CustomApiResponse<Object> view(Long id) {

        Optional<PrivacyPolicy> checkPrivacyPolicy = privacyPolicyRepository.findById(id);

        if (checkPrivacyPolicy.isPresent()) {

            PrivacyPolicy privacyPolicyDetails = checkPrivacyPolicy.get();
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Record Founds");
            customApiResponse.setData(privacyPolicyDetails);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Record not found");
        }
    }


    public CustomApiResponse<Object> delete(Long id) {

        Optional<PrivacyPolicy> checkPrivacyPolicy = privacyPolicyRepository.findById(id);

        if (checkPrivacyPolicy.isPresent()) {

            PrivacyPolicy privacyPolicyDetails = checkPrivacyPolicy.get();

            privacyPolicyRepository.delete(privacyPolicyDetails);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("PrivacyPolicy has been successfully deleted");
            customApiResponse.setData(privacyPolicyDetails);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Record not found");
        }
    }

    public CustomApiResponse<Object> deleteAll() {
        privacyPolicyRepository.deleteAll();
        CustomApiResponse<Object> customApiResponse = new CustomApiResponse("PrivacyPolicy has been successfully deleted");
        customApiResponse.setData(new ArrayList<>());
        return customApiResponse;
    }


    public CustomApiResponse<Object> update(Long id, PrivacyPolicyDTO privacyPolicy) {

        Optional<PrivacyPolicy> findPrivacyPolicy = privacyPolicyRepository.findById(id);

        if (findPrivacyPolicy.isPresent()) {
            PrivacyPolicy updatedPrivacyPolicy = findPrivacyPolicy.get();
            updatedPrivacyPolicy.setPolicy(privacyPolicy.getPolicy());
            if (!(privacyPolicy.getLanguage() == null || Objects.equals(privacyPolicy.getLanguage(), ""))) {
                updatedPrivacyPolicy.setLanguage(privacyPolicy.getLanguage());
            }
            privacyPolicyRepository.save(updatedPrivacyPolicy);
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("PrivacyPolicy has been successfully updated");
            customApiResponse.setData(updatedPrivacyPolicy);
            log.info("PrivacyPolicyService::update Execution ended");
            return customApiResponse;
        } else {
            throw new EntityNotFoundException("Record not found");
        }

    }
}
