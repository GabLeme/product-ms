package com.compasso.uol.productms.dto;

public class CustomErrorDto {

    private String status_code;

    public CustomErrorDto(String status_code, String message) {
        this.status_code = status_code;
        this.message = message;
    }

    private String message;


    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
