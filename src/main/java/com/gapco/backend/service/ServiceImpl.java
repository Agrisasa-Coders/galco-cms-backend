package com.gapco.backend.service;

import com.gapco.backend.dto.ServiceCreateDTO;
import com.gapco.backend.dto.TeamMemberCreateDTO;
import com.gapco.backend.entity.Team;
import com.gapco.backend.entity.Technology;
import com.gapco.backend.repository.ServiceRepository;
import com.gapco.backend.repository.TechnologyRepository;
import com.gapco.backend.response.CustomApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceImpl {
    private final ServiceRepository serviceRepository;
    private final TechnologyRepository technologyRepository;
    private final StorageService storageService;

    public CustomApiResponse<Object> addService(ServiceCreateDTO serviceCreateDTO){
        log.info("ServiceImpl::addService Execution started");

        com.gapco.backend.entity.Service newService = new com.gapco.backend.entity.Service();
        newService.setName(serviceCreateDTO.getName());
        newService.setDescription(serviceCreateDTO.getDescription());

        //Upload the staff profile photo
        String filePath = storageService.storeFileToFileSystem(
                serviceCreateDTO.getPhoto(),
                serviceCreateDTO.getPhoto().getOriginalFilename()
        );

        newService.setPhotoUrl(filePath);


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
