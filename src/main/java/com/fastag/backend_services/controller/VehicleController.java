package com.fastag.backend_services.controller;

import com.fastag.backend_services.service.VehicleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/vehicle")
    public String getVehicle(@RequestParam String chassisNumber) {
        return vehicleService.getVehicleByChassis(chassisNumber);
    }
}
