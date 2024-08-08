package com.gapco.backend.service;


import com.gapco.backend.dto.CustomerCreateDTO;
import com.gapco.backend.dto.GalleryCreateDTO;
import com.gapco.backend.entity.Customer;
import com.gapco.backend.entity.Gallery;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.GalleryRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final ServiceRepository serviceRepository;
    private final StorageService storageService;

    public CustomApiResponse<Object> addGalleries(GalleryCreateDTO galleryCreateDTO){
        log.info("GalleryService::addCustomer Execution started");

        Optional<com.gapco.backend.entity.Service> serviceOptional = serviceRepository.findById(galleryCreateDTO.getServiceId());

        if(serviceOptional.isPresent()){

            List<Gallery> galleryList = new ArrayList<>();

            for (MultipartFile photo: galleryCreateDTO.getPhotos()) {
                Gallery newGallery = new Gallery();

                com.gapco.backend.entity.Service foundService = serviceOptional.get();

                newGallery.setService(foundService);

                if(photo != null){

                    String filePath = storageService.storeFileToFileSystem(
                            photo,
                            photo.getOriginalFilename()
                    );

                    newGallery.setPhotoUrl(filePath);
                }

                //galleryList.add(newGallery);

                galleryRepository.save(newGallery);
            }

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Photos has been successfully saved");
            customApiResponse.setData(galleryList);

            log.info("CustomerService::addNewCustomer Execution ended");
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Service id is not found");
        }

    }


    public CustomApiResponse<Object> getAll(int page, int size, String sortBy, String sortDir,String language) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Gallery> pageableGalleries = galleryRepository.findAll(pageable);

        List<Gallery> galleries = pageableGalleries.getContent();

        List<Gallery> updatedGalleries = galleries.stream().map((gallery)->{
            gallery.getService().setPhotos(null);
            gallery.getService().setTechnologies(null);
            gallery.getService().setSubServices(null);
            return gallery;
        }).toList();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                pageableGalleries.getTotalElements(),
                pageableGalleries.getTotalPages(),
                pageableGalleries.getNumber()

        );
        customApiResponse.setData(updatedGalleries);
        return customApiResponse;
    }

    public CustomApiResponse<Object> deleteAll() {
        galleryRepository.deleteAll();
        CustomApiResponse<Object> customApiResponse = new CustomApiResponse("Galleries has been successfully deleted");
        customApiResponse.setData(new ArrayList<>());
        return customApiResponse;
    }

}
