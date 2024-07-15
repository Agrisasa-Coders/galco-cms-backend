package com.gapco.backend.controller;



import com.gapco.backend.dto.TeamMemberCreateDTO;
import com.gapco.backend.dto.TeamMemberUpdateDTO;
import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.service.TeamService;
import com.gapco.backend.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(AppConstants.BASE_URI+"/teams")
@Tag(name = "TeamController", description = "Operations for managing team members")
public class TeamController {

    private final TeamService teamService;

    @Operation(
            summary = "Create a team member"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<CustomApiResponse<Object>> AddTeamMember(@Valid @ModelAttribute TeamMemberCreateDTO teamMemberCreateDTO){
        log.info("TeamController::AddTeamMember Execution started");
        return new ResponseEntity<>(teamService.addTeamMember(teamMemberCreateDTO), HttpStatus.OK);
    }



    @Operation(
            summary = "Get all team members"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping()
    public ResponseEntity<CustomApiResponse<Object>> getAll(
            @Parameter(description = AppConstants.PAGE_NUMBER_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @Parameter(description = AppConstants.PAGE_SIZE_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @Parameter(description = AppConstants.SORT_BY_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY) String sort,
            @Parameter(description = AppConstants.SORT_DIRECTION_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String dir,
            @Parameter(description = AppConstants.LANGUAGE_DESCRIPTION) @RequestParam(defaultValue = AppConstants.DEFAULT_LANGUAGE) String lan
    ){
        log.info("TeamController::getAll Execution started");
        return new ResponseEntity<>(teamService.getAll(page,size,sort,dir,lan), HttpStatus.OK);
    }


    @Operation(
            summary = "get team member details"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @GetMapping("/{id}")
    public ResponseEntity<CustomApiResponse<Object>> getDetails(
            @Parameter(description = "Id of the team member") @PathVariable Long id
    ){
        log.info("TeamController::getDetails Execution started");
        return new ResponseEntity<>(teamService.view(id), HttpStatus.OK);
    }


    @Operation(
            summary = "update team member",
            description = "update team member"
    )
    @ApiResponse(responseCode = "200",content = { @Content(schema = @Schema(implementation = CustomApiResponse.class), mediaType = "application/json") })
    @PutMapping(value="/{id}",consumes = "multipart/form-data")
    public ResponseEntity<CustomApiResponse<Object>> update(
            @Parameter(description = "Id of the team member") @PathVariable Long id, @ModelAttribute TeamMemberUpdateDTO teamMemberUpdateDTO
    ){
        log.info("TeamController::update Execution started");
        return new ResponseEntity<>(teamService.update(id,teamMemberUpdateDTO), HttpStatus.OK);
    }
}
