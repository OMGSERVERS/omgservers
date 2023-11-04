package com.omgservers.service.module.worker.impl;

import com.omgservers.service.module.worker.WorkerModule;
import com.omgservers.service.module.worker.impl.service.workerService.WorkerService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WorkerModuleImpl implements WorkerModule {

    final WorkerService workerService;

    public WorkerService getWorkerService() {
        return workerService;
    }
}
