package com.gapco.backend.service;

import com.gapco.backend.dto.ServiceCreateDTO;
import com.gapco.backend.dto.ServiceUpdateDTO;
import com.gapco.backend.entity.Blog;
import com.gapco.backend.entity.KnowledgeBase;
import com.gapco.backend.entity.SubService;
import com.gapco.backend.entity.Technology;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.*;
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
public class ServiceImpl {
    private final ServiceRepository serviceRepository;
    private final TechnologyRepository technologyRepository;
    private final KnowledgeBaseRepository knowledgeBaseRepository;
    private final BlogRepository blogRepository;
    private final SubServiceRepository subServiceRepository;
    private final StorageService storageService;

    public CustomApiResponse<Object> addService(ServiceCreateDTO serviceCreateDTO){
        log.info("ServiceImpl::addService Execution started");

        com.gapco.backend.entity.Service newService = new com.gapco.backend.entity.Service();
        newService.setIntroduction(serviceCreateDTO.getIntroduction());
        newService.setName(serviceCreateDTO.getName());
        newService.setDescription(serviceCreateDTO.getDescription());

        //Upload the staff profile photo
        String filePath = storageService.storeFileToFileSystem(
                serviceCreateDTO.getPhoto(),
                serviceCreateDTO.getPhoto().getOriginalFilename()
        );

        newService.setPhotoUrl(Helper.getUploadedPath(filePath));

        if(!(serviceCreateDTO.getLanguage() == null || Objects.equals(serviceCreateDTO.getLanguage(), ""))){
            newService.setLanguage(serviceCreateDTO.getLanguage());
        }

        //Setting technologies
        if(serviceCreateDTO.getTechnologies() !=null && serviceCreateDTO.getTechnologies().length > 0){
            newService.setTechnologies(this.setTechnologyList(serviceCreateDTO.getTechnologies()));
        }

        serviceRepository.save(newService);

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("New service has been successfully created");
        customApiResponse.setData(newService);

        log.info("ServiceImpl::addService Execution ended");
        return customApiResponse;

    }



    public CustomApiResponse<Object> getAll(int page, int size, String sortBy, String sortDir,String language) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

//        Page<com.gapco.backend.entity.Service> pageableServices = serviceRepository.findAll(pageable);

        Page<com.gapco.backend.entity.Service> pageableServices = serviceRepository.getAll(language,pageable);

        List<com.gapco.backend.entity.Service> services = pageableServices.getContent();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                pageableServices.getTotalElements(),
                pageableServices.getTotalPages(),
                pageableServices.getNumber()

        );
        customApiResponse.setData(services);
        return customApiResponse;
    }


    public CustomApiResponse<Object> view(Long id) {

        Optional<com.gapco.backend.entity.Service> checkService = serviceRepository.findById(id);

        if(checkService.isPresent()){

            com.gapco.backend.entity.Service serviceDetails = checkService.get();
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Record Founds");
            customApiResponse.setData(serviceDetails);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Service not found");
        }
    }


    public CustomApiResponse<Object> delete(Long id) {

        Optional<com.gapco.backend.entity.Service> checkService = serviceRepository.findById(id);


        if(checkService.isPresent()){

            com.gapco.backend.entity.Service serviceDetails = checkService.get();

            List<KnowledgeBase> knowledgeBases = knowledgeBaseRepository.getKnowledgeBases(id);
            List<Blog> blogs = blogRepository.getServiceBlogs(id);
            List<SubService> subServices = subServiceRepository.getSubServices(id);


            knowledgeBaseRepository.deleteAll(knowledgeBases);
            blogRepository.deleteAll(blogs);
            subServiceRepository.deleteAll(subServices);

            serviceRepository.delete(serviceDetails);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Service has been successfully deleted");
            customApiResponse.setData(serviceDetails);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Service not found");
        }
    }


    public CustomApiResponse<Object> deleteAll() {
        serviceRepository.deleteAll();
        CustomApiResponse<Object> customApiResponse = new CustomApiResponse("Services have been successfully deleted");
        customApiResponse.setData(new ArrayList<>());
        return customApiResponse;
    }


    public CustomApiResponse<Object> update(Long id, ServiceUpdateDTO serviceUpdateDTO) {

        Optional<com.gapco.backend.entity.Service> findService = serviceRepository.findById(id);

        if(findService.isPresent()){

            com.gapco.backend.entity.Service foundService = findService.get();
            foundService.setIntroduction(serviceUpdateDTO.getIntroduction());
            foundService.setName(serviceUpdateDTO.getName());
            foundService.setDescription(serviceUpdateDTO.getDescription());

            if(serviceUpdateDTO.getPhoto() != null){

                //update photo
                String filePath = storageService.storeFileToFileSystem(
                        serviceUpdateDTO.getPhoto(),
                        serviceUpdateDTO.getPhoto().getOriginalFilename()
                );

                foundService.setPhotoUrl(Helper.getUploadedPath(filePath));
            }


            if(!(serviceUpdateDTO.getLanguage() == null || Objects.equals(serviceUpdateDTO.getLanguage(), ""))){
                foundService.setLanguage(serviceUpdateDTO.getLanguage());
            }

            //Setting technologies
            if(serviceUpdateDTO.getTechnologies() !=null && serviceUpdateDTO.getTechnologies().length > 0){
                foundService.setTechnologies(this.setTechnologyList(serviceUpdateDTO.getTechnologies()));
            }

            serviceRepository.save(foundService);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Service has been successfully updated");
            customApiResponse.setData(foundService);

            log.info("ServiceImpl::update Execution ended");
            return customApiResponse;
        } else {
            throw new EntityNotFoundException("Service specified is not found");
        }

    }

    private List<Technology> setTechnologyList(Long[] technologyList){
        List<Technology> technologies = new ArrayList<>();

        int i;

        if(technologyList.length > 0){

            for(i=0; i< technologyList.length; i++){
                Optional<Technology> optionalRole = technologyRepository.findById(technologyList[i]);

                if(optionalRole.isPresent())
                    technologies.add(optionalRole.get());
            }
        }

        return technologies;
    }
}
