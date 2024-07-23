package com.gapco.backend.service;


import com.gapco.backend.dto.KnowledgeBaseCreateDTO;
import com.gapco.backend.dto.KnowledgeBaseUpdateDTO;
import com.gapco.backend.entity.KnowledgeBase;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.KnowledgeBaseRepository;
import com.gapco.backend.repository.ServiceRepository;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class KnowledgeBaseService {
    private final KnowledgeBaseRepository knowledgeBaseRepository;
    private final ServiceRepository serviceRepository;
    private final StorageService storageService;

    public CustomApiResponse<Object> addNewKnowledgeBase(KnowledgeBaseCreateDTO knowledge){
        log.info("KnowledgeBaseService::addNewKnowledgeBase Execution started");

        Optional<com.gapco.backend.entity.Service> serviceOptional = serviceRepository.findById(knowledge.getServiceId());

        if(serviceOptional.isPresent()){
            KnowledgeBase newKnowledgeBase = new KnowledgeBase();
            newKnowledgeBase.setDescription(knowledge.getDescription());
            newKnowledgeBase.setTitle(knowledge.getTitle());
            newKnowledgeBase.setSubTitle(knowledge.getSubTitle());
            newKnowledgeBase.setIntroduction(knowledge.getIntroduction());
            newKnowledgeBase.setQuote(knowledge.getQuote());

            com.gapco.backend.entity.Service service = serviceOptional.get();

            newKnowledgeBase.setService(service);

            String filePath = storageService.storeFileToFileSystem(
                    knowledge.getPhoto(),
                    knowledge.getPhoto().getOriginalFilename()
            );

            newKnowledgeBase.setPhotoUrl(Helper.getUploadedPath(filePath));

            if(!(knowledge.getLanguage() == null || Objects.equals(knowledge.getLanguage(), ""))){
                newKnowledgeBase.setLanguage(knowledge.getLanguage());
            }

            knowledgeBaseRepository.save(newKnowledgeBase);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("New KnowledgeBase has been successfully created");
            customApiResponse.setData(newKnowledgeBase);

            log.info("KnowledgeBaseService::addNewKnowledgeBase Execution ended");
            return customApiResponse;
        } else {

            throw new EntityNotFoundException("Service with specified id is not found");
        }
    }


    public CustomApiResponse<Object> getAll(int page, int size, String sortBy, String sortDir,String language) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

//        Page<KnowledgeBase> pageableKnowledgeBases = knowledgeBaseRepository.findAll(pageable);

        Page<KnowledgeBase> pageableKnowledgeBases = knowledgeBaseRepository.getAll(language,pageable);

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


    public CustomApiResponse<Object> delete(Long id) {

        Optional<KnowledgeBase> checkKnowledgeBase = knowledgeBaseRepository.findById(id);

        if(checkKnowledgeBase.isPresent()){

            KnowledgeBase knowledgeDetails = checkKnowledgeBase.get();

            knowledgeBaseRepository.delete(knowledgeDetails);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Record has been successfully deleted");
            customApiResponse.setData(knowledgeDetails);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("knowledge not found");
        }
    }


    public CustomApiResponse<Object> deleteAll() {
        knowledgeBaseRepository.deleteAll();
        CustomApiResponse<Object> customApiResponse = new CustomApiResponse("Knowledge base have been successfully deleted");
        customApiResponse.setData(new ArrayList<>());
        return customApiResponse;
    }


    public CustomApiResponse<Object> update(Long id, KnowledgeBaseUpdateDTO knowledgeBase) {

        Optional<KnowledgeBase> findKnowledgeBase = knowledgeBaseRepository.findById(id);

        if(findKnowledgeBase.isPresent()){

            Optional<com.gapco.backend.entity.Service> serviceOptional = serviceRepository.findById(knowledgeBase.getServiceId());

            if(serviceOptional.isPresent()){
                KnowledgeBase updatedKnowledge = findKnowledgeBase.get();
                updatedKnowledge.setDescription(knowledgeBase.getDescription());
                updatedKnowledge.setTitle(knowledgeBase.getTitle());
                updatedKnowledge.setSubTitle(knowledgeBase.getSubTitle());
                updatedKnowledge.setIntroduction(knowledgeBase.getIntroduction());
                updatedKnowledge.setQuote(knowledgeBase.getQuote());

                com.gapco.backend.entity.Service service = serviceOptional.get();

                updatedKnowledge.setService(service);

                if(knowledgeBase.getPhoto() != null){

                    String filePath = storageService.storeFileToFileSystem(
                            knowledgeBase.getPhoto(),
                            knowledgeBase.getPhoto().getOriginalFilename()
                    );

                    updatedKnowledge.setPhotoUrl(Helper.getUploadedPath(filePath));
                }

                if(!(knowledgeBase.getLanguage() == null || Objects.equals(knowledgeBase.getLanguage(), ""))){
                    updatedKnowledge.setLanguage(knowledgeBase.getLanguage());
                }

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
