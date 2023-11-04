package com.omgservers.module.worker;

import com.omgservers.module.worker.impl.service.workerService.WorkerService;

public interface WorkerModule {
    WorkerService getWorkerService();
}
