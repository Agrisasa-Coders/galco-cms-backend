package com.gapco.backend.service;


import com.gapco.backend.dto.CompanyCreateDTO;
import com.gapco.backend.entity.Company;
import com.gapco.backend.entity.Institution;
import com.gapco.backend.exception.EntityExistException;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.CompanyRepository;
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
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {
    private final StorageService storageService;
    private final CompanyRepository companyRepository;

    public CustomApiResponse<Object> createCompany(CompanyCreateDTO companyCreateDTO) {
        log.info("CompanyService::createCompany Execution started");
        log.debug("CompanyService::createCompany the request coming is {}",companyCreateDTO.toString());

        Company newCompany = new Company();

        if(companyCreateDTO.getInstitutionId() != null && companyCreateDTO.getInstitutionId() == 0 ){    // institution is a super institution

            Optional<Company> optionalCompany = companyRepository.findByInstitutionId(companyCreateDTO.getInstitutionId());

            if(optionalCompany.isPresent()){

                log.error("CompanyService::createCompany Super institution exists");
                throw new EntityExistException("Super Institution is already exists");

            } else {

                newCompany.setInstitutionId(0);
            }

        }  else { // Institution is not a super institution

            Integer institutionId = companyRepository.getMaxInstitutionId();

            if(institutionId != null){
                newCompany.setInstitutionId(institutionId + 100);
            } else {
                newCompany.setInstitutionId(100);
            }

        }

        Optional<Company> companyByName = companyRepository.findByName(companyCreateDTO.getName());

        if(companyByName.isEmpty()){

            newCompany.setName(companyCreateDTO.getName());
            newCompany.setCountry(companyCreateDTO.getCountry());
            newCompany.setState(1);
            newCompany.setCity(companyCreateDTO.getCity());
            newCompany.setAddress(companyCreateDTO.getAddress());
            newCompany.setEmail(companyCreateDTO.getEmail());
            newCompany.setContactPhone(companyCreateDTO.getContactPhone());
            newCompany.setContactPhoneTwo(companyCreateDTO.getContactPhoneTwo());
            newCompany.setFoundedYear(companyCreateDTO.getFoundedYear());
            newCompany.setBriefHistory(companyCreateDTO.getBriefHistory());
            newCompany.setVision(companyCreateDTO.getVision());
            newCompany.setMission(companyCreateDTO.getMission());

            newCompany.setWareHouses(companyCreateDTO.getWareHouses());
            newCompany.setSatisfiedClients(companyCreateDTO.getSatisfiedClients());
            newCompany.setDeliveredPackages(companyCreateDTO.getDeliveredPackages());
            newCompany.setOwnedVehicles(companyCreateDTO.getOwnedVehicles());

            newCompany.setCeoFullName(companyCreateDTO.getCeoFullName());
            newCompany.setCeoWord(companyCreateDTO.getCeoWord());

            if(companyCreateDTO.getCeoPhoto() != null){

                String filePath = storageService.storeFileToFileSystem(
                        companyCreateDTO.getCeoPhoto(),
                        companyCreateDTO.getCeoPhoto().getOriginalFilename()
                );

                newCompany.setCeoPhotoUrl(Helper.getUploadedPath(filePath));
            }

            if(!(companyCreateDTO.getLanguage() == null || companyCreateDTO.getLanguage() == "")){
                newCompany.setLanguage(companyCreateDTO.getLanguage());
            }

            companyRepository.save(newCompany);

        } else {
            log.error("");
            throw new EntityExistException("Company with the same name already exists");
        }


        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Company has been successfully created");
        customApiResponse.setData(newCompany);

        log.info("CompanyService::createCompany Execution ended");
        return customApiResponse;
    }


    public CustomApiResponse<Object> updateCompany(Long id,CompanyCreateDTO companyCreateDTO) {
        log.info("CompanyService::updateCompany Execution started");
        log.debug("CompanyService::updateCompany the request coming is {}",companyCreateDTO.toString());

        Optional<Company> companyById = companyRepository.findById(id);

        if(companyById.isPresent()){

            Company foundedCompany = companyById.get();

            foundedCompany.setName(companyCreateDTO.getName());
            foundedCompany.setCountry(companyCreateDTO.getCountry());
            foundedCompany.setState(1);
            foundedCompany.setCity(companyCreateDTO.getCity());
            foundedCompany.setAddress(companyCreateDTO.getAddress());
            foundedCompany.setEmail(companyCreateDTO.getEmail());
            foundedCompany.setContactPhone(companyCreateDTO.getContactPhone());
            foundedCompany.setContactPhoneTwo(companyCreateDTO.getContactPhoneTwo());
            foundedCompany.setFoundedYear(companyCreateDTO.getFoundedYear());
            foundedCompany.setBriefHistory(companyCreateDTO.getBriefHistory());
            foundedCompany.setVision(companyCreateDTO.getVision());
            foundedCompany.setMission(companyCreateDTO.getMission());

            if(companyCreateDTO.getWareHouses() != null){
                foundedCompany.setWareHouses(companyCreateDTO.getWareHouses());
            }

            if(companyCreateDTO.getSatisfiedClients() !=null) {
                foundedCompany.setSatisfiedClients(companyCreateDTO.getSatisfiedClients());
            }

            if(companyCreateDTO.getDeliveredPackages() != null){
                foundedCompany.setDeliveredPackages(companyCreateDTO.getDeliveredPackages());
            }

            if(companyCreateDTO.getOwnedVehicles() != null){
                foundedCompany.setOwnedVehicles(companyCreateDTO.getOwnedVehicles());
            }

            foundedCompany.setCeoFullName(companyCreateDTO.getCeoFullName());
            foundedCompany.setCeoWord(companyCreateDTO.getCeoWord());

            if(companyCreateDTO.getCeoPhoto() != null){

                String filePath = storageService.storeFileToFileSystem(
                        companyCreateDTO.getCeoPhoto(),
                        companyCreateDTO.getCeoPhoto().getOriginalFilename()
                );

                foundedCompany.setCeoPhotoUrl(Helper.getUploadedPath(filePath));
            }

            if(!(companyCreateDTO.getLanguage() == null || companyCreateDTO.getLanguage() == "")){
                foundedCompany.setLanguage(companyCreateDTO.getLanguage());
            }

            companyRepository.save(foundedCompany);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Company has been successfully updated");
            customApiResponse.setData(foundedCompany);

            log.info("CompanyService::updateCompany Execution ended");
            return customApiResponse;
        }else{
            log.error("CompanyService::updateCompany No matching institution");
            throw new EntityNotFoundException("No matching institution found");
        }
    }


    public CustomApiResponse<Object> getCompany(Long companyId) {
        log.info("CompanyService::getCompany Execution started");

        Optional<Company> companyById = companyRepository.findById(companyId);

        if(companyById.isPresent()){
            Company company = companyById.get();

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse(AppConstants.OPERATION_SUCCESSFULLY_MESSAGE);
            customApiResponse.setData(company);

            log.info("CompanyService::getCompany Execution ended");
            return customApiResponse;

        }else{

            log.error("CompanyService::getCompany No matching company found");
            throw new EntityNotFoundException("No matching company found");
        }
    }

    public CustomApiResponse<Object> getAllCompanies(int page, int size, String sortBy, String sortDir,String language) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        //Page<Institution> pageableInstitutions = institutionRepository.findAll(pageable);

        Page<Company> pageableCompanies = companyRepository.getAll(language,pageable);

        List<Company> companies = pageableCompanies.getContent();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                pageableCompanies.getTotalElements(),
                pageableCompanies.getTotalPages(),
                pageableCompanies.getNumber()

        );

        customApiResponse.setData(companies);
        return customApiResponse;
    }


    public CustomApiResponse<Object> deleteCompany(Long companyId) {
        log.info("CompanyService::deleteCompany Execution started");

        Optional<Company> companyById = companyRepository.findById(companyId);

        if(companyById.isPresent()){
            Company company = companyById.get();

            companyRepository.delete(company);
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse(AppConstants.OPERATION_SUCCESSFULLY_MESSAGE);
            customApiResponse.setData(company);

            log.info("CompanyService::deleteCompany Execution ended");
            return customApiResponse;

        }else{

            log.error("CompanyService::deleteCompany No matching company found");
            throw new EntityNotFoundException("No matching company found");
        }
    }

    public CustomApiResponse<Object> deleteAll() {
        companyRepository.deleteAll();
        CustomApiResponse<Object> customApiResponse = new CustomApiResponse("Companies have been successfully deleted");
        customApiResponse.setData(new ArrayList<>());
        return customApiResponse;
    }

}
