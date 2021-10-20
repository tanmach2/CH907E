package com.example.authentication_with_jwt.custom_exception;

import org.springframework.http.ResponseEntity;
import utils.CustomError;


public class ApiResponse<T> {
    private Boolean success = false;
//    private int status;

    private T payload;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

//    public int getStatus() {
//        return status;
//    }

//    public void setStatus(int status) {
//        this.status = status;
//    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public ResponseEntity createResponse(CustomError error, T data) {
        ApiResponse<T> response = new ApiResponse<>();
//        response.setStatus(error.getCode());
        response.setPayload(data);

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity createResponse(CustomError error) {
        ApiResponse<T> response = new ApiResponse<>();
//        response.setStatus(error.getCode());

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity createResponse(CustomError error, int code) {
        ApiResponse<T> response = new ApiResponse<>();
//        response.setStatus(code);

        return ResponseEntity.status(code).body(response);
    }
}
