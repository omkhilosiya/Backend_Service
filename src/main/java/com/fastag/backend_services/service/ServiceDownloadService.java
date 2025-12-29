package com.fastag.backend_services.service;

import com.fastag.backend_services.Repository.ServiceDownloadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import com.fastag.backend_services.Model.ServiceDownload;
@Service
@RequiredArgsConstructor
public class ServiceDownloadService {

    private final ServiceDownloadRepository repository;

    public void recordDownload(String userId, String serviceName) {
        ServiceDownload download = new ServiceDownload();
        download.setUserId(userId);
        download.setServiceName(serviceName);
        download.setDate(LocalDateTime.now());

        repository.save(download);
    }

    public long getServiceCount(String serviceName) {
        return repository.countByServiceName(serviceName);
    }

    public long getUserServiceCount(String userId, String serviceName) {
        return repository.countByUserIdAndServiceName(userId, serviceName);
    }
}