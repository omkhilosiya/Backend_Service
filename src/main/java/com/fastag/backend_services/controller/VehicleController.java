package com.fastag.backend_services.controller;

import com.fastag.backend_services.dto.RcVehiclePaymentRequest;
import com.fastag.backend_services.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;


    //THIS IS BASICALLY THE REVERSE API
    @PostMapping("/rc/vehicle")
    public String getVehicle(@RequestBody RcVehiclePaymentRequest rcVehiclePaymentRequest ) {
        return vehicleService.getVehicleByChassis(rcVehiclePaymentRequest);
    }


}
