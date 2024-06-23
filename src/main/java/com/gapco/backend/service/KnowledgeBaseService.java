package com.gapco.backend.service;


import com.gapco.backend.dto.KnowledgeBaseCreateDTO;
import com.gapco.backend.entity.KnowledgeBase;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.KnowledgeBaseRepository;
import com.gapco.backend.repository.ServiceRepository;
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


    public CustomApiResponse<Object> getAll(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<KnowledgeBase> pageableKnowledgeBases = knowledgeBaseRepository.findAll(pageable);

        List<KnowledgeBase> knowledgeBases = pageableKnowledgeBases.getContent();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                pageableKnowledgeBases.getTotalElements(),
                pageableKnowledgeBases.getTotalPages(),
                pageableKnowledgeBases.getNumber()

        );
        customApiResponse.setData(knowledgeBases);
        return customApiResponse;
    }


    public CustomApiResponse<Object> view(Long id) {

        Optional<KnowledgeBase> checkKnowledgeBase = knowledgeBaseRepository.findById(id);

        if(checkKnowledgeBase.isPresent()){

            KnowledgeBase knowledgeDetails = checkKnowledgeBase.get();
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Record Founds");
            customApiResponse.setData(knowledgeDetails);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("knowledge not found");
        }
    }


    public CustomApiResponse<Object> update(Long id,KnowledgeBaseCreateDTO blog) {

        Optional<KnowledgeBase> findKnowledgeBase = knowledgeBaseRepository.findById(id);

        if(findKnowledgeBase.isPresent()){

            Optional<com.gapco.backend.entity.Service> serviceOptional = serviceRepository.findById(blog.getServiceId());

            if(serviceOptional.isPresent()){
                KnowledgeBase updatedKnowledge = findKnowledgeBase.get();
                updatedKnowledge.setDescription(blog.getDescription());
                updatedKnowledge.setTitle(blog.getTitle());

                com.gapco.backend.entity.Service service = serviceOptional.get();

                updatedKnowledge.setService(service);

                knowledgeBaseRepository.save(updatedKnowledge);

                CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("KnowledgeBase has been successfully updated");
                customApiResponse.setData(updatedKnowledge);

                log.info("KnowledgeBaseService::update Execution ended");
                return customApiResponse;
            } else {

                throw new EntityNotFoundException("Service with specified id is not found");
            }

        } else {
            throw new EntityNotFoundException("KnowledgeBase record is not found");
        }

    }

}
