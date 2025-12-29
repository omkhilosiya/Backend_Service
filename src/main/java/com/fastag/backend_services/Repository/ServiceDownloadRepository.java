package com.fastag.backend_services.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.fastag.backend_services.Model.ServiceDownload;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceDownloadRepository extends MongoRepository<ServiceDownload, String> {

    List<ServiceDownload> findByServiceName(String serviceName);
    List<ServiceDownload> findByUserId(String userId);
    long countByServiceName(String serviceName);
    long countByUserIdAndServiceName(String userId, String serviceName);

}
