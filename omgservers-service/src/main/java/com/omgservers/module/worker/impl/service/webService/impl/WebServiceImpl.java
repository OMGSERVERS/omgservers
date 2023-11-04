package com.omgservers.module.worker.impl.service.webService.impl;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerResponse;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerResponse;
import com.omgservers.module.worker.impl.service.webService.WebService;
import com.omgservers.module.worker.impl.service.workerService.WorkerService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final WorkerService workerService;

    @Override
    public Uni<CreateTokenWorkerResponse> createToken(final CreateTokenWorkerRequest request) {
        return workerService.createToken(request);
    }

    @Override
    public Uni<GetVersionWorkerResponse> getVersion(final GetVersionWorkerRequest request) {
        return workerService.getVersion(request);
    }

    @Override
    public Uni<ViewRuntimeCommandsWorkerResponse> viewRuntimeCommands(final ViewRuntimeCommandsWorkerRequest request) {
        return workerService.viewRuntimeCommands(request);
    }

    @Override
    public Uni<HandleRuntimeCommandsWorkerResponse> handleRuntimeCommands(final HandleRuntimeCommandsWorkerRequest request) {
        return workerService.handleRuntimeCommands(request);
    }
}
