package com.gapco.backend.service;


import com.gapco.backend.dto.KnowledgeBaseCreateDTO;
import com.gapco.backend.entity.KnowledgeBase;
import com.gapco.backend.entity.Technology;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.KnowledgeBaseRepository;
import com.gapco.backend.repository.ServiceRepository;
import com.gapco.backend.response.CustomApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KnowledgeBaseService {
    private final KnowledgeBaseRepository knowledgeBaseRepository;
    private final ServiceRepository serviceRepository;

    public CustomApiResponse<Object> addNewKnowledgeBase(KnowledgeBaseCreateDTO knowledge){
        log.info("KnowledgeBaseService::addNewKnowledgeBase Execution started");

        Optional<com.gapco.backend.entity.Service> serviceOptional = serviceRepository.findById(knowledge.getServiceId());

        if(serviceOptional.isPresent()){
            KnowledgeBase newKnowledgeBase = new KnowledgeBase();
            newKnowledgeBase.setDescription(knowledge.getDescription());
            newKnowledgeBase.setTitle(knowledge.getTitle());

            com.gapco.backend.entity.Service service = serviceOptional.get();

            newKnowledgeBase.setService(service);

            knowledgeBaseRepository.save(newKnowledgeBase);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("New KnowledgeBase has been successfully created");
            customApiResponse.setData(newKnowledgeBase);

            log.info("KnowledgeBaseService::addNewKnowledgeBase Execution ended");
            return customApiResponse;
        } else {

            throw new EntityNotFoundException("Service with specified id is not found");
        }
    }

}
