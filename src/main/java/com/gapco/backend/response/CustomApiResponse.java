package com.gapco.backend.response;

import com.gapco.backend.util.AppConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CustomApiResponse<T> {

    private int statusCode;
    private String message;
    private String description;
    private T data;
    private List<String> errors = new ArrayList<>();
    private int errorCount;

    private int currentPage;
    private long totalItems;
    private long totalPages;

    public CustomApiResponse(String message) {
        this.message = message;
        this.statusCode = AppConstants.SUCCESS_STATUS_CODE;
        this.errorCount = 0;
    }

    public CustomApiResponse(String message, long totalItems, long totalPages, int currentPage) {
        this.message = message;
        this.statusCode = AppConstants.SUCCESS_STATUS_CODE;
        this.errorCount = 0;

        setPaginationValues(totalItems,totalPages,currentPage);
    }

    public CustomApiResponse(String message, String description, int statusCode){
        this.message = message;
        this.description = description;
        this.statusCode = statusCode;
    }

    public void addErrorToList(String error){
        this.errors.add(error);
        this.setErrorCount(this.errorCount++);
    }

    public void setPaginationValues(long totalItems, long totalPages, int currentPage){
        setCurrentPage(currentPage);
        setTotalItems(totalItems);
        setTotalPages(totalPages);
    }
}

