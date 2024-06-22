package com.gapco.backend.service;


import com.gapco.backend.dto.ConfigurationCreateDTO;
import com.gapco.backend.response.CustomApiResponse;

public interface ConfigurationService {
    CustomApiResponse<Object> setConfiguration(ConfigurationCreateDTO configuration);
    CustomApiResponse<Object> updateConfiguration(Integer id, ConfigurationCreateDTO configuration);
}
