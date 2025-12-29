package com.fastag.backend_services.controller;

import com.fastag.backend_services.Repository.ServiceDownloadRepository;
import com.fastag.backend_services.service.ServiceDownloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/downloads")
@RequiredArgsConstructor
public class ServiceDownloadController {

    @Autowired
    private ServiceDownloadService service;

    @Autowired
    private ServiceDownloadRepository repository;

    // 🔹 SAVE DOWNLOAD RECORD
    @PostMapping("/savedownload")
    public String saveDownload(
            @RequestParam String userId,
            @RequestParam String serviceName
    ) {
        service.recordDownload(userId, serviceName);
        return "Download recorded";
    }

    @GetMapping("/count/service/{serviceName}")
    public long getServiceCount(@PathVariable String serviceName) {
        return service.getServiceCount(serviceName);
    }

    @GetMapping("/count/user")
    public long getUserServiceCount(
            @RequestParam String userId,
            @RequestParam String serviceName
    ) {
        return service.getUserServiceCount(userId, serviceName);
    }

    @GetMapping("/all")
    public Object getAll() {
        return repository.findAll();
    }
}
