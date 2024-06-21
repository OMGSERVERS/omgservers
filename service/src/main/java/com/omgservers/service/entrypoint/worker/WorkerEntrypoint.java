package com.omgservers.service.entrypoint.worker;

import com.omgservers.service.entrypoint.worker.impl.service.workerService.WorkerService;

public interface WorkerEntrypoint {
    WorkerService getWorkerService();
}
