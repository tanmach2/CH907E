package com.example.authentication_with_jwt.models;

import java.io.Serializable;

public class LoginResponse implements Serializable {
//    private final String jwt;
//    private final String message;
//
//    public LoginResponse(String jwt, String message) {
//        this.jwt = jwt;
//        this.message = message;
//    }
//
//    public LoginResponse(String message) {
//        this.message = message;
//        this.jwt = "";
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public String getJwt() {
//        return jwt;
//    }


    public LoginResponse() {
    }

    public LoginResponse(Boolean success, LoginResponsePayload payload) {
        this.success = success;
        this.payload = payload;
    }

    private Boolean success;
    private LoginResponsePayload payload;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public LoginResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(LoginResponsePayload payload) {
        this.payload = payload;
    }
}
