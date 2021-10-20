package com.example.authentication_with_jwt.models;

import java.io.Serializable;
import java.util.ArrayList;

public class TemperatureResponse implements Serializable {
    private Boolean success;
    private ArrayList<TemperatureResponsePayload> payload;

    public TemperatureResponse(Boolean success, ArrayList<TemperatureResponsePayload> payload) {
        this.success = success;
        this.payload = payload;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArrayList<TemperatureResponsePayload> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<TemperatureResponsePayload> payload) {
        this.payload = payload;
    }
}
