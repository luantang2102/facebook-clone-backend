package com.luantang.facebookapi.dto.response;

import com.luantang.facebookapi.dto.StatusDto;

import java.util.List;

public class StatusResponse {
    private List<StatusDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private  boolean last;

    public StatusResponse() {
    }

    public List<StatusDto> getContent() {
        return content;
    }

    public void setContent(List<StatusDto> content) {
        this.content = content;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
