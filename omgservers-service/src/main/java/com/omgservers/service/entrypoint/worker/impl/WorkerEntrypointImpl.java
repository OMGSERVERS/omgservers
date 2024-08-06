package com.omgservers.service.entrypoint.worker.impl;

import com.omgservers.service.entrypoint.worker.impl.service.workerService.WorkerService;
import com.omgservers.service.entrypoint.worker.WorkerEntrypoint;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WorkerEntrypointImpl implements WorkerEntrypoint {

    final WorkerService workerService;

    public WorkerService getWorkerService() {
        return workerService;
    }
}
