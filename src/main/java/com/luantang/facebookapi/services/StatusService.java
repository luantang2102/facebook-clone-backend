package com.luantang.facebookapi.services;

import com.luantang.facebookapi.dto.StatusDto;
import com.luantang.facebookapi.dto.response.StatusResponse;

public interface StatusService {
    public StatusDto createStatus(StatusDto statusDto);
    StatusResponse getStatuses(int pageNo, int pageSize);
}
