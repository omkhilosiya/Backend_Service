package com.fastag.backend_services.controller;

import com.fastag.backend_services.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/vehicle")
    public String getVehicle(@RequestParam String chassisNumber) {
        return vehicleService.getVehicleByChassis(chassisNumber);
    }


}
