package com.omgservers.worker.module.service;

import com.omgservers.worker.module.service.service.WorkerService;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ServiceModule {

    final WorkerService workerService;

    public ServiceModule(@RestClient final WorkerService workerService) {
        this.workerService = workerService;
    }

    public WorkerService getWorkerService() {
        return workerService;
    }
}
