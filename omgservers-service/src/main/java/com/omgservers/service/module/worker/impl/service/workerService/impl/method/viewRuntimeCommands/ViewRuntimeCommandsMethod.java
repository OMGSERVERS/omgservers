package com.omgservers.service.module.worker.impl.service.workerService.impl.method.viewRuntimeCommands;

import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerResponse;
import io.smallrye.mutiny.Uni;

public interface ViewRuntimeCommandsMethod {
    Uni<ViewRuntimeCommandsWorkerResponse> viewRuntimeCommands(ViewRuntimeCommandsWorkerRequest request);
}
