package com.omgservers.worker.component.workerJobTask;

import io.smallrye.mutiny.Uni;

public interface WorkerJobTask {
    Uni<Void> executeTask();
}
