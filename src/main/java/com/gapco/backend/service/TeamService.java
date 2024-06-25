package com.gapco.backend.service;


import com.gapco.backend.dto.TeamMemberCreateDTO;
import com.gapco.backend.dto.TeamMemberUpdateDTO;
import com.gapco.backend.entity.Team;
import com.gapco.backend.exception.EntityNotFoundException;
import com.gapco.backend.repository.TeamRepository;
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

import java.util.List;
import java.util.Optional;

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

        newTeamMember.setPhotoUrl(Helper.getUploadedPath(filePath));

        if(!(teamMemberCreateDTO.getLanguage() == null || teamMemberCreateDTO.getLanguage() == "")){
            newTeamMember.setLanguage(teamMemberCreateDTO.getLanguage());
        }

        teamRepository.save(newTeamMember);

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Team member has been successfully created");
        customApiResponse.setData(newTeamMember);

        log.info("TeamService::addTeamMember Execution ended");
        return customApiResponse;

    }



    public CustomApiResponse<Object> getAll(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Team> pageableTeamMembers = teamRepository.findAll(pageable);

        List<Team> teams = pageableTeamMembers.getContent();

        CustomApiResponse<Object> customApiResponse = new CustomApiResponse(
                AppConstants.OPERATION_SUCCESSFULLY_MESSAGE,
                pageableTeamMembers.getTotalElements(),
                pageableTeamMembers.getTotalPages(),
                pageableTeamMembers.getNumber()

        );
        customApiResponse.setData(teams);
        return customApiResponse;
    }

    public CustomApiResponse<Object> view(Long id) {

        Optional<Team> checkTeam = teamRepository.findById(id);

        if(checkTeam.isPresent()){
            Team blogTeams = checkTeam.get();
            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Record Founds");
            customApiResponse.setData(blogTeams);
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Team member not found");
        }
    }


    public CustomApiResponse<Object> update(Long id, TeamMemberUpdateDTO teamMemberUpdateDTO) {

        Optional<Team> findTeam= teamRepository.findById(id);

        if(findTeam.isPresent()){

            Team foundTeamMember = findTeam.get();
            foundTeamMember.setFullName(teamMemberUpdateDTO.getFullName());
            foundTeamMember.setPosition(teamMemberUpdateDTO.getPosition());

            if(teamMemberUpdateDTO.getPhoto() != null){

                //Update photo
                String filePath = storageService.storeFileToFileSystem(
                        teamMemberUpdateDTO.getPhoto(),
                        teamMemberUpdateDTO.getPhoto().getOriginalFilename()
                );

                foundTeamMember.setPhotoUrl(Helper.getUploadedPath(filePath));
            }

            if(!(teamMemberUpdateDTO.getLanguage() == null || teamMemberUpdateDTO.getLanguage() == "")){
                foundTeamMember.setLanguage(teamMemberUpdateDTO.getLanguage());
            }

            teamRepository.save(foundTeamMember);

            CustomApiResponse<Object> customApiResponse = new CustomApiResponse<>("Team member has been successfully updated");
            customApiResponse.setData(foundTeamMember);

            log.info("TeamService::update Execution ended");
            return customApiResponse;

        } else {
            throw new EntityNotFoundException("Team member is found");
        }

    }
}
