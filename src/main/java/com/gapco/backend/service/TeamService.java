package com.gapco.backend.service;

import com.gapco.backend.dto.TeamMemberCreateDTO;
import com.gapco.backend.entity.Team;
import com.gapco.backend.repository.TeamRepository;
import com.gapco.backend.response.CustomApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final StorageService storageService;

    public CustomApiResponse<Object> addTeamMember(TeamMemberCreateDTO teamMemberCreateDTO){
        log.info("TeamService::addTeamMember Execution started");

        Team newTeamMember = new Team();
        newTeamMember.setFullName(teamMemberCreateDTO.getFullName());
        newTeamMember.setPosition(teamMemberCreateDTO.getPosition());

        //Upload the staff profile photo
        String filePath = storageService.storeFileToFileSystem(
                teamMemberCreateDTO.getPhoto(),
                teamMemberCreateDTO.getPhoto().getOriginalFilename()
        );

        newTeamMember.setPhotoUrl(filePath);

        teamRepository.save(newTeamMember);

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Team member has been successfully created");
        customApiResponse.setData(newTeamMember);

        log.info("TeamService::addTeamMember Execution ended");
        return customApiResponse;

    }
}
