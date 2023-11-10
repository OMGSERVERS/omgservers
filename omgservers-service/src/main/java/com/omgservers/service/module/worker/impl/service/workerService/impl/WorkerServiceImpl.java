package com.omgservers.service.module.worker.impl.service.workerService.impl;

import com.omgservers.model.dto.worker.CreateTokenWorkerRequest;
import com.omgservers.model.dto.worker.CreateTokenWorkerResponse;
import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerRequest;
import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerRequest;
import com.omgservers.model.dto.worker.GetWorkerContextWorkerResponse;
import com.omgservers.service.module.worker.impl.service.workerService.WorkerService;
import com.omgservers.service.module.worker.impl.service.workerService.impl.method.createToken.CreateTokenMethod;
import com.omgservers.service.module.worker.impl.service.workerService.impl.method.doWorkerCommands.DoWorkerCommandsMethod;
import com.omgservers.service.module.worker.impl.service.workerService.impl.method.getVersion.GetVersionMethod;
import com.omgservers.service.module.worker.impl.service.workerService.impl.method.getWorkerContext.GetWorkerContextMethod;
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

    final DoWorkerCommandsMethod doWorkerCommandsMethod;
    final GetWorkerContextMethod getWorkerContextMethod;
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
    public Uni<GetWorkerContextWorkerResponse> getWorkerContext(@Valid final GetWorkerContextWorkerRequest request) {
        return getWorkerContextMethod.getWorkerContext(request);
    }

    @Override
    public Uni<DoWorkerCommandsWorkerResponse> doWorkerCommands(@Valid final DoWorkerCommandsWorkerRequest request) {
        return doWorkerCommandsMethod.doWorkerCommands(request);
    }
}
