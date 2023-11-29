package com.luantang.facebookapi.services.impl;

import com.luantang.facebookapi.dto.StatusDto;
import com.luantang.facebookapi.dto.response.StatusResponse;
import com.luantang.facebookapi.models.Post;
import com.luantang.facebookapi.models.Status;
import com.luantang.facebookapi.models.UserEntity;
import com.luantang.facebookapi.repositories.StatusRepository;
import com.luantang.facebookapi.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    @Autowired
    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public StatusDto createStatus(StatusDto statusDto) {
        statusDto.setStatusId(UUID.randomUUID());
        statusDto.setUserName(getCurrentUser().getUsername());
        statusDto.setImageURL(getCurrentUser().getUserImage());
        statusDto.setUploadTime(new Date());
        Status status = mapToEntity(statusDto);

        Status newStatus = statusRepository.save(status);

        return mapToDto(newStatus);
    }

    @Override
    public StatusResponse getStatuses(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Status> statuses = statusRepository.findAll(pageable);
        List<Status> statusList = statuses.getContent();

        List<Status> sortedStatuses = new ArrayList<>(statusList); // Copying elements to a new list

        sortedStatuses.clear(); // Clear the list before sorting
        for (Status status : statusList) {
            boolean added = false;
            for (int i = 0; i < sortedStatuses.size(); i++) {
                if (status.getUploadTime().compareTo(sortedStatuses.get(i).getUploadTime()) > 0) {
                    sortedStatuses.add(i, status); // Insert at correct position
                    added = true;
                    break;
                }
            }
            if (!added) {
                sortedStatuses.add(status); // Add at the end if it's the latest date
            }
        }

        List<StatusDto> content = sortedStatuses.stream().map(status -> mapToDto(status)).collect(Collectors.toList());

        StatusResponse statusResponse = new StatusResponse();
        statusResponse.setContent(content);
        statusResponse.setPageNo(statuses.getNumber());
        statusResponse.setPageSize(statuses.getSize());
        statusResponse.setLast(statuses.isLast());
        statusResponse.setTotalElements(statuses.getTotalElements());
        statusResponse.setTotalPages(statuses.getTotalPages());

        return statusResponse;
    }

    private Status mapToEntity(StatusDto statusDto) {
        Status status = new Status();
        status.setStatusId(statusDto.getStatusId());
        status.setStatusImageURL(statusDto.getStatusImageURL());
        status.setUploadTime(statusDto.getUploadTime());
        status.setUserId(getCurrentUser().getUserId());
        return status;
    }

    private StatusDto mapToDto(Status status) {
        StatusDto statusDto = new StatusDto();
        statusDto.setStatusId(status.getStatusId());
        statusDto.setStatusImageURL(status.getStatusImageURL());
        statusDto.setUploadTime(status.getUploadTime());
        statusDto.setUserName(getCurrentUser().getUsername());
        statusDto.setImageURL(getCurrentUser().getUserImage());
        return statusDto;
    }

    public UserEntity getCurrentUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
