package com.gapco.backend.service;

import com.gapco.backend.dto.TeamMemberCreateDTO;
import com.gapco.backend.entity.Team;
import com.gapco.backend.entity.Technology;
import com.gapco.backend.repository.TeamRepository;
import com.gapco.backend.repository.TechnologyRepository;
import com.gapco.backend.response.CustomApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TechnologyService {
    private final TechnologyRepository technologyRepository;


    public CustomApiResponse<Object> addTechnology(Technology technology){
        log.info("TechnologyService::addTechnology Execution started");

        Technology newTechnology = new Technology();
        newTechnology.setDescription(technology.getDescription());
        newTechnology.setName(technology.getName());

        technologyRepository.save(newTechnology);

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("New Technology has been successfully created");
        customApiResponse.setData(newTechnology);

        log.info("TechnologyService::addTechnology Execution ended");
        return customApiResponse;

    }
}
