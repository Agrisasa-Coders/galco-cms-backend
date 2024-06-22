package com.gapco.backend.service;

import com.gapco.backend.dto.ConfigurationCreateDTO;
import com.gapco.backend.exception.EntityExistException;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.exception.InternalServerErrorException;
import com.gapco.backend.repository.ConfigurationRepository;
import com.gapco.backend.repository.InstitutionRepository;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.entity.Configuration;
import com.gapco.backend.entity.Institution;
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
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConfigurationServiceImpl implements ConfigurationService{

    private final ConfigurationRepository configurationRepository;
    private final InstitutionRepository institutionRepository;

    @Override
    public CustomApiResponse<Object> setConfiguration(ConfigurationCreateDTO configuration) {

        if(configurationRepository.count() == 0){

            Configuration newConfiguration = new Configuration();
            newConfiguration.setMaxPasswordAttempts(configuration.getMaxPasswordAttempts());
            newConfiguration.setMaxPasswordExpiryMonths(configuration.getMaxPasswordExpiryMonths());
            newConfiguration.setOtpExpiryTimeInMinutes(configuration.getOtpExpiryTimeInMinutes());
            newConfiguration.setInstitutionSenderName(configuration.getInstitutionSenderName());

            if(configuration.getOtpLength() !=0)
                newConfiguration.setOtpLength(configuration.getOtpLength());

            Institution institution = institutionRepository.findById(configuration.getInstitutionId())
                            .orElseThrow(()->new EntityNotFoundException("Institution is not found"));

            newConfiguration.setInstitution(institution);

            configurationRepository.save(newConfiguration);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Configuration policy has been successfully created");
            customApiResponse.setData(newConfiguration);

            return customApiResponse;

        } else {
            throw new InternalServerErrorException("Configuration policy already exists, update the current one");
        }

    }

    @Override
    public CustomApiResponse<Object> updateConfiguration(Integer id, ConfigurationCreateDTO configuration) {

        Optional<Configuration> configurationById = configurationRepository.findById(id);

        if(configurationById.isPresent()){

            Configuration updatedConfiguration = configurationById.get();
            updatedConfiguration.setMaxPasswordExpiryMonths(configuration.getMaxPasswordExpiryMonths());
            updatedConfiguration.setMaxPasswordAttempts(configuration.getMaxPasswordAttempts());

            updatedConfiguration.setOtpExpiryTimeInMinutes(configuration.getOtpExpiryTimeInMinutes());
            updatedConfiguration.setInstitutionSenderName(configuration.getInstitutionSenderName());

            if(configuration.getOtpLength() !=0)
                updatedConfiguration.setOtpLength(configuration.getOtpLength());

            configurationRepository.save(updatedConfiguration);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Configuration policy has been successfully updated");
            customApiResponse.setData(updatedConfiguration);

            return customApiResponse;
        } else {
            throw new InternalServerErrorException("Configuration does not exists");
        }

    }
}
