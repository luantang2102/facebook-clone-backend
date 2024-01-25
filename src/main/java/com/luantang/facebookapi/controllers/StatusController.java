package com.luantang.facebookapi.controllers;

import com.luantang.facebookapi.dto.StatusDto;
import com.luantang.facebookapi.dto.response.StatusResponse;
import com.luantang.facebookapi.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class StatusController {

    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @PostMapping("/status/create")
    public ResponseEntity<StatusDto> createStatus(@RequestBody StatusDto statusDto) {
        return new ResponseEntity<>(statusService.createStatus(statusDto), HttpStatus.CREATED);
    }

    @GetMapping("/statuses")
    public ResponseEntity<StatusResponse> getStatuses(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(statusService.getStatuses(pageNo, pageSize), HttpStatus.OK);
    }
}
