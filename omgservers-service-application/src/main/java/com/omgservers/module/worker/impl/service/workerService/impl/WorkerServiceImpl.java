package com.omgservers.module.worker.impl.service.workerService.impl;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerResponse;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerResponse;
import com.omgservers.module.worker.impl.service.workerService.WorkerService;
import com.omgservers.module.worker.impl.service.workerService.impl.method.createToken.CreateTokenMethod;
import com.omgservers.module.worker.impl.service.workerService.impl.method.getVersion.GetVersionMethod;
import com.omgservers.module.worker.impl.service.workerService.impl.method.handleRuntimeCommands.HandleRuntimeCommandsMethod;
import com.omgservers.module.worker.impl.service.workerService.impl.method.viewRuntimeCommands.ViewRuntimeCommandsMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WorkerServiceImpl implements WorkerService {

    final HandleRuntimeCommandsMethod handleRuntimeCommandsMethod;
    final ViewRuntimeCommandsMethod viewRuntimeCommandsMethod;
    final CreateTokenMethod createTokenMethod;
    final GetVersionMethod getVersionMethod;

    @Override
    public Uni<CreateTokenWorkerResponse> createToken(final CreateTokenWorkerRequest request) {
        return createTokenMethod.createToken(request);
    }

    @Override
    public Uni<GetVersionWorkerResponse> getVersion(final GetVersionWorkerRequest request) {
        return getVersionMethod.getVersion(request);
    }

    @Override
    public Uni<ViewRuntimeCommandsWorkerResponse> viewRuntimeCommands(final ViewRuntimeCommandsWorkerRequest request) {
        return viewRuntimeCommandsMethod.viewRuntimeCommands(request);
    }

    @Override
    public Uni<HandleRuntimeCommandsWorkerResponse> handleRuntimeCommands(final HandleRuntimeCommandsWorkerRequest request) {
        return handleRuntimeCommandsMethod.handleRuntimeCommands(request);
    }
}
