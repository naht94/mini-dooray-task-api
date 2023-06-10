package com.nhnacademy.minidooraytaskapi.projectstatus.controller;

import com.nhnacademy.minidooraytaskapi.exception.ValidationFailedException;
import com.nhnacademy.minidooraytaskapi.projectstatus.dto.ProjectStatusDto;
import com.nhnacademy.minidooraytaskapi.projectstatus.dto.ProjectStatusNameDto;
import com.nhnacademy.minidooraytaskapi.projectstatus.dto.ProjectStautusIdDto;
import com.nhnacademy.minidooraytaskapi.projectstatus.entity.ProjectStatus;
import com.nhnacademy.minidooraytaskapi.projectstatus.service.ProjectStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/project-status")
@RequiredArgsConstructor
public class ProjectStatusController {
    private final ProjectStatusService projectStatusService;
    @GetMapping
    public ResponseEntity<List<ProjectStatusDto>> getProjectStatuses() {
        return new ResponseEntity<>(projectStatusService.getProjectStatuses(), HttpStatus.OK);
    }

    @GetMapping("/{projectStatusId}")
    public ResponseEntity<ProjectStatusNameDto> getProjectStatus(@PathVariable int projectStatusId) {
        return new ResponseEntity<>(projectStatusService.getProjectStatus(projectStatusId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjectStautusIdDto> createMember(@Valid @RequestBody ProjectStatusDto projectStatusDto,
                                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        ProjectStatus projectStatus = projectStatusService.createProjectStatus(projectStatusDto);
        ProjectStautusIdDto responseDto = new ProjectStautusIdDto(projectStatus.getProjectStatusId());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{projectStatusId}")
    public ResponseEntity<ProjectStautusIdDto> updateMember(@PathVariable int projectStatusId, @Valid @RequestBody ProjectStatusNameDto projectStatusNameDto,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        ProjectStatus projectStatus = projectStatusService.updateMember(projectStatusId, projectStatusNameDto);
        ProjectStautusIdDto responseDto = new ProjectStautusIdDto(projectStatus.getProjectStatusId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{projectStatusId}")
    public ResponseEntity<Void> deleteMember(@PathVariable int projectStatusId) {
        projectStatusService.deleteProjectStatus(projectStatusId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}