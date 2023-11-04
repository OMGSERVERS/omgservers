package com.omgservers.service.module.worker.impl.service.workerService.impl.method.handleRuntimeCommands;

import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerResponse;
import io.smallrye.mutiny.Uni;

public interface HandleRuntimeCommandsMethod {
    Uni<HandleRuntimeCommandsWorkerResponse> handleRuntimeCommands(HandleRuntimeCommandsWorkerRequest request);
}
