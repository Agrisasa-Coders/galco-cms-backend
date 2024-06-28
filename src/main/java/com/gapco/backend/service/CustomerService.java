package com.gapco.backend.service;


import com.gapco.backend.dto.CustomerCreateDTO;
import com.gapco.backend.entity.Customer;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.CustomerRepository;
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

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final StorageService storageService;


    public CustomApiResponse<Object> addCustomer(CustomerCreateDTO customerCreateDTO){
        log.info("CustomerService::addCustomer Execution started");

        Customer newCustomer = new Customer();
        newCustomer.setFullName(customerCreateDTO.getFullName());
        newCustomer.setCompanyName(customerCreateDTO.getCompanyName());
        newCustomer.setComments(customerCreateDTO.getComments());
        newCustomer.setRating(customerCreateDTO.getRating());


        //Upload the customer profile photo
        if(customerCreateDTO.getPhoto() != null){

            String filePath = storageService.storeFileToFileSystem(
                    customerCreateDTO.getPhoto(),
                    customerCreateDTO.getPhoto().getOriginalFilename()
            );

            newCustomer.setPhotoUrl(Helper.getUploadedPath(filePath));
        }

        if(!(customerCreateDTO.getLanguage() == null || customerCreateDTO.getLanguage() == "")){
            newCustomer.setLanguage(customerCreateDTO.getLanguage());
        }

        customerRepository.save(newCustomer);

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Team customer has been successfully created");
        customApiResponse.setData(newCustomer);

        log.info("CustomerService::addNewCustomer Execution ended");
        return customApiResponse;

    }


    public CustomApiResponse<Object> updateCustomer(Long id,CustomerCreateDTO customerCreateDTO){
        log.info("CustomerService::updateCustomer Execution started");

        Optional<Customer> customerOptional = customerRepository.findById(id);

        if(customerOptional.isPresent()){
            Customer existingCustomer  = customerOptional.get();
            existingCustomer.setFullName(customerCreateDTO.getFullName());
            existingCustomer.setCompanyName(customerCreateDTO.getCompanyName());
            existingCustomer.setComments(customerCreateDTO.getComments());
            existingCustomer.setRating(customerCreateDTO.getRating());

            //Upload the customer profile photo
            if(customerCreateDTO.getPhoto() != null){

                String filePath = storageService.storeFileToFileSystem(
                        customerCreateDTO.getPhoto(),
                        customerCreateDTO.getPhoto().getOriginalFilename()
                );

                existingCustomer.setPhotoUrl(Helper.getUploadedPath(filePath));
            }

            if(!(customerCreateDTO.getLanguage() == null || customerCreateDTO.getLanguage() == "")){
                existingCustomer.setLanguage(customerCreateDTO.getLanguage());
            }

            customerRepository.save(existingCustomer);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("customer has been successfully updated");
            customApiResponse.setData(existingCustomer);

            log.info("CustomerService::updateCustomer Execution ended");
            return customApiResponse;
        } else {
            throw new EntityNotFoundException("Customer not found");
        }
    }

    public CustomApiResponse<Object> view(Long id) {

        Optional<Customer> customerOptional = customerRepository.findById(id);

        if(customerOptional.isPresent()){
            Customer existingCustomer = customerOptional.get();
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Record Founds");
            customApiResponse.setData(existingCustomer);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Customer not found");
        }
    }


    public CustomApiResponse<Object> getAll(int page, int size, String sortBy, String sortDir,String language) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

//      Page<Team> pageableTeamMembers = teamRepository.findAll(pageable);

        Page<Customer> pageableCustomers = customerRepository.getAll(language,pageable);

        List<Customer> customers = pageableCustomers.getContent();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                pageableCustomers.getTotalElements(),
                pageableCustomers.getTotalPages(),
                pageableCustomers.getNumber()

        );
        customApiResponse.setData(customers);
        return customApiResponse;
    }

}
