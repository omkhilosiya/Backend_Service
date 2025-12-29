package com.fastag.backend_services.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "service_downloads")
public class ServiceDownload {

    @Id
    private String id;
    private String userId;
    private String serviceName;
    private LocalDateTime date;
}
