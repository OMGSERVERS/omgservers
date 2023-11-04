package com.omgservers.service.module.worker;

import com.omgservers.service.module.worker.impl.service.workerService.WorkerService;

public interface WorkerModule {
    WorkerService getWorkerService();
}
