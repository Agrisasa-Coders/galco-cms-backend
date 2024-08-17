package com.gapco.backend.service;

import com.gapco.backend.dto.CertificationDTO;
import com.gapco.backend.dto.TechnologyCreateDTO;
import com.gapco.backend.entity.Certification;
import com.gapco.backend.entity.Technology;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.CertificationRepository;
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
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final StorageService storageService;


    public CustomApiResponse<Object> addCertification(CertificationDTO certificationDTO) {
        log.info("CertificationService::addCertification Execution started");

        Certification certification = new Certification();
        certification.setYear(certificationDTO.getYear());
        certification.setName(certificationDTO.getName());

        if (certificationDTO.getPhoto() != null) {

            String filePath = storageService.storeFileToFileSystem(certificationDTO.getPhoto(), certificationDTO.getPhoto().getOriginalFilename());

            certification.setPhotoUrl(filePath);
        }

        if (!(certificationDTO.getLanguage() == null || Objects.equals(certificationDTO.getLanguage(), ""))) {
            certification.setLanguage(certificationDTO.getLanguage());
        }

        certificationRepository.save(certification);

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("New Technology has been successfully created");
        customApiResponse.setData(certification);

        log.info("CertificationService::addCertification Execution ended");
        return customApiResponse;

    }


    public CustomApiResponse<Object> getAll(int page, int size, String sortBy, String sortDir, String language) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

//        Page<Technology> pageableTechnology = technologyRepository.findAll(pageable);

        Page<Certification> pageableTechnology = certificationRepository.getAll(language, pageable);

        List<Certification> certifications = pageableTechnology.getContent();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse(AppConstants.OPERATION_SUCCESSFULLY_MESSAGE, pageableTechnology.getTotalElements(), pageableTechnology.getTotalPages(), pageableTechnology.getNumber()

        );
        customApiResponse.setData(certifications);
        return customApiResponse;
    }


    public CustomApiResponse<Object> view(Long id) {

        Optional<Certification> certification = certificationRepository.findById(id);

        if (certification.isPresent()) {

            Certification certificationDetails = certification.get();
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Record Founds");
            customApiResponse.setData(certificationDetails);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Record not found");
        }
    }


    public CustomApiResponse<Object> delete(Long id) {

        Optional<Certification> checkCertification = certificationRepository.findById(id);

        if (checkCertification.isPresent()) {

            Certification certification = checkCertification.get();

            certificationRepository.delete(certification);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Certification has been successfully deleted");
            customApiResponse.setData(certification);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Record not found");
        }
    }

    public CustomApiResponse<Object> deleteAll() {
        certificationRepository.deleteAll();
        CustomApiResponse<Object> customApiResponse = new CustomApiResponse("Certifications has been successfully deleted");
        customApiResponse.setData(new ArrayList<>());
        return customApiResponse;
    }


    public CustomApiResponse<Object> update(Long id, CertificationDTO certificationDTO) {

        Optional<Certification> optionalCertification = certificationRepository.findById(id);

        if (optionalCertification.isPresent()) {

            Certification certification = optionalCertification.get();
            certification.setYear(certificationDTO.getYear());
            certification.setName(certificationDTO.getName());
            if (certificationDTO.getPhoto() != null) {

                String filePath = storageService.storeFileToFileSystem(certificationDTO.getPhoto(), certificationDTO.getPhoto().getOriginalFilename());

                certification.setPhotoUrl(filePath);
            }

            if (!(certificationDTO.getLanguage() == null || Objects.equals(certificationDTO.getLanguage(), ""))) {
                certification.setLanguage(certificationDTO.getLanguage());
            }

            certificationRepository.save(certification);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Certification has been successfully updated");
            customApiResponse.setData(certification);

            log.info("CertificationService::update Execution ended");
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Record not found");
        }

    }
}
