package com.omgservers.service.module.worker.impl.service.workerService.impl.method.doWorkerCommands;

import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerRequest;
import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerResponse;
import io.smallrye.mutiny.Uni;

public interface DoWorkerCommandsMethod {
    Uni<DoWorkerCommandsWorkerResponse> doWorkerCommands(DoWorkerCommandsWorkerRequest request);
}
