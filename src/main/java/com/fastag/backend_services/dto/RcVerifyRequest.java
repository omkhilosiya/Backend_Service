package com.fastag.backend_services.dto;

public class RcVerifyRequest {

    private String vehicleNumber;

    public RcVerifyRequest(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
}
