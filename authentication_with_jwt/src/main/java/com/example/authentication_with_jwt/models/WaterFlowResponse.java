package com.example.authentication_with_jwt.models;

import java.io.Serializable;
import java.util.ArrayList;

public class WaterFlowResponse implements Serializable {
    private Boolean success;
    private ArrayList<WaterFlowResponsePayload> payload;

    public WaterFlowResponse(Boolean success, ArrayList<WaterFlowResponsePayload> payload) {
        this.success = success;
        this.payload = payload;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArrayList<WaterFlowResponsePayload> getPayload() {
        return payload;
    }

    public void setPayload(ArrayList<WaterFlowResponsePayload> payload) {
        this.payload = payload;
    }
}
