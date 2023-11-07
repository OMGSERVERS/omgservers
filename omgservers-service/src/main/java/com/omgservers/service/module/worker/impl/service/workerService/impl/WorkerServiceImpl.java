package com.omgservers.service.module.worker.impl.service.workerService.impl;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.GetRuntimeStateWorkerRequest;
import com.omgservers.model.dto.worker.GetRuntimeStateWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerResponse;
import com.omgservers.model.dto.worker.UpdateRuntimeStateWorkerRequest;
import com.omgservers.model.dto.worker.UpdateRuntimeStateWorkerResponse;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerResponse;
import com.omgservers.service.module.worker.impl.service.workerService.WorkerService;
import com.omgservers.service.module.worker.impl.service.workerService.impl.method.createToken.CreateTokenMethod;
import com.omgservers.service.module.worker.impl.service.workerService.impl.method.getRuntimeState.GetRuntimeStateMethod;
import com.omgservers.service.module.worker.impl.service.workerService.impl.method.getVersion.GetVersionMethod;
import com.omgservers.service.module.worker.impl.service.workerService.impl.method.handleRuntimeCommands.HandleRuntimeCommandsMethod;
import com.omgservers.service.module.worker.impl.service.workerService.impl.method.updateRuntimeState.UpdateRuntimeStateMethod;
import com.omgservers.service.module.worker.impl.service.workerService.impl.method.viewRuntimeCommands.ViewRuntimeCommandsMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WorkerServiceImpl implements WorkerService {

    final HandleRuntimeCommandsMethod handleRuntimeCommandsMethod;
    final ViewRuntimeCommandsMethod viewRuntimeCommandsMethod;
    final UpdateRuntimeStateMethod updateRuntimeStateMethod;
    final GetRuntimeStateMethod getRuntimeStateMethod;
    final CreateTokenMethod createTokenMethod;
    final GetVersionMethod getVersionMethod;

    @Override
    public Uni<CreateTokenWorkerResponse> createToken(@Valid final CreateTokenWorkerRequest request) {
        return createTokenMethod.createToken(request);
    }

    @Override
    public Uni<GetVersionWorkerResponse> getVersion(@Valid final GetVersionWorkerRequest request) {
        return getVersionMethod.getVersion(request);
    }

    @Override
    public Uni<ViewRuntimeCommandsWorkerResponse> viewRuntimeCommands(
            @Valid final ViewRuntimeCommandsWorkerRequest request) {
        return viewRuntimeCommandsMethod.viewRuntimeCommands(request);
    }

    @Override
    public Uni<HandleRuntimeCommandsWorkerResponse> handleRuntimeCommands(
            @Valid final HandleRuntimeCommandsWorkerRequest request) {
        return handleRuntimeCommandsMethod.handleRuntimeCommands(request);
    }

    @Override
    public Uni<GetRuntimeStateWorkerResponse> getRuntimeState(@Valid final GetRuntimeStateWorkerRequest request) {
        return getRuntimeStateMethod.getRuntimeState(request);
    }

    @Override
    public Uni<UpdateRuntimeStateWorkerResponse> updateRuntimeState(
            @Valid final UpdateRuntimeStateWorkerRequest request) {
        return updateRuntimeStateMethod.updateRuntimeState(request);
    }
}
